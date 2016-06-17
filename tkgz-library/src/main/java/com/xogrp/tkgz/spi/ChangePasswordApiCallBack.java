package com.xogrp.tkgz.spi;

import com.xogrp.tkgz.model.UserProfile;
import com.xogrp.xoapp.spi.RESTfulApiClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by wlao on 2/18/2016.
 */
public class ChangePasswordApiCallBack extends RESTfulApiClient.RESTfulApiCallback {
    private OnChangePasswordListener changePasswordListener;
    private String password;
    private String newPassword;
    private String newPasswordConfirm;

    public ChangePasswordApiCallBack(UserProfile userProfile, OnChangePasswordListener changePasswordListener, String password, String newPassword, String newPasswordConfirm) {
        super(userProfile);
        this.changePasswordListener = changePasswordListener;
        this.password = password;
        this.newPassword = newPassword;
        this.newPasswordConfirm = newPasswordConfirm;
    }

    @Override
    public void writeRequestBody(OutputStream outputStream) throws IOException, JSONException {
        JSONObject jsonObject = new JSONObject();
        JSONObject jsonObjectArray = new JSONObject();
        jsonObjectArray.put("password", password);
        jsonObjectArray.put("new_password", newPassword);
        jsonObjectArray.put("new_password_confirm", newPasswordConfirm);
        jsonObject.put("password", jsonObjectArray);
        outputStream.write(jsonObject.toString().getBytes());
    }

    @Override
    public void readResponseBody(int statusCode, InputStream inStream) throws IOException, JSONException {
        if (statusCode == 200) {
            changePasswordListener.OnSuccess();
        } else {
            changePasswordListener.OnFailed();
        }
    }

    public interface OnChangePasswordListener {
        void OnSuccess();
        void OnFailed();
    }
}
