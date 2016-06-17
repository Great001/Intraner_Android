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
 * Created by ayu on 12/17/2015 0017.
 */
public class EventListByTypeApiCallBack extends RESTfulApiClient.RESTfulApiCallback {

    private OnEventListByTypeApiListener eventListByTypeApiListener;


    public EventListByTypeApiCallBack(MemberProfile memberProfile, OnEventListByTypeApiListener eventListByTypeApiListener) {
        super(memberProfile);
        this.eventListByTypeApiListener = eventListByTypeApiListener;
    }

    @Override
    public void readResponseBody(int statusCode, InputStream inStream) throws IOException, JSONException {
        if (statusCode == 200) {
            String responseString = readResponseBodyAsString(inStream);
            JSONObject jsonObject = new JSONObject(responseString);
            JSONArray jsonArray = jsonObject.getJSONArray("event_list");
            int size = jsonArray.length();
            ArrayList<EventProfile> list = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                list.add(EventProfile.getBriefObjectFromJSON(jsonArray.getJSONObject(i)));
            }
            eventListByTypeApiListener.onGetEventList(list);
        }
    }

    public interface OnEventListByTypeApiListener {

        void onGetEventList(ArrayList<EventProfile> list);
    }
}
