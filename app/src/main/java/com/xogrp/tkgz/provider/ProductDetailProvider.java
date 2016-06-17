package com.xogrp.tkgz.provider;

import com.xogrp.tkgz.TKGZConfiguration;
import com.xogrp.tkgz.model.UserProfile;
import com.xogrp.tkgz.spi.ProductDetailApiCallBack;
import com.xogrp.xoapp.spi.RESTfulApiClient;

/**
 * Created by ayu on 2/25/2016 0025.
 */
public abstract class ProductDetailProvider extends AbstractTKGZRESTLoader {
    private static final String URL_GET_PRODUCT_DETAIL = "http://%s/v1/products/%s";

    protected ProductDetailProvider(RESTfulApiClient.RESTfulApiCallback restfulApiCallback, OnRESTLoaderListener onRESTLoaderListener) {
        super(restfulApiCallback, onRESTLoaderListener);
    }

    public static ProductDetailProvider getProductDetailProvider(final String id, OnRESTLoaderListener onRESTLoaderListener, UserProfile userProfile, ProductDetailApiCallBack.OnProductDetailApiListener onProductDetailApiListener) {
        return new ProductDetailProvider(new ProductDetailApiCallBack(userProfile, onProductDetailApiListener), onRESTLoaderListener) {
            @Override
            protected String getUrl() {
                return String.format(URL_GET_PRODUCT_DETAIL, getWebServiceHost(), id);
            }

            @Override
            protected RESTfulApiClient getRESTfulApiClient() {
                return RESTfulApiClient.getRESTfulGetClient(TKGZConfiguration.getInstance());
            }
        };
    }
}
