package com.xogrp.tkgz.spi;

import com.xogrp.xoapp.model.MemberProfile;
import com.xogrp.xoapp.spi.RESTfulApiClient.RESTfulApiCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by ayu on 1/5/2016 0005.
 */
public class UseProductApiCallBack extends RESTfulApiCallback {
    private OnUseProductApiListener useProductApiListener;
    private String mProductId;


    public UseProductApiCallBack(MemberProfile memberProfile, OnUseProductApiListener useProductApiListener, String id) {
        super(memberProfile);
        this.useProductApiListener = useProductApiListener;
        this.mProductId = id;
    }

    @Override
    public void readResponseBody(int statusCode, InputStream inStream) throws IOException, JSONException {

        String responseString = readResponseBodyAsString(inStream);
        JSONObject jsonObject = new JSONObject(responseString);
        String message = jsonObject.optString("message");
        if (statusCode == 200) {
            useProductApiListener.onUseProductSuccess(message);
        } else {
            useProductApiListener.onUseProductFailed(message);
        }
    }

    @Override
    public void writeRequestBody(OutputStream outputStream) throws IOException, JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", mProductId);
        outputStream.write(jsonObject.toString().getBytes());
    }

    public interface OnUseProductApiListener {
        void onUseProductSuccess(String message);

        void onUseProductFailed(String message);
    }
}
