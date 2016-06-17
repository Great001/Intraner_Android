package com.xogrp.tkgz.model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by hliao on 6/16/2016.
 */
public class ActivityProfile {
    private String title;
    private ActivityType actType;
    private String startTime;
    private String endTime;
    private String baiduAddress;
    private String content;
    private boolean isUseCommonAddress;
    private CommonAdressProfile commonAdressProfile;

    public String getTitle() {
        return title;
    }

    public ActivityType getActType() {
        return actType;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public String getBaiduAddress() {
        return baiduAddress;
    }

    public String getContent() {
        return content;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setActType(ActivityType actType) {
        this.actType = actType;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public void setBaiduAddress(String baiduAddress) {
        this.baiduAddress = baiduAddress;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public CommonAdressProfile getCommonAdressProfile() {
        return commonAdressProfile;
    }

    public void setCommonAdressProfile(CommonAdressProfile commonAdressProfile) {
        this.commonAdressProfile = commonAdressProfile;
    }

    public void  setIsUseCommonAddress(boolean isUseCommonAddress) {
        this.isUseCommonAddress=isUseCommonAddress;
    }

    public JSONObject getActivityProfileAsJson() throws JSONException {
        String address=isUseCommonAddress ? commonAdressProfile.address_name : baiduAddress;
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("title", title);
        jsonObject.put("activity_type_id", actType.getId());
        jsonObject.put("content", content);
        jsonObject.put("activity_start", startTime);
        jsonObject.put("activity_end", endTime);
        jsonObject.put("use_frequent_address", isUseCommonAddress);
        jsonObject.put("custom_address", address);
        return jsonObject;
    }

}