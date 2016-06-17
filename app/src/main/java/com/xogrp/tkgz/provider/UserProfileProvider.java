package com.xogrp.tkgz.provider;

import com.xogrp.tkgz.TKGZConfiguration;
import com.xogrp.tkgz.model.UserProfile;
import com.xogrp.tkgz.spi.UserProfileApiCallBack;
import com.xogrp.xoapp.spi.RESTfulApiClient;

/**
 * Created by wlao on 2/22/2016.
 */
public abstract class UserProfileProvider extends AbstractTKGZRESTLoader {
    private static final String URL_USER_PROVIDER = "http://%s/v1/users/%s";

    protected UserProfileProvider(RESTfulApiClient.RESTfulApiCallback restfulApiCallback, OnRESTLoaderListener onRESTLoaderListener) {
        super(restfulApiCallback, onRESTLoaderListener);
    }

    public static UserProfileProvider getUserProfileWithTokenProvider(OnRESTLoaderListener onRESTLoaderListener, final UserProfile userProfile, UserProfileApiCallBack.OnUserProfileListener onUserProfileListener){
        return new UserProfileProvider(new UserProfileApiCallBack(userProfile, onUserProfileListener), onRESTLoaderListener) {
            @Override
            protected String getUrl() {
                return String.format(URL_USER_PROVIDER, TKGZConfiguration.getInstance().getWebServiceHost(), userProfile.getId());
            }

            @Override
            protected RESTfulApiClient getRESTfulApiClient() {
                return RESTfulApiClient.getRESTfulGetClient(TKGZConfiguration.getInstance());
            }
        };
    }

}
