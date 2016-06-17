package com.xogrp.tkgz.spi;

import com.xogrp.xoapp.model.MemberProfile;
import com.xogrp.xoapp.spi.RESTfulApiClient;

import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by ayu on 12/28/2015 0028.
 */
public class AdminDeleteEventApiCallBack extends RESTfulApiClient.RESTfulApiCallback {
    private OnAdminDeleteEventApiListener adminDeleteEventApiListener;

    public AdminDeleteEventApiCallBack(MemberProfile memberProfile, OnAdminDeleteEventApiListener adminDeleteEventApiListener) {
        super(memberProfile);
        this.adminDeleteEventApiListener = adminDeleteEventApiListener;
    }

    @Override
    public void readResponseBody(int statusCode, InputStream inStream) throws IOException, JSONException {
            adminDeleteEventApiListener.onDeleteActivityResult(statusCode == 204);
    }

    public interface OnAdminDeleteEventApiListener {
        void onDeleteActivityResult(boolean result);
    }
}
