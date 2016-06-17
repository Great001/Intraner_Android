package com.xogrp.tkgz.spi;

import com.xogrp.xoapp.model.MemberProfile;
import com.xogrp.xoapp.spi.RESTfulApiClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by jdeng on 5/10/2016.
 */
public class DeleteCommonAddressCallback extends RESTfulApiClient.RESTfulApiCallback {

    private OnDeleteCommonAddressListener onDeleteCommonAddressListener;

    public DeleteCommonAddressCallback(MemberProfile memberProfile,OnDeleteCommonAddressListener onDeleteActivityListner) {
        super(memberProfile);
        this.onDeleteCommonAddressListener=onDeleteActivityListner;
    }

    @Override
    public void readResponseBody(int statusCode, InputStream inStream) throws IOException, JSONException {
        JSONObject jsonObject= new JSONObject(readResponseBodyAsString(inStream));
        if (statusCode==200){
            onDeleteCommonAddressListener.deleteSuccess(jsonObject.getString("message"));
        }else if (statusCode==400){
            onDeleteCommonAddressListener.deleteFailed(jsonObject.getString("message"));
        }

    }

    public interface OnDeleteCommonAddressListener{
        void deleteSuccess(String message);
        void deleteFailed(String message);
    }

}
