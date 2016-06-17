package com.xogrp.tkgz.spi;

import com.xogrp.tkgz.model.MonthStarsProfile;
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
 * Created by wlao on 1/20/2016.
 */
public class MonthStarsApiCallBack extends RESTfulApiClient.RESTfulApiCallback {
    private OnMonthStarsApiCallBackListener monthStarsApiCallBackListener;

    public MonthStarsApiCallBack(MemberProfile memberProfile, OnMonthStarsApiCallBackListener monthStarsApiCallBackListener){
        super(memberProfile);
        this.monthStarsApiCallBackListener = monthStarsApiCallBackListener;
    }

    @Override
    public void readResponseBody(int statusCode, InputStream inStream) throws IOException, JSONException {
        String responseString = readResponseBodyAsString(inStream);
        JSONObject jsonObject = new JSONObject(responseString);

        if (statusCode == 200){
            JSONArray array = jsonObject.getJSONArray("month_stars_list");
            ArrayList<MonthStarsProfile> messageList = new ArrayList<>();
            int size = array.length();
            for (int index = 0; index < size; index++){
                messageList.add(MonthStarsProfile.fromJSON(array.getJSONObject(index)));
            }
            monthStarsApiCallBackListener.onSuccess(messageList);
        }else{
            monthStarsApiCallBackListener.onFailed();
        }

    }

    public interface OnMonthStarsApiCallBackListener{
        void onSuccess(List<MonthStarsProfile> list);
        void onFailed();
    }

}
