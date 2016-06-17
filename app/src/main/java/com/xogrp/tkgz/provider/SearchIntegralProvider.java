package com.xogrp.tkgz.provider;

import com.xogrp.tkgz.TKGZConfiguration;
import com.xogrp.tkgz.model.UserProfile;
import com.xogrp.tkgz.spi.SearchIntegralApiCallBack;
import com.xogrp.xoapp.spi.RESTfulApiClient;

/**
 * Created by ayu on 12/25/2015 0025.
 */
public abstract class SearchIntegralProvider extends AbstractTKGZRESTLoader {

    private static final String URL_SEARCH_INTEGRAL = "http://%s/v1/user/integrals?startTime=%s&endTime=%s&memberId=%s";

    protected SearchIntegralProvider(RESTfulApiClient.RESTfulApiCallback restfulApiCallback, OnRESTLoaderListener onRESTLoaderListener) {
        super(restfulApiCallback, onRESTLoaderListener);
    }

    public static SearchIntegralProvider getSearchIntegralProvider(OnRESTLoaderListener onRESTLoaderListener, UserProfile userProfile,
                                                                   final String userId, final String startTime, final String endTime,
                                                                   SearchIntegralApiCallBack.OnSearchIntegralApiListener onSearchIntegralApiListener) {
        return new SearchIntegralProvider(new SearchIntegralApiCallBack(userProfile, onSearchIntegralApiListener), onRESTLoaderListener) {
            @Override
            protected String getUrl() {
                return String.format(URL_SEARCH_INTEGRAL, TKGZConfiguration.getInstance().getWebServiceHost(), startTime, endTime, userId);
            }

            @Override
            protected RESTfulApiClient getRESTfulApiClient() {
                return RESTfulApiClient.getRESTfulGetClient(TKGZConfiguration.getInstance());
            }
        };
    }
}
