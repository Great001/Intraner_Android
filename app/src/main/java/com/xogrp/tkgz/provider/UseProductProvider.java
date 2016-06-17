package com.xogrp.tkgz.provider;

import com.xogrp.tkgz.TKGZConfiguration;
import com.xogrp.tkgz.model.UserProfile;
import com.xogrp.tkgz.spi.UseProductApiCallBack;
import com.xogrp.xoapp.spi.RESTfulApiClient;

/**
 * Created by ayu on 1/5/2016 0005.
 */
public abstract class UseProductProvider extends AbstractTKGZRESTLoader {
    private static final String URL_USE_PRODUCT = "http://%s/v1/user/use_product";

    protected UseProductProvider(RESTfulApiClient.RESTfulApiCallback restfulApiCallback, OnRESTLoaderListener onRESTLoaderListener) {
        super(restfulApiCallback, onRESTLoaderListener);
    }

    public static UseProductProvider getUseProductProvider(UserProfile userProfile, OnRESTLoaderListener onRESTLoaderListener, String productId, UseProductApiCallBack.OnUseProductApiListener onUseProductApiListener) {
        return new UseProductProvider(new UseProductApiCallBack(userProfile, onUseProductApiListener, productId), onRESTLoaderListener) {
            @Override
            protected String getUrl() {
                return String.format(URL_USE_PRODUCT, TKGZConfiguration.getInstance().getWebServiceHost());
            }

            @Override
            protected RESTfulApiClient getRESTfulApiClient() {
                return RESTfulApiClient.getRESTfulPutClient(TKGZConfiguration.getInstance());
            }
        };
    }
}
