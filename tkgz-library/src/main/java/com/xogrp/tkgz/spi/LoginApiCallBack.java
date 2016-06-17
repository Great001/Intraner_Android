package com.xogrp.tkgz.spi;

import com.xogrp.tkgz.model.UserProfile;
import com.xogrp.xoapp.spi.RESTfulApiClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


/**
 * Created by ayu on 10/29/2015 0029.
 */
public class LoginApiCallBack extends RESTfulApiClient.RESTfulApiCallback {

    private OnLoginApiListener loginApiListener;
    private UserProfile userProfile;

    public LoginApiCallBack(OnLoginApiListener loginApiListener, UserProfile user) {
        super();
        this.loginApiListener = loginApiListener;
        userProfile = user;
    }

    @Override
    public void readResponseBody(int statusCode, InputStream inStream) throws IOException, JSONException {

        String responseString = readResponseBodyAsString(inStream);
        JSONObject jsonObject = new JSONObject(responseString);

        if (statusCode == 201) {
            loginApiListener.onGetUserProfile(UserProfile.fromJSON(jsonObject));
        } else {
            loginApiListener.onLoginFailed(jsonObject.optString("message"));
        }
    }

    @Override
    public void writeRequestBody(OutputStream outputStream) throws IOException, JSONException {
        JSONObject member = new JSONObject();
        member.put("email", userProfile.getEmail());
        member.put("password", userProfile.getPassword());
        outputStream.write(member.toString().getBytes());
    }

    public interface OnLoginApiListener {
        void onGetUserProfile(UserProfile user);

        void onLoginFailed(String message);
    }
}
