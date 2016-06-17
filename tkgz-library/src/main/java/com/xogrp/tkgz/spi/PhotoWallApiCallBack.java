package com.xogrp.tkgz.spi;

import com.xogrp.tkgz.model.EmployeeProfile;
import com.xogrp.xoapp.model.MemberProfile;
import com.xogrp.xoapp.spi.RESTfulApiClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wlao on 1/4/2016.
 */
public class PhotoWallApiCallBack extends RESTfulApiClient.RESTfulApiCallback {
    private OnPhotoWallApiCallBackListener photoWallApiCallBackListener;


    public PhotoWallApiCallBack(MemberProfile memberProfile, OnPhotoWallApiCallBackListener photoWallApiCallBackListener){
        super(memberProfile);
        this.photoWallApiCallBackListener = photoWallApiCallBackListener;
    }

    @Override
    public void readResponseBody(int statusCode, InputStream inStream) throws IOException, JSONException {
        String responseString = readResponseBodyAsString(inStream);
        JSONObject jsonObject = new JSONObject(responseString);

        if (statusCode == 200){
            JSONArray array = jsonObject.getJSONArray("employee_information_list");
            ArrayList<EmployeeProfile> messageList = new ArrayList<>();
            int size = array.length();
            for (int index = 0; index< size; index++){
                messageList.add(EmployeeProfile.fromJSON(array.getJSONObject(index)));
            }
            photoWallApiCallBackListener.OnSuccess(messageList);
        }else{
            photoWallApiCallBackListener.OnFailed();
        }

    }

    public interface OnPhotoWallApiCallBackListener{
        void OnSuccess(List<EmployeeProfile> list);
        void OnFailed();
    }

}
