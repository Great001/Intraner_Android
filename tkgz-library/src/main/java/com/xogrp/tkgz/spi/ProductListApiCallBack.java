package com.xogrp.tkgz.spi;

import com.xogrp.tkgz.model.ProductProfile;
import com.xogrp.xoapp.model.MemberProfile;
import com.xogrp.xoapp.spi.RESTfulApiClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.ArrayList;

/**
 * Created by ayu on 12/24/2015 0024.
 */
public class ProductListApiCallBack extends RESTfulApiClient.RESTfulApiCallback {
    private OnProductListApiListener productListApiListener;

    public ProductListApiCallBack(MemberProfile memberProfile, OnProductListApiListener productListApiListener) {
        super(memberProfile);
        this.productListApiListener = productListApiListener;
    }

    @Override
    public void readResponseBody(int statusCode, InputStream inStream) throws IOException, JSONException {
        if (statusCode == 200) {
            String responseString = readResponseBodyAsString(inStream);
            JSONObject jsonObject = new JSONObject(responseString);
            JSONArray jsonArray = jsonObject.getJSONArray("product_list");
            int size = jsonArray.length();
            ArrayList<ProductProfile> list = new ArrayList<>();
            for (int index = 0; index < size; index++) {
                try {
                    list.add(ProductProfile.getObjectFromJSON(jsonArray.getJSONObject(index)));
                } catch (ParseException e) {
                    LoggerFactory.getLogger("ProductListApiCallBack").error("ProductListApiCallBack: getObjectFromJSON", e.getMessage());
                }
            }
            productListApiListener.getProductList(list);
        }
    }

    public interface OnProductListApiListener {
        void getProductList(ArrayList<ProductProfile> list);
    }
}
