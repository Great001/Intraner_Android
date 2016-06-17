package com.xogrp.tkgz.Widget;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.xogrp.tkgz.fragment.AbstractTKGZFragment;

import java.util.List;

/**
 * Created by wlao on 1/19/2016.
 */
public class MonthStarsPageAdapter extends FragmentStatePagerAdapter {
    private List<AbstractTKGZFragment> mMonthStarsProfileList;

    public MonthStarsPageAdapter(FragmentManager fm, List<AbstractTKGZFragment> monthStarsProfile) {
        super(fm);
        this.mMonthStarsProfileList = monthStarsProfile;
    }

    @Override
    public Fragment getItem(int position) {
        return mMonthStarsProfileList.get(position);
    }

    @Override
    public int getCount() {
        return mMonthStarsProfileList.size();
    }
}
