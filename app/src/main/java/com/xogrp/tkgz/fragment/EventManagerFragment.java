package com.xogrp.tkgz.fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.xogrp.tkgz.R;

/**
 * Created by ayu on 11/9/2015 0009.
 */
public class EventManagerFragment extends EventBaseFragment {

    public static EventManagerFragment newInstance(String string, boolean hasMenu) {
        EventManagerFragment fragment = new EventManagerFragment();
        Bundle args = new Bundle();
        args.putString(TITLE, string);
        args.putBoolean(HAS_MENU, hasMenu);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getMenuResourceId() {
        return R.menu.menu_activity_manage;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.menu_add_new_activity:
                getOnScreenNavigationListener().navigateToCreateNewEventPage();
                break;
            case R.id.menu_edit_activity_type:
                getOnScreenNavigationListener().navigateToEventTypePage();
                break;
            default:
                break;
        }
        return false;
    }
}
