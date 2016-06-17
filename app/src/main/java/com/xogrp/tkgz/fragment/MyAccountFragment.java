package com.xogrp.tkgz.fragment;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.xogrp.tkgz.R;
import com.xogrp.tkgz.TKGZApplication;
import com.xogrp.tkgz.Widget.TextWatcherListener;
import com.xogrp.tkgz.activity.MainActivity;
import com.xogrp.tkgz.listener.OnRefreshUIListener;
import com.xogrp.tkgz.model.SaveMyAccountProfile;
import com.xogrp.tkgz.model.UserProfile;
import com.xogrp.tkgz.provider.SaveMyAccountProvider;
import com.xogrp.tkgz.spi.SaveMyAccountCallBack;

import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * Created by wlao on 1/4/2016.
 */
public class MyAccountFragment extends AbstractEmployeeIntroductionFragment implements MainActivity.OnRefreshAvatarCallBack, MainActivity.OnRefreshBackgroundCallBack, TextWatcherListener.OnTextWatcherListener {
    private static final String MY_ACCOUNT_FRAGMENT_TAG = "my_account_fragment";
    private static final int PERMISSION_REQUEST_READ_EXTERNAL_STORAGE = 2;
    private static final int PHONE_NUM_INDEX = 0;
    private static final int INFORMATION_INDEX = 1;
    private TextView mTvInformationSuperior;
    private EditText mEtInformationPhoneNumber;
    private EditText mEtSelfIntroduction;
    private Button mBtnSave;
    private int mIsAbleToClick;
    private OnRefreshUIListener mOnRefreshUIListener;
    private String mTemp;
    private boolean mIsSavedSuccessfully;
    private boolean[] mTextChangedStatusArray;
    private OnRefreshMyAvatarListener mOnRefreshMyAvatarListener;

    public static MyAccountFragment newInstance(UserProfile userProfile, OnRefreshMyAvatarListener onRefreshMyAvatarListener) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(KEY_EMPLOYEE_INFORMATION, userProfile);
        MyAccountFragment myAccountFragment = new MyAccountFragment();
        if (onRefreshMyAvatarListener != null) {
            myAccountFragment.setOnUpdateAvatarListener(onRefreshMyAvatarListener);
        }
        myAccountFragment.setArguments(bundle);

