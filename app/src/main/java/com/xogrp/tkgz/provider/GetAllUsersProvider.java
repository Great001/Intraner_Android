package com.xogrp.tkgz.provider;

import com.xogrp.tkgz.TKGZConfiguration;
import com.xogrp.tkgz.model.UserProfile;
import com.xogrp.tkgz.spi.GetAllUsersCallback;
import com.xogrp.xoapp.spi.RESTfulApiClient;

/**
 * Created by jdeng on 5/31/2016.
 */
 public  abstract class GetAllUsersProvider extends AbstractTKGZRESTLoader {
    private static final String GET_ALL_USERS_URL="http://%s/v1/users/";
    protected GetAllUsersProvider(RESTfulApiClient.RESTfulApiCallback restfulApiCallback, OnRESTLoaderListener onRESTLoaderListener) {
        super(restfulApiCallback, onRESTLoaderListener);
    }

    public static  GetAllUsersProvider getGetAllUsersProvider(OnRESTLoaderListener onRESTLoaderListener,UserProfile userProfile,GetAllUsersCallback.OnGetAllUsersListener onGetAllUsersListener){
        return new GetAllUsersProvider(new GetAllUsersCallback(userProfile, onGetAllUsersListener), onRESTLoaderListener) {
            @Override
            protected String getUrl() {
                return String.format(GET_ALL_USERS_URL, TKGZConfiguration.getInstance().getWebServiceHost());
            }

            @Override
            protected RESTfulApiClient getRESTfulApiClient() {
                return RESTfulApiClient.getRESTfulGetClient(TKGZConfiguration.getInstance());
            }
        };
    }
}
