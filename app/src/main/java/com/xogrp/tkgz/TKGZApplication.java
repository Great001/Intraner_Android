package com.xogrp.tkgz;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.text.TextUtils;
import android.util.DisplayMetrics;

import com.baidu.mapapi.SDKInitializer;
import com.xogrp.tkgz.model.EventTypeProfile;
import com.xogrp.tkgz.model.UserProfile;
import com.xogrp.xoapp.XOApplication;
import com.xogrp.xoapp.XOConfiguration;

import org.json.JSONException;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Locale;


/**
 * Created by dgao on 7/27/2015.
 */
public class TKGZApplication extends XOApplication {

    private UserProfile mUserProfile;
    private String mUserEmail;
    private static ArrayList<EventTypeProfile> sEventTypeList;
    private static TKGZApplication sInstance;
    private static final String PREFERENCES_USER_PROFILE = "user_profile";
    private static final String PREFERENCES_USER_PROFILE_NAME = "tkgz_properties";
    private static final String PREFERENCES_USER_EMAIL = "user_email";
    private static final String PREFERENCES_USER_EMAIL_NAME = "tkgz_user_email";
    private static final String PREFERENCES_LANGUAGE = "tkgz_language";

    public UserProfile getUserProfile() {
        return mUserProfile;
    }

    public String getUserEmail() {
        return mUserEmail;
    }

    public static TKGZApplication getInstance() {
        return sInstance;
    }

    public void setUserProfile(UserProfile userProfile) {
        mUserProfile = userProfile;
    }

    public void setUserEmail(String userEmail) {
        mUserEmail = userEmail;
    }

    public void saveUserProfileToSharePreferences(UserProfile userProfile){
        setUserProfile(userProfile);
        if (mUserProfile != null){
            SharedPreferences sharedPreferences = getSharedPreferences(PREFERENCES_USER_PROFILE_NAME, Context.MODE_PRIVATE);
            Editor editor = sharedPreferences.edit();
            try {
                editor.putString(PREFERENCES_USER_PROFILE, userProfile.encodeUserProfileToJSON().toString());
                editor.apply();
            } catch (JSONException e) {
                LoggerFactory.getLogger("UserProfile").error("UserProfile: encodeUserProfileToJSON", e.getMessage());
            }
        }
    }

    public void clearUserProfile(){
        mUserProfile = null;
        getSharedPreferences(PREFERENCES_USER_PROFILE_NAME, Context.MODE_PRIVATE).edit()
                .remove(PREFERENCES_USER_PROFILE).apply();
    }

    public void loadUserProfile(){
        String tkgzUserProfile = getSharedPreferences(PREFERENCES_USER_PROFILE_NAME, Context.MODE_PRIVATE)
                .getString(PREFERENCES_USER_PROFILE, null);
        if (tkgzUserProfile != null && tkgzUserProfile.length() > 0){
            mUserProfile = new UserProfile();
            try {
                mUserProfile.decodeUserProfileFromJSON(tkgzUserProfile);
            } catch (JSONException e) {
                LoggerFactory.getLogger("UserProfile").error("UserProfile: decodeUserProfileFromJSON", e.getMessage());
            }
        }
    }

    public void saveUserEmailToSharePreferences(String email){
        setUserEmail(email);
        if (!TextUtils.isEmpty(mUserEmail)) {
            SharedPreferences sharedPreferences = getSharedPreferences(PREFERENCES_USER_EMAIL_NAME, Context.MODE_PRIVATE);
            Editor editor = sharedPreferences.edit();
            editor.putString(PREFERENCES_USER_EMAIL, email);
            editor.apply();
        }
    }

    public void loadUserEmail(){
        String userEmail = getSharedPreferences(PREFERENCES_USER_EMAIL_NAME, Context.MODE_PRIVATE).getString(PREFERENCES_USER_EMAIL, "");
        if (!TextUtils.isEmpty(userEmail)){
            mUserEmail = userEmail;
        }
    }

    public static ArrayList<EventTypeProfile> getEventTypeList() {
        if (sEventTypeList == null) {
            sEventTypeList = new ArrayList<>();
        }
        return sEventTypeList;
    }

    public static void setEventTypeList(ArrayList<EventTypeProfile> eventTypes) {
        sEventTypeList = eventTypes;
    }

    @Override
    public void onXOCreate() {
        sInstance = this;
        loadUserProfile();
        loadUserEmail();
        loadPreferencesLanguage();
    }

    @Override
    protected String getConfigurationAssetsPath() {
        return "tkgz.properties";
    }

    @Override
    protected XOConfiguration getConfiguration() {
        return TKGZConfiguration.getInstance();
    }

    @Override
    protected void initConfiguration() {
        TKGZConfiguration.init(this);
    }

    public void switchLanguage(String language) {
        Resources resources = getResources();
        Configuration configuration = resources.getConfiguration();
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        if (language.equals("en")){
            configuration.locale = Locale.ENGLISH;
        } else {
            configuration.locale = Locale.SIMPLIFIED_CHINESE;
        }
        resources.updateConfiguration(configuration, displayMetrics);
        saveLanguageToSharePreferences(language);
    }

    public void loadPreferencesLanguage(){
        String language = getSharedPreferences(PREFERENCES_LANGUAGE, Context.MODE_PRIVATE).getString(PREFERENCES_LANGUAGE, "");
        if (!TextUtils.isEmpty(language)){
            switchLanguage(language);
        }
    }

    public void saveLanguageToSharePreferences(String language){
        if (!TextUtils.isEmpty(language)) {
            SharedPreferences sharedPreferences = getSharedPreferences(PREFERENCES_LANGUAGE, Context.MODE_PRIVATE);
            Editor editor = sharedPreferences.edit();
            editor.putString(PREFERENCES_LANGUAGE, language);
            editor.apply();
        }
    }

}
