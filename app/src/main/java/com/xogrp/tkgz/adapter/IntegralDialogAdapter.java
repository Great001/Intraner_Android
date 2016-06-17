package com.xogrp.tkgz.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.xogrp.tkgz.R;
import com.xogrp.tkgz.model.IntegralTypeProfile;

import java.util.List;

/**
 * Created by jdeng on 5/30/2016.
 */
public class IntegralDialogAdapter extends BaseAdapter {
    private List<IntegralTypeProfile> mIntegralList;
    private Context mContext;

    public IntegralDialogAdapter(Context context, List<IntegralTypeProfile> mIntegralList) {
        this.mContext = context;
        this.mIntegralList = mIntegralList;
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
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView textView;
        if (convertView==null){
            convertView= LayoutInflater.from(mContext).inflate(R.layout.integral_type_dialog_item,null);
            textView= (TextView) convertView.findViewById(R.id.tv_integral_dialog_item);
            convertView.setTag(textView);
        }else {
            textView= (TextView) convertView.getTag();
        }
        textView.setText(mIntegralList.get(position).getName());
        return convertView;
    }
}
