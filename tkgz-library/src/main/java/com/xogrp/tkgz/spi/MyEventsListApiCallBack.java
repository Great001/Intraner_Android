package com.xogrp.tkgz.spi;

import com.xogrp.tkgz.model.EventProfile;
import com.xogrp.xoapp.model.MemberProfile;
import com.xogrp.xoapp.spi.RESTfulApiClient.RESTfulApiCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by ayu on 12/23/2015 0023.
 */
public class MyEventsListApiCallBack extends RESTfulApiCallback {

    private OnMyEventsApiListener myEventsApiListener;

    public MyEventsListApiCallBack(MemberProfile memberProfile, OnMyEventsApiListener myEventsApiListener) {
        super(memberProfile);
        this.myEventsApiListener = myEventsApiListener;
    }

    @Override
    public void readResponseBody(int statusCode, InputStream inStream) throws IOException, JSONException {
        String responseString = readResponseBodyAsString(inStream);
        JSONObject jsonObject = new JSONObject(responseString);
        if (statusCode == 200) {
            JSONArray jsonArray = jsonObject.getJSONArray("event_list");
            int size = jsonArray.length();
            ArrayList<EventProfile> list = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                list.add(EventProfile.getBriefObjectFromJSON(jsonArray.getJSONObject(i)));
            }
            myEventsApiListener.onGetEventsList(list);
        }
    }

    public interface OnMyEventsApiListener {
        void onGetEventsList(ArrayList<EventProfile> list);
    }
}
