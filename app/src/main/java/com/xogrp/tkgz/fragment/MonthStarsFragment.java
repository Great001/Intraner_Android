package com.xogrp.tkgz.fragment;

import android.app.Activity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.xogrp.tkgz.R;
import com.xogrp.tkgz.TKGZApplication;
import com.xogrp.tkgz.Widget.MonthStarsPageAdapter;
import com.xogrp.tkgz.Widget.MonthStarsViewPager;
import com.xogrp.tkgz.model.MonthStarsProfile;
import com.xogrp.tkgz.provider.MonthStarsProvider;
import com.xogrp.tkgz.spi.MonthStarsApiCallBack;

import java.util.ArrayList;
import java.util.List;

public class MonthStarsFragment extends AbstractTKGZFragment implements OnClickListener {
    private static final int FIRST_PAGE = 0;
    private static final int LAST_PAGE = 11;
    private int mCurrentItem;
    private TextView mTvDescriptionOne;
    private TextView mTvDescriptionTwo;
    private TextView mTvDescriptionThree;
    private List<MonthStarsProfile> mMonthStarsProfileList = new ArrayList<>();
    private MonthStarsViewPager mVpMonthStar;
    private ImageView mIvArrowLeft;
    private ImageView mIvArrowRight;

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_month_stars_layout;
    }

    @Override
    protected void onTKGZCreateView(View rootView) {
        mIvArrowLeft = (ImageView) rootView.findViewById(R.id.iv_arrow_left);
        mIvArrowLeft.setOnClickListener(this);
        mIvArrowRight = (ImageView) rootView.findViewById(R.id.iv_arrow_right);
        mIvArrowRight.setOnClickListener(this);
        mTvDescriptionOne = (TextView) rootView.findViewById(R.id.tv_description_one);
        mTvDescriptionTwo = (TextView) rootView.findViewById(R.id.tv_description_two);
        mTvDescriptionThree = (TextView) rootView.findViewById(R.id.tv_description_three);
        mVpMonthStar = (MonthStarsViewPager) rootView.findViewById(R.id.vp_month_star);
        mVpMonthStar.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener(){
            @Override
            public void onPageSelected(int position) {
                if (mMonthStarsProfileList.size() != 0) {
                    mCurrentItem = position;
                    refreshData(getActivity(), mMonthStarsProfileList);
                }
            }
        });
    }

    @Override
    protected void onTKGZActivityCreated() {
        mCurrentItem = LAST_PAGE;
        showSpinner();
        initLoader(MonthStarsProvider.getMonthStarsProvider(TKGZApplication.getInstance().getUserProfile(), this, new MonthStarsApiCallBack.OnMonthStarsApiCallBackListener() {
            @Override
            public void onSuccess(List<MonthStarsProfile> list) {
                mMonthStarsProfileList = list;
                int listSize = list.size();
                final List<AbstractTKGZFragment> fragmentList = new ArrayList<>();
                for (int i = 0; i < listSize; i++) {
                    fragmentList.add(MonthStarsPagerFragment.newInstance(mMonthStarsProfileList.get(i)));
                }
                final Activity activity = getActivity();
                if (activity != null) {
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mVpMonthStar.setAdapter(new MonthStarsPageAdapter(getChildFragmentManager(), fragmentList));
                            refreshData(activity, mMonthStarsProfileList);
                        }
                    });
                }
            }

            @Override
            public void onFailed() {
                final Activity activity = getActivity();
                if (activity != null) {
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mIvArrowLeft.setVisibility(View.GONE);
                            mIvArrowRight.setVisibility(View.GONE);
                            Toast.makeText(activity, getString(R.string.get_month_data_faild), Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        }));
    }

    private void refreshData(Activity activity, List<MonthStarsProfile> monthStarsProfileList) {
        switch (mCurrentItem){
            case FIRST_PAGE:
                mIvArrowLeft.setVisibility(View.GONE);
                break;
            case LAST_PAGE:
                mIvArrowRight.setVisibility(View.GONE);
                break;
            default:
                mIvArrowLeft.setVisibility(View.VISIBLE);
                mIvArrowRight.setVisibility(View.VISIBLE);
                break;
        }
        mVpMonthStar.setCurrentItem(mCurrentItem);
        MonthStarsProfile currentMonthStar = monthStarsProfileList.get(mCurrentItem);
        String[] monthArray = activity.getResources().getStringArray(R.array.month);
        StringBuffer teamNameStringBuffer = new StringBuffer();
        String[] teamNameArray = currentMonthStar.getTeamName();
        if(teamNameArray != null && teamNameArray.length > 0){
            int teamCount = teamNameArray.length;
            teamNameStringBuffer.append(teamNameArray[0].trim());
            for (int index = 1; index < teamCount; index++){
                teamNameStringBuffer.append(", ");
                teamNameStringBuffer.append(teamNameArray[index].trim());
            }
        }
        mTvDescriptionOne.setText(String.format("%s %s %s %s %s", activity.getResources().getString(R.string.staff_of_the_month_in),
                monthArray[currentMonthStar.getMonth() - 1],
                activity.getResources().getString(R.string.comes_from),
                teamNameStringBuffer.toString(), activity.getResources().getString(R.string.corporate_system_team)));

        mTvDescriptionTwo.setText(String.format("%s %s %s %s %s!", currentMonthStar.getMemberName(),
                activity.getResources().getString(R.string.gets_highest_score_by_the_team_voting),
                monthArray[currentMonthStar.getMonth() - 1], activity.getResources().getString(R.string.goes_to),
                currentMonthStar.getMemberName()));

        mTvDescriptionThree.setText(String.format("%s %s %s", activity.getResources().getString(R.string.congratulations_to),
                currentMonthStar.getMemberName(), activity.getResources().getString(R.string.and_thanks_for_all_hard_working)));
    }

    @Override
    public String getTransactionTag() {
        return "fragment_month_stars_tag";
    }

    @Override
    public boolean isHideActionBar() {
        return false;
    }

    @Override
    public String getActionBarTitle() {
        return getString(R.string.actionbar_title_month_stars_page);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_arrow_left:
                if (mCurrentItem > FIRST_PAGE && mMonthStarsProfileList.size() != 0) {
                    mVpMonthStar.setCurrentItem(--mCurrentItem);
                    refreshData(getActivity(), mMonthStarsProfileList);
                }
                break;
            case R.id.iv_arrow_right:
                if (mCurrentItem < LAST_PAGE && mMonthStarsProfileList.size() != 0) {
                    mVpMonthStar.setCurrentItem(++mCurrentItem);
                    refreshData(getActivity(), mMonthStarsProfileList);
                }
                break;
        }
    }
}
