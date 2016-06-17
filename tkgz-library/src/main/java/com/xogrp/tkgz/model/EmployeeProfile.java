package com.xogrp.tkgz.model;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by ayu on 12/4/2015 0004.
 */
public class EmployeeProfile extends UserProfile {
    private boolean isStarOfTheMonth;

    @Override
    public String getTokenName() {
        return null;
    }

    public boolean isStarOfTheMonth() {
        return isStarOfTheMonth;
    }

    public void setStarOfTheMonth(boolean isMonthStar) {
        this.isStarOfTheMonth = isMonthStar;
    }

    public static EmployeeProfile fromJSON(JSONObject jsonObject) {
        EmployeeProfile employeeProfile = new EmployeeProfile();
        setTheSameField(jsonObject, employeeProfile);
        setTheDepartmentField(jsonObject, employeeProfile);
        employeeProfile.setStarOfTheMonth(jsonObject.optBoolean("is_month_star"));

        return employeeProfile;
    }

    public static void setTheDepartmentField(JSONObject jsonObject, EmployeeProfile employeeProfile){
        JSONArray jsonArray = jsonObject.optJSONArray("department");
        if (jsonArray != null) {
            int arrayLength = jsonArray.length();
            String[] teamName = new String[arrayLength];
            for (int index = 0; index < arrayLength; index++) {
                teamName[index] = jsonArray.optString(index);
            }
            employeeProfile.setTeamName(teamName);
        }
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
