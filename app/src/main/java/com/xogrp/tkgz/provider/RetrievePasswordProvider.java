package com.xogrp.tkgz.provider;

import com.xogrp.tkgz.TKGZConfiguration;
import com.xogrp.tkgz.spi.RetrievePasswordApiCallBack;
import com.xogrp.xoapp.spi.RESTfulApiClient;

/**
 * Created by ayu on 2/18/2016 0018.
 */
abstract public class RetrievePasswordProvider extends AbstractTKGZRESTLoader {
    private static final String URL_RETRIEVE_PASSWORD = "http://%s/v1/users/forget_password";

    protected RetrievePasswordProvider(RESTfulApiClient.RESTfulApiCallback restfulApiCallback, OnRESTLoaderListener onRESTLoaderListener) {
        super(restfulApiCallback, onRESTLoaderListener);
    }

    public static RetrievePasswordProvider getRetrievePasswordProvider(String email, OnRESTLoaderListener onRESTLoaderListener, RetrievePasswordApiCallBack.OnRetrievePasswordApiListener retrievePasswordApiListener) {
        return new RetrievePasswordProvider(new RetrievePasswordApiCallBack(retrievePasswordApiListener, email), onRESTLoaderListener) {
            @Override
            protected String getUrl() {
                return String.format(URL_RETRIEVE_PASSWORD, getWebServiceHost());
            }

            @Override
            protected RESTfulApiClient getRESTfulApiClient() {
                return RESTfulApiClient.getRESTfulPutClient(TKGZConfiguration.getInstance());
            }
        };
    }
}
