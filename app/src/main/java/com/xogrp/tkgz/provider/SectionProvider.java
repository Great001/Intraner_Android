package com.xogrp.tkgz.provider;

import com.xogrp.tkgz.TKGZConfiguration;
import com.xogrp.tkgz.spi.SectionApiCallback;
import com.xogrp.xoapp.spi.RESTfulApiClient;

/**
 * Created by ayu on 10/29/2015 0029.
 */
public abstract class SectionProvider extends AbstractTKGZRESTLoader {

    private static final String URL_GET_SECTION = "";

    protected SectionProvider(RESTfulApiClient.RESTfulApiCallback restfulApiCallback, OnRESTLoaderListener onRESTLoaderListener) {
        super(restfulApiCallback, onRESTLoaderListener);
    }

    public static SectionProvider getSectionProvider(OnRESTLoaderListener onRESTLoaderListener, SectionApiCallback.OnSectionApiListener onSectionApiListener) {
        return new SectionProvider(new SectionApiCallback(onSectionApiListener), onRESTLoaderListener) {
            @Override
            protected String getUrl() {
                return URL_GET_SECTION;
            }

            @Override
            protected RESTfulApiClient getRESTfulApiClient() {
                return RESTfulApiClient.getRESTfulGetClient(TKGZConfiguration.getInstance());
            }
        };
    }
}
