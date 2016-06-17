package com.xogrp.tkgz.fragment;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.MenuItem;
import android.view.View;

import com.xogrp.tkgz.R;

/**
 * Created by ayu on 9/16/2015.
 */
public class MessageManageFragment extends AbstractTKGZFragment {

    private ViewPager mVpContainer;
    private TabLayout mTabLayout;

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_message_manage;
    }

    @Override
    protected void onTKGZCreateView(View rootView) {
        mTabLayout = (TabLayout) rootView.findViewById(R.id.tabLayout);
        mVpContainer = (ViewPager) rootView.findViewById(R.id.vp_container);

    }

    @Override
    protected void onTKGZActivityCreated() {
        String[] mArray = getResources().getStringArray(R.array.message_variety);
        MessageManageAdapter mAdapter = new MessageManageAdapter(getChildFragmentManager());
        mAdapter.setArray(mArray);
        mVpContainer.setAdapter(mAdapter);
        mTabLayout.setupWithViewPager(mVpContainer);
        mVpContainer.setCurrentItem(0);
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
        return getString(R.string.actionbar_title_message_manage);
    }

    @Override
    protected int getMenuResourceId() {
        return R.menu.menu_message_manage;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.menu_add_new_message:
                getOnScreenNavigationListener().navigateToMessageEditPage(MessageEditFragment.newInstance(null));
                break;
            default:
                break;
        }
        return true;
    }

    private static class MessageManageAdapter extends FragmentStatePagerAdapter {

        private String[] mMessagesArray;

        public MessageManageAdapter(FragmentManager fm) {
            super(fm);
            mMessagesArray = new String[]{};
        }

        public void setArray(String[] array) {
            mMessagesArray = array;
        }

        @Override
        public Fragment getItem(int position) {
            return MessageListFragment.newInstance(position);
        }

        @Override
        public int getCount() {
            return mMessagesArray.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mMessagesArray[position];
        }
    }
}
