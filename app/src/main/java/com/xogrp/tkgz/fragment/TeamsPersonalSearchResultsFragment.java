package com.xogrp.tkgz.fragment;


import android.app.Fragment;
import android.graphics.BitmapFactory;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.xogrp.tkgz.R;
import com.xogrp.tkgz.Widget.IntegralListAdapter;
import com.xogrp.tkgz.model.IntegralDetailProfile;
import com.xogrp.tkgz.util.TKGZUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class TeamsPersonalSearchResultsFragment extends AbstractTKGZFragment {

    private TextView mTvTeamName;
    private TextView mTvDate;
    private TextView mTvScore;
    private ImageView mIvBadge;
    private ListView mLvDetail;

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_teams_personal_search_results;
    }

    @Override
    protected void onTKGZCreateView(View rootView) {
        mTvTeamName = (TextView) rootView.findViewById(R.id.tv_team_name);
        mTvDate = (TextView) rootView.findViewById(R.id.tv_title);
        mTvScore = (TextView) rootView.findViewById(R.id.tv_year_integral);
        mIvBadge = (ImageView) rootView.findViewById(R.id.iv_badge);
        mLvDetail = (ListView) rootView.findViewById(R.id.lv_detail);

    }

    @Override
    protected void onTKGZActivityCreated() {

        //Demo data
        String name = "Ashley Han";
        mTvTeamName.setText(name);
        List<IntegralDetailProfile> integralDetailList = new ArrayList<IntegralDetailProfile>();
        for (int index = 0; index < 10; index++) {
            integralDetailList.add(new IntegralDetailProfile(getString(R.string.txt_basketball_game), 86400000L, 1500));
        }

        Collections.sort(integralDetailList, new Comparator<IntegralDetailProfile>() {
            @Override
            public int compare(IntegralDetailProfile t1, IntegralDetailProfile t2) {
                return t1.getDate() < t2.getDate() ? 1 : -1;
            }
        });

        IntegralListAdapter integralRankPageAdapter = new IntegralListAdapter(getActivity());
        integralRankPageAdapter.setIntegralList(integralDetailList);
        mLvDetail.setAdapter(integralRankPageAdapter);

        long startDate = integralDetailList.get(0).getDate();
        long endDate = integralDetailList.get(integralDetailList.size() - 1).getDate();
        mTvDate.setText(String.format("%s%s%s", TKGZUtil.getStringDate(startDate), getString(R.string.tv_date_text), TKGZUtil.getStringDate(endDate)));

        int score = 0;
        for (IntegralDetailProfile integralDetailProfile : integralDetailList) {
            score += integralDetailProfile.getIntegral();
        }
        mTvScore.setText(String.valueOf(score));

        int grade = TKGZUtil.getLevel(score);
        mIvBadge.setImageBitmap(BitmapFactory.decodeResource(getResources(), TKGZUtil.getLevelImage(grade)));
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
