package com.xogrp.tkgz.spi;

import com.xogrp.xoapp.model.MemberProfile;
import com.xogrp.xoapp.spi.RESTfulApiClient;

import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by ayu on 12/18/2015 0018.
 */
public class ChangeNewsStatusApiCallBack extends RESTfulApiClient.RESTfulApiCallback {

    private OnChangeNewsStatusApiListener changeNewsStatusApiListener;

    public ChangeNewsStatusApiCallBack(MemberProfile memberProfile, OnChangeNewsStatusApiListener changeNewsStatusApiListener) {
        super(memberProfile);
        this.changeNewsStatusApiListener = changeNewsStatusApiListener;
    }

    @Override
    public void readResponseBody(int statusCode, InputStream inStream) throws IOException, JSONException {
        if (statusCode == 201) {
            changeNewsStatusApiListener.onGetChangeResult();
        }

    }

    public interface OnChangeNewsStatusApiListener {
        void onGetChangeResult();
    }

}
