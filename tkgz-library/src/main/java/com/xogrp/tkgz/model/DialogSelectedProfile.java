package com.xogrp.tkgz.model;

/**
 * Created by ayu on 11/23/2015 0023.
 */
public class DialogSelectedProfile {
    private String name;
    private String id;
    private boolean isSelected;

    public DialogSelectedProfile() {
    }

    public DialogSelectedProfile(String name, String id, boolean isSelected) {
        this.name = name;
        this.id = id;
        this.isSelected = isSelected;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }
}
