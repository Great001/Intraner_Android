package com.xogrp.tkgz.provider;

import com.xogrp.tkgz.TKGZConfiguration;
import com.xogrp.tkgz.model.UserProfile;
import com.xogrp.tkgz.spi.EntireEventListApiCallBack;
import com.xogrp.tkgz.spi.EventDetailApiCallBack;
import com.xogrp.tkgz.spi.EventListByTypeApiCallBack;
import com.xogrp.xoapp.spi.RESTfulApiClient;

/**
 * Created by ayu on 1/14/2016 0014.
 */
public abstract class EventProvider extends AbstractTKGZRESTLoader {
    private static final String URL_GET_ENTIRE_EVENT = "http://%s/v1/activities";
    private static final String URL_GET_EVENT_LIST = "http://%s/v1/activities?eventType=%s";
    private static final String URL_GET_EVENT_DETAIL = "http://%s/v1/activities/%s";

    protected EventProvider(RESTfulApiClient.RESTfulApiCallback restfulApiCallback, OnRESTLoaderListener onRESTLoaderListener) {
        super(restfulApiCallback, onRESTLoaderListener);
    }

    public static EventProvider getEntireEventListProvider(UserProfile userProfile, OnRESTLoaderListener onRESTLoaderListener, EntireEventListApiCallBack.OnEntireEventListApiListener onEntireEventListApiListener) {
        return new EventProvider(new EntireEventListApiCallBack(userProfile, onEntireEventListApiListener), onRESTLoaderListener) {
            @Override
            protected String getUrl() {
                return String.format(URL_GET_ENTIRE_EVENT, getWebServiceHost());
            }

            @Override
            protected RESTfulApiClient getRESTfulApiClient() {
                return RESTfulApiClient.getRESTfulGetClient(TKGZConfiguration.getInstance());
            }
        };
    }

    public static EventProvider getEventListByTypeProvider(UserProfile userProfile, OnRESTLoaderListener onRESTLoaderListener, final String eventTypeId, EventListByTypeApiCallBack.OnEventListByTypeApiListener onEventListByTypeApiListener) {
        return new EventProvider(new EventListByTypeApiCallBack(userProfile, onEventListByTypeApiListener), onRESTLoaderListener) {
            @Override
            protected String getUrl() {
                return String.format(URL_GET_EVENT_LIST, TKGZConfiguration.getInstance().getWebServiceHost(), eventTypeId);
            }

            @Override
            protected RESTfulApiClient getRESTfulApiClient() {
                return RESTfulApiClient.getRESTfulGetClient(TKGZConfiguration.getInstance());
            }
        };

    }

    public static EventProvider getEventDetailProvider(UserProfile userProfile, final String eventId, OnRESTLoaderListener onRESTLoaderListener, EventDetailApiCallBack.OnEventDetailApiListener onEventDetailApiListener) {
        return new EventProvider(new EventDetailApiCallBack(userProfile, onEventDetailApiListener), onRESTLoaderListener) {
            @Override
            protected String getUrl() {
                return String.format(URL_GET_EVENT_DETAIL, getWebServiceHost(), eventId);
            }

            @Override
            protected RESTfulApiClient getRESTfulApiClient() {
                return RESTfulApiClient.getRESTfulGetClient(TKGZConfiguration.getInstance());
            }
        };
    }
}
