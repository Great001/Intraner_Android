package com.xogrp.tkgz.spi;

import com.xogrp.xoapp.model.MemberProfile;
import com.xogrp.xoapp.spi.RESTfulApiClient;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;

import sun.rmi.runtime.Log;


/**
 * Created by jdeng on 5/9/2016.
 */
public class DeleteActivityTypeCallBack extends RESTfulApiClient.RESTfulApiCallback {
     private OnDeleteActivityListner onDeleteActivityListner;
    public DeleteActivityTypeCallBack(MemberProfile memberProfile,OnDeleteActivityListner onDeleteActivityListner ) {
        super(memberProfile);
        this.onDeleteActivityListner=onDeleteActivityListner;
    }

    @Override
    public void readResponseBody(int statusCode, InputStream inStream) throws IOException, JSONException {
        String deletemessage=readResponseBodyAsString(inStream);
        if (statusCode==204){
            onDeleteActivityListner. deletesuccessful("delete successfully");
        }else if (statusCode==400){
            JSONObject jsonObject = new JSONObject(deletemessage);
            onDeleteActivityListner.deletefail(jsonObject.getString("message"));
        }

    }

    public interface OnDeleteActivityListner{
        void deletesuccessful( final String message);
        void deletefail( final String message);
    }
}
