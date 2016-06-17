package com.xogrp.tkgz.fragment;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.xogrp.tkgz.R;
import com.xogrp.tkgz.TKGZApplication;
import com.xogrp.tkgz.View.CircleImageView;
import com.xogrp.tkgz.model.EmployeeProfile;

import java.util.ArrayList;

/**
 * Created by wlao on 12/25/2015.
 */
public class PhotoWallPageFragment extends AbstractTKGZFragment implements OnClickListener, MyAccountFragment.OnRefreshMyAvatarListener {
    private final static String KEY_PHOTOS_LIST = "is_photo_list";
    private static final int EACH_PAGE_PHOTOS_AMOUNT = 12;

    private RelativeLayout[] mRlPhotosArray = new RelativeLayout[EACH_PAGE_PHOTOS_AMOUNT];
    private int[] mRlPhotosIdArray = {R.id.rl_photo_1, R.id.rl_photo_2, R.id.rl_photo_3, R.id.rl_photo_4, R.id.rl_photo_5, R.id.rl_photo_6,
            R.id.rl_photo_7, R.id.rl_photo_8, R.id.rl_photo_9, R.id.rl_photo_10, R.id.rl_photo_11, R.id.rl_photo_12};

    private CircleImageView[] mIvPhotosArray = new CircleImageView[EACH_PAGE_PHOTOS_AMOUNT];
    private TextView[] mTvEmployeeNameArray = new TextView[EACH_PAGE_PHOTOS_AMOUNT];
    private ImageView[] mIvStarArray = new ImageView[EACH_PAGE_PHOTOS_AMOUNT];
    private ImageView[] mIvLeaderArray = new ImageView[EACH_PAGE_PHOTOS_AMOUNT];

    private String mPhotosUrl;
    private ArrayList<EmployeeProfile> mEmployeeNameList;

    public static PhotoWallPageFragment newInstance(ArrayList<EmployeeProfile> photoWallProfile) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(KEY_PHOTOS_LIST, photoWallProfile);
        PhotoWallPageFragment photoWallPageFragment = new PhotoWallPageFragment();
        photoWallPageFragment.setArguments(bundle);
        return photoWallPageFragment;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_photo_wall_page_layout;
    }

    @Override
    protected void onTKGZCreateView(View rootView) {
        for (int i = 0; i < EACH_PAGE_PHOTOS_AMOUNT; i++){
            mRlPhotosArray[i] = (RelativeLayout)rootView.findViewById(mRlPhotosIdArray[i]);
            mRlPhotosArray[i].setOnClickListener(PhotoWallPageFragment.this);
            mIvPhotosArray[i] = (CircleImageView) mRlPhotosArray[i].findViewById(R.id.iv_photos);
            mTvEmployeeNameArray[i] = (TextView) mRlPhotosArray[i].findViewById(R.id.tv_employee_name);
            mIvStarArray[i] = (ImageView) mRlPhotosArray[i].findViewById(R.id.iv_star);
            mIvLeaderArray[i] = (ImageView)mRlPhotosArray[i].findViewById(R.id.iv_leader);
        }

    }

    @Override
    protected void onTKGZActivityCreated() {
        mEmployeeNameList = (ArrayList<EmployeeProfile>) getArguments().get(KEY_PHOTOS_LIST);
        refreshPhotoPage();
    }

    private void refreshPhotoPage() {
        int currentPagePhotoAccount = mEmployeeNameList.size();
        for (int i = 0; i < currentPagePhotoAccount; i++) {
            if (mEmployeeNameList.get(i).getMemberName() != null){
                mRlPhotosArray[i].setVisibility(View.VISIBLE);
            }
            String[] memberFirstName = mEmployeeNameList.get(i).getMemberName().split(" ");
            String memberLastName = "";
            if (memberFirstName.length > 1){
                memberLastName = memberFirstName[1].substring(0,1);
            }
            mTvEmployeeNameArray[i].setText(String.format("%s %s", memberFirstName[0], memberLastName));
            boolean isMyAvatar = TKGZApplication.getInstance().getUserProfile().getId().equalsIgnoreCase(mEmployeeNameList.get(i).getId());
            mPhotosUrl = isMyAvatar ? TKGZApplication.getInstance().getUserProfile().getAvatar() : mEmployeeNameList.get(i).getAvatar();
            ImageLoader.getInstance().displayImage(mPhotosUrl, mIvPhotosArray[i]);
            if (mEmployeeNameList.get(i).isStarOfTheMonth()){
                mIvStarArray[i].setVisibility(View.VISIBLE);
            }
            if (mEmployeeNameList.get(i).isLeader()){
                mIvLeaderArray[i].setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public String getTransactionTag() {
        return "photo_wall_photos_fragment";
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
    public void onClick(View v) {
        for (int i = 0;i < EACH_PAGE_PHOTOS_AMOUNT; i++){
            if (v.getId() == mRlPhotosArray[i].getId()){
                if (TKGZApplication.getInstance().getUserProfile().getId().equalsIgnoreCase(mEmployeeNameList.get(i).getId())){
                    getOnScreenNavigationListener().navigateToMyAccountPage(TKGZApplication.getInstance().getUserProfile(), PhotoWallPageFragment.this);
                }else {
                    getOnScreenNavigationListener().navigateToSelfIntroductionPage(mEmployeeNameList.get(i));
                }
            }
        }
    }

    @Override
    public void onRefreshMyAvatar(boolean isAbleToRefresh) {
        if (isAbleToRefresh) {
            refreshPhotoPage();
        }
    }
}
