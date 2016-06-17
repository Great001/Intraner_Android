package com.xogrp.tkgz.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.xogrp.tkgz.R;
import com.xogrp.tkgz.Widget.TKGZCustomDialog;
import com.xogrp.tkgz.model.UserProfile;

import java.util.jar.Manifest;

/**
 * Created by wlao on 12/29/2015.
 */
public class SelfIntroductionFragment extends AbstractEmployeeIntroductionFragment {
    private TextView mTvInformationPhoneNumber;
    private TextView mTvSelfIntroduction;
    private static final int REQUEST_PERMISSION_CALL_PHONE = 2;
    private String mPhoneNumber;

    public static SelfIntroductionFragment newInstance(UserProfile employeeInformation) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(KEY_EMPLOYEE_INFORMATION, employeeInformation);
        SelfIntroductionFragment selfIntroductionFragment = new SelfIntroductionFragment();
        selfIntroductionFragment.setArguments(bundle);

        return selfIntroductionFragment;
    }

    @Override
    protected int getLayoutFragmentResourceId() {
        return R.layout.fragment_self_introduction;
    }

    @Override
    protected Bundle getBundle() {
        return getArguments();
    }

    @Override
    protected void onTKGZCreateView(View rootView) {
        super.onTKGZCreateView(rootView);
        mTvInformationPhoneNumber = (TextView) rootView.findViewById(R.id.tv_information_phone_number);
        mTvInformationPhoneNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String number = mTvInformationPhoneNumber.getText().toString().trim();
                if (!TextUtils.isEmpty(number)) {
                    mPhoneNumber = number;
                    (new TKGZCustomDialog(getActivity(), R.string.dialog_content_call, number, new TKGZCustomDialog.OnDialogActionCallback() {
                        @Override
                        public void onConfirmSelected() {
                            if (isPermissionGranted(android.Manifest.permission.CALL_PHONE, REQUEST_PERMISSION_CALL_PHONE, "We need permission to call phone.")) {
                                onCallPhoneNumber();
                            }
                        }
                    }, null, true)).show();
                }
            }

        });
        mTvInformationEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mTvInformationEmail.getText().toString().trim();
                if (!TextUtils.isEmpty(email)) {
                    Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:" + email));
                    try {
                        startActivity(intent);
                    } catch (Exception e) {
                        getLogger().error(getClass().getSimpleName(), e.getMessage());
                        showMessage(getString(R.string.dialog_message_not_found_email_app));
                    }
                }
            }
        });
        mTvSelfIntroduction = (TextView) rootView.findViewById(R.id.tv_self_introduction);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == REQUEST_PERMISSION_CALL_PHONE) {
            onCallPhoneNumber();
        }
    }

    @Override
    protected void onTKGZActivityCreated() {
        super.onTKGZActivityCreated();
        if (mEmployeeProfile != null) {
            mTvInformationPhoneNumber.setText(mEmployeeProfile.getPhoneNumber());
            mTvSelfIntroduction.setText(mEmployeeProfile.getIntroduction());
        }
    }

    private void onCallPhoneNumber() {
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + mPhoneNumber));
        startActivity(intent);
    }

    @Override
    public String getTransactionTag() {
        return "self_introduction_fragment";
    }

    @Override
    public boolean isHideActionBar() {
        return false;
    }

    @Override
    public String getActionBarTitle() {
        return getString(R.string.actionbar_title_self_introduction_page);
    }

    @Override
    public boolean isEmptyTopMargin() {
        return true;
    }

}
