package com.xogrp.tkgz.spi;

import com.xogrp.xoapp.model.MemberProfile;
import com.xogrp.xoapp.spi.RESTfulApiClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by ayu on 12/2/2015 0002.
 */
public class EnrollEventApiCallBack extends RESTfulApiClient.RESTfulApiCallback {

    private OnEnrollApiListener enrollApiListener;
    private String eventId = null;

    public EnrollEventApiCallBack(MemberProfile memberProfile, OnEnrollApiListener enrollApiListener, String eventId) {
        super(memberProfile);
        this.enrollApiListener = enrollApiListener;
        this.eventId = eventId;
    }

    @Override
    public void readResponseBody(int statusCode, InputStream inStream) throws IOException, JSONException {
        String responseString = readResponseBodyAsString(inStream);
        JSONObject jsonObject = new JSONObject(responseString);
        String message = jsonObject.optString("message");
        if (statusCode == 200) {
            enrollApiListener.onEnrollSuccess(message,jsonObject.optInt("integral_year"),jsonObject.optInt("integral_month"));
        } else {
            enrollApiListener.onEnrollFailed(message);
        }
    }

    @Override
    public void writeRequestBody(OutputStream outputStream) throws IOException, JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("event_id", eventId);
        outputStream.write(jsonObject.toString().getBytes());
    }

    public interface OnEnrollApiListener {
        void onEnrollSuccess(String message, int integral_year, int integral_month);

        void onEnrollFailed(String message);
    }
}
