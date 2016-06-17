package com.xogrp.tkgz.model;

/**
 * Created by jdeng on 5/10/2016.
 */
public class CommonAdressProfile {
    int address_id;
    String address_name;

    public int getAddress_id() {
        return address_id;
    }

    public CommonAdressProfile(String address_name, int address_id) {
        this.address_name = address_name;
        this.address_id = address_id;
    }

    public void setAddress_id(int address_id) {
        this.address_id = address_id;
    }

    public String getAddress_name() {
        return address_name;
    }

    public void setAddress_name(String address_name) {
        this.address_name = address_name;
    }
}
