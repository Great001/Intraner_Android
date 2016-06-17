package com.xogrp.tkgz.model;

import com.xogrp.xoapp.model.MemberProfile;

import org.json.JSONObject;

/**
 * Created by ayu on 2/24/2016 0024.
 */
public class PeopleProfile extends MemberProfile {
    private String id;
    private String name;

    @Override
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

    @Override
    public String getTokenName() {
        return null;
    }

    public static PeopleProfile formJSON(JSONObject jsonObject) {
        PeopleProfile peopleProfile = new PeopleProfile();
        peopleProfile.setId(jsonObject.optString("id"));
        peopleProfile.setName(jsonObject.optString("name"));
        return peopleProfile;
    }
}
