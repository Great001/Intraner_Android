package com.xogrp.tkgz.provider;

import com.xogrp.tkgz.TKGZConfiguration;
import com.xogrp.tkgz.model.UserProfile;
import com.xogrp.tkgz.spi.EventTypeApiCallBack;
import com.xogrp.xoapp.spi.RESTfulApiClient;

/**
 * Created by ayu on 12/2/2015 0002.
 */
public abstract class EventTypeProvider extends AbstractTKGZRESTLoader {


    private static final String URL_GET_EVENT_TYPE = "http://%s/v1/activity_types";

    protected EventTypeProvider(RESTfulApiClient.RESTfulApiCallback restfulApiCallback, OnRESTLoaderListener onRESTLoaderListener) {
        super(restfulApiCallback, onRESTLoaderListener);
    }

    public static EventTypeProvider getEventTypeProvider(OnRESTLoaderListener onRESTLoaderListener, UserProfile userProfile, EventTypeApiCallBack.OnEventTypeProviderApiListener onEventTypeProviderApiListener) {
        return new EventTypeProvider(new EventTypeApiCallBack(userProfile, onEventTypeProviderApiListener), onRESTLoaderListener) {
            @Override
            protected String getUrl() {
                return String.format(URL_GET_EVENT_TYPE,TKGZConfiguration.getInstance().getWebServiceHost());
            }

            @Override
            protected RESTfulApiClient getRESTfulApiClient() {
                return RESTfulApiClient.getRESTfulGetClient(TKGZConfiguration.getInstance());
            }
        };
    }
}
