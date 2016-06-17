package com.xogrp.tkgz.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.xogrp.tkgz.R;
import com.xogrp.tkgz.Widget.RadioButtonDialog;
import com.xogrp.tkgz.listener.DatePickerController;
import com.xogrp.tkgz.listener.FragmentController;
import com.xogrp.tkgz.model.DialogSelectedProfile;
import com.xogrp.tkgz.util.TKGZUtil;

import java.util.ArrayList;

import static com.xogrp.tkgz.util.TKGZUtil.getStringDate;


/**
 * Created by ayu on 11/27/2015 0027.
 */

public class SearchIntegralFragment extends AbstractTKGZFragment implements View.OnClickListener, DatePickerController, FragmentController {
    private TextView mTvStartTime;
    private long mStartTime = 0;
    private long mEndTime = 0;
    private int mLayoutResId;
    private TextView mTvEndTime;
    private TextView mTvTextChoose;
    private TextView mTvChoose;
    private Button mBtnSearch;
    private int mPosition;
    private String mSearchId;
    private ArrayList<DialogSelectedProfile> mSelectedItem = new ArrayList<>();
    private static final String KEY_POSITION = "position";

    private ArrayList<DialogSelectedProfile> mTeamList = new ArrayList<>();
    private ArrayList<DialogSelectedProfile> mEmployeeList = new ArrayList<>();
    private String[] mEmployeeName = {"Deon Gao", "Alex Qiu", "Piano Chen", "Alpha Yu",
            "Jerry Li", "Ethan Hunt", "Jane Li", "Will", "Ashley Han", "Claire Li", "Emily Ling", "Minnie"};
    private String[] mTeamName = {"Personnel Dept.", "Finance Dept.", "R&D Center", "Marketing Dept.", "Sales Dept."};


    public static SearchIntegralFragment newInstance(int position) {

        Bundle args = new Bundle();
        args.putInt(KEY_POSITION, position);
        SearchIntegralFragment fragment = new SearchIntegralFragment();
        fragment.setArguments(args);
        return fragment;
    }

    void initDemoData() {
        DialogSelectedProfile team;
        int index = 0;
        for (String name : mTeamName) {
            team = new DialogSelectedProfile();
            team.setName(name);
            team.setId(String.valueOf(index++));
            team.setSelected(false);
            mTeamList.add(team);
        }
        DialogSelectedProfile member;
        index = 0;
        for (String name : mEmployeeName) {
            member = new DialogSelectedProfile(name, String.valueOf(index), false);
            mEmployeeList.add(member);
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_start_time:
            case R.id.tv_end_time:
                TKGZUtil.setDateDialog(v.getId(), getActivity(), this);
                break;
            case R.id.tv_choose:
                RadioButtonDialog teamDialog = new RadioButtonDialog(getActivity(), R.id.tv_choose, mSelectedItem, mLayoutResId, this);
                teamDialog.show();
                break;
            case R.id.btn_search:
                if (mStartTime == 0 || mEndTime == 0 || mStartTime > mEndTime) {
                    //hard code : just a demo.
                    Toast.makeText(getActivity(), "Date Error!", Toast.LENGTH_SHORT).show();
                } else if (mPosition == 0) {
                    getOnScreenNavigationListener().navigateToTeamInquireResultPage();
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
                setStartTime(time);
                break;
            case R.id.tv_end_time:
                setEndTime(time);
                break;
            default:
                break;
        }
    }

    private void setStartTime(long time) {
        mTvStartTime.setText(getStringDate(time));
        mStartTime = time;
    }

    private void setEndTime(long time) {
        mTvEndTime.setText(getStringDate(time));
        mEndTime = time;
    }

    @Override
    public void setSelectedItem(int viewId, ArrayList<DialogSelectedProfile> list) {
        if (viewId == R.id.tv_choose)
            for (DialogSelectedProfile item : list) {
                if (item.isSelected()) {
                    mSearchId = item.getId();
                    mTvChoose.setText(item.getName());
                }
            }
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_search_integral;
    }

    @Override
    protected void onTKGZCreateView(View view) {

        mTvStartTime = (TextView) view.findViewById(R.id.tv_start_time);
        mTvEndTime = (TextView) view.findViewById(R.id.tv_end_time);
        mTvTextChoose = (TextView) view.findViewById(R.id.tv_txt_choose);
        mTvChoose = (TextView) view.findViewById(R.id.tv_choose);
        mBtnSearch = (Button) view.findViewById(R.id.btn_search);
    }

    @Override
    protected void onTKGZActivityCreated() {
        initDemoData();
        mPosition = getArguments().getInt(KEY_POSITION);
        if (mPosition == 0) {
            mTvTextChoose.setText(getString(R.string.txt_team_name));
            mLayoutResId = R.layout.alert_dialog_select_team;
            mSelectedItem = mTeamList;
        } else {
            mTvTextChoose.setText(getString(R.string.txt_member_name));
            mLayoutResId = R.layout.alert_dialog_select_supervisor;
            mSelectedItem = mEmployeeList;
        }
        mTvStartTime.setOnClickListener(this);
        mTvEndTime.setOnClickListener(this);
        mTvChoose.setOnClickListener(this);
        mBtnSearch.setOnClickListener(this);
    }

    @Override
    public String getTransactionTag() {
        return getClass().getSimpleName();
    }

    @Override
    public boolean isHideActionBar() {
        return false;
    }

    @Override
    public String getActionBarTitle() {
        return null;
    }
}
