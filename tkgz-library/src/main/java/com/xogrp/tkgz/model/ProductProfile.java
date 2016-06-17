package com.xogrp.tkgz.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.text.ParseException;

/**
 * Created by ayu on 12/24/2015 0024.
 */
public class ProductProfile implements Serializable {
    private String type;
    private String id;
    private String name;
    private int integral;
    private String avatarUrl;
    private String description;
    private String expiryDate;
    private int exchanged;
    private int quantity;
    private String[] images;

    private boolean isTangible;
    private boolean isUsed;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIntegral() {
        return integral;
    }

    public void setIntegral(int integral) {
        this.integral = integral;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public int getExchanged() {
        return exchanged;
    }

    public void setExchanged(int exchanged) {
        this.exchanged = exchanged;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String[] getImages() {
        return images;
    }

    public void setImages(String[] images) {
        this.images = images;
    }

    public boolean isTangible() {
        return isTangible;
    }

    public void setTangible(boolean tangible) {
        this.isTangible = tangible;
    }

    public boolean isUsed() {
        return isUsed;
    }

    public void setUsed(boolean used) {
        this.isUsed = used;
    }

    public static ProductProfile getObjectFromJSON(JSONObject jsonObject) throws JSONException, ParseException {
        ProductProfile product = new ProductProfile();
        product.setId(jsonObject.optString("id"));
        product.setName(jsonObject.optString("product_name"));
        product.setAvatarUrl(jsonObject.optString("avatar"));
//        product.setDescription(jsonObject.optString("description"));
//        product.setExchanged(jsonObject.optInt("exchanged"));
        product.setIntegral(jsonObject.optInt("integral"));
//        product.setExpiryDate(jsonObject.optString("expiry_date"));
//        JSONArray images = jsonObject.getJSONArray("images");
//        if (images.length() != 0) {
//            product.setImages(getScrollImages(images));
//        } else {
//            product.setImages(new String[0]);
//        }
        return product;
    }


    public static ProductProfile fromJSON(JSONObject jsonObject) throws JSONException, ParseException {
        ProductProfile product = new ProductProfile();
        product.setId(jsonObject.optString("id"));
        product.setName(jsonObject.optString("name"));
        product.setAvatarUrl(jsonObject.optString("avatar"));
        product.setDescription(jsonObject.optString("description"));
        product.setExchanged(jsonObject.optInt("exchange_count"));
        product.setTangible(jsonObject.optBoolean("is_tangible"));
        product.setQuantity(jsonObject.optInt("quantity"));
        product.setIntegral(jsonObject.optInt("integral"));
        product.setExpiryDate(jsonObject.optString("valid_time"));
        JSONArray images = jsonObject.getJSONArray("images");
        if (images.length() != 0) {
            product.setImages(getScrollImages(images));
        } else {
            product.setImages(new String[0]);
        }
        return product;
    }

    private static String[] getScrollImages(JSONArray jsonArray) throws JSONException {
        int size = jsonArray.length();
        String[] images = new String[size];
        for (int index = 0; index < size; index++) {
            JSONObject j = jsonArray.getJSONObject(index);
            images[index] = j.optString("url");
        }
        return images;
    }

    public static ProductProfile getObjectFromMyProductsPage(JSONObject jsonObject) {
        ProductProfile product = new ProductProfile();
        product.setId(jsonObject.optString("id"));
        product.setName(jsonObject.optString("product_name"));
        product.setAvatarUrl(jsonObject.optString("avatar"));
        product.setTangible(jsonObject.optBoolean("is_tangible"));
        product.setUsed(jsonObject.optBoolean("is_used"));
        product.setExpiryDate(jsonObject.optString("expiry_date"));
        return product;
    }
}
