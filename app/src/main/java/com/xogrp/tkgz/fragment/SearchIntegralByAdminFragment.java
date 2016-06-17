package com.xogrp.tkgz.fragment;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.xogrp.tkgz.R;

/**
 * Created by ayu on 11/27/2015 0027.
 */
public class SearchIntegralByAdminFragment extends AbstractTKGZFragment {
    private TabLayout mTabLayout;
    private ViewPager mVpSearch;
    private SearchLayoutAdapter mAdapter;
    private String[] mTitleArray = new String[]{};

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_search_integral_by_admin;
    }

    @Override
    protected void onTKGZCreateView(View rootView) {
        mTabLayout = (TabLayout) rootView.findViewById(R.id.tabLayout);
        mVpSearch = (ViewPager) rootView.findViewById(R.id.vp_search);
        mTitleArray = getResources().getStringArray(R.array.tablayout_title);
    }

    @Override
    protected void onTKGZActivityCreated() {
        mVpSearch.setOnTouchListener(this);
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);
        mAdapter = new SearchLayoutAdapter(getChildFragmentManager());
        mAdapter.setTitleArray(mTitleArray);
        mVpSearch.setAdapter(mAdapter);
        mTabLayout.setupWithViewPager(mVpSearch);
        mVpSearch.setCurrentItem(0);
        mVpSearch.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                mAdapter.getItem(position);
                mAdapter.getPageTitle(position);
//                mTabLayout.setSelected(true);
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.d("YuCL ViewPager", "ACTION_DOWN");
                break;
            case MotionEvent.ACTION_MOVE:
                Log.d("YuCL ViewPager", "ACTION_MOVE");
                break;
            case MotionEvent.ACTION_UP:
                Log.d("YuCL ViewPager", "ACTION_UP");
                break;
            case MotionEvent.ACTION_CANCEL:
                Log.d("YuCL ViewPager", "ACTION_CANCEL");
                break;
            case MotionEvent.ACTION_SCROLL:
                Log.d("YuCL ViewPager", "ACTION_SCROLL");
                break;
            default:
                break;
        }
        return false;
    }


    @Override
    public String getTransactionTag() {
        return null;
    }

    @Override
    public boolean isHideActionBar() {
        return false;
    }

    @Override
    public String getActionBarTitle() {
        return getString(R.string.actionbar_title_search_integral_page);
    }

    private static class SearchLayoutAdapter extends FragmentStatePagerAdapter {
        private String[] mArray;

        public SearchLayoutAdapter(FragmentManager fm) {
            super(fm);
            mArray = new String[]{};
        }

        public void setTitleArray(String[] array) {
            mArray = array;
        }

        @Override
        public Fragment getItem(int position) {
            return SearchIntegralFragment.newInstance(position);
        }

        @Override
        public int getCount() {
            return mArray.length;
        }


        @Override
        public CharSequence getPageTitle(int position) {
            return mArray[position];
        }
    }
}
