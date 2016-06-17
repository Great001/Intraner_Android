package com.xogrp.tkgz.model;

import org.json.JSONObject;

/**
 * Created by ayu on 10/20/2015 0020.
 */
public class TeamProfile {
    private String id;
    private String name;
    private int teamIntegral;
    private int memberCount;

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

    public int getTeamIntegral() {
        return teamIntegral;
    }

    public void setTeamIntegral(int teamIntegral) {
        this.teamIntegral = teamIntegral;
    }

    public int getMemberCount() {
        return memberCount;
    }

    public void setMemberCount(int memberCount) {
        this.memberCount = memberCount;
    }

    public static TeamProfile getObjectFromJSON(JSONObject jsonObject) {
        TeamProfile section = new TeamProfile();
        section.setName(jsonObject.optString("name"));
        section.setId(jsonObject.optString("id"));
        return section;
    }
}
