package com.xogrp.tkgz.spi;

import com.xogrp.tkgz.model.IntegralDetailProfile;
import com.xogrp.xoapp.model.MemberProfile;
import com.xogrp.xoapp.spi.RESTfulApiClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.ArrayList;

/**
 * Created by ayu on 12/25/2015 0025.
 */
public class SearchIntegralApiCallBack extends RESTfulApiClient.RESTfulApiCallback {
    private OnSearchIntegralApiListener searchIntegralApiListener;

    public SearchIntegralApiCallBack(MemberProfile memberProfile, OnSearchIntegralApiListener searchIntegralApiListener) {
        super(memberProfile);
        this.searchIntegralApiListener = searchIntegralApiListener;
    }

    @Override
    public void readResponseBody(int statusCode, InputStream inStream) throws IOException, JSONException {
        String responseString = readResponseBodyAsString(inStream);
        JSONObject jsonObject = new JSONObject(responseString);
        if (statusCode == 200) {
            JSONArray array = jsonObject.optJSONArray("integral_list");
            ArrayList<IntegralDetailProfile> list = new ArrayList<>();
            int size = array.length();
            for (int index = 0; index < size; index++) {
                try {
                    list.add(IntegralDetailProfile.getObjectFromJSON(array.getJSONObject(index)));
                } catch (ParseException e) {
                    LoggerFactory.getLogger("SearchIntegralApiCallBack").error("SearchIntegralApiCallBack: getObjectFromJSON", e.getMessage());
                }
            }
            searchIntegralApiListener.onSearchIntegral(list);
        }
    }

    public interface OnSearchIntegralApiListener {
        void onSearchIntegral(ArrayList<IntegralDetailProfile> list);
    }
}
