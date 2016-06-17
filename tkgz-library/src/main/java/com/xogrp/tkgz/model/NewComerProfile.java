package com.xogrp.tkgz.model;

import org.json.JSONObject;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Created by wlao on 1/21/2016.
 */
public class NewComerProfile extends EmployeeProfile {
    private static final SimpleDateFormat ENTRY_TIME_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    private long entryTime;

    @Override
    public long getEntryTime() {
        return entryTime;
    }

    @Override
    public void setEntryTime(long entryTime) {
        this.entryTime = entryTime;
    }

    public static NewComerProfile fromJSON(JSONObject jsonObject){
        NewComerProfile newComerProfile = new NewComerProfile();
        setTheSameField(jsonObject, newComerProfile);
        setTheDepartmentField(jsonObject, newComerProfile);
        newComerProfile.setEntryTime(getEntryTimeToLong(jsonObject.optString("entry_time")));
        newComerProfile.setStarOfTheMonth(jsonObject.optBoolean("is_month_star"));

        return newComerProfile;
    }

    protected static long getEntryTimeToLong(String date){
        long entryTime = 0;
        try {
            entryTime = ENTRY_TIME_DATE_FORMAT.parse(date).getTime();
        } catch (ParseException e) {
            LoggerFactory.getLogger("NewComerProfile").error("NewComerProfile: fromJSON", e.getMessage());
        }
        return entryTime;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
