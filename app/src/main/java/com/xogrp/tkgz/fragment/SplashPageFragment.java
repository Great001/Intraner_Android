package com.xogrp.tkgz.fragment;

import android.app.Activity;
import android.view.View;

import com.xogrp.tkgz.R;
import com.xogrp.tkgz.TKGZApplication;
import com.xogrp.tkgz.Widget.TKGZCustomDialog;
import com.xogrp.tkgz.Widget.TKGZCustomHomeMenuDialog;
import com.xogrp.tkgz.model.UserProfile;
import com.xogrp.tkgz.provider.UserProfileProvider;
import com.xogrp.tkgz.spi.UserProfileApiCallBack;

/**
 * Created by wlao on 2/22/2016.
 */
public class SplashPageFragment extends AbstractTKGZFragment {

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_splash_page;
    }

    @Override
    protected void onTKGZCreateView(View rootView) {

    }

    @Override
    protected void onTKGZActivityCreated() {
        final Activity activity = getActivity();
        UserProfile userProfile = TKGZApplication.getInstance().getUserProfile();
        final String userProfileToken = userProfile.getTokenValue();
        initLoader(UserProfileProvider.getUserProfileWithTokenProvider(this, userProfile, new UserProfileApiCallBack.OnUserProfileListener() {
            @Override
            public void OnSuccess(final UserProfile userProfile) {
                if (activity != null) {
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            TKGZApplication.getInstance().setUserProfile(userProfile);
                            TKGZApplication.getInstance().getUserProfile().setTokenValue(userProfileToken);
                            getOnScreenNavigationListener().navigateToUserHomePage();
                        }
                    });
                }
            }

            @Override
            public void OnFailed() {
                if (activity != null) {
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            new TKGZCustomHomeMenuDialog(activity, R.string.dialog_title_reminder, R.string.splash_page_tip,
                                    "", "", R.string.dialog_ok_button, new TKGZCustomDialog.OnDialogActionCallback() {
                                @Override
                                public void onConfirmSelected() {
                                    getOnScreenNavigationListener().navigateToLoginPage();
                                }
                            }, null).show();
                        }
                    });
                }
            }
        }));

    }

    @Override
    public String getTransactionTag() {
        return null;
    }

    @Override
    public boolean isHideActionBar() {
        return false;
    }

    @Override
    public String getActionBarTitle() {
        return null;
    }
}
