package com.xogrp.tkgz.provider;

import com.xogrp.tkgz.TKGZConfiguration;
import com.xogrp.tkgz.model.UserProfile;
import com.xogrp.tkgz.spi.LoginApiCallBack;
import com.xogrp.xoapp.spi.RESTfulApiClient;

/**
 * Created by ayu on 10/29/2015 0029.
 */
public abstract class LoginProvider extends AbstractTKGZRESTLoader {

    private static final String URL_LOG_IN = "http://%s/v1/sessions";

    protected LoginProvider(RESTfulApiClient.RESTfulApiCallback restfulApiCallback, OnRESTLoaderListener onRESTLoaderListener) {
        super(restfulApiCallback, onRESTLoaderListener);
    }

    public static LoginProvider getLoginProvider(OnRESTLoaderListener onRESTLoaderListener, UserProfile userProfile, LoginApiCallBack.OnLoginApiListener onLoginApiListener) {
        return new LoginProvider(new LoginApiCallBack(onLoginApiListener, userProfile), onRESTLoaderListener) {
            @Override
            protected String getUrl() {
                return String.format(URL_LOG_IN, TKGZConfiguration.getInstance().getWebServiceHost());
            }

            @Override
            protected RESTfulApiClient getRESTfulApiClient() {
                return RESTfulApiClient.getRESTfulPostClient(TKGZConfiguration.getInstance());
            }
        };
    }
}
