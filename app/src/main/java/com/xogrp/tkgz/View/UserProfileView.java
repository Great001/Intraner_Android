package com.xogrp.tkgz.View;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.xogrp.tkgz.R;
import com.xogrp.tkgz.TKGZApplication;
import com.xogrp.tkgz.model.UserProfile;
import com.xogrp.tkgz.util.TKGZUtil;

import java.io.File;
import java.io.FileNotFoundException;

public abstract class UserProfileView {
    static private DisplayImageOptions mAvatarOptions = new DisplayImageOptions.Builder()
            .cacheOnDisk(true)
            .cacheInMemory(true)
            .considerExifParams(true)
            .resetViewBeforeLoading(false)
            .bitmapConfig(Bitmap.Config.RGB_565)
            .imageScaleType(ImageScaleType.EXACTLY)
            .showImageOnLoading(R.drawable.header_big_default)
            .showImageForEmptyUri(R.drawable.header_big_default)
            .showImageOnFail(R.drawable.header_big_default)
            .build();
    static private DisplayImageOptions mBackgroundOptions = new DisplayImageOptions.Builder()
            .cacheOnDisk(true)
            .cacheInMemory(true)
            .considerExifParams(true)
            .resetViewBeforeLoading(false)
            .bitmapConfig(Bitmap.Config.RGB_565)
            .imageScaleType(ImageScaleType.EXACTLY)
            .showImageOnLoading(R.drawable.home_bg)
            .showImageForEmptyUri(R.drawable.home_bg)
            .showImageOnFail(R.drawable.home_bg)
            .build();
    private ImageView mIvBackground;
    private CircleImageView mCivAvatar;
    private TextView mTvName;
    private TextView mTvAnnualRewardPoints;
    private TextView mTvMonthlyRewardPoints;
    private ImageView mIvRewardPointsGrade;
    private TextView mTvRewardPointsGrade;
    private TextView mTvPosition;
    private TextView mTvTeamRewardPoints;

    public UserProfileView(View rootView) {
        mIvBackground = (ImageView) rootView.findViewById(getBackgroundImageViewId());
        mCivAvatar = (CircleImageView) rootView.findViewById(getAvatarImageViewId());
        mTvName = (TextView) rootView.findViewById(getNameTextViewId());
        mTvAnnualRewardPoints = (TextView) rootView.findViewById(getAnnualRewardPointsTextViewId());
        mTvMonthlyRewardPoints = (TextView) rootView.findViewById(getMonthlyRewardPointsTextViewId());
        mTvRewardPointsGrade = (TextView) rootView.findViewById(getRewardPointsGradeTextViewId());
        mIvRewardPointsGrade = (ImageView) rootView.findViewById(getRewardPointsGradeImageViewId());
        mTvPosition = (TextView) rootView.findViewById(getPositionTextViewId());
        mTvTeamRewardPoints = (TextView) rootView.findViewById(getTeamRewardPointsTextViewId());
    }

    public abstract int getBackgroundImageViewId();

    public abstract int getAvatarImageViewId();

    public abstract int getNameTextViewId();

    public abstract int getAnnualRewardPointsTextViewId();

    public abstract int getMonthlyRewardPointsTextViewId();

    public abstract int getRewardPointsGradeTextViewId();

    public abstract int getRewardPointsGradeImageViewId();

    public abstract int getPositionTextViewId();

    public abstract int getTeamRewardPointsTextViewId();

    public void render(Context context, UserProfile userProfile) {
        ImageLoader.getInstance().displayImage(userProfile.getAvatar(), mCivAvatar, mAvatarOptions);
        ImageLoader.getInstance().displayImage(userProfile.getBackgroundUri(), mIvBackground, mBackgroundOptions);

        int annualRewardPoints = userProfile.getIntegralForYear();
        int level = TKGZUtil.getLevel(annualRewardPoints);
        mTvName.setText(userProfile.getMemberName());
        mIvRewardPointsGrade.setImageBitmap(BitmapFactory.decodeResource(context.getResources(), TKGZUtil.getLevelImage(level)));
        mTvAnnualRewardPoints.setText(String.valueOf(annualRewardPoints));
        mTvMonthlyRewardPoints.setText(String.valueOf(userProfile.getIntegralForMonth()));
        mTvRewardPointsGrade.setText(TKGZUtil.getLevelAppellation(level));
        mTvPosition.setText(userProfile.getPosition());
        if (userProfile.isLeader()) {
            if (mTvTeamRewardPoints != null) {
                mTvTeamRewardPoints.setText(String.valueOf(userProfile.getTeamIntegral()));
            }
        }
    }

    public void setOnAvatarClickListener(View.OnClickListener onAvatarClickListener) {
        mCivAvatar.setOnClickListener(onAvatarClickListener);
    }

    public void setOnBackgroundClickListener(View.OnClickListener onBackgroundClickListener) {
        mIvBackground.setOnClickListener(onBackgroundClickListener);
    }

    public void onRewardPointsUpdate() {
        UserProfile userProfile = TKGZApplication.getInstance().getUserProfile();
        int integral = userProfile.getIntegralForYear();
        mTvAnnualRewardPoints.setText(String.valueOf(integral));
        integral = userProfile.getIntegralForMonth();
        mTvMonthlyRewardPoints.setText(String.valueOf(integral));
    }

    public void onAvatarUpdate(Context context, File avatarFile)
            throws FileNotFoundException {
        Bitmap bitmap = avatarFile != null ? decodeUriAsBitmap(context, Uri.fromFile(avatarFile)) : null;
        if (bitmap != null) {
            mCivAvatar.setImageBitmap(bitmap);
        } else {
            ImageLoader.getInstance().displayImage(TKGZApplication.getInstance().getUserProfile().getAvatar(), mCivAvatar, mAvatarOptions);
        }
    }

    public void onBackgroundUpdate(Context context, File backgroundFile)
            throws FileNotFoundException {
        Bitmap bitmap = backgroundFile != null ? decodeUriAsBitmap(context, Uri.fromFile(backgroundFile)) : null;
        if (bitmap != null) {
            mIvBackground.setImageBitmap(bitmap);
        } else {
            ImageLoader.getInstance().displayImage(TKGZApplication.getInstance().getUserProfile().getBackgroundUri(), mIvBackground, mBackgroundOptions);
        }
    }

    private Bitmap decodeUriAsBitmap(Context context, Uri uri)
            throws FileNotFoundException {
        return BitmapFactory.decodeStream(context.getContentResolver().openInputStream(uri));
    }
}
