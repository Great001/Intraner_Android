package com.xogrp.tkgz.provider;

import com.xogrp.tkgz.TKGZConfiguration;
import com.xogrp.tkgz.model.UserProfile;
import com.xogrp.tkgz.spi.ExchangeProductApiCallBack;
import com.xogrp.xoapp.spi.RESTfulApiClient;

/**
 * Created by ayu on 12/25/2015 0025.
 */
public abstract class ExchangeProductProvider extends AbstractTKGZRESTLoader {

    private static final String URL_EXCHANGE_PRODUCT = "http://%s/v1/user/exchange_product";

    protected ExchangeProductProvider(RESTfulApiClient.RESTfulApiCallback restfulApiCallback, OnRESTLoaderListener onRESTLoaderListener) {
        super(restfulApiCallback, onRESTLoaderListener);
    }

    public static ExchangeProductProvider getExchangeProductProvider(OnRESTLoaderListener onRESTLoaderListener,
                                                                     UserProfile userProfile, final String productId, ExchangeProductApiCallBack.OnExchangeProductApiListener onExchangeProductApiListener) {
        return new ExchangeProductProvider(new ExchangeProductApiCallBack(userProfile, productId, onExchangeProductApiListener), onRESTLoaderListener) {
            @Override
            protected String getUrl() {
                return String.format(URL_EXCHANGE_PRODUCT, TKGZConfiguration.getInstance().getWebServiceHost());
            }

            @Override
            protected RESTfulApiClient getRESTfulApiClient() {
                return RESTfulApiClient.getRESTfulPostClient(TKGZConfiguration.getInstance());
            }
        };
    }
}
