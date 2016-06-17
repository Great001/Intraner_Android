package com.xogrp.tkgz.provider;

import com.xogrp.tkgz.TKGZConfiguration;
import com.xogrp.tkgz.model.UserProfile;
import com.xogrp.tkgz.spi.DeleteCommonAddressCallback;
import com.xogrp.xoapp.spi.RESTfulApiClient;

/**
 * Created by jdeng on 5/10/2016.
 */
public abstract class DeleteCommonAddressProvider extends AbstractTKGZRESTLoader {
    final static String URL_DELETE_COMMON_ADDRESS="http://%s/frequent_addresses/%d";

    protected DeleteCommonAddressProvider(RESTfulApiClient.RESTfulApiCallback restfulApiCallback, OnRESTLoaderListener onRESTLoaderListener) {
        super(restfulApiCallback, onRESTLoaderListener);
    }

    public static DeleteCommonAddressProvider getDeleteCommonAddressProvider(OnRESTLoaderListener onRESTLoaderListener,UserProfile userProfile,
                                                                       final int position,DeleteCommonAddressCallback.OnDeleteCommonAddressListener onDeleteCommonAddressListener){
        return  new DeleteCommonAddressProvider(new DeleteCommonAddressCallback(userProfile,onDeleteCommonAddressListener),onRESTLoaderListener) {
            @Override
            protected String getUrl() {
                return String.format(URL_DELETE_COMMON_ADDRESS, TKGZConfiguration.getInstance().getWebServiceHost(),position);
            }

            @Override
            protected RESTfulApiClient getRESTfulApiClient() {
                return RESTfulApiClient.getRESTDeleteClient(TKGZConfiguration.getInstance());
            }
        };
    }

}
