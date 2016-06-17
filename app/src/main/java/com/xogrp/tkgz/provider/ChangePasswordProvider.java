package com.xogrp.tkgz.provider;

import com.xogrp.tkgz.TKGZConfiguration;
import com.xogrp.tkgz.model.UserProfile;
import com.xogrp.tkgz.spi.ChangePasswordApiCallBack;
import com.xogrp.xoapp.spi.RESTfulApiClient;

/**
 * Created by wlao on 2/18/2016.
 */
public abstract class ChangePasswordProvider extends AbstractTKGZRESTLoader {
    private static final String URL_CHANGE_PASSWORD = "http://%s/v1/users/modify_password";

    protected ChangePasswordProvider(RESTfulApiClient.RESTfulApiCallback restfulApiCallback, OnRESTLoaderListener onRESTLoaderListener) {
        super(restfulApiCallback, onRESTLoaderListener);
    }

    public static ChangePasswordProvider getChangePasswordProvider(OnRESTLoaderListener onRESTLoaderListener, final UserProfile userProfile, String password, String newPassword, String newPasswordConfirm,ChangePasswordApiCallBack.OnChangePasswordListener onChangePasswordListener){
        return new ChangePasswordProvider(new ChangePasswordApiCallBack(userProfile, onChangePasswordListener, password, newPassword, newPasswordConfirm), onRESTLoaderListener){

            @Override
            protected String getUrl() {
                return String.format(URL_CHANGE_PASSWORD, TKGZConfiguration.getInstance().getWebServiceHost());
            }

            @Override
            protected RESTfulApiClient getRESTfulApiClient() {
                return RESTfulApiClient.getRESTfulPutClient(TKGZConfiguration.getInstance());
            }
        };
    }

}