        return myAccountFragment;
    }

    @Override
    protected int getLayoutFragmentResourceId() {
        return R.layout.fragment_my_account_layout;
    }

    @Override
    protected Bundle getBundle() {
        return getArguments();
    }

    @Override
    protected void onClickChildListener(View view) {
        mIsSavedSuccessfully = false;

        switch (view.getId()) {
            case R.id.iv_employee_icon:
                if (isPermissionGranted(Manifest.permission.READ_EXTERNAL_STORAGE,
                        AbstractCropImageFragment.SELECT_A_PICTURE_FOR_AVATAR, getResources().getString(R.string.toast_no_permission))) {
                    selectImage(getActivity(), Uri.fromFile(getAvatarFile()), AbstractCropImageFragment.SELECT_A_PICTURE_FOR_AVATAR);
                }
                break;
            case R.id.iv_employee_bg:
                if (isPermissionGranted(Manifest.permission.READ_EXTERNAL_STORAGE,
                        AbstractCropImageFragment.SELECT_A_PICTURE_FOR_BACKGROUND, getResources().getString(R.string.toast_no_permission))) {
                    selectImage(getActivity(), Uri.fromFile(getBackgroundFile()), AbstractCropImageFragment.SELECT_A_PICTURE_FOR_BACKGROUND);
                }
                break;
        }
    }

    @Override
    protected void onTKGZAttach(Activity activity) {
        super.onTKGZAttach(activity);
        ((MainActivity) activity).addOnRefreshAvatarCallBack(this);
        ((MainActivity) activity).addOnRefreshBackgroundCallBack(this);
        mOnRefreshUIListener = (OnRefreshUIListener) activity;
    }

    @Override
    protected void onTKGZCreateView(View rootView) {
        super.onTKGZCreateView(rootView);
        mTvInformationSuperior = (TextView) rootView.findViewById(R.id.tv_information_superior);
        final LinearLayout llInformation = (LinearLayout) rootView.findViewById(R.id.ll_information);
        mEtInformationPhoneNumber = (EditText) rootView.findViewById(R.id.et_information_phone_number);
        mEtInformationPhoneNumber.addTextChangedListener(new TextWatcherListener(this, mEtInformationPhoneNumber));
        mEtSelfIntroduction = (EditText) rootView.findViewById(R.id.et_self_introduction);
        mEtSelfIntroduction.addTextChangedListener(new TextWatcherListener(this, mEtSelfIntroduction));
        mBtnSave = (Button) rootView.findViewById(R.id.btn_save);
        mBtnSave.setEnabled(false);
        mBtnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String phone = mEtInformationPhoneNumber.getText().toString().trim();
                final String introduction = mEtSelfIntroduction.getText().toString().trim();
                SaveMyAccountProfile saveMyAccountProfile = new SaveMyAccountProfile();
                saveMyAccountProfile.setAvatar(bitmapToBase64(decodeUriAsBitmap(getActivity(), Uri.fromFile(getAvatarFile()))));
                saveMyAccountProfile.setBackground(bitmapToBase64(decodeUriAsBitmap(getActivity(), Uri.fromFile(getBackgroundFile()))));
                saveMyAccountProfile.setPhoneNumber(phone);
                saveMyAccountProfile.setSelfDescription(introduction);
                hideKeyboard();
                mEtInformationPhoneNumber.clearFocus();
                mEtSelfIntroduction.clearFocus();
                llInformation.setFocusable(true);
                showSpinner();
                initLoader(SaveMyAccountProvider.saveMyAccount(MyAccountFragment.this, TKGZApplication.getInstance().getUserProfile(), saveMyAccountProfile, new SaveMyAccountCallBack.OnSaveMyAccountListener() {
                    @Override
                    public void onUpDataSuccess(String avatarUrl, String backgroundUrl) {
                        TKGZApplication.getInstance().getUserProfile().setPhoneNumber(phone);
                        TKGZApplication.getInstance().getUserProfile().setIntroduction(introduction);
                        if (!avatarUrl.equals("null")) {
                            TKGZApplication.getInstance().getUserProfile().setAvatar(avatarUrl);
                        }
                        if (!backgroundUrl.equals("null")) {
                            TKGZApplication.getInstance().getUserProfile().setBackgroundUri(backgroundUrl);
                        }

                        final Activity activity = getActivity();
                        if (activity != null) {
                            activity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    mIsAbleToClick = 0;
                                    mTextChangedStatusArray[PHONE_NUM_INDEX] = false;
                                    mTextChangedStatusArray[INFORMATION_INDEX] = false;
                                    mIsSavedSuccessfully = true;
                                    mOnRefreshUIListener.callRefreshAvatar(null);
                                    mOnRefreshUIListener.callRefreshBackground(null);
                                    if (mOnRefreshMyAvatarListener != null) {
                                        mOnRefreshMyAvatarListener.onRefreshMyAvatar(true);
                                    }
                                    Toast.makeText(activity, activity.getString(R.string.save_success_text), Toast.LENGTH_LONG).show();
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
                                    Toast.makeText(activity, activity.getString(R.string.save_failed_text), Toast.LENGTH_LONG).show();
                                }
                            });
                        }
                    }
                }));
            }
        });
        openOrCreatePicFile();
    }

    @Override
    protected void onTKGZActivityCreated() {
        super.onTKGZActivityCreated();
        mTextChangedStatusArray = new boolean[2];
        if (mEmployeeProfile != null) {
            mTvInformationSuperior.setText(mEmployeeProfile.getSupervisorName());
            mEtInformationPhoneNumber.setText(mEmployeeProfile.getPhoneNumber());
            mEtSelfIntroduction.setText(mEmployeeProfile.getIntroduction());
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            switch (requestCode) {
                case AbstractCropImageFragment.SELECT_A_PICTURE_FOR_AVATAR:
                    selectImage(getActivity(), Uri.fromFile(getAvatarFile()), AbstractCropImageFragment.SELECT_A_PICTURE_FOR_AVATAR);
                    break;
                case AbstractCropImageFragment.SELECT_A_PICTURE_FOR_BACKGROUND:
                    selectImage(getActivity(), Uri.fromFile(getBackgroundFile()), AbstractCropImageFragment.SELECT_A_PICTURE_FOR_BACKGROUND);
                    break;
            }
        } else {
            showToast("No Permission.");
        }
    }

    @Override
    public String getTransactionTag() {
        return MY_ACCOUNT_FRAGMENT_TAG;
    }

    @Override
    public boolean isHideActionBar() {
        return false;
    }

    @Override
    public String getActionBarTitle() {
        return getString(R.string.actionbar_title_my_account_page);
    }

    @Override
    public boolean isEmptyTopMargin() {
        return true;
    }


    private void checkSaveButtonStatus() {
        if (mIsAbleToClick > 0 || mTextChangedStatusArray[PHONE_NUM_INDEX] || mTextChangedStatusArray[INFORMATION_INDEX]) {
            mBtnSave.setBackgroundResource(R.drawable.btn_my_account_green_shape);
            mBtnSave.setEnabled(true);
        } else {
            mBtnSave.setBackgroundResource(R.drawable.btn_my_account_gray_shape);
            mBtnSave.setEnabled(false);
        }
    }

    private String getLimitSubstring(String inputStr) {
        int originLen = inputStr.length();
        int resultLen = 0;
        for (int i = 0; i < originLen; i++) {
            try {
                if (inputStr.substring(i, i + 1).getBytes("GBK").length == 2) {
                    resultLen += 2;
                } else {
                    resultLen++;
                }
            } catch (UnsupportedEncodingException e) {
                Log.d(MY_ACCOUNT_FRAGMENT_TAG, e.getMessage());
            }
            if (resultLen > 200) {
                inputStr = inputStr.substring(0, i);
                break;
            }
        }
        return inputStr;
    }

    private void checkTextChanged(int viewId, String before, String after) {
        int arrayIndex = viewId == R.id.et_information_phone_number ? PHONE_NUM_INDEX : INFORMATION_INDEX;
        if (!after.equals(before)) {
            if (!mTextChangedStatusArray[arrayIndex]) {
                mTextChangedStatusArray[arrayIndex] = true;
                checkSaveButtonStatus();
            }
        } else {
            mTextChangedStatusArray[arrayIndex] = false;
            checkSaveButtonStatus();
        }
    }

    @Override
    public void onRefreshAvatar() {
        final Activity activity = getActivity();
        if (activity != null) {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    try {
                        File file = null;
                        if (!mIsSavedSuccessfully) {
                            file = getAvatarFile();
                            mIsAbleToClick++;
                        }
                        updateAvatar(file);
                    } catch (FileNotFoundException fnfe) {
                        getLogger().error("Error in updating avatar", fnfe.getMessage());
                    }
                    checkSaveButtonStatus();
                }
            });
        }
    }

    @Override
    public void onRefreshBackground() {
        final Activity activity = getActivity();
        if (activity != null) {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    try {
                        File file = null;
                        if (!mIsSavedSuccessfully) {
                            file = getBackgroundFile();
                            mIsAbleToClick++;
                        }
                        updateBackground(file);
                        checkSaveButtonStatus();
                    } catch (FileNotFoundException fnfe) {
                        getLogger().error("Error in updating background image", fnfe.getMessage());
                    }
                }
            });
        }
    }

    @Override
    public void onDestroy() {
        final Activity activity = getActivity();
        if (activity != null) {
            ((MainActivity) activity).removeOnRefreshAvatarCallBack(this);
            ((MainActivity) activity).removeOnRefreshBackgroundCallBack(this);
        }
        getAvatarFile().delete();
        getBackgroundFile().delete();
        super.onDestroy();
    }

    public static String bitmapToBase64(Bitmap bitmap) {
        String result = null;
        ByteArrayOutputStream byteArrayOutputStream = null;
        try {
            if (bitmap != null) {
                byteArrayOutputStream = compressImage(bitmap);
                byteArrayOutputStream.flush();
                byteArrayOutputStream.close();
                byte[] bitmapBytes = byteArrayOutputStream.toByteArray();
                result = Base64.encodeToString(bitmapBytes, Base64.DEFAULT);
            }
        } catch (IOException e) {
            LoggerFactory.getLogger("MyAccountFragment").error("bitmapToBase64", e.getMessage());
        } finally {
            try {
                if (byteArrayOutputStream != null) {
                    byteArrayOutputStream.flush();
                    byteArrayOutputStream.close();
                }
            } catch (IOException e) {
                Log.d(MY_ACCOUNT_FRAGMENT_TAG, e.getMessage());
            }
        }
        return result;
    }

    @Override
    public void onTextChanged(View view, String text) {
        switch (view.getId()) {
            case R.id.et_information_phone_number:
                if (text.length() <= 11) {
                    checkTextChanged(R.id.et_information_phone_number, mEmployeeProfile.getPhoneNumber(), text.trim());
                }
                mTemp = text;
                break;
            case R.id.et_self_introduction:
                checkTextChanged(R.id.et_self_introduction, mEmployeeProfile.getIntroduction(), text.trim());
                mTemp = text;
                break;
        }
    }

    @Override
    public void afterTextChanged(View view) {
        String limitSubstring;
        switch (view.getId()) {
            case R.id.et_information_phone_number:
                if (!TextUtils.isEmpty(mTemp) && mTemp.length() > 11) {
                    limitSubstring = mTemp.substring(0, 11);
                    showWordLimitTip(view, limitSubstring, getResources().getString(R.string.self_introduction_phone_num_too_long_tip));
                }
                break;
            case R.id.et_self_introduction:
                if (!TextUtils.isEmpty(mTemp)) {
                    limitSubstring = getLimitSubstring(mTemp);
                    if (!TextUtils.isEmpty(limitSubstring) && !limitSubstring.equals(mTemp)) {
                        showWordLimitTip(view, limitSubstring, getResources().getString(R.string.self_introduction_too_long_tip));
                    }
                }
                break;
        }
    }

    private static ByteArrayOutputStream compressImage(Bitmap image) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        int options = 100;
        while (byteArrayOutputStream.toByteArray().length / 1024 > 2000) {
            byteArrayOutputStream.reset();
            options -= 10;
            image.compress(Bitmap.CompressFormat.JPEG, options, byteArrayOutputStream);
        }
        return byteArrayOutputStream;
    }

    private void showWordLimitTip(View view, String limitText, String tip) {
        showToast(tip);
        ((EditText) view).setText(limitText);
        ((EditText) view).setSelection(limitText.length());
    }

    public void setOnUpdateAvatarListener(OnRefreshMyAvatarListener onRefreshMyAvatarListener) {
        this.mOnRefreshMyAvatarListener = onRefreshMyAvatarListener;
    }

    public interface OnRefreshMyAvatarListener {
        void onRefreshMyAvatar(boolean isAbleToRefresh);
    }

}
