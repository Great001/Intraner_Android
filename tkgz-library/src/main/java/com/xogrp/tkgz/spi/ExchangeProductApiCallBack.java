package com.xogrp.tkgz.spi;

import com.xogrp.xoapp.model.MemberProfile;
import com.xogrp.xoapp.spi.RESTfulApiClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by ayu on 12/25/2015 0025.
 */
public class ExchangeProductApiCallBack extends RESTfulApiClient.RESTfulApiCallback {
    private String productId;
    private OnExchangeProductApiListener exchangeProductApiListener;

    public ExchangeProductApiCallBack(MemberProfile memberProfile, String id, OnExchangeProductApiListener exchangeProductApiListener) {
        super(memberProfile);
        productId = id;
        this.exchangeProductApiListener = exchangeProductApiListener;
    }

    @Override
    public void readResponseBody(int statusCode, InputStream inStream) throws IOException, JSONException {
        String responseString = readResponseBodyAsString(inStream);
        JSONObject jsonObject = new JSONObject(responseString);
        exchangeProductApiListener.onExchangeProduct(statusCode == 201, jsonObject.optInt("integral_year"));
    }

    @Override
    public void writeRequestBody(OutputStream outputStream) throws IOException, JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("product_id", productId);
        outputStream.write(jsonObject.toString().getBytes());
    }

    public interface OnExchangeProductApiListener {
        void onExchangeProduct(boolean result, int integral);
    }
}
