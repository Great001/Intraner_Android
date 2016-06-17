package com.xogrp.tkgz.model;

import java.io.Serializable;

/**
 * Created by wlao on 1/5/2016.
 */
public class SaveMyAccountProfile implements Serializable {
    private String background;
    private String avatar;
    private String phoneNumber;
    private String selfDescription;

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getSelfDescription() {
        return selfDescription;
    }

    public void setSelfDescription(String selfDescription) {
        this.selfDescription = selfDescription;
    }
}
