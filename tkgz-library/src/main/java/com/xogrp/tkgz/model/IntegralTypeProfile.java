package com.xogrp.tkgz.model;

import org.json.JSONObject;

/**
 * Created by ayu on 1/27/2016 0027.
 */
public class IntegralTypeProfile {
    private String id;
    private String name;

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

    public static IntegralTypeProfile getObjectFromJSON(JSONObject jsonObject) {
        IntegralTypeProfile integralTypeProfile = new IntegralTypeProfile();
        integralTypeProfile.setId(jsonObject.optString("id"));
        integralTypeProfile.setName(jsonObject.optString("name"));
        return integralTypeProfile;
    }


}
