package com.xogrp.tkgz.fragment;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xogrp.tkgz.R;
import com.xogrp.tkgz.View.CircleImageView;
import com.xogrp.tkgz.activity.MainActivity;

/**
 *created by jdeng 4/26/2016
 */
public class AdministratorHomeFragment extends  AbstractTKGZFragment implements View.OnClickListener {
    private TextView mTvName;
    private TextView mTvJobtitle;
    private TextView mTvSystemManage;
    private TextView mTvStaffManage;
    private TextView mTvMessageManage;
    private TextView mTvIntegralManage;
    private TextView mTvActivityManage;
    private TextView mTvActivityTypeManage;
    private CircleImageView mCivStat;
    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_add_activity;
    }

    @Override
    protected void onTKGZCreateView(View rootView) {
        mCivStat= (CircleImageView) rootView.findViewById(R.id.civ_head_portrait);


    }

    @Override
    protected void onTKGZActivityCreated() {

    }

    @Override
    public String getTransactionTag() {
        return getString(R.string.administratorfragment);
    }

    @Override
    public boolean isHideActionBar() {
        return false;
    }

    @Override
    public String getActionBarTitle() {
        return null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
//            case R.id.btn
        }
    }
}

