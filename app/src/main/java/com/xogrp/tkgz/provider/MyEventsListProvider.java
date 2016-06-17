package com.xogrp.tkgz.provider;

import com.xogrp.tkgz.TKGZConfiguration;
import com.xogrp.tkgz.model.UserProfile;
import com.xogrp.tkgz.spi.MyEventsListApiCallBack;
import com.xogrp.xoapp.spi.RESTfulApiClient;
import com.xogrp.xoapp.spi.RESTfulApiClient.RESTfulApiCallback;

/**
 * Created by ayu on 12/23/2015 0023.
 */
public abstract class MyEventsListProvider extends AbstractTKGZRESTLoader {

    private static final String URL_GET_MY_EVENTS = "http://%s/v1/user/activities";

    public MyEventsListProvider(RESTfulApiCallback restfulApiCallback, OnRESTLoaderListener onRESTLoaderListener) {
        super(restfulApiCallback, onRESTLoaderListener);
    }

    public static MyEventsListProvider getMyEventsListProvider(UserProfile userProfile, OnRESTLoaderListener onRESTLoaderListener, MyEventsListApiCallBack.OnMyEventsApiListener onMyEventsApiListener) {
        return new MyEventsListProvider(new MyEventsListApiCallBack(userProfile, onMyEventsApiListener), onRESTLoaderListener) {
            @Override
            protected String getUrl() {
                return String.format(URL_GET_MY_EVENTS, TKGZConfiguration.getInstance().getWebServiceHost());
            }

            @Override
            protected RESTfulApiClient getRESTfulApiClient() {
                return RESTfulApiClient.getRESTfulGetClient(TKGZConfiguration.getInstance());
            }
        };
    }
}
