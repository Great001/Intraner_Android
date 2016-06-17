package com.xogrp.tkgz.provider;

import com.xogrp.tkgz.TKGZConfiguration;
import com.xogrp.tkgz.model.UserProfile;
import com.xogrp.tkgz.spi.EnrollEventApiCallBack;
import com.xogrp.xoapp.spi.RESTfulApiClient;

/**
 * Created by ayu on 12/2/2015 0002.
 */
public abstract class EnrollEventProvider extends AbstractTKGZRESTLoader {

    private static final String URL_ENROLL = "http://%s/v1/user/enroll_activity";

    protected EnrollEventProvider(RESTfulApiClient.RESTfulApiCallback restfulApiCallback, OnRESTLoaderListener onRESTLoaderListener) {
        super(restfulApiCallback, onRESTLoaderListener);
    }

    public static EnrollEventProvider getEnrollEventProvider(OnRESTLoaderListener onRESTLoaderListener, EnrollEventApiCallBack.OnEnrollApiListener onEnrollApiListener, UserProfile user, String eventId) {
        return new EnrollEventProvider(new EnrollEventApiCallBack(user, onEnrollApiListener, eventId), onRESTLoaderListener) {
            @Override
            protected String getUrl() {
                return String.format(URL_ENROLL, TKGZConfiguration.getInstance().getWebServiceHost());
            }

            @Override
            protected RESTfulApiClient getRESTfulApiClient() {
                return RESTfulApiClient.getRESTfulPostClient(TKGZConfiguration.getInstance());
            }
        };
    }
}
