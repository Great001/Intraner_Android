package com.xogrp.tkgz.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.xogrp.tkgz.R;
import com.xogrp.tkgz.model.CommonAdressProfile;

import java.util.List;

/**
 * Created by jdeng on 5/10/2016.
 */
public class CommonAddressAdapter extends BaseAdapter {
    private Context context;
    private List<CommonAdressProfile> mCommonAddressProfilelist;

    public CommonAddressAdapter(Context context, List<CommonAdressProfile> listCommonAddress) {
        this.context = context;
        this.mCommonAddressProfilelist = listCommonAddress;
    }

    @Override
    public int getCount() {
        return mCommonAddressProfilelist.size();
    }

    @Override
    public Object getItem(int position) {
        return mCommonAddressProfilelist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        TextView tvCommonAddresitem;
        view= LayoutInflater.from(context).inflate(R.layout.commont_address_item,null);
        tvCommonAddresitem= (TextView) view.findViewById(R.id.tv_common_address_item);
        tvCommonAddresitem.setText(mCommonAddressProfilelist.get(position).getAddress_name());
        return view;
    }
}
