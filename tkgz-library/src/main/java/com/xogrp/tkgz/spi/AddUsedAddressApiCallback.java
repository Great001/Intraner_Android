package com.xogrp.tkgz.spi;

import com.xogrp.tkgz.model.UserProfile;
import com.xogrp.xoapp.spi.RESTfulApiClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by hliao on 5/5/2016.
 */
public class AddUsedAddressApiCallback extends RESTfulApiClient.RESTfulApiCallback {

    private OnAddAddressApiListener onAddAddressApiListener;
    private String address;
    public AddUsedAddressApiCallback(UserProfile userProfile,OnAddAddressApiListener onAddAddressApiListener,String address){
     super(userProfile);
        this.onAddAddressApiListener = onAddAddressApiListener;
        this.address =address;
    }

    @Override
    public void readResponseBody(int statusCode, InputStream inStream) throws IOException, JSONException {
        String response=readResponseBodyAsString(inStream);
        JSONObject jsonObject=new JSONObject(response);
        String message=jsonObject.getString("message");
        if(statusCode==201){
            onAddAddressApiListener.onSuccess(message);
        }
        else if(statusCode==400){
            onAddAddressApiListener.onFailed(message);
        }

    }

    @Override
    public void writeRequestBody(OutputStream outputStream) throws IOException, JSONException {
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("address", address);
        outputStream.write(jsonObject.toString().getBytes());
    }

    public interface OnAddAddressApiListener {
        void onSuccess(String message);
        void onFailed(String message);
    }

}
