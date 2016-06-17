package com.xogrp.tkgz.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by ayu on 10/20/2015 0020.
 */
public class EventProfile implements Serializable {
    private String id;
    private String title;
    private String content;
    private EventTypeProfile eventTypeProfile;
    private List<PeopleProfile> lecture;
    private UserProfile initiator;
    private FrequentAddressProfile frequentAddress;
    private String address;
    private ArrayList<UserProfile> invitedUsers;
    private ArrayList<UserProfile> staff;
    private ArrayList<IntegralTypeProfile> integralTypes;
    private int capacity;
    private int registration;
    private int status;
    private long enrollStartTime;
    private long enrollEndTime;
    private long eventStartTime;
    private long eventEndTime;
    private long createTime;
    private boolean allowEnroll;
    private boolean allowCheckIn;
    private boolean isInternal;
    private boolean useFrequentAddress;
    private String eventTypeName;
    private boolean needEnroll;

    private static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public EventTypeProfile getEventTypeProfile() {
        return eventTypeProfile;
    }

    public void setEventTypeProfile(EventTypeProfile type) {
        this.eventTypeProfile = type;
    }

    public long getEnrollStartTime() {
        return enrollStartTime;
    }

    public void setEnrollStartTime(long enrollStartTime) {
        this.enrollStartTime = enrollStartTime;
    }

    public long getEnrollEndTime() {
        return enrollEndTime;
    }

    public void setEnrollEndTime(long enrollEndTime) {
        this.enrollEndTime = enrollEndTime;
    }

    public long getEventStartTime() {
        return eventStartTime;
    }

    public void setEventStartTime(long eventStartTime) {
        this.eventStartTime = eventStartTime;
    }

    public long getEventEndTime() {
        return eventEndTime;
    }

