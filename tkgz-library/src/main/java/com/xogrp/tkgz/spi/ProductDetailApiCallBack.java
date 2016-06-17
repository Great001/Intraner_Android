package com.xogrp.tkgz.spi;

import com.xogrp.tkgz.model.ProductProfile;
import com.xogrp.xoapp.model.MemberProfile;
import com.xogrp.xoapp.spi.RESTfulApiClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;

/**
 * Created by ayu on 2/25/2016 0025.
 */
public class ProductDetailApiCallBack extends RESTfulApiClient.RESTfulApiCallback {
    private OnProductDetailApiListener onProductDetailApiListener;

    public ProductDetailApiCallBack(MemberProfile memberProfile, OnProductDetailApiListener onProductDetailApiListener) {
        super(memberProfile);
        this.onProductDetailApiListener = onProductDetailApiListener;
    }

    @Override
    public void readResponseBody(int statusCode, InputStream inStream) throws IOException, JSONException {
        String responseString = readResponseBodyAsString(inStream);
        JSONObject jsonObject = new JSONObject(responseString);
        if (statusCode == 200) {
            try {
                onProductDetailApiListener.showProductDetail(ProductProfile.fromJSON(jsonObject));
            } catch (ParseException e) {
                getLogger().error("ProductDetailApiCallBack: readResponseBody", e.getMessage());
            }
        }
    }

    public interface OnProductDetailApiListener {
        void showProductDetail(ProductProfile productProfile);
    }
}
