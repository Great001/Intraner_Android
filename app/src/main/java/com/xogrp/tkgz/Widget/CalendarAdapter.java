package com.xogrp.tkgz.Widget;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xogrp.tkgz.R;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by wlao on 1/14/2016.
 */
public class CalendarAdapter extends BaseAdapter {
    // current displayed month
    private Date mSelectedDate;

    // days with events
    private Set<Date> mEventSet;
    private ArrayList<Date> mDateList;
    private Set<Date> mEnrollStatusSet;
    private Set<Date> mEventStatusSet;
    private Context mContext;
    private Date mDafaultDate = new Date();

    // for view inflation
    private LayoutInflater mInflater;
    private Date mToday;

    public CalendarAdapter(Context context) {
        mContext = context;
        mSelectedDate = new Date();
        mDateList = new ArrayList<>();
        mEventSet = new HashSet<>();
        mEnrollStatusSet = new HashSet<>();
        mEventStatusSet = new HashSet<>();
        mInflater = LayoutInflater.from(context);
    }

    public void updateAdapter(ArrayList<Date> days, Date currentDate, Set<Date> eventSet, Set<Date> enrollStatusSet, Set<Date> eventStatusSet) {
        mSelectedDate = currentDate;
        mDateList = days;
        mEventSet = eventSet;
        mEnrollStatusSet = enrollStatusSet;
        mEventStatusSet = eventStatusSet;
    }

    @Override
    public int getCount() {
        return mDateList.size();
    }

    @Override
    public Object getItem(int position) {
        return mDateList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        Date calendarDate = mDateList.get(position);
        Date currentDate = new Date(calendarDate.getYear(), calendarDate.getMonth(), calendarDate.getDate());
        int month = mDateList.get(position).getMonth();
        int displayMonth = mSelectedDate.getMonth();

        // mToday
        Date date = new Date();
        mToday = new Date(date.getYear(), date.getMonth(), date.getDate());

        ViewHolder viewHolder;
        if (view == null) {
            viewHolder = new ViewHolder();
            view = mInflater.inflate(R.layout.tkgz_calendar_item, parent, false);
            viewHolder.mRlCalendarItem = (RelativeLayout) view.findViewById(R.id.rl_calendar_item);
            viewHolder.mTvCalendarText = (TextView) view.findViewById(R.id.tv_calendar_text);
            viewHolder.mIvAbleSignUp = (ImageView) view.findViewById(R.id.iv_able_sign_up);
            viewHolder.mIvEventOngoing = (ImageView) view.findViewById(R.id.iv_event_ongoing);
            viewHolder.mVBlank = view.findViewById(R.id.v_blank);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        // clear styling
        viewHolder.mTvCalendarText.setBackgroundResource(0);
        viewHolder.mTvCalendarText.setTypeface(null, Typeface.NORMAL);
        viewHolder.mTvCalendarText.setTextColor(mContext.getResources().getColor(R.color.calendar_dafault));
        viewHolder.mRlCalendarItem.setTag(R.string.calendar_tag, false);
        viewHolder.mIvAbleSignUp.setVisibility(View.GONE);
        viewHolder.mVBlank.setVisibility(View.GONE);
        viewHolder.mIvEventOngoing.setVisibility(View.GONE);

        // it is today
        if (currentDate.equals(mToday)) {
            viewHolder.mTvCalendarText.setBackgroundResource(R.drawable.calendar_today);
        }

        //only display current month date
        if (month != displayMonth) {
            viewHolder.mRlCalendarItem.setVisibility(View.INVISIBLE);
        } else {
            viewHolder.mRlCalendarItem.setVisibility(View.VISIBLE);
        }

        //weekend text color
        if (position == 0 || (position + 1) % 7 == 0 || (position) % 7 == 0) {
            viewHolder.mTvCalendarText.setTextColor(mContext.getResources().getColor(R.color.calendar_weekend));
        }

        if (currentDate.getDate() == 1 && month == displayMonth){
            mDafaultDate = currentDate;
        }

        if (!mEventSet.isEmpty() && mEventSet.contains(currentDate)){
            viewHolder.mTvCalendarText.setTextColor(mContext.getResources().getColor(R.color.calendar_focus_event));
            viewHolder.mRlCalendarItem.setTag(R.string.calendar_tag, true);

            if (currentDate.compareTo(mToday) >= 0) {
                if (mEnrollStatusSet.contains(currentDate)) {
                    viewHolder.mIvAbleSignUp.setVisibility(View.VISIBLE);
                }
                if (mEventStatusSet.contains(currentDate)) {
                    int signUpStatus = viewHolder.mIvAbleSignUp.getVisibility();
                    viewHolder.mVBlank.setVisibility(signUpStatus == View.VISIBLE ? View.VISIBLE : View.GONE);
                    viewHolder.mIvEventOngoing.setVisibility(View.VISIBLE);
                }
            }
        }

        // set text
        viewHolder.mTvCalendarText.setText(String.valueOf(mDateList.get(position).getDate()));
        return view;
    }

    public Date getCalendarDafaultDate(int increase){
        mDafaultDate.setMonth(mDafaultDate.getMonth() + increase);
        if (mDafaultDate.getYear() == mToday.getYear() && mDafaultDate.getMonth() == mToday.getMonth()){
            mDafaultDate = mToday;
        }
        return mDafaultDate;
    }

    private static class ViewHolder {
        private RelativeLayout mRlCalendarItem;
        private TextView mTvCalendarText;
        private ImageView mIvAbleSignUp;
        private ImageView mIvEventOngoing;
        private View mVBlank;
    }
}
