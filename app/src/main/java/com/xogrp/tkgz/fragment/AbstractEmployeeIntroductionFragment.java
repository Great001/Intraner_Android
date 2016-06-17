package com.xogrp.tkgz.fragment;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.xogrp.tkgz.R;
import com.xogrp.tkgz.View.UserProfileView;
import com.xogrp.tkgz.model.UserProfile;
import com.xogrp.tkgz.util.TKGZUtil;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * Created by wlao on 1/5/2016.
 */
public abstract class AbstractEmployeeIntroductionFragment extends AbstractCropImageFragment {
    protected static final String KEY_EMPLOYEE_INFORMATION = "is_employee_information";
    protected UserProfile mEmployeeProfile;
    private ImageView mIvLeader;
    private TextView mTvTips;
    private ProgressBar mPbarIntegral;
    private TextView mTvInformationSection;
    private TextView mTvInformationBirthday;
    protected TextView mTvInformationEmail;
    private int[] mProgressbarMax;
    private int[] mLevelTotalScore;

    private static class BasicUserProfileView extends UserProfileView {
        public BasicUserProfileView(View rootView) {
            super(rootView);
        }

        @Override
        public int getBackgroundImageViewId() {
            return R.id.iv_employee_bg;
        }

        @Override
        public int getAvatarImageViewId() {
            return R.id.iv_employee_icon;
        }

        @Override
        public int getNameTextViewId() {
            return R.id.tv_employee_name;
        }

        @Override
        public int getAnnualRewardPointsTextViewId() {
            return R.id.tv_year_integral;
        }

        @Override
        public int getMonthlyRewardPointsTextViewId() {
            return R.id.tv_month_integral;
        }

        @Override
        public int getRewardPointsGradeTextViewId() {
            return R.id.tv_grade_name;
        }

        @Override
        public int getRewardPointsGradeImageViewId() {
            return R.id.iv_grade;
        }

        @Override
        public int getPositionTextViewId() {
            return R.id.tv_information_position;
        }

        @Override
        public int getTeamRewardPointsTextViewId() {
            return -1;
        }
    }
    private UserProfileView mUserProfileView;

    protected abstract int getLayoutFragmentResourceId();

    protected abstract Bundle getBundle();

    protected void onClickChildListener(View view) {
    }

    @Override
    protected int getLayoutResId() {
        return getLayoutFragmentResourceId();
    }

    @Override
    protected void onTKGZCreateView(View rootView) {
        mUserProfileView = new BasicUserProfileView(rootView);

        mUserProfileView.setOnBackgroundClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickChildListener(view);
            }
        });

        mUserProfileView.setOnAvatarClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickChildListener(view);
            }
        });
        mIvLeader = (ImageView) rootView.findViewById(R.id.iv_leader);
        mTvTips = (TextView) rootView.findViewById(R.id.tv_tipsscore);
        mPbarIntegral = (ProgressBar) rootView.findViewById(R.id.pbar_integral);

        mTvInformationSection = (TextView) rootView.findViewById(R.id.tv_information_section);
        mTvInformationBirthday = (TextView) rootView.findViewById(R.id.tv_information_birthday);
        mTvInformationEmail = (TextView) rootView.findViewById(R.id.tv_information_email);

        mProgressbarMax = getResources().getIntArray(R.array.progressbar_max_item);
        mLevelTotalScore = getResources().getIntArray(R.array.level_total_score_item);
    }

    @Override
    protected void onTKGZActivityCreated() {
        Bundle bundle = getBundle();
        if (bundle != null) {
            mEmployeeProfile = (UserProfile) bundle.getSerializable(KEY_EMPLOYEE_INFORMATION);
            if (mEmployeeProfile != null) {

                mUserProfileView.render(getActivity(), mEmployeeProfile);

                mIvLeader.setVisibility(mEmployeeProfile.isLeader() ? View.VISIBLE : View.INVISIBLE);
                mTvInformationSection.setText(mEmployeeProfile.toString());
                mTvInformationBirthday.setText(mEmployeeProfile.getBirthday());
                mTvInformationEmail.setText(mEmployeeProfile.getEmail());
                showRewardPoints();
            }
        }
    }

    private void showRewardPoints() {
        int currentScore = mEmployeeProfile.getIntegralForYear();
        int level = TKGZUtil.getLevel(currentScore);
        mPbarIntegral.setIndeterminate(false);

        if (level < 4) {
            int tipsScore = (mLevelTotalScore[level]) - currentScore;
            mTvTips.setText(String.format("%s / %s", currentScore, (mLevelTotalScore[level])));
            mPbarIntegral.setMax(mProgressbarMax[level]);
            mPbarIntegral.setProgress(mProgressbarMax[level] - tipsScore);
        } else {
            mTvTips.setText(getString(R.string.expectation_text));
            mPbarIntegral.setProgress(mLevelTotalScore[level - 1]);
        }
    }

    protected void updateAvatar(File file)
    throws FileNotFoundException{
        mUserProfileView.onAvatarUpdate(getActivity(), file);
    }

    protected void updateBackground(File file)
    throws FileNotFoundException {
        mUserProfileView.onBackgroundUpdate(getActivity(), file);
    }
}
