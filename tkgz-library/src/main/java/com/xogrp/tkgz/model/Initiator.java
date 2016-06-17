package com.xogrp.tkgz.model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by jdeng on 5/31/2016.
 */
public class Initiator {

    private static final String KEY_ID="id";
    private static final String KEY_NAME="member_name";
    private int id;
    private String name;

    public Initiator(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static Initiator fromJSON(JSONObject jsonObject) throws JSONException {
        Initiator initiator =new Initiator(jsonObject.getInt(KEY_ID),jsonObject.getString(KEY_NAME));
        return initiator;
    }

}
