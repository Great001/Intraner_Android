package com.xogrp.tkgz.spi;

import com.xogrp.tkgz.model.EventTypeProfile;
import com.xogrp.xoapp.model.MemberProfile;
import com.xogrp.xoapp.spi.RESTfulApiClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by ayu on 12/2/2015 0002.
 */
public class EventTypeApiCallBack extends RESTfulApiClient.RESTfulApiCallback {

    private OnEventTypeProviderApiListener eventTypeProviderApiListener;

    public EventTypeApiCallBack(MemberProfile memberProfile, OnEventTypeProviderApiListener eventTypeProviderApiListener) {
        super(memberProfile);
        this.eventTypeProviderApiListener = eventTypeProviderApiListener;
    }

    @Override
    public void readResponseBody(int statusCode, InputStream inStream) throws IOException, JSONException {
        String responseString = readResponseBodyAsString(inStream);
        JSONObject jsonObject = new JSONObject(responseString);
        if (statusCode == 200) {
            JSONArray array = jsonObject.getJSONArray("event_type_list");
            ArrayList<EventTypeProfile> list = new ArrayList<>();
            int size = array.length();
            for (int i = 0; i < size; i++) {
                list.add(EventTypeProfile.getObjectFromJSON(array.getJSONObject(i)));
            }
            eventTypeProviderApiListener.onGetActivityType(list);
        }
    }

    public interface OnEventTypeProviderApiListener {
        void onGetActivityType(ArrayList<EventTypeProfile> list);
    }
}
