package com.xogrp.tkgz.provider;

import com.xogrp.tkgz.TKGZConfiguration;
import com.xogrp.tkgz.model.UserProfile;
import com.xogrp.tkgz.spi.ChangeNewsStatusApiCallBack;
import com.xogrp.xoapp.spi.RESTfulApiClient;

/**
 * Created by ayu on 12/22/2015 0022.
 */
public abstract class ChangeNewsStatusProvider extends AbstractTKGZRESTLoader {

    private static final String URL_CHANGE_NEWS_STATUS = "http://%s/v1/user/messages/%s";

    protected ChangeNewsStatusProvider(RESTfulApiClient.RESTfulApiCallback restfulApiCallback, OnRESTLoaderListener onRESTLoaderListener) {
        super(restfulApiCallback, onRESTLoaderListener);
    }

    public static ChangeNewsStatusProvider getChangeNewsStatusProvider(final String id, UserProfile userProfile, ChangeNewsStatusApiCallBack.OnChangeNewsStatusApiListener onChangeNewsStatusApiListener, OnRESTLoaderListener onRESTLoaderListener) {
        return new ChangeNewsStatusProvider(new ChangeNewsStatusApiCallBack(userProfile, onChangeNewsStatusApiListener), onRESTLoaderListener) {
            @Override
            protected String getUrl() {
                return String.format(URL_CHANGE_NEWS_STATUS, TKGZConfiguration.getInstance().getWebServiceHost(), id);
            }

            @Override
            protected RESTfulApiClient getRESTfulApiClient() {
                return RESTfulApiClient.getRESTfulPutClient(TKGZConfiguration.getInstance());
            }
        };
    }
}
