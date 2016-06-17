package com.xogrp.tkgz.spi;

import com.xogrp.tkgz.model.EventProfile;
import com.xogrp.xoapp.model.MemberProfile;
import com.xogrp.xoapp.spi.RESTfulApiClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by ayu on 1/14/2016 0014.
 */
public class EntireEventListApiCallBack extends RESTfulApiClient.RESTfulApiCallback {
    private OnEntireEventListApiListener entireEventListApiListener;

    public EntireEventListApiCallBack(MemberProfile memberProfile, OnEntireEventListApiListener entireEventListApiListener) {
        super(memberProfile);
        this.entireEventListApiListener = entireEventListApiListener;
    }

    @Override
    public void readResponseBody(int statusCode, InputStream inStream) throws IOException, JSONException {
        if (statusCode == 200) {
            String responseString = readResponseBodyAsString(inStream);
            JSONObject jsonObject = new JSONObject(responseString);
            JSONArray jsonArray = jsonObject.getJSONArray("event_list");
            ArrayList<EventProfile> list = new ArrayList<>();
            int size = jsonArray.length();
            for (int index = 0; index < size; index++) {
                list.add(EventProfile.getBriefObjectFromJSON(jsonArray.getJSONObject(index)));
            }
            entireEventListApiListener.getEntireEventList(list);
        }
    }

    public interface OnEntireEventListApiListener {
        void getEntireEventList(ArrayList<EventProfile> list);
    }
}
