package com.xogrp.tkgz.provider;

import com.xogrp.tkgz.TKGZConfiguration;
import com.xogrp.tkgz.model.UserProfile;
import com.xogrp.tkgz.spi.PhotoWallApiCallBack;
import com.xogrp.xoapp.spi.RESTfulApiClient;

/**
 * Created by wlao on 1/4/2016.
 */
public abstract class PhotoWallProvider extends AbstractTKGZRESTLoader {
    private static final String URL_PHOTO_WALL = "http://%s/v1/user/";

    protected PhotoWallProvider(RESTfulApiClient.RESTfulApiCallback restfulApiCallback, OnRESTLoaderListener onRESTLoaderListener) {
        super(restfulApiCallback, onRESTLoaderListener);
    }

    public static PhotoWallProvider getPhotoWallProvider(UserProfile userProfile, OnRESTLoaderListener onRESTLoaderListener, PhotoWallApiCallBack.OnPhotoWallApiCallBackListener onPhotoWallApiCallBackListener){
        return new PhotoWallProvider(new PhotoWallApiCallBack(userProfile, onPhotoWallApiCallBackListener), onRESTLoaderListener){

            @Override
            protected String getUrl() {
                return String.format(URL_PHOTO_WALL, TKGZConfiguration.getInstance().getWebServiceHost());
            }

            @Override
            protected RESTfulApiClient getRESTfulApiClient() {
                return RESTfulApiClient.getRESTfulGetClient(TKGZConfiguration.getInstance());
            }
        };

    }
}
