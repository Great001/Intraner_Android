package com.xogrp.tkgz.fragment;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.xogrp.tkgz.R;
import com.xogrp.tkgz.TKGZApplication;
import com.xogrp.tkgz.View.CircleImageView;
import com.xogrp.tkgz.model.UserProfile;

import java.io.File;

public class MasterHomeFragment extends AbstractCropImageFragment
        implements OnClickListener {
    private CircleImageView mIvHeadImage;
    public static final String APP_DIR = Environment.getExternalStorageDirectory().getPath() + "/TKGZ/";
    private static final String IMAGE_PATH = APP_DIR + "icon_cache/";
    private static final String AVATAR_FILE_NAME = "admin_avatar.jpeg";
    private UserProfile userProfile=TKGZApplication.getInstance().getUserProfile();
    private TextView mTvName;
    private ImageView mIvBackground;
    private File mAvatar = null;
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

    @Override
    protected void onTKGZAttach(Activity activity) {
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.master_home_layout;
    }

    @Override
    protected void onTKGZCreateView(View rootView) {
        rootView.findViewById(R.id.tv_personnel_manage).setOnClickListener(this);
        rootView.findViewById(R.id.tv_integral_manage).setOnClickListener(this);
        rootView.findViewById(R.id.tv_activity_manage).setOnClickListener(this);
        mIvHeadImage = (CircleImageView) rootView.findViewById(R.id.iv_icon);
        mTvName= (TextView) rootView.findViewById(R.id.tv_name);
        mIvBackground= (ImageView) rootView.findViewById(R.id.iv_background);
        mTvName.setText(userProfile.getMemberName());
        ImageLoader.getInstance().displayImage(userProfile.getAvatar(), mIvHeadImage, mAvatarOptions);
        ImageLoader.getInstance().displayImage(userProfile.getBackgroundUri(), mIvBackground, mBackgroundOptions);
        mIvHeadImage.setOnClickListener(this);
        File appDir = new File(APP_DIR);
        folderIsExist(appDir);
        File imagePath = new File(IMAGE_PATH);
        folderIsExist(imagePath);
        mAvatar = new File(IMAGE_PATH, AVATAR_FILE_NAME);
    }


    @Override
    protected void onTKGZActivityCreated() {

    }

    @Override
    public String getTransactionTag() {
        return "master_home_fragment";
    }

    @Override
    public boolean isHideActionBar() {
        return false;
    }

    @Override
    public String getActionBarTitle() {
        return getString(R.string.actionbar_title_master_page);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_personnel_manage:
                getOnScreenNavigationListener().navigateToPersonnelManagePage();
                break;
            case R.id.tv_integral_manage:
                getOnScreenNavigationListener().navigateToInquireManagerPage();
                break;
            case R.id.tv_activity_manage:
                getOnScreenNavigationListener().navigateToActivityManagePage();
                break;
            case R.id.iv_icon:
                break;
            default:
                break;
        }
    }

    @Override
    public boolean isEmptyTopMargin() {
        return true;
    }

}
