package com.xogrp.tkgz.spi;

import com.xogrp.xoapp.spi.RESTfulApiClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by ayu on 2/18/2016 0018.
 */
public class RetrievePasswordApiCallBack extends RESTfulApiClient.RESTfulApiCallback {

    private OnRetrievePasswordApiListener retrievePasswordApiListener;
    private String email;

    public RetrievePasswordApiCallBack(OnRetrievePasswordApiListener retrievePasswordApiListener, String email) {
        this.retrievePasswordApiListener = retrievePasswordApiListener;
        this.email = email;
    }

    @Override
    public void readResponseBody(int statusCode, InputStream inStream) throws IOException, JSONException {
        String responseString = readResponseBodyAsString(inStream);
        JSONObject jsonObject = new JSONObject(responseString);

        retrievePasswordApiListener.retrievePasswordResult(jsonObject.optString("message"));
    }

    @Override
    public void writeRequestBody(OutputStream outputStream) throws IOException, JSONException {
        JSONObject member = new JSONObject();
        member.put("email", email);
        outputStream.write(member.toString().getBytes());
    }

    public interface OnRetrievePasswordApiListener {
        void retrievePasswordResult(String message);
    }
}
