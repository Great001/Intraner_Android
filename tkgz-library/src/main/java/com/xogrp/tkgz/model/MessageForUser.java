package com.xogrp.tkgz.model;

import org.json.JSONObject;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Created by ayu on 10/21/2015 0021.
 */
public class MessageForUser implements Serializable {
    private String id;
    private String title;
    private String content;
    private long createTime;
    private boolean status;
    private static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");


    public MessageForUser() {
    }

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

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public static MessageForUser getObjectFromJSON(JSONObject jsonObject) {
        MessageForUser message = new MessageForUser();
        try {
            message.setCreateTime(SIMPLE_DATE_FORMAT.parse(jsonObject.optString("create_time")).getTime());
        } catch (ParseException e) {
            LoggerFactory.getLogger("MessageForUser").error("MessageForUser: getObjectFromJSON", e.getMessage());
        }
        message.setId(jsonObject.optString("id"));
        message.setTitle(jsonObject.optString("message_title"));
        message.setContent(jsonObject.optString("message_content"));
        message.setStatus(jsonObject.optBoolean("status"));
        return message;
    }
}
