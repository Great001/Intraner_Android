package com.xogrp.tkgz.provider;

import com.xogrp.tkgz.TKGZApplication;
import com.xogrp.tkgz.TKGZConfiguration;
import com.xogrp.tkgz.model.UserProfile;
import com.xogrp.tkgz.spi.FreAddressApiCallback;
import com.xogrp.xoapp.spi.RESTfulApiClient;

/**
 * Created by hliao on 5/6/2016.
 */
public abstract  class FreAddressProvider extends AbstractTKGZRESTLoader {

    private final static String URL_ADDRESS="http://%s/v1/frequent_addresses";
    public FreAddressProvider(RESTfulApiClient.RESTfulApiCallback resTfulApiCallback,OnRESTLoaderListener onRESTLoaderListener){
     super(resTfulApiCallback,onRESTLoaderListener);
    }

    public  static  FreAddressProvider getFreAddressProvider(UserProfile userProfile,FreAddressApiCallback.FreAddressApiListner freAddressApiListner,OnRESTLoaderListener onRESTLoaderListener){
        return new FreAddressProvider(new FreAddressApiCallback(userProfile,freAddressApiListner),onRESTLoaderListener){
            @Override
            protected String getUrl() {
                return String.format(URL_ADDRESS, TKGZConfiguration.getInstance().getWebServiceHost());
            }

            @Override
            protected RESTfulApiClient getRESTfulApiClient() {
                return RESTfulApiClient.getRESTfulGetClient(TKGZConfiguration.getInstance());
            }
        };
    }
}
