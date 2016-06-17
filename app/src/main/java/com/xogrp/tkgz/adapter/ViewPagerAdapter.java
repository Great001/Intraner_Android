package com.xogrp.tkgz.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by jdeng on 5/19/2016.
 */
public class ViewPagerAdapter extends PagerAdapter {
    List<View> mViewList;

    public ViewPagerAdapter(List<View> list){
        this.mViewList=list;
    }
    @Override
    public int getCount() {
        return mViewList.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(mViewList.get(position));
        return mViewList.get(position);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }


}
