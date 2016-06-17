package com.xogrp.tkgz.fragment;

import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.xogrp.tkgz.R;
import com.xogrp.tkgz.TKGZApplication;
import com.xogrp.tkgz.Widget.IntegralListAdapter;
import com.xogrp.tkgz.listener.DatePickerController;
import com.xogrp.tkgz.model.IntegralDetailProfile;
import com.xogrp.tkgz.provider.SearchIntegralProvider;
import com.xogrp.tkgz.spi.SearchIntegralApiCallBack;
import com.xogrp.tkgz.util.TKGZUtil;

import java.util.ArrayList;

/**
 * Created by wlao on 8/13/2015.
 */
public class SearchMyIntegralFragment extends AbstractTKGZFragment implements View.OnClickListener, DatePickerController {

    private TextView mTvStartTime;
    private TextView mTvEndTime;
    private Button mBtnSearch;
    private ListView mLvDetail;

    private ArrayList<IntegralDetailProfile> mIntegralList = new ArrayList<>();
    private IntegralListAdapter mAdapter;
    private long mStartTime;
    private long mEndTime;

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_search_my_integral;
    }

    @Override
    protected void onTKGZCreateView(View rootView) {
        mTvStartTime = (TextView) rootView.findViewById(R.id.tv_start_time);
        mTvEndTime = (TextView) rootView.findViewById(R.id.tv_end_time);
        mLvDetail = (ListView) rootView.findViewById(R.id.lv_detail);
        mBtnSearch = (Button) rootView.findViewById(R.id.btn_search);
    }

    @Override
    protected void onTKGZActivityCreated() {
        mTvStartTime.setOnClickListener(this);
        mTvEndTime.setOnClickListener(this);
        mBtnSearch.setOnClickListener(this);
        mAdapter = new IntegralListAdapter(getActivity());
        mLvDetail.setAdapter(mAdapter);
    }


    private void onSearchIntegral() {
        showSpinner();
        initLoader(SearchIntegralProvider.getSearchIntegralProvider(this, TKGZApplication.getInstance().getUserProfile(), TKGZApplication.getInstance().getUserProfile().getId(), mTvStartTime.getText().toString(), mTvEndTime.getText().toString(), new SearchIntegralApiCallBack.OnSearchIntegralApiListener() {
            @Override
            public void onSearchIntegral(final ArrayList<IntegralDetailProfile> list) {
                mIntegralList = list;
                final Activity activity = getActivity();
                if (activity != null) {
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mAdapter.setIntegralList(list);
                            if (list.isEmpty()) {
                                showMessage(getString(R.string.dialog_message_result_is_empty));
                            }
                        }
                    });
                }

            }
        }));
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
        return getString(R.string.actionbar_title_search_integral_page);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_start_time:
            case R.id.tv_end_time:
                TKGZUtil.setDateDialog(v.getId(), getActivity(), this);
                break;
            case R.id.btn_search:
                if (mStartTime == 0 || mEndTime == 0) {
                    Toast.makeText(getActivity(), getString(R.string.toast_time_is_null), Toast.LENGTH_SHORT).show();
                } else if (mStartTime > mEndTime) {
                    Toast.makeText(getActivity(), getString(R.string.toast_end_time_early_than_start_time), Toast.LENGTH_SHORT).show();
                } else {
                    onSearchIntegral();
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void setDateString(int viewId, long time) {
        if (viewId == R.id.tv_start_time) {
            mStartTime = time;
            mTvStartTime.setText(TKGZUtil.getStringDate(mStartTime));
        } else if (viewId == R.id.tv_end_time) {
            mEndTime = time;
            mTvEndTime.setText(TKGZUtil.getStringDate(mEndTime));
        }
    }
}
