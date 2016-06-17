package com.xogrp.tkgz.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.xogrp.tkgz.R;
import com.xogrp.tkgz.View.CircleImageView;
import com.xogrp.tkgz.model.EventProfile;
import com.xogrp.tkgz.util.TKGZUtil;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by ayu on 1/25/2016 0025.
 */
public class EventCalendarListViewAdapter extends BaseAdapter {
    private ArrayList<EventProfile> mEventList;
    private Context mContext;
    private String[] mStatusText;

    public EventCalendarListViewAdapter(Context context) {
        mContext = context;
        mStatusText = mContext.getResources().getStringArray(R.array.event_status);
        mEventList = new ArrayList<>();
    }

    public void setAdapterData(ArrayList<EventProfile> list, Date date) {
        mEventList = list;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mEventList.size();
    }

    @Override
    public Object getItem(int position) {
        return mEventList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder viewHolder = null;
        EventProfile event = mEventList.get(position);
        if (view == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.list_calendar_event, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.mCivImage = (CircleImageView) view.findViewById(R.id.civ_event_sign);
            viewHolder.mTvEnroll = (TextView) view.findViewById(R.id.tv_txt_enroll_time);
            viewHolder.mTvTitle = (TextView) view.findViewById(R.id.tv_event_title);
            viewHolder.mTvStatus = (TextView) view.findViewById(R.id.tv_event_status);
            viewHolder.mTvCount = (TextView) view.findViewById(R.id.tv_count);
            viewHolder.mTvEnrollTime = (TextView) view.findViewById(R.id.tv_enroll_time);
            viewHolder.mTvEventTime = (TextView) view.findViewById(R.id.tv_event_time);
//            viewHolder.mTvContent = (TextView) view.findViewById(R.id.tv_event_content);
            viewHolder.mTvUpload = (TextView) view.findViewById(R.id.tv_upload_resource);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        int statusCode = event.getStatus();

        viewHolder.mTvEnrollTime.setTextColor(mContext.getResources().getColor(R.color.black));
        viewHolder.mTvEventTime.setTextColor(mContext.getResources().getColor(R.color.black));
        switch (statusCode) {
            case 1:
                viewHolder.mTvEnrollTime.setTextColor(mContext.getResources().getColor(R.color.calendar_green));
                break;
            case 4:
                viewHolder.mTvEventTime.setTextColor(mContext.getResources().getColor(R.color.calendar_orange));
                break;
        }

        viewHolder.mTvTitle.setText(event.getTitle());
        viewHolder.mTvCount.setText(String.format("%s/%s", event.getRegistration(), event.getCapacity()));
        viewHolder.mTvEnrollTime.setVisibility(View.GONE);

        viewHolder.mTvEnroll.setVisibility(View.INVISIBLE);
        if (event.getEnrollStartTime() != 0) {
            viewHolder.mTvEnrollTime.setVisibility(View.VISIBLE);
            viewHolder.mTvEnroll.setVisibility(View.VISIBLE);
            viewHolder.mTvEnrollTime.setText(String.format(mContext.getString(R.string.txt_to), TKGZUtil.getStringDateTime(event.getEnrollStartTime()), TKGZUtil.getStringDateTime(event.getEnrollEndTime())));
        }
        viewHolder.mTvEventTime.setText(String.format(mContext.getString(R.string.txt_to), TKGZUtil.getStringDateTime(event.getEventStartTime()), TKGZUtil.getStringDateTime(event.getEventEndTime())));
//        viewHolder.mTvContent.setText(event.getContent());
        viewHolder.mTvStatus.setText(mStatusText[statusCode]);
        viewHolder.mTvStatus.setBackgroundColor(mContext.getResources().getColor(TKGZUtil.getForeColor(statusCode)));
        viewHolder.mTvUpload.setVisibility(statusCode == 5 ? View.VISIBLE : View.GONE);
        return view;
    }

    private static class ViewHolder {
        private CircleImageView mCivImage;
        private TextView mTvEnroll;
        private TextView mTvTitle;
        private TextView mTvStatus;
        private TextView mTvCount;
        private TextView mTvEnrollTime;
        private TextView mTvEventTime;
        //        private TextView mTvContent;
        private TextView mTvUpload;
    }
}
