package com.xogrp.tkgz.provider;

import com.xogrp.tkgz.TKGZConfiguration;
import com.xogrp.tkgz.model.UserProfile;
import com.xogrp.tkgz.spi.UnreadNewsCountApiCallBack;
import com.xogrp.xoapp.spi.RESTfulApiClient;

/**
 * Created by ayu on 12/1/2015 0001.
 */
public abstract class UnreadNewsCountProvider extends AbstractTKGZRESTLoader {

    private static final String URL_GET_RANKING_LIST = "http://%s/v1/user/messages_count";

    protected UnreadNewsCountProvider(RESTfulApiClient.RESTfulApiCallback restfulApiCallback, OnRESTLoaderListener onRESTLoaderListener) {
        super(restfulApiCallback, onRESTLoaderListener);
    }

    public static UnreadNewsCountProvider getUnreadNewsCountProvider(OnRESTLoaderListener onRESTLoaderListener, UnreadNewsCountApiCallBack.OnUnreadNewsCountApiListener onUnreadNewsCountApiListener, UserProfile user) {
        return new UnreadNewsCountProvider(new UnreadNewsCountApiCallBack(user, onUnreadNewsCountApiListener), onRESTLoaderListener) {
            @Override
            protected String getUrl() {
                return String.format(URL_GET_RANKING_LIST, TKGZConfiguration.getInstance().getWebServiceHost());
            }

            @Override
            protected RESTfulApiClient getRESTfulApiClient() {
                return RESTfulApiClient.getRESTfulGetClient(TKGZConfiguration.getInstance());
            }
        };
    }
}
