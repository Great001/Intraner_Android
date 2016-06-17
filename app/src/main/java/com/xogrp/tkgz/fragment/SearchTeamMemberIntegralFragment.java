package com.xogrp.tkgz.fragment;

import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.xogrp.tkgz.R;
import com.xogrp.tkgz.Widget.RadioButtonDialog;
import com.xogrp.tkgz.listener.DatePickerController;
import com.xogrp.tkgz.listener.FragmentController;
import com.xogrp.tkgz.model.DialogSelectedProfile;
import com.xogrp.tkgz.util.TKGZUtil;

import java.util.ArrayList;

/**
 * Created by ayu on 12/2/2015 0002.
 */
public class SearchTeamMemberIntegralFragment extends AbstractTKGZFragment implements View.OnClickListener, DatePickerController, FragmentController {

    private TextView mTvStartTime;
    private long mStartTime = 0;
    private long mEndTime = 0;
    private TextView mTvEndTime;
    private TextView mTvTextChoose;
    private TextView mTvChoose;
    private Button mBtnSearch;
    private CoordinatorLayout mClContain;
    private String mSearchId;
    private int mLayoutResId;
    private ArrayList mSelectedItem = new ArrayList<>();

    private ArrayList<DialogSelectedProfile> mEmployeeList = new ArrayList<>();
    private String[] mEmployeeName = {"Deon Gao", "Alex Qiu", "Piano Chen", "Alpha Yu",
            "Jerry Li", "Ethan Hunt", "Jane Li", "Will", "Ashley Han", "Claire Li", "Emily Ling", "Minnie"};


    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_search_integral;
    }

    @Override
    protected void onTKGZCreateView(View rootView) {
        mTvStartTime = (TextView) rootView.findViewById(R.id.tv_start_time);
        mTvEndTime = (TextView) rootView.findViewById(R.id.tv_end_time);
        mBtnSearch = (Button) rootView.findViewById(R.id.btn_search);
        mTvTextChoose = (TextView) rootView.findViewById(R.id.tv_txt_choose);
        mTvChoose = (TextView) rootView.findViewById(R.id.tv_choose);
        mClContain = (CoordinatorLayout) rootView.findViewById(R.id.cl_contain);

        //Demo data
        DialogSelectedProfile member;
        int index = 0;
        for (String name : mEmployeeName) {
            member = new DialogSelectedProfile(name, String.valueOf(index), false);
            mEmployeeList.add(member);
        }
    }

    @Override
    protected void onTKGZActivityCreated() {
        mLayoutResId = R.layout.alert_dialog_select_supervisor;
        mTvStartTime.setOnClickListener(this);
        mTvEndTime.setOnClickListener(this);
        mTvTextChoose.setText(getString(R.string.txt_member_name));
        mTvChoose.setOnClickListener(this);
        mBtnSearch.setOnClickListener(this);
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
                TKGZUtil.setDateDialog(R.id.tv_start_time, getActivity(), this);
                break;
            case R.id.tv_end_time:
                TKGZUtil.setDateDialog(R.id.tv_end_time, getActivity(), this);
                break;
            case R.id.tv_choose:
                if (mSelectedItem != null) {
                    RadioButtonDialog teamDialog = new RadioButtonDialog(getActivity(), R.id.tv_choose, mEmployeeList, mLayoutResId, this);
                    teamDialog.show();
                }
                break;
            case R.id.btn_search:
                if (mStartTime == 0 || mEndTime == 0 || mEndTime < mStartTime) {
                    Snackbar.make(mClContain, "Make Sure you set the correct time.", Snackbar.LENGTH_LONG).show();
                } else if (mSearchId == null) {
                    Snackbar.make(mClContain, "Make Sure you have choose member.", Snackbar.LENGTH_LONG).show();
                } else {
                    getOnScreenNavigationListener().navigateToSearchMemberIntegralResultPage();
                }
                break;
            default:
                break;
        }

    }

    @Override
    public void setDateString(int viewId, long time) {
        switch (viewId) {
            case R.id.tv_start_time:
                mTvStartTime.setText(TKGZUtil.getStringDate(time));
                mStartTime = time;
                break;
            case R.id.tv_end_time:
                mTvEndTime.setText(TKGZUtil.getStringDate(time));
                mEndTime = time;
                break;
            default:
                break;
        }
    }

    @Override
    public void setSelectedItem(int viewId, ArrayList<DialogSelectedProfile> list) {
        if (viewId == R.id.tv_choose) {
            for (DialogSelectedProfile item : list) {
                if (item.isSelected()) {
                    mSearchId = item.getId();
                    mTvChoose.setText(item.getName());
                }
            }
        }
    }

}
