package com.xogrp.tkgz.provider;

import com.xogrp.tkgz.TKGZConfiguration;
import com.xogrp.tkgz.model.UserProfile;
import com.xogrp.tkgz.spi.CommonAddressCallback;
import com.xogrp.tkgz.spi.DeleteCommonAddressCallback;
import com.xogrp.xoapp.spi.RESTfulApiClient;

/**
 * Created by jdeng on 5/10/2016.
 */
public abstract class CommonAddressProvider extends AbstractTKGZRESTLoader{
    final static String URL_GET_COMMON_ADDRESS="http://%s/v1/frequent_addresses";
    protected CommonAddressProvider(RESTfulApiClient.RESTfulApiCallback restfulApiCallback, OnRESTLoaderListener onRESTLoaderListener) {
        super(restfulApiCallback, onRESTLoaderListener);
    }
    public static CommonAddressProvider getCommonAddressProvider(OnRESTLoaderListener onRESTLoaderListener,UserProfile userProfile,CommonAddressCallback.OnGetCommonAddressListener onGetCommonAddressListener){
        return new CommonAddressProvider(new CommonAddressCallback(userProfile,onGetCommonAddressListener), onRESTLoaderListener) {
            @Override
            protected String getUrl() {
                return String.format(URL_GET_COMMON_ADDRESS, TKGZConfiguration.getInstance().getWebServiceHost());
            }

            @Override
            protected RESTfulApiClient getRESTfulApiClient() {
                return RESTfulApiClient.getRESTfulGetClient(TKGZConfiguration.getInstance());
            }
        };
    }
}
