package com.xogrp.tkgz.spi;

import com.xogrp.tkgz.model.CommonAdressProfile;
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
 * Created by jdeng on 5/10/2016.
 */
public class CommonAddressCallback extends RESTfulApiClient.RESTfulApiCallback {
    private OnGetCommonAddressListener onGetCommonAddressListener;
    private List<CommonAdressProfile> listCommonaddress;
    public CommonAddressCallback(MemberProfile memberProfile,OnGetCommonAddressListener onGetCommonAddressListener) {
        super(memberProfile);
        this.onGetCommonAddressListener=onGetCommonAddressListener;
    }

    @Override
    public void readResponseBody(int statusCode, InputStream inStream) throws IOException, JSONException {
        JSONObject jsonObject= new JSONObject(readResponseBodyAsString(inStream));
        if (statusCode==200){
            JSONArray jsonArray= jsonObject.getJSONArray("event_address_list");
            int size=jsonArray.length();
            listCommonaddress=new ArrayList<>();
            for (int i=0;i<size;++i){
                listCommonaddress.add(new CommonAdressProfile(jsonArray.getJSONObject(i).getString("address"),jsonArray.getJSONObject(i).getInt("address_id")));
            }
           onGetCommonAddressListener.getAddressSuccess(listCommonaddress);
        }else if (statusCode==400){
            onGetCommonAddressListener.getAddressFailed(jsonObject.getString("message"));
        }

    }

    public interface OnGetCommonAddressListener{
        void getAddressSuccess(List<CommonAdressProfile> listCommonAddress);
        void getAddressFailed(String message);
    }

}
