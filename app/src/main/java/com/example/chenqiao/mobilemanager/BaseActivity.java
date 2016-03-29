package com.example.chenqiao.mobilemanager;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

public abstract class BaseActivity extends ActionBarActivity {
    //系统提供给我们的用于滑动手势检测的类
    GestureDetector gestureDetector;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        gestureDetector = new GestureDetector(this,new MyGestureListener());
        super.onCreate(savedInstanceState);

    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {

        gestureDetector.onTouchEvent(event);

        return super.onTouchEvent(event);
    }

    class MyGestureListener extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onFling(
                MotionEvent e1, //包含了改fling的起点
                MotionEvent e2, //包含了改fling的终点
                float velocityX,
                float velocityY) {//快速在屏幕上滑动的时候，会call到该callback

            float startx = e1.getX();
            float starty = e1.getY();

            float endx = e2.getX();
            float endy = e2.getY();

            if (endx-startx>200){//右滑
                //显示左边的页面》
                previous(null);

            }else if(startx-endx>200){//坐滑，
                //显示右边的页面
                next(null);
            }
            return super.onFling(e1, e2, velocityX, velocityY);
        }
        @Override
        public boolean onDoubleTap(MotionEvent e) {

            Log.i("onDoubleTap", "onDoubleTap");
            return super.onDoubleTap(e);
        }
        @Override
        public void onLongPress(MotionEvent e) {
            Log.i("onLongPress","onLongPress");
            super.onLongPress(e);
        }
    }
    public abstract void next(View v);
    public abstract void previous(View v);

}
