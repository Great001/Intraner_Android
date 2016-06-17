package com.xogrp.tkgz.fragment;


import android.app.Activity;
import android.app.Fragment;
import android.graphics.BitmapFactory;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.xogrp.tkgz.R;
import com.xogrp.tkgz.TKGZApplication;
import com.xogrp.tkgz.Widget.IntegralListAdapter;
import com.xogrp.tkgz.model.IntegralDetailProfile;
import com.xogrp.tkgz.provider.SearchIntegralProvider;
import com.xogrp.tkgz.spi.SearchIntegralApiCallBack;
import com.xogrp.tkgz.util.TKGZUtil;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyIntegralFragment extends AbstractTKGZFragment {

    private TextView mTvYearIntegral;
    private TextView mTvMonthIntegral;
    private TextView mTvGrade;
    private ProgressBar mPbarIntegral;
    private ImageView mIvBadge;
    private TextView mTvTips;
    private ListView mLvDetail;
    private ArrayList<IntegralDetailProfile> mIntegralList = new ArrayList<>();
    private IntegralListAdapter mAdapter;

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_my_integral;
    }

    @Override
    protected void onTKGZCreateView(View rootView) {

        mTvYearIntegral = (TextView) rootView.findViewById(R.id.tv_year_integral);
        mTvMonthIntegral = (TextView) rootView.findViewById(R.id.tv_month_integral);
        mTvGrade = (TextView) rootView.findViewById(R.id.tv_grade);
        mIvBadge = (ImageView) rootView.findViewById(R.id.iv_badge);
        mPbarIntegral = (ProgressBar) rootView.findViewById(R.id.pbar_integral);

        Button mBtnUpgrade = (Button) rootView.findViewById(R.id.btn_upgrade);
        mBtnUpgrade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getOnScreenNavigationListener().navigateToShowEventsPage();
            }
        });

        mTvTips = (TextView) rootView.findViewById(R.id.tv_tipsscore);
        mLvDetail = (ListView) rootView.findViewById(R.id.lv_detail);
        mLvDetail.setEmptyView(rootView.findViewById(R.id.tv_empty));
    }

    private void getCurrentMonthIntegral() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), 1);
        String startTime = TKGZUtil.getStringDate(calendar.getTimeInMillis());
        String endTime = TKGZUtil.getStringDate(new Date().getTime());

        initLoader(SearchIntegralProvider.getSearchIntegralProvider(this, TKGZApplication.getInstance().getUserProfile(), TKGZApplication.getInstance().getUserProfile().getId(), startTime, endTime, new SearchIntegralApiCallBack.OnSearchIntegralApiListener() {
            @Override
            public void onSearchIntegral(ArrayList<IntegralDetailProfile> list) {
                if (list != null) {
                    mIntegralList = list;
                    final Activity activity = getActivity();
                    if (activity != null) {
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mAdapter.setIntegralList(mIntegralList);
//                                mAdapter.notifyDataSetChanged();
                            }
                        });
                    }
                }
            }
        }));
    }

    @Override
    protected void onTKGZActivityCreated() {
        showTheIntegralPage();

        getCurrentMonthIntegral();
        mAdapter = new IntegralListAdapter(getActivity());
        mAdapter.setIntegralList(mIntegralList);
        mLvDetail.setAdapter(mAdapter);
    }

    @Override
    public String getTransactionTag() {
        return "my_integral";
    }

    @Override
    public boolean isHideActionBar() {
        return false;
    }

    @Override
    public String getActionBarTitle() {
        return getString(R.string.actionbar_title_my_integral_page);
    }

    public void showTheIntegralPage() {
        int[] progressbarMax = getResources().getIntArray(R.array.progressbar_max_item);
        int[] levelTotalScore = getResources().getIntArray(R.array.level_total_score_item);
        int currentIntegral = TKGZApplication.getInstance().getUserProfile().getIntegralForYear();
        int level = TKGZUtil.getLevel(currentIntegral);

        mTvYearIntegral.setText(String.valueOf(currentIntegral));
        mTvMonthIntegral.setText(String.valueOf(TKGZApplication.getInstance().getUserProfile().getIntegralForMonth()));
        mTvGrade.setText(TKGZUtil.getLevelAppellation(level));
        mIvBadge.setImageBitmap(BitmapFactory.decodeResource(getActivity().getResources(), TKGZUtil.getLevelImage(level)));
        mPbarIntegral.setIndeterminate(false);

        if (level < 4) {
            mTvGrade.setText(TKGZUtil.getLevelAppellation(level));
            int tipsScore = (levelTotalScore[level]) - currentIntegral;
            mTvTips.setText(String.format("%d/%d", currentIntegral, levelTotalScore[level]));
            int progress = progressbarMax[level] - tipsScore;
            mPbarIntegral.setMax(progressbarMax[level]);
            mPbarIntegral.setProgress(progress);
        } else {
            mTvGrade.setText(TKGZUtil.getLevelAppellation(4));
            mPbarIntegral.setProgress(levelTotalScore[3]);

        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        menu.clear();
        menuInflater.inflate(R.menu.menu_container_item, menu);
        MenuItem menuItem = menu.findItem(R.id.menu_search_guest);
        menuItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                getOnScreenNavigationListener().navigateToSearchIntegralPage();
                menuItem.setVisible(true);
                return true;
            }
        });
    }


}
