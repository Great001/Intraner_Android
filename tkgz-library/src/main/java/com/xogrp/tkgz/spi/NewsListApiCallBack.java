package com.xogrp.tkgz.spi;

import com.xogrp.tkgz.model.MessageForUser;
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
 * Created by ayu on 12/18/2015 0018.
 */
public class NewsListApiCallBack extends RESTfulApiClient.RESTfulApiCallback {

    private OnNewsListApiCallBackListener newsListApiCallBackListener;

    public NewsListApiCallBack(MemberProfile memberProfile, OnNewsListApiCallBackListener newsListApiCallBackListener) {
        super(memberProfile);
        this.newsListApiCallBackListener = newsListApiCallBackListener;
    }

    @Override
    public void readResponseBody(int statusCode, InputStream inStream) throws IOException, JSONException {
        String responseString = readResponseBodyAsString(inStream);
        JSONObject jsonObject = new JSONObject(responseString);

        if (statusCode == 200) {
            JSONArray array = jsonObject.getJSONArray("message");
            ArrayList<MessageForUser> messageList = new ArrayList<>();
            int size = array.length();
            for (int index = 0; index < size; index++) {
                messageList.add(MessageForUser.getObjectFromJSON(array.getJSONObject(index)));
            }
            newsListApiCallBackListener.onGetNewsList(messageList);
        }
    }

    public interface OnNewsListApiCallBackListener {
        void onGetNewsList(List<MessageForUser> list);
    }
}
