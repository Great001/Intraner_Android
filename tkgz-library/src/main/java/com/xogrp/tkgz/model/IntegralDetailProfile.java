package com.xogrp.tkgz.model;

import org.json.JSONObject;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Created by wlao on 8/4/2015.
 */
public class IntegralDetailProfile implements Serializable {

    private String title;
    private long date;
    private int Integral;

    public IntegralDetailProfile() {

    }

    public IntegralDetailProfile(String title, long date, int Integral) {
        this.title = title;
        this.date = date;
        this.Integral = Integral;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public int getIntegral() {
        return Integral;
    }

    public void setIntegral(int integral) {
        this.Integral = integral;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public static IntegralDetailProfile getObjectFromJSON(JSONObject jsonObject) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
        IntegralDetailProfile integralEvent = new IntegralDetailProfile();
        integralEvent.setTitle(jsonObject.optString("title"));
        integralEvent.setIntegral(jsonObject.optInt("integral"));
        integralEvent.setDate(format.parse(jsonObject.optString("time")).getTime());

        return integralEvent;
    }
}
