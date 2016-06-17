package com.xogrp.tkgz.provider;

import com.xogrp.tkgz.TKGZConfiguration;
import com.xogrp.xoapp.provider.XOAbstractRESTLoader;
import com.xogrp.xoapp.spi.RESTfulApiClient.RESTfulApiCallback;

public abstract class AbstractTKGZRESTLoader
        extends XOAbstractRESTLoader {
    private String mWebServiceHost;

    protected AbstractTKGZRESTLoader(RESTfulApiCallback restfulApiCallback, OnRESTLoaderListener onRESTLoaderListener) {
        super(restfulApiCallback, onRESTLoaderListener);

        TKGZConfiguration tkgzConfiguration = TKGZConfiguration.getInstance();
        mWebServiceHost = tkgzConfiguration.getWebServiceHost();

    }

    protected String getWebServiceHost() {
        return mWebServiceHost;
    }
}
