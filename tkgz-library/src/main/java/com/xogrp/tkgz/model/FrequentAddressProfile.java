package com.xogrp.tkgz.model;

import org.json.JSONObject;

/**
 * Created by ayu on 1/27/2016 0027.
 */
public class FrequentAddressProfile {
    private String id;
    private String address;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public static FrequentAddressProfile getObjectFromJSON(JSONObject jsonObject) {
        FrequentAddressProfile frequentAddress = new FrequentAddressProfile();
        frequentAddress.setId(jsonObject.optString("id"));
        frequentAddress.setAddress(jsonObject.optString("address"));
        return frequentAddress;
    }
}
