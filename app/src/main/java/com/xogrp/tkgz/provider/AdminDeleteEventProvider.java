package com.xogrp.tkgz.provider;

import com.xogrp.tkgz.TKGZConfiguration;
import com.xogrp.tkgz.model.UserProfile;
import com.xogrp.tkgz.spi.AdminDeleteEventApiCallBack;
import com.xogrp.xoapp.spi.RESTfulApiClient;

/**
 * Created by ayu on 12/28/2015 0028.
 */
public abstract class AdminDeleteEventProvider extends AbstractTKGZRESTLoader {

    private static final String URL_DELETE_ACTIVITY = "http://%s/v1/activities/%s";

    protected AdminDeleteEventProvider(RESTfulApiClient.RESTfulApiCallback restfulApiCallback, OnRESTLoaderListener onRESTLoaderListener) {
        super(restfulApiCallback, onRESTLoaderListener);
    }

    public static AdminDeleteEventProvider getAdminDeleteEventProvider(OnRESTLoaderListener onRESTLoaderListener, UserProfile userProfile, final String eventId, AdminDeleteEventApiCallBack.OnAdminDeleteEventApiListener listener) {
        return new AdminDeleteEventProvider(new AdminDeleteEventApiCallBack(userProfile, listener), onRESTLoaderListener) {
            @Override
            protected String getUrl() {
                return String.format(URL_DELETE_ACTIVITY, TKGZConfiguration.getInstance().getWebServiceHost(), eventId);
            }

            @Override
            protected RESTfulApiClient getRESTfulApiClient() {
                return RESTfulApiClient.getRESTDeleteClient(TKGZConfiguration.getInstance());
            }
        };
    }
}
