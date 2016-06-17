package com.xogrp.tkgz.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.xogrp.tkgz.R;
import com.xogrp.tkgz.model.EventProfile;
import com.xogrp.tkgz.util.TKGZUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lzhou on 2015/8/3.
 */
public class EventListBaseAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private List<EventProfile> mEventList;
    private Context mContext;
    private String[] statusText;

    public EventListBaseAdapter(Context context) {
        this.mContext = context;
        mEventList = new ArrayList<>();
        mInflater = LayoutInflater.from(context);
        statusText = mContext.getResources().getStringArray(R.array.event_status);
    }

    public void setEventList(List<EventProfile> dataList) {
        mEventList = dataList;
    }

    @Override
    public int getCount() {
        return mEventList.size();
    }

    @Override
    public Object getItem(int i) {
        return mEventList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final ViewHolder viewHolder;
        EventProfile event = (EventProfile) getItem(i);
        if (view == null) {
            viewHolder = new ViewHolder();
            view = mInflater.inflate(R.layout.list_training_item_layout, viewGroup, false);
            viewHolder.mTvStatus = (TextView) view.findViewById(R.id.tv_train_status);
            viewHolder.mTvCreateTime = (TextView) view.findViewById(R.id.tv_create_time);
            viewHolder.mTvContent = (TextView) view.findViewById(R.id.tv_train_content);
            viewHolder.mTvEnrollTime = (TextView) view.findViewById(R.id.tv_check_in_time);
            viewHolder.mTvEventTime = (TextView) view.findViewById(R.id.tv_activity_time);
            viewHolder.mTvTitle = (TextView) view.findViewById(R.id.tv_training_title);

            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.mTvTitle.setText(event.getTitle());
        viewHolder.mTvEnrollTime.setVisibility(View.GONE);
        if (event.getEnrollStartTime() != 0) {
            viewHolder.mTvEnrollTime.setVisibility(View.VISIBLE);
            viewHolder.mTvEnrollTime.setText(String.format(mContext.getString(R.string.txt_to), TKGZUtil.getStringDateTime(event.getEnrollStartTime()), TKGZUtil.getStringDateTime(event.getEnrollEndTime())));
        }
        viewHolder.mTvEventTime.setText(String.format(mContext.getString(R.string.txt_to), TKGZUtil.getStringDateTime(event.getEventStartTime()), TKGZUtil.getStringDateTime(event.getEventEndTime())));
        viewHolder.mTvContent.setText(event.getContent());
        viewHolder.mTvCreateTime.setText(TKGZUtil.getStringDateTime(event.getCreateTime()));

        int statusCode = event.getStatus();

        viewHolder.mTvStatus.setText(statusText[statusCode]);
        viewHolder.mTvStatus.setBackgroundColor(mContext.getResources().getColor(TKGZUtil.getForeColor(statusCode)));

        return view;
    }

    private static class ViewHolder {
        private TextView mTvTitle;
        private TextView mTvStatus;
        private TextView mTvEnrollTime;
        private TextView mTvEventTime;
        private TextView mTvContent;
        private TextView mTvCreateTime;
    }
}