    public void setEventEndTime(long eventEndTime) {
        this.eventEndTime = eventEndTime;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getRegistration() {
        return registration;
    }

    public void setRegistration(int registration) {
        this.registration = registration;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public UserProfile getInitiator() {
        return initiator;
    }

    public void setInitiator(UserProfile initiator) {
        this.initiator = initiator;
    }

    public boolean isAllowEnroll() {
        return allowEnroll;
    }

    public void setAllowEnroll(boolean allow_enroll) {
        this.allowEnroll = allow_enroll;
    }

    public boolean isAllowCheckIn() {
        return allowCheckIn;
    }

    public void setAllowCheckIn(boolean allow_check_in) {
        this.allowCheckIn = allow_check_in;
    }

    public ArrayList<UserProfile> getInvitedUsers() {
        return invitedUsers;
    }

    public void setInvitedUsers(ArrayList<UserProfile> invitedUsers) {
        this.invitedUsers = invitedUsers;
    }

    public ArrayList<UserProfile> getStaff() {
        return staff;
    }

    public void setStaff(ArrayList<UserProfile> staff) {
        this.staff = staff;
    }

    public List<PeopleProfile> getLecture() {
        return lecture;
    }

    public void setLecture(List<PeopleProfile> lecture) {
        this.lecture = lecture;
    }

    public ArrayList<IntegralTypeProfile> getIntegralTypes() {
        return integralTypes;
    }

    public void setIntegralTypes(ArrayList<IntegralTypeProfile> integralTypes) {
        this.integralTypes = integralTypes;
    }

    public FrequentAddressProfile getFrequentAddress() {
        return frequentAddress;
    }

    public void setFrequentAddress(FrequentAddressProfile frequentAddressProfile) {
        this.frequentAddress = frequentAddressProfile;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean isInternal() {
        return isInternal;
    }

    public void setIsInternal(boolean isInternal) {
        this.isInternal = isInternal;
    }

    public boolean isUseFrequentAddress() {
        return useFrequentAddress;
    }

    public void setUseFrequentAddress(boolean useFrequentAddress) {
        this.useFrequentAddress = useFrequentAddress;
    }

    public String getEventTypeName() {
        return eventTypeName;
    }

    public void setEventTypeName(String eventTypeName) {
        this.eventTypeName = eventTypeName;
    }

    public boolean isNeedEnroll() {
        return needEnroll;
    }

    public void setNeedEnroll(boolean needEnroll) {
        this.needEnroll = needEnroll;
    }

    private void setEventDate(JSONObject jsonObject) throws ParseException {
        Date date;
        String str;
        // TODO: 2/24/2016 0024
        // show the right data with a stupid way, show add a filed(need_enroll) in api when get event list
        str = jsonObject.optString("sign_start");
        if (!str.equals("null")) {
            date = SIMPLE_DATE_FORMAT.parse(str);
            this.setEnrollStartTime(date.getTime());
            date = SIMPLE_DATE_FORMAT.parse(jsonObject.optString("sign_end"));
            this.setEnrollEndTime(date.getTime());
        }
        date = SIMPLE_DATE_FORMAT.parse(jsonObject.optString("event_start"));
        this.setEventStartTime(date.getTime());
        date = SIMPLE_DATE_FORMAT.parse(jsonObject.optString("event_end"));
        this.setEventEndTime(date.getTime());
        date = SIMPLE_DATE_FORMAT.parse(jsonObject.optString("create_time"));
        this.setCreateTime(date.getTime());
    }

    public static EventProfile getDetailObjectFromJSON(JSONObject jsonObject) throws JSONException, ParseException {
        EventProfile event = new EventProfile();
        event.setId(jsonObject.optString("event_id"));
        event.setTitle(jsonObject.optString("event_title"));
        event.setEventTypeName(jsonObject.optJSONObject("event_type").optString("name"));
        event.setContent(jsonObject.optString("content"));
        event.setCapacity(jsonObject.optInt("capacity"));
        event.setRegistration(jsonObject.optInt("registration"));
        event.setStatus(jsonObject.optInt("status"));
        event.setAllowCheckIn(jsonObject.optBoolean("allow_check_in"));
        event.setAllowEnroll(jsonObject.optBoolean("allow_enroll"));
        event.setInitiator(UserProfile.getObjectFromEventDetailPage(jsonObject.optJSONObject("initiator")));
        event.setIntegralTypes(getIntegralTypes(jsonObject.optJSONArray("integral_type")));
        event.setIsInternal(jsonObject.optBoolean("is_internal"));
        event.setNeedEnroll(jsonObject.optBoolean("need_enroll"));

        event.setUseFrequentAddress(jsonObject.optBoolean("use_frequent_address"));
        if (event.isUseFrequentAddress()) {
            event.setFrequentAddress(FrequentAddressProfile.getObjectFromJSON(jsonObject.optJSONObject("frequent_address")));
        } else {
            event.setAddress(jsonObject.optString("address"));
        }
        JSONArray meta_data = jsonObject.optJSONArray("meta_data");

        List<PeopleProfile> list = new ArrayList<>();
        if (meta_data.length() != 0) {
            JSONObject lecture = meta_data.optJSONObject(0).optJSONObject("lecturer");
            if (lecture != null) {
                JSONArray array = lecture.optJSONArray("names");
                if (array != null) {
                    int length = array.length();
                    for (int index = 0; index < length; index++) {
                        list.add(PeopleProfile.formJSON(array.getJSONObject(index)));
                    }
                }
            }
        }
        event.setLecture(list);

        event.setEventDate(jsonObject);

        event.setInvitedUsers(getUserProfilesFromJSONArray(jsonObject.optJSONArray("invited_users")));
        event.setStaff(getUserProfilesFromJSONArray(jsonObject.optJSONArray("staff")));

        return event;
    }

    private static ArrayList<IntegralTypeProfile> getIntegralTypes(JSONArray jsonArray) {
        ArrayList<IntegralTypeProfile> list = new ArrayList<>();
        if (jsonArray != null) {
            int size = jsonArray.length();
            for (int index = 0; index < size; index++) {
                list.add(IntegralTypeProfile.getObjectFromJSON(jsonArray.optJSONObject(index)));
            }
        }
        return list;
    }

    private static ArrayList<UserProfile> getUserProfilesFromJSONArray(JSONArray jsonArray) throws JSONException {
        ArrayList<UserProfile> userProfiles = new ArrayList<>();
        if (jsonArray != null) {
            int size = jsonArray.length();
            for (int index = 0; index < size; index++) {
                JSONObject object = jsonArray.optJSONObject(index);
                JSONArray users = object.optJSONArray("users");
                int userCount = users.length();
                for (int i = 0; i < userCount; i++) {
                    JSONObject user = users.optJSONObject(i);
                    userProfiles.add(UserProfile.getObjectFromEventDetailPage(user));
                }
            }
        }
        return userProfiles;
    }

    public static EventProfile getBriefObjectFromJSON(JSONObject jsonObject) {
        EventProfile event = new EventProfile();

        event.setId(jsonObject.optString("event_id"));
        event.setTitle(jsonObject.optString("event_title"));
        event.setContent(jsonObject.optString("content"));
        event.setCapacity(jsonObject.optInt("capacity"));
        event.setRegistration(jsonObject.optInt("registration"));
        event.setStatus(jsonObject.optInt("status"));
        try {
            event.setEventDate(jsonObject);
        } catch (ParseException e) {
            LoggerFactory.getLogger("EventProfile").error("EventProfile: getBriefObjectFromJSON", e.getMessage());
        }
        return event;
    }
}
