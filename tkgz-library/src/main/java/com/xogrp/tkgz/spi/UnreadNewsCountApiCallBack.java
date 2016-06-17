package com.xogrp.tkgz.spi;

import com.xogrp.xoapp.model.MemberProfile;
import com.xogrp.xoapp.spi.RESTfulApiClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by ayu on 12/1/2015 0001.
 */
public class UnreadNewsCountApiCallBack extends RESTfulApiClient.RESTfulApiCallback {

    private OnUnreadNewsCountApiListener unreadNewsCountApiListener;

    public UnreadNewsCountApiCallBack(MemberProfile memberProfile, OnUnreadNewsCountApiListener unreadNewsCountApiListener) {
        super(memberProfile);
        this.unreadNewsCountApiListener = unreadNewsCountApiListener;
    }

    @Override
    public void readResponseBody(int statusCode, InputStream inStream) throws IOException, JSONException {
        String responseString = readResponseBodyAsString(inStream);

        if (statusCode == 200) {
            JSONObject jsonObject = new JSONObject(responseString);
            int count = jsonObject.optInt("messages_count");
            unreadNewsCountApiListener.onGetUnreadNewsCount(count);
        } else {
            unreadNewsCountApiListener.onGetUnreadNewsCount(0);
        }
    }

    public interface OnUnreadNewsCountApiListener {
        void onGetUnreadNewsCount(int count);
    }
}
