package com.xogrp.tkgz.model;

import org.json.JSONObject;

/**
 * Created by ayu on 12/17/2015 0017.
 */
public class RankingItemProfile {
    private String avatarUrl;
    private String name;
    private int integral;
    private int ranking;
    private String id;

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIntegral() {
        return integral;
    }

    public void setIntegral(int integral) {
        this.integral = integral;
    }

    public int getRanking() {
        return ranking;
    }

    public void setRanking(int ranking) {
        this.ranking = ranking;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public static RankingItemProfile getObjectFromJSON(JSONObject jsonObject) {
        RankingItemProfile item = new RankingItemProfile();
        item.setId(jsonObject.optString("user_id"));
        item.setAvatarUrl(jsonObject.optString("avatar"));
        item.setName(jsonObject.optString("name"));
        item.setIntegral(jsonObject.optInt("integral"));
        item.setRanking(jsonObject.optInt("ranking"));
        return item;
    }
}
