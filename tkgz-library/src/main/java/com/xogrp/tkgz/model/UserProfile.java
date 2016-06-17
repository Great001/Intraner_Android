package com.xogrp.tkgz.model;

import com.xogrp.xoapp.model.MemberProfile;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by dgao on 7/28/2015.
 * Rewrite by ayu on 10/20/2015.
 */
public class UserProfile extends MemberProfile implements Serializable {
    private static final String KEY_TOKEN = "token";
    private static final String KEY_USER_NAME = "username";
    private static final String KEY_TEAMS = "teams";
    private static final String KEY_TEAM_NAME = "team_name";
    private static final String KEY_TEAM_ID = "team_id";
    private static final String KEY_TEAM_INTEGRAL = "team_integral";
    private static final String KEY_ID = "id";
    private static final String KEY_SUPERVISORNAME = "superior";
    private static final String KEY_MEMBER_NAME = "member_name";
    private static final String KEY_AVATAR = "avatar";
    private static final String KEY_POSITION = "position";
    private static final String KEY_INTEGRAL_YEAR = "integral_year";
    private static final String KEY_INTEGRAL_MONTH = "integral_month";
    private static final String KEY_IS_LEADER = "is_leader";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_BACKGROUND = "background";
    private static final String KEY_BIRTHDAY = "birthday";
    private static final String KEY_PHONE_NUMBER = "phone_number";
    private static final String KEY_SELF_DESCRIPTION = "self_description";

    private String username;
    private String memberName;
    private String password;
    private boolean isLeader;
    private String avatar;
    private String position;
    private int integralForYear;
    private int integralForMonth;
    private String teamId;
    private String[] teamName;
    private int teamIntegral;
    private String phoneNumber;
    private long entryTime;     // MillsSecond
    private String jobId;
    private String supervisorId;
    private String supervisorName;
    private String remark;
    private String email;
    private String backgroundUri;
    private String birthday;
    private String introduction;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isLeader() {
        return isLeader;
    }

    public void setIsLeader(boolean isLeader) {
        this.isLeader = isLeader;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public int getIntegralForYear() {
        return integralForYear;
    }

    public void setIntegralForYear(int integralForYear) {
        this.integralForYear = integralForYear;
    }

    public int getIntegralForMonth() {
        return integralForMonth;
    }

    public void setIntegralForMonth(int integralForMonth) {
        this.integralForMonth = integralForMonth;
    }

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    public String[] getTeamName() {
        return teamName;
    }

    public void setTeamName(String[] teamName) {
        this.teamName = teamName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public long getEntryTime() {
        return entryTime;
    }

    public void setEntryTime(long entryTime) {
        this.entryTime = entryTime;
    }

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public String getSupervisorId() {
        return supervisorId;
    }

    public void setSupervisorId(String supervisorId) {
        this.supervisorId = supervisorId;
    }

    public String getSupervisorName() {
        return supervisorName;
    }

    public void setSupervisorName(String supervisorName) {
        this.supervisorName = supervisorName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getTeamIntegral() {
        return teamIntegral;
    }

    public void setTeamIntegral(int teamIntegral) {
        this.teamIntegral = teamIntegral;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBackgroundUri() {
        return backgroundUri;
    }

    public void setBackgroundUri(String backgroundUri) {
        this.backgroundUri = backgroundUri;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    @Override
    public String getTokenName() {
        return "Authorization";
    }

    public JSONObject encodeUserProfileToJSON() throws JSONException {
        JSONObject json = new JSONObject();
        json.put(KEY_TOKEN, getTokenValue());
        json.put(KEY_ID, getId());

        return json;
    }

    public void decodeUserProfileFromJSON(String userProflie) throws JSONException {
        JSONObject json = new JSONObject(userProflie);
        this.setTokenValue(json.optString(KEY_TOKEN));
        this.setId(json.optString(KEY_ID));
    }

    public static UserProfile fromJSON(JSONObject jsonObject) throws JSONException {
        UserProfile user = new UserProfile();
        setTheSameField(jsonObject, user);
        user.setTokenValue(jsonObject.optString(KEY_TOKEN));
        user.setUsername(jsonObject.optString(KEY_USER_NAME));
        JSONArray jsonArray = jsonObject.optJSONArray(KEY_TEAMS);
        if (jsonArray != null) {
            int arrayLength = jsonArray.length();
            String[] teamName = new String[arrayLength];
            for (int index = 0; index < arrayLength; index++) {
                teamName[index] = jsonArray.optJSONObject(index).optString(KEY_TEAM_NAME);
            }
            user.setTeamName(teamName);
        }
        user.setSupervisorName(jsonObject.optString(KEY_SUPERVISORNAME));
        return user;
    }

    public static UserProfile getObjectFromEventDetailPage(JSONObject jsonObject) {
        UserProfile userProfile = new UserProfile();
        userProfile.setId(jsonObject.optString(KEY_ID));
        userProfile.setMemberName(jsonObject.optString(KEY_MEMBER_NAME));
        return userProfile;
    }

    protected static void setTheSameField(JSONObject jsonObject, UserProfile user) {
        user.setId(jsonObject.optString(KEY_ID));
        user.setMemberName(jsonObject.optString(KEY_MEMBER_NAME));
        user.setAvatar(jsonObject.optString(KEY_AVATAR));
        user.setPosition(jsonObject.optString(KEY_POSITION));
        user.setIntegralForYear(jsonObject.optInt(KEY_INTEGRAL_YEAR));
        user.setIntegralForMonth(jsonObject.optInt(KEY_INTEGRAL_MONTH));
        user.setIsLeader(jsonObject.optBoolean(KEY_IS_LEADER));
        user.setEmail(jsonObject.optString(KEY_EMAIL));
        user.setBackgroundUri(jsonObject.optString(KEY_BACKGROUND));
        user.setBirthday(jsonObject.optString(KEY_BIRTHDAY));
        user.setPhoneNumber(jsonObject.optString(KEY_PHONE_NUMBER));
        user.setIntroduction(jsonObject.optString(KEY_SELF_DESCRIPTION));
    }

    @Override
    public String toString() {
        StringBuffer name = new StringBuffer();
        if (teamName != null && teamName.length > 0) {
            int teamCount = teamName.length;
            name.append(teamName[0].trim());
            for (int index = 1; index < teamCount; index++) {
                name.append("\n");
                name.append(teamName[index].trim());
            }
        }
        return name.toString();
    }

}
