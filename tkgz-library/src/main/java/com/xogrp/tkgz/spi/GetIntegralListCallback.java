package com.xogrp.tkgz.spi;

import com.xogrp.tkgz.model.IntegralTypeProfile;
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
 * Created by jdeng on 5/30/2016.
 */
public class GetIntegralListCallback extends RESTfulApiClient.RESTfulApiCallback {
    private onGetIntegralListListener getIntegralListListener;
    private List<IntegralTypeProfile> integralTypeProfileslist;

    public GetIntegralListCallback(MemberProfile memberProfile, onGetIntegralListListener getIntegralListListener) {
        super(memberProfile);
        this.getIntegralListListener = getIntegralListListener;
    }

    @Override
    public void readResponseBody(int statusCode, InputStream inStream) throws IOException, JSONException {
        JSONObject jsonObject=new JSONObject(readResponseBodyAsString(inStream));
        if (statusCode==200){
            JSONArray jsonArray=jsonObject.getJSONArray("integral_types");
            integralTypeProfileslist=new ArrayList<>();
            int size=jsonArray.length();
            for (int i=0;i<size;++i){
                integralTypeProfileslist.add(IntegralTypeProfile.getObjectFromJSON(jsonArray.getJSONObject(i)));
            }
            getIntegralListListener.getIntegralListSuccess(integralTypeProfileslist);
        }else {
            getIntegralListListener.getIntegralListError(jsonObject.getString("message"));
        }

    }

    public interface onGetIntegralListListener{
        public void getIntegralListSuccess(List<IntegralTypeProfile> integralTypeProfilesList);
        public void getIntegralListError(String message);
    }
}
