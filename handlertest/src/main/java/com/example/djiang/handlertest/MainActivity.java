package com.example.djiang.handlertest;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //MyHandler在主线程中创建，所以关联的是主线程的Looper
        new Thread(new Task(new MyHanlder("handler 1"))).start();

        new Thread(new Task(new MyHanlder("handler 2"))).start();

    }


    class MyHanlder extends Handler {
        String label = "";

        public MyHanlder(String label) {
            this.label = label;
        }

        @Override
        public void handleMessage(Message msg) {
            String res = msg.getData().getString("message");
            String output = label + ":" + res + " of " + Thread.currentThread().toString();
            Log.v("handler test", output);
        }
    }


    class Task implements Runnable {
        Handler handler;

        public Task(Handler handler) {
            super();
            this.handler = handler;
        }

        @Override
        public void run() {
            try {
                Thread.sleep(3000);
                // 任务完成后通知activity更新UI
                Message msg = prepareMessage("task completed!");
                // message将被添加到主线程的MQ中
                handler.sendMessage(msg);
            } catch (InterruptedException e) {
            }

        }

        private Message prepareMessage(String str) {
            Message result = handler.obtainMessage();
            Bundle data = new Bundle();
            data.putString("message", str);
            result.setData(data);
            return result;
        }
    }

}
