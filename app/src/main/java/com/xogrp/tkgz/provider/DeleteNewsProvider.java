package com.xogrp.tkgz.provider;

import com.xogrp.tkgz.TKGZConfiguration;
import com.xogrp.tkgz.model.UserProfile;
import com.xogrp.tkgz.spi.DeleteNewsApiCallBack;
import com.xogrp.xoapp.spi.RESTfulApiClient;

/**
 * Created by ayu on 12/18/2015 0018.
 */
public abstract class DeleteNewsProvider extends AbstractTKGZRESTLoader {

    private static final String URL_DELETE_MESSAGE = "http://%s/v1/user/messages/%s";

    protected DeleteNewsProvider(RESTfulApiClient.RESTfulApiCallback restfulApiCallback, OnRESTLoaderListener onRESTLoaderListener) {
        super(restfulApiCallback, onRESTLoaderListener);
    }


    public static DeleteNewsProvider getDeleteNewsProvider(UserProfile userProfile, final String messageId, OnRESTLoaderListener onRESTLoaderListener, DeleteNewsApiCallBack.OnDeleteNewsApiListener onDeleteNewsApiListener) {
        return new DeleteNewsProvider(new DeleteNewsApiCallBack(userProfile, onDeleteNewsApiListener), onRESTLoaderListener) {
            @Override
            protected String getUrl() {
                return String.format(URL_DELETE_MESSAGE, TKGZConfiguration.getInstance().getWebServiceHost(), messageId);
            }

            @Override
            protected RESTfulApiClient getRESTfulApiClient() {
                return RESTfulApiClient.getRESTDeleteClient(TKGZConfiguration.getInstance());
            }
        };
    }
}
