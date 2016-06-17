package com.xogrp.tkgz.fragment;


import android.app.Fragment;

import com.xogrp.tkgz.R;
import com.xogrp.tkgz.model.TeamMembersIntegralAndPropsProfile;
import com.xogrp.tkgz.util.TKGZUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class TeamInquireResultFragment extends AbstractInquireManagerFragment {

    private List<String> mParentItem;
    private List<List<TeamMembersIntegralAndPropsProfile>> mDailyInformationList;
    private int mListSize;

    @Override
    protected List<List<TeamMembersIntegralAndPropsProfile>> getDailyInformationList() {
        mDailyInformationList = new ArrayList<List<TeamMembersIntegralAndPropsProfile>>();
        List<TeamMembersIntegralAndPropsProfile> teamMembersIntegralAndPropsProfileList1 = new ArrayList<TeamMembersIntegralAndPropsProfile>();

        //demo data1
        for (int i = 5; i < 15; i++) {
            teamMembersIntegralAndPropsProfileList1.add(new TeamMembersIntegralAndPropsProfile("National Team", "Kelvin Zhong" + i, i, 6000, 954000400000L, R.drawable.header_big_default, null));
        }
        teamMembersIntegralAndPropsProfileList1.add(new TeamMembersIntegralAndPropsProfile("National Team", "Vear Wang", 1, 31800, 954000400000L, R.drawable.header_big_default, null));
        teamMembersIntegralAndPropsProfileList1.add(new TeamMembersIntegralAndPropsProfile("National Team", "Daisy Xiao1", 2, 21800, 954000400000L, 0, null));
        teamMembersIntegralAndPropsProfileList1.add(new TeamMembersIntegralAndPropsProfile("National Team", "Thomas Li", 3, 13800, 954000400000L, R.drawable.header_big_default, null));
        teamMembersIntegralAndPropsProfileList1.add(new TeamMembersIntegralAndPropsProfile("National Team", "Daisy Xiao2", 4, 31800, 954000400000L, 0, null));
        teamMembersIntegralAndPropsProfileList1.add(new TeamMembersIntegralAndPropsProfile("National Team", "Will", 5, 2000, 954000400000L, R.drawable.header_big_default, null));

        Collections.sort(teamMembersIntegralAndPropsProfileList1, new Comparator<TeamMembersIntegralAndPropsProfile>() {
            @Override
            public int compare(TeamMembersIntegralAndPropsProfile t1, TeamMembersIntegralAndPropsProfile t2) {
                return t1.getObjectId() > t2.getObjectId() ? 1 : -1;
            }
        });
        mDailyInformationList.add(teamMembersIntegralAndPropsProfileList1);

        //demo data2
        List<TeamMembersIntegralAndPropsProfile> teamMembersIntegralAndPropsProfileList2 = new ArrayList<TeamMembersIntegralAndPropsProfile>();
        for (int i = 5; i < 21; i++) {
            teamMembersIntegralAndPropsProfileList2.add(new TeamMembersIntegralAndPropsProfile("National Team", "Kelvin Zhong" + i, i, 7000, 964000400000L, R.drawable.header_big_default, null));
        }
        teamMembersIntegralAndPropsProfileList2.add(new TeamMembersIntegralAndPropsProfile("National Team", "Vear Wang", 1, 32800, 964000400000L, R.drawable.header_big_default, null));
        teamMembersIntegralAndPropsProfileList2.add(new TeamMembersIntegralAndPropsProfile("National Team", "Daisy Xiao1", 2, 22800, 964000400000L, 0, null));
        teamMembersIntegralAndPropsProfileList2.add(new TeamMembersIntegralAndPropsProfile("National Team", "Thomas Li", 3, 14800, 964000400000L, R.drawable.header_big_default, null));
        teamMembersIntegralAndPropsProfileList2.add(new TeamMembersIntegralAndPropsProfile("National Team", "Daisy Xiao2", 4, 32800, 964000400000L, 0, null));
        teamMembersIntegralAndPropsProfileList2.add(new TeamMembersIntegralAndPropsProfile("National Team", "Will", 5, 3000, 964000400000L, R.drawable.header_big_default, null));

        Collections.sort(teamMembersIntegralAndPropsProfileList2, new Comparator<TeamMembersIntegralAndPropsProfile>() {
            @Override
            public int compare(TeamMembersIntegralAndPropsProfile t1, TeamMembersIntegralAndPropsProfile t2) {
                return t1.getObjectId() > t2.getObjectId() ? 1 : -1;
            }
        });

        mDailyInformationList.add(teamMembersIntegralAndPropsProfileList2);

        Collections.sort(mDailyInformationList, new Comparator<List<TeamMembersIntegralAndPropsProfile>>() {
            @Override
            public int compare(List<TeamMembersIntegralAndPropsProfile> t1, List<TeamMembersIntegralAndPropsProfile> t2) {
                return t1.get(0).getDate() > t2.get(0).getDate() ? 1 : -1;
            }
        });
        return mDailyInformationList;
    }

    @Override
    protected List<Integer> getParentItemScoreDataList() {
        int totalScore = 0;
        List<Integer> mParentItemScore = new ArrayList<Integer>();
        for (int i = 0; i < mListSize; i++) {
            for (TeamMembersIntegralAndPropsProfile teamMembersIntegralAndPropsProfile : mDailyInformationList.get(i)) {
                totalScore += teamMembersIntegralAndPropsProfile.getScore();
            }
            mParentItemScore.add(totalScore);
        }
        mParentItemScore.add(totalScore);

        return mParentItemScore;
    }

    @Override
    protected Map<String, List<TeamMembersIntegralAndPropsProfile>> getExpandableListView() {
        Map<String, List<TeamMembersIntegralAndPropsProfile>> mExpandableListViewData = new HashMap<String, List<TeamMembersIntegralAndPropsProfile>>();

        int size = mParentItem.size();
        for (int i = 0; i < size; i++) {
            mExpandableListViewData.put(mParentItem.get(i).trim(), mDailyInformationList.get(i));
        }

        return mExpandableListViewData;
    }

    @Override
    protected List<String> getParentItemDataList() {
        mParentItem = new ArrayList<String>();
        mListSize = mDailyInformationList.size();
        mParentItem.add(TKGZUtil.getStringDate(mDailyInformationList.get(0).get(0).getDate()));
        for (int i = 1; i < mListSize; i++) {
            if (mDailyInformationList.get(i).get(0).getDate() != mDailyInformationList.get(i - 1).get(0).getDate()) {
                mParentItem.add(TKGZUtil.getStringDate(mDailyInformationList.get(i).get(0).getDate()));
            }
        }

        return mParentItem;
    }

    @Override
    public String getTransactionTag() {
        return "team_inquire_result";
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
