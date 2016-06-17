package com.xogrp.tkgz.provider;

import com.xogrp.tkgz.TKGZConfiguration;
import com.xogrp.tkgz.model.UserProfile;
import com.xogrp.tkgz.spi.NewComerApiCallBack;
import com.xogrp.xoapp.spi.RESTfulApiClient;

/**
 * Created by wlao on 1/25/2016.
 */
public abstract class NewComerProvider extends AbstractTKGZRESTLoader {
    private static final String URL_NEW_COMMER = "http://%s/v1/users/new_comers";

    protected NewComerProvider(RESTfulApiClient.RESTfulApiCallback restfulApiCallback, OnRESTLoaderListener onRESTLoaderListener) {
        super(restfulApiCallback, onRESTLoaderListener);
    }

    public static NewComerProvider getNewComerProvider(UserProfile userProfile, OnRESTLoaderListener onRESTLoaderListener, NewComerApiCallBack.OnNewComerApiCallBackListener onNewComerApiCallBackListener){
        return new NewComerProvider(new NewComerApiCallBack(userProfile, onNewComerApiCallBackListener), onRESTLoaderListener) {
            @Override
            protected String getUrl() {
                return String.format(URL_NEW_COMMER, TKGZConfiguration.getInstance().getWebServiceHost());
            }

            @Override
            protected RESTfulApiClient getRESTfulApiClient() {
                return RESTfulApiClient.getRESTfulGetClient(TKGZConfiguration.getInstance());
            }
        };
    }


}
