package com.xogrp.tkgz.fragment;

import android.app.Activity;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.xogrp.tkgz.R;
import com.xogrp.tkgz.TKGZApplication;
import com.xogrp.tkgz.model.EventProfile;
import com.xogrp.tkgz.model.EventTypeProfile;
import com.xogrp.tkgz.provider.EventTypeProvider;
import com.xogrp.tkgz.spi.EventTypeApiCallBack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ayu on 11/9/2015 0009.
 */
public class EventBaseFragment extends AbstractTKGZFragment {
    protected static final String TITLE = "title";
    protected static final String HAS_MENU = "has_menu";
    private static boolean sHasMenu;
    private ViewPager mVpContainer;
    private ArrayList<EventTypeProfile> mEventTypeList;
    private TabLayout mTlNavigation;
    private SimpleFragmentAdapter mAdapter;
    private String mTitle;

    private void setTitleAndMenu() {
        if (getArguments() != null) {
            mTitle = getArguments().getString(TITLE);
            sHasMenu = getArguments().getBoolean(HAS_MENU);
        }
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_activity_total;
    }

    @Override
    protected void onTKGZCreateView(View rootView) {
        setTitleAndMenu();
        mEventTypeList = TKGZApplication.getEventTypeList();
        mTlNavigation = (TabLayout) rootView.findViewById(R.id.tl_navigation);
        mVpContainer = (ViewPager) rootView.findViewById(R.id.vp_display_item);
    }

    @Override
    protected void onTKGZActivityCreated() {
        mAdapter = new SimpleFragmentAdapter(getChildFragmentManager());
        mAdapter.setEventTypes(mEventTypeList);
        mVpContainer.setAdapter(mAdapter);
        mTlNavigation.setupWithViewPager(mVpContainer);
        mVpContainer.setCurrentItem(0);

        if (mEventTypeList.size() == 0) {
            getEventTypeList();
        } else {
            mTlNavigation.setTabMode(mEventTypeList.size() > 4 ? TabLayout.MODE_SCROLLABLE : TabLayout.MODE_FIXED);
        }
    }

    private void getEventTypeList() {
        initLoader(EventTypeProvider.getEventTypeProvider(this, TKGZApplication.getInstance().getUserProfile(), new EventTypeApiCallBack.OnEventTypeProviderApiListener() {
            @Override
            public void onGetActivityType(ArrayList<EventTypeProfile> list) {
                if (list != null) {
                    mEventTypeList = list;
                    TKGZApplication.setEventTypeList(mEventTypeList);
                    mAdapter.setEventTypes(mEventTypeList);

                    final Activity activity = getActivity();
                    if (activity != null) {
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mTlNavigation.setTabMode(mEventTypeList.size() > 4 ? TabLayout.MODE_SCROLLABLE : TabLayout.MODE_FIXED);
                                mAdapter.notifyDataSetChanged();
                                mTlNavigation.setupWithViewPager(mVpContainer);
                            }
                        });
                    }
                }
            }
        }));
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
        return mTitle;
    }

    private static class SimpleFragmentAdapter extends FragmentStatePagerAdapter implements EventListFragment.onSaveEventListListener {

        private ArrayList<EventTypeProfile> mEventTypes;
        private Map<String, List<EventProfile>> mEventMap;

        public SimpleFragmentAdapter(FragmentManager fm) {
            super(fm);
            mEventTypes = new ArrayList<>();
            mEventMap = new HashMap<>();
        }

        @Override
        public Fragment getItem(int position) {
            EventListFragment fragment = EventListFragment.newInstance(sHasMenu, mEventTypes.get(position).getId());
            fragment.setListener(this);
            return fragment;
        }

        public void setEventTypes(ArrayList<EventTypeProfile> eventTypes) {
            mEventTypes = eventTypes;
        }

        @Override
        public int getCount() {
            return mEventTypes.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mEventTypes.get(position).getName();
        }

        @Override
        public void setEventsByType(String type, List<EventProfile> list) {
            if (mEventMap.containsKey(type)) {
                mEventMap.get(type).clear();
            }
            mEventMap.put(type, list);
        }

        @Override
        public List<EventProfile> getEventsByType(String type) {
            List<EventProfile> list = new ArrayList<>();
            if (mEventMap.containsKey(type)) {
                list = mEventMap.get(type);
            }
            return list;
        }
    }
}
