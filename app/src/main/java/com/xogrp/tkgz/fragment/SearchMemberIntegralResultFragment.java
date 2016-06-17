package com.xogrp.tkgz.fragment;

import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.xogrp.tkgz.R;

/**
 * Created by ayu on 11/30/2015 0030.
 */
public class SearchMemberIntegralResultFragment extends AbstractTKGZFragment {

    private TextView mTvName;
    private TextView mTvIntegral;
    private TextView mTvTime;
    private ListView mLvResult;

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_search_member_integral_result;
    }

    @Override
    protected void onTKGZCreateView(View rootView) {
        mTvName = (TextView) rootView.findViewById(R.id.tv_member_name);
        mTvIntegral = (TextView) rootView.findViewById(R.id.tv_integral);
        mTvTime = (TextView) rootView.findViewById(R.id.tv_time);
        mLvResult = (ListView) rootView.findViewById(R.id.lv_result_list);
    }

    @Override
    protected void onTKGZActivityCreated() {
        //drawableRight
        mTvName.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0);
    }

    @Override
    public String getTransactionTag() {
        return null;
    }

    @Override
    public boolean isHideActionBar() {
        return false;
    }

    @Override
    public String getActionBarTitle() {
        return getString(R.string.actionbar_title_search_result);
    }
}
