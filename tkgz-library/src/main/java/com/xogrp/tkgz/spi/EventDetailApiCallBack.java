package com.xogrp.tkgz.spi;

import com.xogrp.tkgz.model.EventProfile;
import com.xogrp.xoapp.model.MemberProfile;
import com.xogrp.xoapp.spi.RESTfulApiClient;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;

/**
 * Created by ayu on 1/13/2016 0013.
 */
public class EventDetailApiCallBack extends RESTfulApiClient.RESTfulApiCallback {
    private OnEventDetailApiListener eventDetailApiListener;

    public EventDetailApiCallBack(MemberProfile memberProfile, OnEventDetailApiListener eventDetailApiListener) {
        super(memberProfile);
        this.eventDetailApiListener = eventDetailApiListener;
    }

    @Override
    public void readResponseBody(int statusCode, InputStream inStream) throws IOException, JSONException {
        String responseString = readResponseBodyAsString(inStream);
        JSONObject jsonObject = new JSONObject(responseString);
        if (statusCode == 200) {
            try {
                eventDetailApiListener.onGetEventDetail(EventProfile.getDetailObjectFromJSON(jsonObject));
            } catch (ParseException e) {
                LoggerFactory.getLogger("EventDetailApiCallBack").error("EventDetailApiCallBack: readResponseBody", e.getMessage());
            }
        } else {
            eventDetailApiListener.onGetEventFailed(jsonObject.optString("message"));
        }
    }

    public interface OnEventDetailApiListener {
        void onGetEventDetail(EventProfile eventProfile);

        void onGetEventFailed(String message);
    }
}
