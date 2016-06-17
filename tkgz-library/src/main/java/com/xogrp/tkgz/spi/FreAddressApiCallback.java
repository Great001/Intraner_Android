package com.xogrp.tkgz.spi;

import com.xogrp.tkgz.model.UserProfile;
import com.xogrp.xoapp.spi.RESTfulApiClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hliao on 5/6/2016.
 */
public class FreAddressApiCallback extends RESTfulApiClient.RESTfulApiCallback {

    private FreAddressApiListner freAddressApiListner;
    private List<String> list=new ArrayList<>();
    public FreAddressApiCallback(UserProfile userProfile,FreAddressApiListner freAddressApiListner) {
        super(userProfile);
        this.freAddressApiListner =freAddressApiListner;
    }

    @Override
    public void readResponseBody(int statusCode, InputStream inStream) throws IOException, JSONException {
        String response=readResponseBodyAsString(inStream);
        JSONObject jsonObject=new JSONObject(response);
        JSONArray jsonArray=jsonObject.getJSONArray("event_address_list");
        int len=jsonArray.length();
        for(int i=0;i<len;i++){
            list.add(jsonArray.getJSONObject(i).getString("address"));
        }

        if(statusCode==200) {
            freAddressApiListner.onSuccess(list);
        }

        else if(statusCode==400)
            freAddressApiListner.onFailed("failed");
    }

    public interface FreAddressApiListner{
        void onSuccess(final List<String> list);
        void onFailed(String message);
    }
}
