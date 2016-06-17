package com.xogrp.tkgz.Widget;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.xogrp.tkgz.fragment.AbstractTKGZFragment;

import java.util.List;

/**
 * Created by wlao on 12/24/2015.
 */
public class PhotoWallViewPagerAdapter  extends FragmentStatePagerAdapter {
    private List<AbstractTKGZFragment> mFragmentList;
    private boolean mIsAfterNotifyDataSetChanged;

    public PhotoWallViewPagerAdapter(FragmentManager fm, List<AbstractTKGZFragment> mFragmentList) {
        super(fm);
        this.mFragmentList = mFragmentList;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
        mIsAfterNotifyDataSetChanged = true;
    }

    public boolean getIsAfterNotifyDataSetChanged(){
        return mIsAfterNotifyDataSetChanged;
    }

    public void setAbleToDoNotifyDataSetChanged(boolean isAble){
        mIsAfterNotifyDataSetChanged = isAble;
    }

}
