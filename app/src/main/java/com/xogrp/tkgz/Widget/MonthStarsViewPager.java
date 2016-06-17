package com.xogrp.tkgz.Widget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by wlao on 1/19/2016.
 */
public class MonthStarsViewPager extends ViewPager {
    private boolean mIsCanScroll = true;

    public MonthStarsViewPager(Context context) {
        super(context);
    }

    public MonthStarsViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setIsCanScroll(boolean isCanScroll){
        this.mIsCanScroll = isCanScroll;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (mIsCanScroll){
            return super.onTouchEvent(ev);
        }
        return false;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (mIsCanScroll) {
            return super.onInterceptTouchEvent(ev);
        }
        return false;
    }

    @Override
    public void setCurrentItem(int item, boolean smoothScroll) {
        super.setCurrentItem(item, smoothScroll);
    }

    @Override
    public void setCurrentItem(int item) {
        super.setCurrentItem(item, false);
    }

}
