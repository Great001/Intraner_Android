package com.xogrp.tkgz.provider;

import com.xogrp.tkgz.TKGZConfiguration;
import com.xogrp.tkgz.model.UserProfile;
import com.xogrp.tkgz.spi.NewsListApiCallBack;
import com.xogrp.xoapp.spi.RESTfulApiClient;

/**
 * Created by ayu on 12/18/2015 0018.
 */
public abstract class NewsListProvider extends AbstractTKGZRESTLoader {

    private static final String URL_GET_ALL_NEWS = "http://%s/v1/user/messages";

    protected NewsListProvider(RESTfulApiClient.RESTfulApiCallback restfulApiCallback, OnRESTLoaderListener onRESTLoaderListener) {
        super(restfulApiCallback, onRESTLoaderListener);
    }

    public static NewsListProvider getAllNewsProvider(UserProfile userProfile, OnRESTLoaderListener onRESTLoaderListener, NewsListApiCallBack.OnNewsListApiCallBackListener onNewsListApiCallBackListener) {
        return new NewsListProvider(new NewsListApiCallBack(userProfile, onNewsListApiCallBackListener), onRESTLoaderListener) {
            @Override
            protected String getUrl() {
                return String.format(URL_GET_ALL_NEWS,TKGZConfiguration.getInstance().getWebServiceHost());
            }

            @Override
            protected RESTfulApiClient getRESTfulApiClient() {
                return RESTfulApiClient.getRESTfulGetClient(TKGZConfiguration.getInstance());
            }
        };
    }
}
