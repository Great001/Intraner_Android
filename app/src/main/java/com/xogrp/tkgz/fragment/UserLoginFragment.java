package com.xogrp.tkgz.fragment;

import android.app.Activity;
import android.text.Editable;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.xogrp.tkgz.R;
import com.xogrp.tkgz.TKGZApplication;
import com.xogrp.tkgz.model.UserProfile;
import com.xogrp.tkgz.provider.LoginProvider;
import com.xogrp.tkgz.spi.LoginApiCallBack;

/**
 * Created by lzhou on 2015/7/31.
 */
public class UserLoginFragment extends AbstractTKGZFragment implements TextView.OnEditorActionListener {

    private EditText mEtEmail;
    private EditText mEtPwd;

    @Override
    protected int getLayoutResId() {
        return R.layout.my_login_layout;
    }

    @Override
    protected void onTKGZCreateView(View rootView) {
        mEtEmail = (EditText) rootView.findViewById(R.id.et_email);
        mEtPwd = (EditText) rootView.findViewById(R.id.et_pwd);
        mEtPwd.setOnEditorActionListener(this);
        mEtPwd.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                return keyCode == KeyEvent.ACTION_DOWN && event.getAction() == KeyEvent.ACTION_DOWN;
            }
        });

        rootView.findViewById(R.id.tv_retrieve_pwd).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getOnScreenNavigationListener().navigateToRetrievePasswordPage();
            }
        });
        rootView.findViewById(R.id.btn_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onLogin();
            }
        });
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
    }

    private void onLogin() {
        Editable email = mEtEmail.getText();
        Editable password = mEtPwd.getText();
        if (TextUtils.isEmpty(email)) {
            showDialog(R.string.txt_btn_title, R.string.txt_username_invalid);
        } else if (TextUtils.isEmpty(password)) {
            showDialog(R.string.txt_btn_title, R.string.txt_pwd_invalid);
        } else {
            UserProfile user = new UserProfile();
            user.setEmail(email.toString());
            user.setPassword(password.toString());
            hideKeyboard();

            showSpinner();
            //login
            initLoader(LoginProvider.getLoginProvider(this, user, new LoginApiCallBack.OnLoginApiListener() {
                @Override
                public void onGetUserProfile(final UserProfile user) {
                    if (user != null) {
                        TKGZApplication.getInstance().setUserProfile(user);
                        final Activity activity = getActivity();
                        if (activity != null) {
                            activity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    TKGZApplication.getInstance().saveUserEmailToSharePreferences(mEtEmail.getText().toString());
                                    TKGZApplication.getInstance().saveUserProfileToSharePreferences(user);
                                }
                            });
                        }
                        getOnScreenNavigationListener().navigateToUserHomePage();
                    }
                }

                @Override
                public void onLoginFailed(final String message) {
                    final Activity activity = getActivity();
                    if (activity != null) {
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                showMessage(getString(R.string.txt_login_failed));
                            }
                        });
                    }
                }
            }));
        }

    }

    @Override
    public void onDestroy() {
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        super.onDestroy();
    }

    @Override
    protected void onTKGZActivityCreated() {
        if (!TextUtils.isEmpty(TKGZApplication.getInstance().getUserEmail())) {
            mEtEmail.setText(TKGZApplication.getInstance().getUserEmail());
        }
    }

    @Override
    public String getTransactionTag() {
        return "login_fragment";
    }

    @Override
    public boolean isHideActionBar() {
        return true;
    }

    @Override
    public String getActionBarTitle() {
        return null;
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        boolean isImeActionDone = false;
        if (actionId == EditorInfo.IME_ACTION_DONE) {
            onLogin();
            isImeActionDone = true;
        }
        return isImeActionDone;
    }

    @Override
    public boolean isEmptyTopMargin() {
        return true;
    }

}
