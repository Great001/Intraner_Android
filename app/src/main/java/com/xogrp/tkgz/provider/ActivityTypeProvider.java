package com.xogrp.tkgz.provider;

import com.xogrp.tkgz.TKGZConfiguration;
import com.xogrp.tkgz.model.UserProfile;
import com.xogrp.tkgz.spi.ActivityTypeCallback;
import com.xogrp.xoapp.spi.RESTfulApiClient;

/**
 * Created by jdeng on 4/28/2016.
 */
public abstract class ActivityTypeProvider extends AbstractTKGZRESTLoader {
    private static final String URL_GET_ACTIVITY_TYPE = "http://%s/v1/activity_types";

    protected ActivityTypeProvider(RESTfulApiClient.RESTfulApiCallback restfulApiCallback, OnRESTLoaderListener onRESTLoaderListener) {
        super(restfulApiCallback, onRESTLoaderListener);
    }

    public static ActivityTypeProvider getActivityTypeProvider(OnRESTLoaderListener onRESTLoaderListener,UserProfile userProfile,ActivityTypeCallback.OnActivityTypeListner listener) {
        return  new ActivityTypeProvider(new ActivityTypeCallback(userProfile,listener),onRESTLoaderListener){

            @Override
            protected String getUrl() {
                return String.format(URL_GET_ACTIVITY_TYPE,TKGZConfiguration.getInstance().getWebServiceHost());
            }

            @Override
            protected RESTfulApiClient getRESTfulApiClient() {
                return RESTfulApiClient.getRESTfulGetClient(TKGZConfiguration.getInstance());
            }
        };
    }
}
