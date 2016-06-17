package com.xogrp.tkgz.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.xogrp.tkgz.R;
import com.xogrp.tkgz.model.Initiator;
import com.xogrp.tkgz.model.UserProfile;

import java.util.List;

/**
 * Created by jdeng on 5/31/2016.
 */
public class InitiatorAdapter extends BaseAdapter {
    private List<Initiator> mInitiatorList;
    private Context mContext;

    public InitiatorAdapter(Context mContext, List<Initiator> initiatorList) {
        this.mContext = mContext;
        this.mInitiatorList = initiatorList;
    }

    @Override

    public int getCount() {
        return mInitiatorList.size();
    }

    @Override
    public Object getItem(int position) {
        return mInitiatorList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView textView;
        if (convertView==null){
            convertView= LayoutInflater.from(mContext).inflate(R.layout.choose_initiator_item,null);
            textView= (TextView) convertView.findViewById(R.id.tv_choose_initiator_item);
            convertView.setTag(textView);
        } else {
            textView= (TextView) convertView.getTag();
        }
        textView.setText(mInitiatorList.get(position).getName());
        return convertView;
    }
}
