package com.xogrp.tkgz.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.ListView;

import com.xogrp.tkgz.R;
import com.xogrp.tkgz.TKGZApplication;
import com.xogrp.tkgz.adapter.EventListBaseAdapter;
import com.xogrp.tkgz.model.EventProfile;
import com.xogrp.tkgz.provider.EventProvider;
import com.xogrp.tkgz.spi.EventListByTypeApiCallBack;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ayu on 11/9/2015 0009.
 */
public class EventListFragment extends AbstractTKGZFragment implements EventDetailFragment.OnUpdateEventStatusListener {

    private static final String HAS_MENU = "has_menu";
    private static final String EVENT_TYPE_ID = "event_type_id";
    private boolean mHasMenu;
    private String mEventTypeId;
    private ListView mLvEventList;
    protected EventListBaseAdapter mAdapter;
    protected List<EventProfile> mEventList = new ArrayList<>();
    protected SwipeRefreshLayout mRefreshLayout;
    private onSaveEventListListener mEventListener;

    private SwipeRefreshLayout.OnRefreshListener mListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            getEventsList();
        }
    };

    public static EventListFragment newInstance(boolean hasMenu, String id) {
        Bundle args = new Bundle();
        args.putBoolean(HAS_MENU, hasMenu);
        args.putString(EVENT_TYPE_ID, id);
        EventListFragment fragment = new EventListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.list_training_layout;
    }

    protected boolean isHasMenu() {
        return mHasMenu;
    }

    @Override
    protected void onTKGZCreateView(View rootView) {
        if (getArguments() != null) {
            mHasMenu = getArguments().getBoolean(HAS_MENU);
            mEventTypeId = getArguments().getString(EVENT_TYPE_ID);
        }
        mLvEventList = (ListView) rootView.findViewById(R.id.lv_train_list);
        mLvEventList.setEmptyView(rootView.findViewById(R.id.tv_empty));
        mRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.refresh_layout);
        mRefreshLayout.setDistanceToTriggerSync(100);
        mRefreshLayout.setSize(SwipeRefreshLayout.DEFAULT);
        mRefreshLayout.setColorSchemeColors(getResources().getIntArray(R.array.refresh_color));
        mRefreshLayout.setOnRefreshListener(mListener);
    }

    @Override
    protected void onTKGZActivityCreated() {
        if (mEventListener != null) {
            mEventList = mEventListener.getEventsByType(String.valueOf(mEventTypeId));
        }
        if (mEventList == null || mEventList.size() == 0) {
            OnAutoRefresh();
        }
        mAdapter = new EventListBaseAdapter(getActivity());
        mAdapter.setEventList(mEventList);
        mLvEventList.setAdapter(mAdapter);
        mLvEventList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                getOnScreenNavigationListener().navigateToEventDetailsPage(EventListFragment.this, mEventList.get(i).getId(), isHasMenu());
            }
        });
    }

    public void setListener(onSaveEventListListener listener) {
        mEventListener = listener;
    }

    protected void getEventsList() {
        initLoader(EventProvider.getEventListByTypeProvider(TKGZApplication.getInstance().getUserProfile(), this, mEventTypeId, new EventListByTypeApiCallBack.OnEventListByTypeApiListener() {
            @Override
            public void onGetEventList(final ArrayList<EventProfile> list) {
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
                                if (mEventListener != null) {
                                    mEventListener.setEventsByType(String.valueOf(mEventTypeId), list);
                                }
                            }
                        });
                    }
                }
            }
        }));
    }

    private void OnAutoRefresh() {
        mRefreshLayout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mRefreshLayout.setRefreshing(true);
                mListener.onRefresh();
                mRefreshLayout.getViewTreeObserver().removeGlobalOnLayoutListener(this);
            }
        });
    }

    @Override
    protected boolean hasTKGZOptionsMenu() {
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
        return null;
    }

    @Override
    public void onUpdateEventStatus(boolean enrollSuccess) {
        if (enrollSuccess) {
            OnAutoRefresh();
        }
    }

    public interface onSaveEventListListener {
        void setEventsByType(String type, List<EventProfile> list);

        List<EventProfile> getEventsByType(String type);
    }
}
