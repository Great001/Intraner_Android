package com.xogrp.tkgz.provider;

import com.xogrp.tkgz.TKGZConfiguration;
import com.xogrp.tkgz.model.UserProfile;
import com.xogrp.tkgz.spi.ProductListApiCallBack;
import com.xogrp.xoapp.spi.RESTfulApiClient;

/**
 * Created by ayu on 12/24/2015 0024.
 */
public abstract class ProductListProvider extends AbstractTKGZRESTLoader {

    private static final String URL_PRODUCT_LIST = "http://%s/v1/products?product_type=%s";

    protected ProductListProvider(RESTfulApiClient.RESTfulApiCallback restfulApiCallback, OnRESTLoaderListener onRESTLoaderListener) {
        super(restfulApiCallback, onRESTLoaderListener);
    }

    public static ProductListProvider getProductListProvider(final String type, OnRESTLoaderListener onRESTLoaderListener, UserProfile userProfile, ProductListApiCallBack.OnProductListApiListener onProductListApiListener) {
        return new ProductListProvider(new ProductListApiCallBack(userProfile, onProductListApiListener), onRESTLoaderListener) {
            @Override
            protected String getUrl() {
                return String.format(URL_PRODUCT_LIST, TKGZConfiguration.getInstance().getWebServiceHost(), type);
            }

            @Override
            protected RESTfulApiClient getRESTfulApiClient() {
                return RESTfulApiClient.getRESTfulGetClient(TKGZConfiguration.getInstance());
            }
        };
    }
}
