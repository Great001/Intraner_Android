package com.xogrp.tkgz.spi;

import com.xogrp.xoapp.model.MemberProfile;
import com.xogrp.xoapp.spi.RESTfulApiClient;

import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by ayu on 12/18/2015 0018.
 */
public class DeleteNewsApiCallBack extends RESTfulApiClient.RESTfulApiCallback {
    private OnDeleteNewsApiListener deleteNewsApiListener;

    public DeleteNewsApiCallBack(MemberProfile memberProfile, OnDeleteNewsApiListener deleteNewsApiListener) {
        super(memberProfile);
        this.deleteNewsApiListener = deleteNewsApiListener;
    }

    @Override
    public void readResponseBody(int statusCode, InputStream inStream) throws IOException, JSONException {
        if (statusCode == 204) {
            deleteNewsApiListener.onGetResult();
        }
    }

    public interface OnDeleteNewsApiListener {
        void onGetResult();
    }
}
