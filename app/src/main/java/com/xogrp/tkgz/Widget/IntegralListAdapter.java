package com.xogrp.tkgz.Widget;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.xogrp.tkgz.R;
import com.xogrp.tkgz.model.IntegralDetailProfile;
import com.xogrp.tkgz.util.TKGZUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wlao on 8/4/2015.
 */
public class IntegralListAdapter extends BaseAdapter {

    private List<IntegralDetailProfile> mIntegralList;
    private Context mContext;

    public IntegralListAdapter(Context context) {
        this.mContext = context;
        this.mIntegralList = new ArrayList<>();
    }

    public void setIntegralList(List<IntegralDetailProfile> list) {
        mIntegralList = list;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mIntegralList.size();
    }

    @Override
    public Object getItem(int position) {
        return mIntegralList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.integral_detail_item, viewGroup, false);
            holder = new ViewHolder();
            holder.mTvTitle = (TextView) convertView.findViewById(R.id.tv_title);
            holder.mTvDate = (TextView) convertView.findViewById(R.id.tv_date);
            holder.mTvScore = (TextView) convertView.findViewById(R.id.tv_year_integral);
            convertView.setTag(holder);
        }
        holder = (ViewHolder) convertView.getTag();
        IntegralDetailProfile integralEvent = mIntegralList.get(position);
        holder.mTvTitle.setText(integralEvent.getTitle());
        holder.mTvDate.setText(TKGZUtil.getStringDate(integralEvent.getDate()));
        holder.mTvScore.setText(String.valueOf(integralEvent.getIntegral()));

        return convertView;
    }

    private static class ViewHolder {
        private TextView mTvTitle;
        private TextView mTvDate;
        private TextView mTvScore;
    }

}
