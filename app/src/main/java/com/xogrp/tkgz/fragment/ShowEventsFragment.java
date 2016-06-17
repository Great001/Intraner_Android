package com.xogrp.tkgz.fragment;

import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.xogrp.tkgz.R;

/**
 * Created by ayu on 1/28/2016 0028.
 */
public class ShowEventsFragment extends AbstractTKGZFragment {
    private int mMenuId;

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_show_events;
    }

    @Override
    protected void onTKGZCreateView(View rootView) {

    }

    @Override
    protected void onTKGZActivityCreated() {
        replaceFragment(new EventCalendarViewFragment());
        mMenuId = R.menu.menu_show_recent_event;
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
        return getString(R.string.actionbar_title_event);
    }

    @Override
    protected int getMenuResourceId() {
        return mMenuId;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        super.onCreateOptionsMenu(menu, menuInflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.menu_show_recent_event:
                mMenuId = R.menu.menu_show_event_calendar;
                getChildFragmentManager().popBackStack();
                replaceFragment(RecentEventFragment.newInstance(false));
                break;
            case R.id.menu_show_event_calendar:
                mMenuId = R.menu.menu_show_recent_event;
                getChildFragmentManager().popBackStack();
                replaceFragment(new EventCalendarViewFragment());
            default:
                break;
        }
        super.onOptionsItemSelected(menuItem);
        getActivity().invalidateOptionsMenu();
        return true;
    }

    private void replaceFragment(AbstractTKGZFragment fragment) {
        FragmentTransaction ft = getChildFragmentManager().beginTransaction();
        ft.replace(R.id.fl_show_event, fragment, fragment.getTransactionTag());
        ft.addToBackStack(fragment.getTransactionTag());
        ft.commitAllowingStateLoss();
    }
}
