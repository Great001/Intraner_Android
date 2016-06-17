package com.xogrp.tkgz.spi;

import com.xogrp.tkgz.model.ProductProfile;
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
 * Created by ayu on 12/28/2015 0028.
 */
public class MyProductApiCallBack extends RESTfulApiClient.RESTfulApiCallback {
    private OnMyProductApiListener myProductApiListener;

    public MyProductApiCallBack(MemberProfile memberProfile, OnMyProductApiListener myProductApiListener) {
        super(memberProfile);
        this.myProductApiListener = myProductApiListener;
    }

    @Override
    public void readResponseBody(int statusCode, InputStream inStream) throws IOException, JSONException {
        if (statusCode == 200) {
            String responseString = readResponseBodyAsString(inStream);
            JSONObject jsonObject = new JSONObject(responseString);
            JSONArray jsonArray = jsonObject.getJSONArray("product_list");
            ArrayList<ProductProfile> list = new ArrayList<>();
            int size = jsonArray.length();
            for (int index = 0; index < size; index++) {
                list.add(ProductProfile.getObjectFromMyProductsPage(jsonArray.getJSONObject(index)));
            }
            myProductApiListener.onGetMyProductList(list);
        }
    }

    public interface OnMyProductApiListener {
        void onGetMyProductList(List<ProductProfile> list);
    }
}
