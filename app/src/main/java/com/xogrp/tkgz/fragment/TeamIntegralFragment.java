package com.xogrp.tkgz.fragment;


import android.app.Fragment;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.xogrp.tkgz.R;
import com.xogrp.tkgz.Widget.IntegralListAdapter;
import com.xogrp.tkgz.model.IntegralDetailProfile;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class TeamIntegralFragment extends AbstractTKGZFragment {

    private TextView mTvTeamName;
    private TextView mTvMemberAmount;
    private ListView mLvDetail;

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_team_integral;
    }

    @Override
    protected void onTKGZCreateView(View rootView) {
        mTvTeamName = (TextView) rootView.findViewById(R.id.tv_team_name);
        mTvMemberAmount = (TextView) rootView.findViewById(R.id.tv_member_amount);
        mLvDetail = (ListView) rootView.findViewById(R.id.lv_detail);
    }

    @Override
    protected void onTKGZActivityCreated() {
        //Demo data
        String teamName = "Mobile Team";
        int memberAmount = 21;

        mTvTeamName.setText(teamName);
        mTvMemberAmount.setText(String.valueOf(memberAmount));
        //Demo data
        List<IntegralDetailProfile> integralDetailList = new ArrayList<>();
        integralDetailList.add(new IntegralDetailProfile(getString(R.string.demo_txt_basketball_game), 86400000L, 1500));
        integralDetailList.add(new IntegralDetailProfile(getString(R.string.demo_txt_basketball_game), 864000000L, 1500));
        integralDetailList.add(new IntegralDetailProfile(getString(R.string.demo_txt_basketball_game), 864000000L, 1500));
        integralDetailList.add(new IntegralDetailProfile(getString(R.string.demo_txt_basketball_game), 8640000000L, 1500));
        integralDetailList.add(new IntegralDetailProfile(getString(R.string.demo_txt_basketball_game), 86400000L, 1500));
        integralDetailList.add(new IntegralDetailProfile(getString(R.string.demo_txt_basketball_game), 864000000L, 1500));

        IntegralListAdapter integralRankPageAdapter = new IntegralListAdapter(getActivity());
        integralRankPageAdapter.setIntegralList(integralDetailList);
        mLvDetail.setAdapter(integralRankPageAdapter);
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
        return getString(R.string.actionbar_title_team_integral_page);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        menu.clear();
        menuInflater.inflate(R.menu.menu_container_item, menu);
        MenuItem menuItem = menu.findItem(R.id.menu_search_guest);
        menuItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                getOnScreenNavigationListener().navigateToSearchTeamMemberIntegralPage();
                menuItem.setVisible(true);
                return true;
            }
        });
    }
}

