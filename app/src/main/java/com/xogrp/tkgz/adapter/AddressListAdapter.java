package com.xogrp.tkgz.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.xogrp.tkgz.R;

import java.util.List;

/**
 * Created by hliao on 5/6/2016.
 */
public class AddressListAdapter extends BaseAdapter {
    private Context mContext;
    private List<String> mList;

    public AddressListAdapter(Context context,List<String> list){
        this.mContext =context;
        this.mList =list;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView textView;
        if(convertView==null){
            convertView= LayoutInflater.from(mContext).inflate(R.layout.activity_types_item,null);
            textView=(TextView)convertView.findViewById(R.id.tv_activity_type_item);
            convertView.setTag(textView);
        }
        else {
            textView = (TextView) convertView.getTag();
        }
        textView.setText(mList.get(position));
        return convertView;
    }
}
