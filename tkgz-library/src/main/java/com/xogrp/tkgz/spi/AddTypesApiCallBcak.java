package com.xogrp.tkgz.spi;

import com.xogrp.tkgz.model.UserProfile;
import com.xogrp.xoapp.spi.RESTfulApiClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by hliao on 4/26/2016.
 */
public class AddTypesApiCallBcak extends RESTfulApiClient.RESTfulApiCallback {
    private OnAddTypesApiListener onAddTypesApiListener;
    private String ActiviyType;
    public AddTypesApiCallBcak(UserProfile userProfile,OnAddTypesApiListener onAddTypesApiListener,String type)
    {
        super(userProfile) ;
        this.onAddTypesApiListener=onAddTypesApiListener;
        this.ActiviyType =type;
    }

    @Override
    public void readResponseBody(int statusCode, InputStream inStream) throws IOException, JSONException {
        String responseString = readResponseBodyAsString(inStream);
        JSONObject jsonObject = new JSONObject(responseString);
        String message = jsonObject.getString("message");
        if(statusCode==201){
            onAddTypesApiListener.onSuccess();
        }
        else{
            onAddTypesApiListener.onFailed(message);
        }
    }

    @Override
    public void writeRequestBody(OutputStream outputStream) throws IOException, JSONException {
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("name", ActiviyType);
        outputStream.write(jsonObject.toString().getBytes());

    }

    public interface OnAddTypesApiListener{
        void onSuccess();
        void onFailed(String message);
    }

}
