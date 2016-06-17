package com.xogrp.tkgz.model;

import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by wlao on 1/19/2016.
 */
public class MonthStarsProfile extends EmployeeProfile implements Serializable {
    private int month;
    private int year;

    public int getMonth() {
        return month > 0 ? month : 1;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public static MonthStarsProfile fromJSON(JSONObject jsonObject) {
        MonthStarsProfile monthStarsProfile = new MonthStarsProfile();
        setTheSameField(jsonObject, monthStarsProfile);
        setTheDepartmentField(jsonObject, monthStarsProfile);
        monthStarsProfile.setMonth(jsonObject.optInt("month"));
        monthStarsProfile.setYear(jsonObject.optInt("year"));

        return monthStarsProfile;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
