package com.xogrp.tkgz.Widget;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.xogrp.tkgz.fragment.AbstractTKGZFragment;

import java.util.List;

/**
 * Created by wlao on 7/30/2015.
 */
public class IntegralRankPageAdapter extends FragmentStatePagerAdapter {
    private List<AbstractTKGZFragment> mFragmentList;

    public IntegralRankPageAdapter(FragmentManager fm,List<AbstractTKGZFragment> mFragmentList) {
        super(fm);
        this.mFragmentList = mFragmentList;
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }
}
