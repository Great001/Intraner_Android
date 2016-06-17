package com.xogrp.tkgz.model;

import org.json.JSONObject;

/**
 * Created by ayu on 12/2/2015 0002.
 */
public class EventTypeProfile {
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

    public static EventTypeProfile getObjectFromJSON(JSONObject jsonObject) {
        EventTypeProfile eventTypeProfile = new EventTypeProfile();
        eventTypeProfile.setId(jsonObject.optString("type_id"));
        eventTypeProfile.setName(jsonObject.optString("type_name"));
        return eventTypeProfile;
    }
}
