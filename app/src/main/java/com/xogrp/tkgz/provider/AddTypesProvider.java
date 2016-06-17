package com.xogrp.tkgz.provider;

import com.xogrp.tkgz.TKGZConfiguration;
import com.xogrp.tkgz.model.UserProfile;
import com.xogrp.tkgz.spi.AddTypesApiCallBcak;
import com.xogrp.tkgz.spi.CheckInEventApiCallBack;
import com.xogrp.xoapp.XOConfiguration;
import com.xogrp.xoapp.spi.RESTfulApiClient;

/**
 * Created by hliao on 4/26/2016.
 */
public abstract class AddTypesProvider extends AbstractTKGZRESTLoader {
    private static final String URL_ADDTYPES = "http://%s/v1/activity_types";

    public AddTypesProvider(RESTfulApiClient.RESTfulApiCallback resTfulApiCallback, OnRESTLoaderListener onRESTLoaderListener) {
        super(resTfulApiCallback, onRESTLoaderListener);
    }

    public static AddTypesProvider getAddTypesProvider(OnRESTLoaderListener onRESTLoaderListener, AddTypesApiCallBcak.OnAddTypesApiListener onAddTypesApiListener, UserProfile userProfile,String type) {
        return new AddTypesProvider(new AddTypesApiCallBcak(userProfile, onAddTypesApiListener,type), onRESTLoaderListener) {
            @Override
            protected String getUrl() {
                return String.format(URL_ADDTYPES,TKGZConfiguration.getInstance().getWebServiceHost());
            }

            @Override
            protected RESTfulApiClient getRESTfulApiClient() {
                return RESTfulApiClient.getRESTfulPostClient(TKGZConfiguration.getInstance());
            }
        };
    }
}
