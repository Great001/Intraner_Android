package com.xogrp.tkgz.provider;

import com.xogrp.tkgz.TKGZConfiguration;
import com.xogrp.tkgz.model.UserProfile;
import com.xogrp.tkgz.spi.GetIntegralListCallback;
import com.xogrp.xoapp.spi.RESTfulApiClient;

/**
 * Created by jdeng on 5/30/2016.
 */
public abstract class GetIntegralListProvider extends AbstractTKGZRESTLoader {
    final private static String GET_INTEGRAL_LIST_URL="http://%s/v1/integral_types";
    protected GetIntegralListProvider(RESTfulApiClient.RESTfulApiCallback restfulApiCallback, OnRESTLoaderListener onRESTLoaderListener) {
        super(restfulApiCallback, onRESTLoaderListener);
    }

    public static GetIntegralListProvider getIntegralListProvider(OnRESTLoaderListener onRESTLoaderListener,UserProfile userProfile,GetIntegralListCallback.onGetIntegralListListener onGetIntegralListListener){
        return new GetIntegralListProvider(new GetIntegralListCallback(userProfile, onGetIntegralListListener), onRESTLoaderListener) {
            @Override
            protected String getUrl() {
                return String.format(GET_INTEGRAL_LIST_URL, TKGZConfiguration.getInstance().getWebServiceHost());
            }

            @Override
            protected RESTfulApiClient getRESTfulApiClient() {
                return RESTfulApiClient.getRESTfulGetClient(TKGZConfiguration.getInstance());
            }
        };
    }
}
