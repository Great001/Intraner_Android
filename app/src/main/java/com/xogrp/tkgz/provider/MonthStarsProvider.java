package com.xogrp.tkgz.provider;

import com.xogrp.tkgz.TKGZConfiguration;
import com.xogrp.tkgz.model.UserProfile;
import com.xogrp.tkgz.spi.MonthStarsApiCallBack;
import com.xogrp.xoapp.spi.RESTfulApiClient;

/**
 * Created by wlao on 1/20/2016.
 */
public abstract class MonthStarsProvider extends AbstractTKGZRESTLoader {
    private static final String URL_MONTH_STARS = "http://%s/v1/stars?limit=12";


    protected MonthStarsProvider(RESTfulApiClient.RESTfulApiCallback restfulApiCallback, OnRESTLoaderListener onRESTLoaderListener) {
        super(restfulApiCallback, onRESTLoaderListener);
    }

    public static MonthStarsProvider getMonthStarsProvider(UserProfile userProfile, OnRESTLoaderListener onRESTLoaderListener, MonthStarsApiCallBack.OnMonthStarsApiCallBackListener onMonthStarsApiCallBackListener){
        return new MonthStarsProvider(new MonthStarsApiCallBack(userProfile, onMonthStarsApiCallBackListener), onRESTLoaderListener){

            @Override
            protected String getUrl() {
                return String.format(URL_MONTH_STARS, TKGZConfiguration.getInstance().getWebServiceHost());
            }

            @Override
            protected RESTfulApiClient getRESTfulApiClient() {
                return RESTfulApiClient.getRESTfulGetClient(TKGZConfiguration.getInstance());
            }

        };
    }

}
