package com.xogrp.tkgz.provider;

import com.xogrp.tkgz.TKGZConfiguration;
import com.xogrp.tkgz.model.UserProfile;
import com.xogrp.tkgz.spi.CheckInEventApiCallBack;
import com.xogrp.xoapp.spi.RESTfulApiClient;

/**
 * Created by wlao on 2/16/2016.
 */
public abstract class CheckInEventProvider extends AbstractTKGZRESTLoader {
    private static final String URL_CHECK_IN = "http://%s/v1/user/check_in/%s";

    protected CheckInEventProvider(RESTfulApiClient.RESTfulApiCallback restfulApiCallback, OnRESTLoaderListener onRESTLoaderListener) {
        super(restfulApiCallback, onRESTLoaderListener);
    }

    public static CheckInEventProvider getCheckInEventProvider(OnRESTLoaderListener onRESTLoaderListener, CheckInEventApiCallBack.OnCheckInApiListener onCheckInApiListener, UserProfile user, final String eventId){
        return new CheckInEventProvider(new CheckInEventApiCallBack(user, onCheckInApiListener), onRESTLoaderListener) {
            @Override
            protected String getUrl() {
                return String.format(URL_CHECK_IN, TKGZConfiguration.getInstance().getWebServiceHost(), eventId);
            }

            @Override
            protected RESTfulApiClient getRESTfulApiClient() {
                return RESTfulApiClient.getRESTfulPutClient(TKGZConfiguration.getInstance());
            }
        };
    }
}
