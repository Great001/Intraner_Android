package com.xogrp.tkgz.spi;

import com.xogrp.tkgz.model.UserProfile;
import com.xogrp.xoapp.spi.RESTfulApiClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by hliao on 5/6/2016.
 */
public class FrequentAddressApiCallback extends RESTfulApiClient.RESTfulApiCallback {
    private OnFreAddressApiListener onFreAddressApiListener;
    public FrequentAddressApiCallback(UserProfile userProfile,OnFreAddressApiListener onFreAddressApiListener) {
        super(userProfile);
        this.onFreAddressApiListener=onFreAddressApiListener;
    }

    @Override
    public void readResponseBody(int statusCode, InputStream inStream) throws IOException, JSONException {
        String response=readResponseBodyAsString(inStream);
        JSONObject jsonObject=new JSONObject(response);
        JSONArray jsonArray=jsonObject.getJSONArray("event_address_list") ;
        if(statusCode==200){
            onFreAddressApiListener.onSuccess();
        }
        else if(statusCode==400){
            onFreAddressApiListener.onFailed();
        }
    }

    public interface OnFreAddressApiListener{
        void onSuccess();
        void onFailed();
    }

}
