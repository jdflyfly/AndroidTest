package com.example.djiang.layoutinflatertest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {

    LinearLayout wrapper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        wrapper = (LinearLayout) findViewById(R.id.wrapper);

        //root为null，button声明的layout将失效
        View content1 = getLayoutInflater().inflate(R.layout.content, null);
        wrapper.addView(content1);

        //root不为null，addToRoot为false，button声明的layout有效，但需要手动addView添加
        View content2 = getLayoutInflater().inflate(R.layout.content, wrapper, false);
        wrapper.addView(content2);

        //root不为null，addToRoot为true，button声明的layout有效，自动添加到wrapper中
        View content3 = getLayoutInflater().inflate(R.layout.content, wrapper, true);

        //同上面的情况
        View content4 = getLayoutInflater().inflate(R.layout.content, wrapper);


    }
}
