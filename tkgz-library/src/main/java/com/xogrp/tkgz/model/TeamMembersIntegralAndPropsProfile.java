package com.xogrp.tkgz.model;

import java.io.Serializable;

/**
 * Created by wlao on 8/20/2015.
 */
// TODO: 11/25/2015 0025 Delete
public class TeamMembersIntegralAndPropsProfile implements Serializable {
    private long date;
    private String teamName;
    private String objectName;
    private int objectId;
    private int score;
    private int iconId;
    private String type;

    public TeamMembersIntegralAndPropsProfile(String objectName, int objectId, int score, int iconId, String type) {
        this.iconId = iconId;
        this.objectId = objectId;
        this.objectName = objectName;
        this.score = score;
        this.type = type;
    }

    public TeamMembersIntegralAndPropsProfile(String teamName, String objectName, int objectId, int score, long date, int iconId, String type) {
        this(objectName, objectId, score, iconId, type);
        this.teamName = teamName;
        this.date = date;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public int getIconId() {
        return iconId;
    }

    public void setIconId(int iconId) {
        this.iconId = iconId;
    }

    public int getObjectId() {
        return objectId;
    }

    public void setObjectId(int objectId) {
        this.objectId = objectId;
    }

    public String getObjectName() {
        return objectName;
    }

    public void setObjectName(String objectName) {
        this.objectName = objectName;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
