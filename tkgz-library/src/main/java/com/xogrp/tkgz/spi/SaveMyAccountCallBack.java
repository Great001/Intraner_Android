package com.xogrp.tkgz.spi;

import com.xogrp.tkgz.model.SaveMyAccountProfile;
import com.xogrp.tkgz.model.UserProfile;
import com.xogrp.xoapp.spi.RESTfulApiClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by wlao on 1/5/2016.
 */
public class SaveMyAccountCallBack extends RESTfulApiClient.RESTfulApiCallback {
    private OnSaveMyAccountListener saveMyAccountListener;
    private SaveMyAccountProfile saveMyAccountProfile;

    public SaveMyAccountCallBack(UserProfile userProfile, SaveMyAccountProfile saveMyAccountProfile, OnSaveMyAccountListener saveMyAccountListener) {
        super(userProfile);
        this.saveMyAccountListener = saveMyAccountListener;
        this.saveMyAccountProfile = saveMyAccountProfile;
    }

    @Override
    public void readResponseBody(int statusCode, InputStream inStream) throws IOException, JSONException {
        String responseString = readResponseBodyAsString(inStream);
        JSONObject jsonObject = new JSONObject(responseString);
        if (statusCode == 200){
            String avatarUrl = jsonObject.getString("avatar");
            String backgroundUrl = jsonObject.getString("background_url");
            saveMyAccountListener.onUpDataSuccess(avatarUrl, backgroundUrl);
        }else{
            saveMyAccountListener.onFailed();
        }

    }

    @Override
    public void writeRequestBody(OutputStream outputStream) throws IOException, JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("background", saveMyAccountProfile.getBackground());
        jsonObject.put("avatar", saveMyAccountProfile.getAvatar());
        jsonObject.put("phone", saveMyAccountProfile.getPhoneNumber());
        jsonObject.put("self_description", saveMyAccountProfile.getSelfDescription());
        outputStream.write(jsonObject.toString().getBytes());

    }

    public interface OnSaveMyAccountListener{
        void onUpDataSuccess(String avatarUrl, String backgroundUrl);
        void onFailed();
    }
}
