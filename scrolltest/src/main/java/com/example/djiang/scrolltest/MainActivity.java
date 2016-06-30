package com.example.djiang.scrolltest;

import android.graphics.Rect;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ScrollView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.textview1)
    TextView textview1;
    @Bind(R.id.textview2)
    TextView textview2;
    @Bind(R.id.textview3)
    TextView textview3;
    @Bind(R.id.textview4)
    TextView textview4;
    @Bind(R.id.textview5)
    TextView textview5;
    @Bind(R.id.textview6)
    TextView textview6;
    @Bind(R.id.textview7)
    TextView textview7;
    @Bind(R.id.textview8)
    TextView textview8;
    @Bind(R.id.textview9)
    TextView textview9;
    @Bind(R.id.textview10)
    TextView textview10;
    @Bind(R.id.textview11)
    TextView textview11;
    @Bind(R.id.scrollView)
    InteractiveScrollView scrollView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        Rect scrollBounds = new Rect();
        scrollView.getHitRect(scrollBounds);
        Log.d("mainActivity", scrollBounds.toString());

        final TextView[] textViewArray = {textview1, textview2, textview3, textview4, textview5, textview6, textview7, textview8, textview9, textview10, textview11};


//        scrollView.setOnScrollViewListener(new InteractiveScrollView.OnScrollViewListener() {
//            @Override
//            public void onScrollChanged(ScrollView v, int l, int t, int oldl, int oldt) {
//                Log.d("scrollListener", l + "," + t + "," + oldl + "," + oldt);
//
//                Rect scrollBounds = new Rect();
//                scrollView.getDrawingRect(scrollBounds);
//
//                float top1 = textview1.getY();
//                float bottom1 = top1 + textview1.getHeight();
//
//                Log.d("scrollListener", scrollBounds.toString() + ":" + top1 + "," + bottom1);
//            }
//        });


        scrollView.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {

                if (event.getAction() == MotionEvent.ACTION_UP) {

                    scrollView.startScrollerTask();
                }

                return false;
            }
        });
        scrollView.setOnScrollStartedListener(new InteractiveScrollView.OnScrollStartedListener() {

            public void onScrollStarted(long lastShowTS, List<Long> lastShowGathIDs) {

                Log.d("start:duration", (System.currentTimeMillis() - lastShowTS) + " ms,lastShowIDs:" + lastShowGathIDs);

            }
        });

        scrollView.setOnPauseListener(new InteractiveScrollView.OnPauseListener() {

            public void onPaused(long lastShowTS, List<Long> lastShowGathIDs) {

                Log.d("paused:duration", (System.currentTimeMillis() - lastShowTS) + " ms,lastShowIDs:" + lastShowGathIDs);

            }
        });

        scrollView.setOnScrollStoppedListener(new InteractiveScrollView.OnScrollStoppedListener() {
            @Override
            public void onScrollStopped(List<Long> lastShowGathIDs) {
                lastShowGathIDs.clear();

                Rect scrollBounds = new Rect();
                scrollView.getDrawingRect(scrollBounds);
                Log.d("stop:", scrollBounds.toString());

                for (int i = 0; i < 11; i++) {
                    TextView textView = textViewArray[i];
                    float top = textView.getY();
                    float bottom = top + textView.getHeight();

                    //Log.d("stop:", i + "," + top + "," + bottom);
                    if (top > scrollBounds.top && bottom < scrollBounds.bottom) {
                        //Log.d("stop:", "textview" + i + "is in!");
                        lastShowGathIDs.add((long) i);
                    }

                }
            }
        });


    }

    @Override
    public void onPause() {
        super.onPause();
        scrollView.onPause();
    }

}
