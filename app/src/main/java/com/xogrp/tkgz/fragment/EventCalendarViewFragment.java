package com.xogrp.tkgz.fragment;

import android.app.Activity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.xogrp.tkgz.R;
import com.xogrp.tkgz.TKGZApplication;
import com.xogrp.tkgz.View.CalendarView;
import com.xogrp.tkgz.adapter.EventCalendarListViewAdapter;
import com.xogrp.tkgz.model.EventProfile;
import com.xogrp.tkgz.provider.EventProvider;
import com.xogrp.tkgz.spi.EntireEventListApiCallBack;
import com.xogrp.tkgz.util.TKGZUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by ayu on 1/20/2016 0020.
 */
public class EventCalendarViewFragment extends AbstractTKGZFragment implements EventDetailFragment.OnUpdateEventStatusListener {
    private CalendarView mCalendarView;
    private ArrayList<EventProfile> mTotalEventList;
    private ArrayList<EventProfile> mShowEventList;
    private ListView mLvEvents;
    private static final long ONE_DAY_MILLISECONDS = 24 * 60 * 60 * 1000;
    private EventCalendarListViewAdapter mAdapter;
    private TextView mTvSelected;
    private TextView mTvTip;
    private boolean mIsRefreshData;

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_event_calendar_view;
    }

    @Override
    protected void onTKGZCreateView(View rootView) {
        mTotalEventList = new ArrayList<>();
        mShowEventList = new ArrayList<>();
        mLvEvents = (ListView) rootView.findViewById(R.id.lv_event);
        mCalendarView = (CalendarView) rootView.findViewById(R.id.v_calendar);
        mTvSelected = (TextView) rootView.findViewById(R.id.tv_selected);
        mTvTip = (TextView) rootView.findViewById(R.id.tv_no_event_tip);
    }

    @Override
    protected void onTKGZActivityCreated() {
        getEventsList();
        mAdapter = new EventCalendarListViewAdapter(getActivity());
        mAdapter.setAdapterData(new ArrayList<EventProfile>(), new Date());
        mLvEvents.setAdapter(mAdapter);
        mLvEvents.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                getOnScreenNavigationListener().navigateToEventDetailsPage(EventCalendarViewFragment.this, mShowEventList.get(position).getId(), false);
            }
        });
        mCalendarView.setEventHandler(new CalendarView.EventHandler() {
            @Override
            public void onDayShortPress(Date date) {
                getSelectedDateEventList(date);
            }

            @Override
            public void onDayPrev(Date date) {
                getSelectedDateEventList(date);
            }

            @Override
            public void onDayNext(Date date) {
                getSelectedDateEventList(date);
            }
        });
    }

    private void getEventsList() {
        showSpinner();
        initLoader(EventProvider.getEntireEventListProvider(TKGZApplication.getInstance().getUserProfile(), this, new EntireEventListApiCallBack.OnEntireEventListApiListener() {
            @Override
            public void getEntireEventList(final ArrayList<EventProfile> list) {
                if (list != null) {
                    mTotalEventList = list;
                    final Activity activity = getActivity();
                    if (activity != null) {
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Set<Date> events = new HashSet();
                                Set<Date> enrollStatusSet = new HashSet();
                                Set<Date> eventStatusSet = new HashSet();
                                Date currentEventDate;
                                long eventDay;
                                long enrollStartDay;
                                long enrollEndDay;
                                long eventStartDay;
                                long eventEndDay;
                                int size = list.size();
                                for (int index = 0; index < size; index++) {

                                    enrollStartDay = TKGZUtil.getDaysSinceUTC(list.get(index).getEnrollStartTime());
                                    enrollEndDay = TKGZUtil.getDaysSinceUTC(list.get(index).getEnrollEndTime());
                                    eventStartDay = TKGZUtil.getDaysSinceUTC(list.get(index).getEventStartTime());
                                    eventEndDay = TKGZUtil.getDaysSinceUTC(list.get(index).getEventEndTime());

                                    for (eventDay = enrollStartDay; eventDay <= enrollEndDay; eventDay++) {
                                        currentEventDate = new Date(eventDay * ONE_DAY_MILLISECONDS);
                                        currentEventDate.setHours(0);
                                        events.add(currentEventDate);
                                        enrollStatusSet.add(currentEventDate);
                                    }
                                    for (eventDay = eventStartDay; eventDay <= eventEndDay; eventDay++) {
                                        currentEventDate = new Date(eventDay * ONE_DAY_MILLISECONDS);
                                        currentEventDate.setHours(0);
                                        events.add(currentEventDate);
                                        eventStatusSet.add(currentEventDate);
                                    }
                                }
                                mCalendarView.updateCalendar(events, enrollStatusSet, eventStatusSet);
                                getSelectedDateEventList(new Date());
                            }
                        });
                    }
                }
            }
        }));
    }

    private void getSelectedDateEventList(Date date) {
        mShowEventList.clear();
        long currentDay = TKGZUtil.getDaysSinceUTC(date.getTime());
        int size = mTotalEventList.size();
        for (int index = 0; index < size; index++) {
            EventProfile event = mTotalEventList.get(index);
            if ((currentDay >= TKGZUtil.getDaysSinceUTC(event.getEnrollStartTime()) && currentDay <= TKGZUtil.getDaysSinceUTC(event.getEnrollEndTime()))
                    || (currentDay >= TKGZUtil.getDaysSinceUTC(event.getEventStartTime()) && currentDay <= TKGZUtil.getDaysSinceUTC(event.getEventEndTime()))) {
                mShowEventList.add(event);
            }
        }
        mAdapter.setAdapterData(mShowEventList, date);
        mTvSelected.setText(TKGZUtil.getStringDate(date.getTime()));
        mLvEvents.setVisibility(mShowEventList.size() == 0 ? View.GONE : View.VISIBLE);
        mTvTip.setVisibility(mShowEventList.size() == 0 ? View.VISIBLE : View.GONE);
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
    protected boolean hasTKGZOptionsMenu() {
        return false;
    }

    @Override
    public void onRefreshData() {
        super.onRefreshData();
        if (mIsRefreshData) {
            getEventsList();
        }
    }

    @Override
    public void onUpdateEventStatus(boolean enrollSuccess) {
        mIsRefreshData = enrollSuccess;
    }
}
