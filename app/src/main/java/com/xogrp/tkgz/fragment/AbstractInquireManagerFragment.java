package com.xogrp.tkgz.fragment;

import android.view.View;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.xogrp.tkgz.R;
import com.xogrp.tkgz.Widget.ExpandableListViewAdapter;
import com.xogrp.tkgz.model.TeamMembersIntegralAndPropsProfile;
import com.xogrp.tkgz.util.TKGZUtil;

import java.util.List;
import java.util.Map;

/**
 * Created by wlao on 9/28/2015.
 */
public abstract class AbstractInquireManagerFragment extends AbstractTKGZFragment {

    private TextView mTvTeamName;
    private TextView mTvDate;
    private TextView mTvMemberAmount;
    private TextView mTvTheFirstAmount;
    private TextView mTvTheSecondAmount;
    private TextView mTvTheThirdAmount;
    private TextView mTvTheFourthAmount;
    private TextView mTvTheFifthAmount;
    private ExpandableListView mElvContant;

    private List<String> mParentItem;
    private List<Integer> mParentItemScore;
    private List<List<TeamMembersIntegralAndPropsProfile>> mDailyInformationList;
    private Map<String, List<TeamMembersIntegralAndPropsProfile>> mExpandableListViewData;

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_team_inquire_result;
    }

    @Override
    protected void onTKGZCreateView(View rootView) {
        mTvTeamName = (TextView) rootView.findViewById(R.id.tv_team_name);
        mTvDate = (TextView) rootView.findViewById(R.id.tv_title);
        mTvMemberAmount = (TextView) rootView.findViewById(R.id.tv_member_amount);
        mTvTheFirstAmount = (TextView) rootView.findViewById(R.id.tv_the_first_amount);
        mTvTheSecondAmount = (TextView) rootView.findViewById(R.id.tv_the_second_amount);
        mTvTheThirdAmount = (TextView) rootView.findViewById(R.id.tv_the_third_amount);
        mTvTheFourthAmount = (TextView) rootView.findViewById(R.id.tv_the_fourth_amount);
        mTvTheFifthAmount = (TextView) rootView.findViewById(R.id.tv_the_fifth_amount);
        mElvContant = (ExpandableListView) rootView.findViewById(R.id.elv_container);
        mElvContant.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                int listSize = mDailyInformationList.size();
                for (int i = 0; i < listSize; i++) {
                    if (groupPosition != i) {
                        mElvContant.collapseGroup(i);
                    }
                }
            }
        });
        mElvContant.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView expandableListView, View view, int groupPosition, long id) {

                showTheTeamInfo(groupPosition);

                if (expandableListView.isGroupExpanded(groupPosition)) {
                    expandableListView.collapseGroup(groupPosition);
                } else {
                    expandableListView.expandGroup(groupPosition, false);
                }

                return true;
            }
        });

    }


    protected abstract List<String> getParentItemDataList();

    protected abstract List<Integer> getParentItemScoreDataList();

    protected abstract List<List<TeamMembersIntegralAndPropsProfile>> getDailyInformationList();

    protected abstract Map<String, List<TeamMembersIntegralAndPropsProfile>> getExpandableListView();

    @Override
    protected void onTKGZActivityCreated() {
        //Demo data
        initData();

        String teamName = mDailyInformationList.get(0).get(0).getTeamName();
        long startDate = mDailyInformationList.get(0).get(0).getDate();
        long endDate = mDailyInformationList.get(mDailyInformationList.size() - 1).get(0).getDate();
        showTheTeamInfo(mDailyInformationList.size() - 1);
        mTvTeamName.setText(teamName);
        mTvDate.setText(String.format("%s%s%s", TKGZUtil.getStringDate(startDate), getString(R.string.tv_date_text), TKGZUtil.getStringDate(endDate)));

        ExpandableListViewAdapter expandableListViewAdapter = new ExpandableListViewAdapter(getActivity(), mParentItem, mParentItemScore, mExpandableListViewData, R.layout.layout_expandable_list_child);
        mElvContant.setAdapter(expandableListViewAdapter);
    }


    private void initData() {
        mDailyInformationList = getDailyInformationList();
        mParentItem = getParentItemDataList();
        mParentItemScore = getParentItemScoreDataList();
        mExpandableListViewData = getExpandableListView();

    }

    private void showTheTeamInfo(int groupPositon) {
        mTvMemberAmount.setText(String.valueOf(mDailyInformationList.get(groupPositon).size()));
        int[] badgeAmount = {0, 0, 0, 0, 0, 0};
        for (TeamMembersIntegralAndPropsProfile teamMembersIntegralAndPropsProfile : mDailyInformationList.get(groupPositon)) {
//            int grade = TKGZApplication.getTheLevel(getContext(), teamMembersIntegralAndPropsProfile.getScore());
            int grade = TKGZUtil.getLevel(teamMembersIntegralAndPropsProfile.getScore());
            badgeAmount[grade]++;
        }
        mTvTheFirstAmount.setText(String.valueOf(badgeAmount[4]));
        mTvTheSecondAmount.setText(String.valueOf(badgeAmount[3]));
        mTvTheThirdAmount.setText(String.valueOf(badgeAmount[2]));
        mTvTheFourthAmount.setText(String.valueOf(badgeAmount[1]));
        mTvTheFifthAmount.setText(String.valueOf(badgeAmount[0]));

    }
}
