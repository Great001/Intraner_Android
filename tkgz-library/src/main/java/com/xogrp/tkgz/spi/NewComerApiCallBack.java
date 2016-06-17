package com.xogrp.tkgz.spi;

import com.xogrp.tkgz.model.NewComerProfile;
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
 * Created by wlao on 1/25/2016.
 */
public class NewComerApiCallBack extends RESTfulApiClient.RESTfulApiCallback {
    private OnNewComerApiCallBackListener newComerApiCallBackListener;

    public NewComerApiCallBack(MemberProfile memberProfile, OnNewComerApiCallBackListener newComerApiCallBackListener) {
        super(memberProfile);
        this.newComerApiCallBackListener = newComerApiCallBackListener;
    }

    @Override
    public void readResponseBody(int statusCode, InputStream inStream) throws IOException, JSONException {
        String responseString = readResponseBodyAsString(inStream);
        JSONObject jsonObject = new JSONObject(responseString);

        if (statusCode == 200){
            JSONArray array = jsonObject.getJSONArray("newcomers_list");
            List<NewComerProfile> messageList = new ArrayList<>();
            int size = array.length();
            for (int index = 0; index < size; index++){
                messageList.add(NewComerProfile.fromJSON(array.getJSONObject(index)));
            }
            newComerApiCallBackListener.onSuccess(messageList);
        }else{
            newComerApiCallBackListener.onFail();
        }
    }

    public interface OnNewComerApiCallBackListener {
        void onSuccess(List<NewComerProfile> list);
        void onFail();
    }

}
