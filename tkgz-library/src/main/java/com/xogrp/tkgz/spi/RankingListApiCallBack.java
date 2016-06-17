package com.xogrp.tkgz.spi;

import com.xogrp.tkgz.model.RankingItemProfile;
import com.xogrp.xoapp.model.MemberProfile;
import com.xogrp.xoapp.spi.RESTfulApiClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by ayu on 12/1/2015 0001.
 */
public class RankingListApiCallBack extends RESTfulApiClient.RESTfulApiCallback {

    private OnRankingListApiListener rankingListApiListener;

    public RankingListApiCallBack(MemberProfile memberProfile, OnRankingListApiListener rankingListApiListener) {
        super(memberProfile);
        this.rankingListApiListener = rankingListApiListener;
    }

    @Override
    public void readResponseBody(int statusCode, InputStream inStream) throws IOException, JSONException {
        String responseString = readResponseBodyAsString(inStream);
        JSONObject jsonObject = new JSONObject(responseString);

        if (statusCode == 200) {
            JSONArray jsonArray = jsonObject.getJSONArray("rank_list");
            int size = jsonArray.length();
            ArrayList<RankingItemProfile> list = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                list.add(RankingItemProfile.getObjectFromJSON(jsonArray.getJSONObject(i)));
            }
            rankingListApiListener.onGetRankingList(list);
        }
    }


    public interface OnRankingListApiListener {
        void onGetRankingList(ArrayList<RankingItemProfile> list);
    }
}
