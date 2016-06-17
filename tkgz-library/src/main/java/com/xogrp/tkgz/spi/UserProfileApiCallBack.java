package com.xogrp.tkgz.spi;

import com.xogrp.tkgz.model.UserProfile;
import com.xogrp.xoapp.spi.RESTfulApiClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by wlao on 2/22/2016.
 */
public class UserProfileApiCallBack extends RESTfulApiClient.RESTfulApiCallback {
    private OnUserProfileListener userProfileListener;

    public UserProfileApiCallBack(UserProfile userProfile, OnUserProfileListener userProfileListener) {
        super(userProfile);
        this.userProfileListener = userProfileListener;
    }

    @Override
    public void readResponseBody(int statusCode, InputStream inStream) throws IOException, JSONException {
        String responseBodyAsString = readResponseBodyAsString(inStream);
        JSONObject jsonObject = new JSONObject(responseBodyAsString);

        if (statusCode == 200){
            userProfileListener.OnSuccess(UserProfile.fromJSON(jsonObject));
        } else {
            userProfileListener.OnFailed();
        }
    }

    public interface OnUserProfileListener{
        void OnSuccess(UserProfile userProfile);
        void OnFailed();
    }
}
