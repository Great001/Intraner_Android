package com.xogrp.tkgz.fragment;

import android.app.Activity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.xogrp.tkgz.R;
import com.xogrp.tkgz.TKGZApplication;
import com.xogrp.tkgz.Widget.TKGZCustomDialog;
import com.xogrp.tkgz.Widget.TKGZCustomHomeMenuDialog;
import com.xogrp.tkgz.provider.ChangePasswordProvider;
import com.xogrp.tkgz.spi.ChangePasswordApiCallBack;

public class ChangePasswordFragment extends AbstractTKGZFragment implements TextView.OnEditorActionListener{
    private EditText mEtOldPassword, mEtNewPassword, mEtNewPasswordConfirm;
    private Button mBtnAmend;

    @Override
    protected void onTKGZAttach(Activity activity) {
        super.onTKGZAttach(activity);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.change_password_layout;
    }

    @Override
    protected void onTKGZCreateView(View rootView) {
        mEtOldPassword = (EditText) rootView.findViewById(R.id.et_old_password);
        mEtNewPassword = (EditText) rootView.findViewById(R.id.et_new_password);
        mEtNewPasswordConfirm = (EditText) rootView.findViewById(R.id.et_repeat_new_password);
        mEtNewPasswordConfirm.setOnEditorActionListener(this);
        mBtnAmend = (Button) rootView.findViewById(R.id.btn_amend);
        mBtnAmend.setOnClickListener(mOnAmendListener);
    }

    @Override
    protected void onTKGZActivityCreated() {
    }

    @Override
    public String getTransactionTag() {
        return "change_password_fragment";
    }

    @Override
    public boolean isHideActionBar() {
        return false;
    }

    @Override
    public String getActionBarTitle() {
        return getString(R.string.actionbar_title_change_password_page);
    }

    @Override
    protected int getMenuResourceId() {
        return -1;
    }

    private View.OnClickListener mOnAmendListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            hideKeyboard();
            changePassword();
        }
    };

    private void changePassword() {
        String oldPassword = mEtOldPassword.getText().toString();
        String newPassword = mEtNewPassword.getText().toString();
        String newPasswordConfirm = mEtNewPasswordConfirm.getText().toString();
        if (oldPassword.isEmpty() || newPassword.isEmpty() || newPasswordConfirm.isEmpty()) {
            showDialog(R.string.txt_btn_title, R.string.change_password_empty_tip);
        } else if (newPassword.length() < 8){
            showDialog(R.string.txt_btn_title, R.string.wrong_new_password_too_short_tip);
        } else if (!newPassword.equals(newPasswordConfirm)) {
            showDialog(R.string.txt_btn_title, R.string.wrong_repeat_new_password_tip);
        } else {
            changePassword(oldPassword, newPassword, newPasswordConfirm);
        }
    }

    private void changePassword(String oldPassword, String newPassword, String newPasswordConfirm){
        final Activity activity = getActivity();
        showSpinner();
        initLoader(ChangePasswordProvider.getChangePasswordProvider(this, TKGZApplication.getInstance().getUserProfile(), oldPassword, newPassword, newPasswordConfirm, new ChangePasswordApiCallBack.OnChangePasswordListener() {
            @Override
            public void OnSuccess() {
                final Activity activity = getActivity();
                if (activity != null) {
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            new TKGZCustomHomeMenuDialog(activity, R.string.dialog_title_reminder, R.string.change_password_success,
                                    "", "", R.string.dialog_ok_button, new TKGZCustomDialog.OnDialogActionCallback() {
                                @Override
                                public void onConfirmSelected() {
                                    getOnScreenNavigationListener().logOutFromCurrentPage();
                                    getOnScreenNavigationListener().navigateToLoginPage();
                                    TKGZApplication.getInstance().clearUserProfile();
                                }
                            }, null).show();
                        }
                    });
                }
            }

            @Override
            public void OnFailed() {
                final Activity activity = getActivity();
                if (activity != null) {
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(activity, activity.getResources().getString(R.string.wrong_old_password_tip), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        }));
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        boolean isImeActionDone = false;
        if (actionId == EditorInfo.IME_ACTION_DONE) {
            changePassword();
            isImeActionDone = true;
        }
        return isImeActionDone;
    }
}
