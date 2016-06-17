package com.xogrp.tkgz.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.xogrp.tkgz.R;
import com.xogrp.tkgz.model.ActivityType;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by jdeng on 4/28/2016.
 */
public class ActivityTypeAdapter extends BaseAdapter {
    private List<ActivityType> mActivityTypeList;
    private Context mContext;
    public ActivityTypeAdapter(Context context,ArrayList<ActivityType> list) {
        mActivityTypeList=list;
        this.mContext=context;
    }

    @Override
    public int getCount() {
        return mActivityTypeList.size();
    }

    @Override
    public Object getItem(int position) {
        return mActivityTypeList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        TextView tvType;
         view= LayoutInflater.from(mContext).inflate(R.layout.activity_types_item,null);
        tvType= (TextView) view.findViewById(R.id.tv_activity_type_item);
        tvType.setText(mActivityTypeList.get(position).getName());
        return view;
    }
}
