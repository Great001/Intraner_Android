package com.xogrp.tkgz.fragment;


import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.xogrp.tkgz.R;
import com.xogrp.tkgz.model.RankingItemProfile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IntegralRankFragment extends AbstractTKGZFragment {

    private TabLayout mTlNavigation;
    private ViewPager mVpIntegralRank;
    private String[] mTabs;
    private IntegralRankAdapter mAdapter;

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_integral_rank;
    }

    @Override
    protected void onTKGZCreateView(View rootView) {
        mTlNavigation = (TabLayout) rootView.findViewById(R.id.tl_navigation);
        mVpIntegralRank = (ViewPager) rootView.findViewById(R.id.vp_display_item);

    }

    @Override
    protected void onTKGZActivityCreated() {
        mTabs = getResources().getStringArray(R.array.tab_layout_title);
        mTlNavigation.setTabMode(TabLayout.MODE_FIXED);
        mAdapter = new IntegralRankAdapter(getChildFragmentManager());
        mAdapter.setArray(mTabs);
        mVpIntegralRank.setAdapter(mAdapter);
        mTlNavigation.setupWithViewPager(mVpIntegralRank);
        mVpIntegralRank.setCurrentItem(0);
    }


    @Override
    public String getTransactionTag() {
        return "integral_bank";
    }

    @Override
    public boolean isHideActionBar() {
        return false;
    }

    @Override
    public String getActionBarTitle() {
        return getString(R.string.actionbar_title_integral_rank_page);
    }

    private static class IntegralRankAdapter extends FragmentStatePagerAdapter implements IntegralRankViewFragment.OnRankingDataListener {

        private String[] mData;
        private Map<String, List<RankingItemProfile>> mRankingMap = new HashMap<>();

        public IntegralRankAdapter(FragmentManager fm) {
            super(fm);
            mData = new String[]{};
        }

        @Override
        public List<RankingItemProfile> getRankingListByRankType(String type) {
            List<RankingItemProfile> list = new ArrayList<>();
            if (mRankingMap.containsKey(type)) {
                list = mRankingMap.get(type);
            }
            return list;
        }

        @Override
        public void setRankingListByRankType(String type, List<RankingItemProfile> list) {
            if (mRankingMap.containsKey(type)) {
                mRankingMap.get(type).clear();
            }
            mRankingMap.put(type, list);
        }

        @Override
        public Fragment getItem(int position) {
            IntegralRankViewFragment fragment = IntegralRankViewFragment.newInstance(position);
            fragment.setListener(this);
            return fragment;
        }

        protected void setArray(String[] array) {
            this.mData = array;
        }

        @Override
        public int getCount() {
            return this.mData.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return this.mData[position];
        }
    }
}
