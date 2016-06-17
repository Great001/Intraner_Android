package com.xogrp.tkgz.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ayu on 9/17/2015.
 */
public class MessageProfile implements Serializable {
    private long id;
    private String title;
    private String content;
    private String createTime;
    private List<UserProfile> receiverList;

    public MessageProfile() {
    }

    public MessageProfile(String title, String content, String createTime) {
        this.title = title;
        this.content = content;
        this.createTime = createTime;
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

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public List<UserProfile> getReceiverList() {
        return receiverList;
    }

    public void setReceiverList(List<UserProfile> receiverList) {
        this.receiverList = receiverList;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
