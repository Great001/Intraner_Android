package com.xogrp.tkgz.provider;

import com.xogrp.tkgz.TKGZConfiguration;
import com.xogrp.tkgz.model.SaveMyAccountProfile;
import com.xogrp.tkgz.model.UserProfile;
import com.xogrp.tkgz.spi.SaveMyAccountCallBack;
import com.xogrp.xoapp.spi.RESTfulApiClient;

/**
 * Created by wlao on 1/5/2016.
 */
public abstract class SaveMyAccountProvider extends AbstractTKGZRESTLoader {
    private static final String URL_SAVE = "http://%s/v1/users/%s";

    protected SaveMyAccountProvider(RESTfulApiClient.RESTfulApiCallback restfulApiCallback, OnRESTLoaderListener onRESTLoaderListener) {
        super(restfulApiCallback, onRESTLoaderListener);
    }

    public static SaveMyAccountProvider saveMyAccount(OnRESTLoaderListener onRESTLoaderListener, final UserProfile userProfile,SaveMyAccountProfile saveMyAccountProfile,
                                                      SaveMyAccountCallBack.OnSaveMyAccountListener onSaveMyAccountListener){
        return new SaveMyAccountProvider(new SaveMyAccountCallBack(userProfile, saveMyAccountProfile, onSaveMyAccountListener), onRESTLoaderListener){
            @Override
            protected String getUrl() {
                return String.format(URL_SAVE, getWebServiceHost(), userProfile.getId());
            }

            @Override
            protected RESTfulApiClient getRESTfulApiClient() {
                return RESTfulApiClient.getRESTfulPutClient(TKGZConfiguration.getInstance());
            }
        };
    }
}
