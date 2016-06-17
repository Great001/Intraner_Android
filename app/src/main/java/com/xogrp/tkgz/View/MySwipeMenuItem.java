package com.xogrp.tkgz.View;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;

import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.xogrp.tkgz.R;

/**
 * Created by jdeng on 5/23/2016.
 */
public class MySwipeMenuItem extends SwipeMenuItem {
    public MySwipeMenuItem(Context context ,String title,int color) {
        super(context);
        setTitle(title);
        setTitleSize(context.getResources().getInteger(R.integer.swipe_menu_title_size));
        setWidth(context.getResources().getInteger(R.integer.swipe_menu_width));
        setBackground(new ColorDrawable(color));
        setTitleColor(Color.WHITE);
    }
}
