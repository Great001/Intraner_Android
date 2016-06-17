package com.xogrp.tkgz.spi;

import com.xogrp.tkgz.model.TeamProfile;
import com.xogrp.xoapp.spi.RESTfulApiClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ayu on 10/29/2015 0029.
 */
public class SectionApiCallback extends RESTfulApiClient.RESTfulApiCallback {

    private OnSectionApiListener sectionApiListener;

    public SectionApiCallback(OnSectionApiListener sectionApiListener) {
        super();
        this.sectionApiListener = sectionApiListener;
    }

    @Override
    public void readResponseBody(int statusCode, InputStream inStream) throws IOException, JSONException {
        String responseString = readResponseBodyAsString(inStream);
        JSONObject jsonObject = new JSONObject(responseString);
        JSONArray array = jsonObject.optJSONArray("section");

        List<TeamProfile> list = new ArrayList<>();

        int size = array.length();
        for (int index = 0; index < size; index++) {
            list.add(TeamProfile.getObjectFromJSON(array.getJSONObject(index)));
        }
        sectionApiListener.onGetSectionProfile(list);
    }

    public interface OnSectionApiListener {
        void onGetSectionProfile(List<TeamProfile> list);
    }
}
