package com.xogrp.tkgz.Widget;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.xogrp.tkgz.R;
import com.xogrp.tkgz.model.MessageForUser;
import com.xogrp.tkgz.util.TKGZUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wlao on 9/11/2015.
 */
public class NewsListAdapter extends BaseAdapter {
    private List<MessageForUser> mMessageList;
    private Context mContext;

    public NewsListAdapter(Context context) {
        this.mContext = context;
        this.mMessageList = new ArrayList<>();
    }

    public void setMessageList(List<MessageForUser> list) {
        mMessageList = list;
    }

    @Override
    public int getCount() {
        return mMessageList.size();
    }

    @Override
    public Object getItem(int position) {
        return mMessageList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View view, ViewGroup parent) {
        ViewHolder holder = null;
        final MessageForUser message = mMessageList.get(position);
        if (view == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.news_list_item, parent, false);
            holder = new ViewHolder();
            holder.mTvTitle = (TextView) view.findViewById(R.id.tv_results_title);
            holder.mTvContent = (TextView) view.findViewById(R.id.tv_train_content);
            holder.mTvTime = (TextView) view.findViewById(R.id.tv_current_time);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        holder.mTvTitle.setText(message.getTitle());
        holder.mTvContent.setText(message.getContent());
        holder.mTvTime.setText(TKGZUtil.getStringDateTime(message.getCreateTime()));

        if (message.isStatus()) {
            holder.mTvTitle.setTextColor(mContext.getResources().getColor(R.color.c_line_color));
            holder.mTvContent.setTextColor(mContext.getResources().getColor(R.color.c_line_color));
        } else {
            holder.mTvTitle.setTextColor(mContext.getResources().getColor(R.color.black));
            holder.mTvContent.setTextColor(mContext.getResources().getColor(R.color.black));
        }

        return view;
    }

    private static class ViewHolder {
        private TextView mTvTitle;
        private TextView mTvContent;
        private TextView mTvTime;
    }

}
