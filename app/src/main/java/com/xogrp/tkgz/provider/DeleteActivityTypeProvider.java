package com.xogrp.tkgz.provider;

import com.xogrp.tkgz.TKGZConfiguration;
import com.xogrp.tkgz.model.UserProfile;
import com.xogrp.tkgz.spi.DeleteActivityTypeCallBack;
import com.xogrp.xoapp.spi.RESTfulApiClient;

/**
 * Created by jdeng on 5/9/2016.
 */
public abstract class DeleteActivityTypeProvider extends AbstractTKGZRESTLoader{
    public static final String URL_DELETE_ACTIVIYT_TYPE="http://%s/v1/activity_types/%d";

    protected DeleteActivityTypeProvider(RESTfulApiClient.RESTfulApiCallback restfulApiCallback, OnRESTLoaderListener onRESTLoaderListener) {
        super(restfulApiCallback, onRESTLoaderListener);
    }
    public static DeleteActivityTypeProvider getDeleteActivityTypeProvider(OnRESTLoaderListener onRESTLoaderListener,
                                                                           UserProfile userProfile,final int id,DeleteActivityTypeCallBack.OnDeleteActivityListner onDeleteActivityListner ){
        return new DeleteActivityTypeProvider(new DeleteActivityTypeCallBack(userProfile, onDeleteActivityListner), onRESTLoaderListener) {
            @Override
            protected String getUrl() {
                return String.format(URL_DELETE_ACTIVIYT_TYPE, TKGZConfiguration.getInstance().getWebServiceHost(),id);
            }

            @Override
            protected RESTfulApiClient getRESTfulApiClient() {
                return RESTfulApiClient.getRESTDeleteClient(TKGZConfiguration.getInstance());
            }
        };
    }

}
