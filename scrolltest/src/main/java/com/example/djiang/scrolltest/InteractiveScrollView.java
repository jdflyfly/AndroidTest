package com.example.djiang.scrolltest;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ScrollView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by djiang on 6/20/16.
 */
public class InteractiveScrollView extends ScrollView {


    private OnScrollStartedListener onScrollStartedListener;
    private OnScrollStoppedListener onScrollStoppedListener;
    private OnPauseListener onPauseListener;

    private boolean isScrolling = false;


    private Runnable scrollerTask;
    private int initialPosition;
    private int newCheck = 50;
    private long lastShowTS = 0;
    private List<Long> lastShowGathIDs = new ArrayList<>();

    {
        scrollerTask = new Runnable() {

            public void run() {

                int newPosition = getScrollY();
                if (initialPosition - newPosition == 0) {//has stopped

                    isScrolling = false;

                    if (onScrollStoppedListener != null) {
                        onScrollStoppedListener.onScrollStopped(lastShowGathIDs);
                    }

                    lastShowTS = System.currentTimeMillis();


                } else {

//                    //从停止启动
//                    if (isScrolling == false) {
//                        if (onScrollStartedListener != null) {
//                            onScrollStartedListener.onScrollStarted(lastShowTS, lastShowGathIDs);
//                        }
//                    }
//
//                    isScrolling = true;

                    startScrollerTask();
                }
            }
        };
    }


    public interface OnScrollStartedListener {
        /**
         * 滑动开始时，回调返回上次停留的时间和展示的活动列表
         * <p/>
         * 第一次滑动结束lastShowTS返回0
         * lastShowGathIDs调用者需要调用者手动设置，下次回调的时候返给调用者
         */
        void onScrollStarted(long lastShowTS, List<Long> lastShowGathIDs);
    }


    public interface OnPauseListener {
        /**
         * scrollview所在页面pause时，回调返回上次停留的时间和展示的活动列表
         * <p/>
         * 第一次滑动结束lastShowTS返回0
         * lastShowGathIDs调用者需要调用者手动设置，下次回调的时候返给调用者
         */
        void onPaused(long lastShowTS, List<Long> lastShowGathIDs);
    }


    public interface OnScrollStoppedListener {
        /**
         * 滑动停止时时回调
         * <p/>
         * lastShowGathIDs暴露给调用者赋值
         */
        void onScrollStopped(List<Long> lastShowGathIDs);
    }


    public InteractiveScrollView(Context context, AttributeSet attrs,
                                 int defStyle) {
        super(context, attrs, defStyle);
    }

    public InteractiveScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public InteractiveScrollView(Context context) {
        super(context);
    }


    public void setOnScrollStoppedListener(OnScrollStoppedListener listener) {
        onScrollStoppedListener = listener;
    }

    public void setOnScrollStartedListener(OnScrollStartedListener listener) {
        onScrollStartedListener = listener;
    }

    public void setOnPauseListener(OnPauseListener listener) {
        onPauseListener = listener;
    }

    public void onPause() {
        if (onPauseListener != null) {
            onPauseListener.onPaused(lastShowTS, lastShowGathIDs);
        }
    }


    public void startScrollerTask() {

        if (isScrolling == false) {
            if (onScrollStartedListener != null) {
                onScrollStartedListener.onScrollStarted(lastShowTS, lastShowGathIDs);
            }
        }
        isScrolling = true;

        initialPosition = getScrollY();
        InteractiveScrollView.this.postDelayed(scrollerTask, newCheck);

    }


}
