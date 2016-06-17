package com.xogrp.tkgz.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.xogrp.tkgz.R;
import com.xogrp.tkgz.TKGZApplication;
import com.xogrp.tkgz.View.CircleImageView;
import com.xogrp.tkgz.model.MonthStarsProfile;

/**
 * Created by wlao on 1/19/2016.
 */
public class MonthStarsPagerFragment extends AbstractTKGZFragment implements MyAccountFragment.OnRefreshMyAvatarListener {
    private static final String KEY_MONTH_STAR = "is_month_star";
    private CircleImageView mVAvatar;
    private TextView mTvMonthStarTitle;
    private TextView mTvMonthStarName;
    private TextView mTvMonthStarPosition;
    private MonthStarsProfile mMonthStarsProfile;

    public static MonthStarsPagerFragment newInstance(MonthStarsProfile monthStarsProfile){
        Bundle bundle = new Bundle();
        bundle.putSerializable(KEY_MONTH_STAR, monthStarsProfile);
        MonthStarsPagerFragment monthStarsPagerFragment = new MonthStarsPagerFragment();
        monthStarsPagerFragment.setArguments(bundle);
        return monthStarsPagerFragment;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_month_stars_pager_layout;
    }

    @Override
    protected void onTKGZCreateView(View rootView) {
        mVAvatar = (CircleImageView)rootView.findViewById(R.id.v_avatar);
        mTvMonthStarTitle = (TextView)rootView.findViewById(R.id.tv_month_star_title);
        mTvMonthStarName = (TextView)rootView.findViewById(R.id.tv_month_star_name);
        mTvMonthStarName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TKGZApplication.getInstance().getUserProfile().getId().equalsIgnoreCase(mMonthStarsProfile.getId())){
                    getOnScreenNavigationListener().navigateToMyAccountPage(TKGZApplication.getInstance().getUserProfile(), MonthStarsPagerFragment.this);
                }else {
                    getOnScreenNavigationListener().navigateToSelfIntroductionPage(mMonthStarsProfile);
                }
            }
        });
        mTvMonthStarPosition = (TextView)rootView.findViewById(R.id.tv_month_star_position);
    }

    @Override
    protected void onTKGZActivityCreated() {
        refreshMonthStarsPage();
    }

    private void refreshMonthStarsPage() {
        String[] monthArray = getResources().getStringArray(R.array.month_chinese);
        if (getArguments() != null) {
            mMonthStarsProfile = (MonthStarsProfile) getArguments().get(KEY_MONTH_STAR);
            mTvMonthStarTitle.setText(String.format("%s %s", monthArray[mMonthStarsProfile.getMonth() - 1], mMonthStarsProfile.getYear()));
            mTvMonthStarName.setText(mMonthStarsProfile.getMemberName());
            mTvMonthStarPosition.setText(mMonthStarsProfile.getPosition());
            boolean isMyAvatar = TKGZApplication.getInstance().getUserProfile().getId().equalsIgnoreCase(mMonthStarsProfile.getId());
            String avatar = isMyAvatar ? TKGZApplication.getInstance().getUserProfile().getAvatar() : mMonthStarsProfile.getAvatar();
            ImageLoader.getInstance().displayImage(avatar, mVAvatar);
        }
    }

    @Override
    public String getTransactionTag() {
        return "fragment_month_stars_page_tag";
    }

    @Override
    public boolean isHideActionBar() {
        return false;
    }

    @Override
    public String getActionBarTitle() {
        return null;
    }

    @Override
    public void onRefreshMyAvatar(boolean isAbleToRefresh) {
        if (isAbleToRefresh) {
            refreshMonthStarsPage();
        }
    }
}
