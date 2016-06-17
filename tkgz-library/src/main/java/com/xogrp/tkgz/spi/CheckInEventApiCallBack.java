package com.xogrp.tkgz.spi;

import com.xogrp.xoapp.model.MemberProfile;
import com.xogrp.xoapp.spi.RESTfulApiClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by wlao on 2/16/2016.
 */
public class CheckInEventApiCallBack extends RESTfulApiClient.RESTfulApiCallback {
    private OnCheckInApiListener checkInApiListener;

    public CheckInEventApiCallBack(MemberProfile memberProfile, OnCheckInApiListener checkInApiListener) {
        super(memberProfile);
        this.checkInApiListener = checkInApiListener;
    }

    @Override
    public void readResponseBody(int statusCode, InputStream inStream) throws IOException, JSONException {
        String responseString = readResponseBodyAsString(inStream);
        JSONObject jsonObject = new JSONObject(responseString);
        String message = jsonObject.optString("message");
        if (statusCode == 200) {
            checkInApiListener.OnCheckInSuccess(message, jsonObject.optInt("integral_year"), jsonObject.optInt("integral_month"));
        } else {
            checkInApiListener.OnCheckInFailed(message);
        }
    }

    public interface OnCheckInApiListener {
        void OnCheckInSuccess(String message, int integral_year, int integral_month);

        void OnCheckInFailed(String message);
    }

}
