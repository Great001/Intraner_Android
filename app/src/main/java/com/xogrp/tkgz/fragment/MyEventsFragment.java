package com.xogrp.tkgz.fragment;

import android.app.Activity;

import com.xogrp.tkgz.R;
import com.xogrp.tkgz.TKGZApplication;
import com.xogrp.tkgz.model.EventProfile;
import com.xogrp.tkgz.provider.MyEventsListProvider;
import com.xogrp.tkgz.spi.MyEventsListApiCallBack;

import java.util.ArrayList;

/**
 * Created by wlao on 9/14/2015.
 */

public class MyEventsFragment extends EventListFragment {

    @Override
    protected void getEventsList() {

        initLoader(MyEventsListProvider.getMyEventsListProvider(TKGZApplication.getInstance().getUserProfile(), this, new MyEventsListApiCallBack.OnMyEventsApiListener() {
            @Override
            public void onGetEventsList(ArrayList<EventProfile> list) {
                if (list != null) {
                    mEventList = list;
                    mAdapter.setEventList(mEventList);
                    final Activity activity = getActivity();
                    if (activity != null) {
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mAdapter.notifyDataSetChanged();
                                mRefreshLayout.setRefreshing(false);
                            }
                        });
                    }
                }
            }
        }));
    }

    @Override
    protected boolean hasTKGZOptionsMenu() {
        return true;
    }

    @Override
    public String getActionBarTitle() {
        return getString(R.string.actionbar_title_my_training_page);
    }

    @Override
    protected boolean isHasMenu() {
        return true;
    }
}