package com.xogrp.tkgz.fragment;

import android.os.Bundle;

/**
 * Created by ayu on 11/11/2015 0011.
 */
public class RecentEventFragment extends EventBaseFragment {

    public static RecentEventFragment newInstance( boolean hasMenu) {

        Bundle args = new Bundle();
        args.putString(TITLE, "");
        args.putBoolean(HAS_MENU, hasMenu);
        RecentEventFragment fragment = new RecentEventFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected boolean hasTKGZOptionsMenu() {
        return false;
    }

}
