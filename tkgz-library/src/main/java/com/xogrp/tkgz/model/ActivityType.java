package com.xogrp.tkgz.model;

import java.io.Serializable;

/**
 * Created by jdeng on 4/28/2016.
 */
public class ActivityType implements Serializable  {
    int id;
    String name;

    public ActivityType(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
