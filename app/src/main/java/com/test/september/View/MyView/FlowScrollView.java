package com.test.september.View.MyView;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.BounceInterpolator;
import android.widget.ScrollView;

public class FlowScrollView extends ScrollView {


    private View contentView;
    private final float scalCount = 0.5f;//阻尼系数
    private float downY;//点击时的y点
    private ObjectAnimator objectAnimator;//动画
    private float distanceY;//移动距离
    private boolean isMoveing = false;//动画是正在进行

//    private int scrollHeight = 0;//当内容不足以全屏时，内容随手指可滑动距离



    public FlowScrollView(Context context) {
        super(context);
    }

    public FlowScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FlowScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public FlowScrollView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onFinishInflate() {//视图加载完成
        super.onFinishInflate();
        if (getChildCount() > 0) {
            contentView = getChildAt(0);
//            scrollHeight = contentView.getHeight();

        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        return super.onInterceptTouchEvent(ev);
    }


    @Override
    public boolean onTouchEvent(MotionEvent ev) {

        if (isMoveing) {
            return false;
        }

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downY = ev.getY();
                break;

            case MotionEvent.ACTION_MOVE:
                float deltY = ev.getY() - downY;
                if (Math.abs(deltY) > 10 &&(getScrollY() == 0 || (getScrollY()+getHeight())==contentView.getHeight())) {
                    contentView.setY(contentView.getY() + deltY * scalCount);
                    distanceY += deltY * scalCount;
                }
                downY = ev.getY();
                break;
            case MotionEvent.ACTION_UP:

                if (Math.abs(distanceY) > 0) {
                    objectAnimator = ObjectAnimator.ofFloat(contentView, "translationY", distanceY, -(float) contentView.getTop());
                    objectAnimator.setInterpolator(new BounceInterpolator());
                    objectAnimator.addListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationStart(Animator animation) {
                            super.onAnimationStart(animation);
                            isMoveing = true;
                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            distanceY = 0;
                            isMoveing = false;
                        }
                    });
                    objectAnimator.setDuration(500);
                    objectAnimator.start();
                }

                break;
            default:
                break;
        }

        return super.onTouchEvent(ev);
    }


    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
    }
}
