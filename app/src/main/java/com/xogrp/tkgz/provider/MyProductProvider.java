package com.xogrp.tkgz.provider;

import com.xogrp.tkgz.TKGZConfiguration;
import com.xogrp.tkgz.model.UserProfile;
import com.xogrp.tkgz.spi.MyProductApiCallBack;
import com.xogrp.xoapp.spi.RESTfulApiClient;

/**
 * Created by ayu on 12/28/2015 0028.
 */
public abstract class MyProductProvider extends AbstractTKGZRESTLoader {

    private static final String URL_GET_MY_PRODUCT = "http://%s/v1/user/products";

    protected MyProductProvider(RESTfulApiClient.RESTfulApiCallback restfulApiCallback, OnRESTLoaderListener onRESTLoaderListener) {
        super(restfulApiCallback, onRESTLoaderListener);
    }

    public static MyProductProvider getMyProductProvider(OnRESTLoaderListener onRESTLoaderListener, UserProfile userProfile, MyProductApiCallBack.OnMyProductApiListener listener) {
        return new MyProductProvider(new MyProductApiCallBack(userProfile, listener), onRESTLoaderListener) {
            @Override
            protected String getUrl() {
                return String.format(URL_GET_MY_PRODUCT, TKGZConfiguration.getInstance().getWebServiceHost());
            }

            @Override
            protected RESTfulApiClient getRESTfulApiClient() {
                return RESTfulApiClient.getRESTfulGetClient(TKGZConfiguration.getInstance());
            }
        };
    }
}
