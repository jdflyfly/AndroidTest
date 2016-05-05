package com.example.djiang.viewmeasurelayoutdrawtest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewTreeObserver;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView topView;
    TextView bottomView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        topView = (TextView) findViewById(R.id.top);
        bottomView = (TextView) findViewById(R.id.bottom);

        int width = topView.getMeasuredWidth();
        int height = topView.getMeasuredHeight();

        topView.post(new Runnable() {
            @Override
            public void run() {
                int widthPost = topView.getMeasuredWidth();
                int heightPost = topView.getMeasuredHeight();
            }
        });

        final ViewTreeObserver observer = topView.getViewTreeObserver();
        observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                topView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                int widthGlobalLayout = topView.getMeasuredWidth();
                int heightGlobalLayout = topView.getMeasuredHeight();
            }
        });


    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            int widthWindow = topView.getMeasuredWidth();
            int heightWindow = topView.getMeasuredHeight();
        }
    }
}
