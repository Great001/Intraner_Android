package com.xogrp.tkgz.model;


public class PersonnelSectionSwipe {

    private long sectionId;
    private String sectionName;
    private int memberAmount;
//    private String memberAmount;

    public PersonnelSectionSwipe(long sectionId, String sectionName, int memberAmount) {
        this.sectionId = sectionId;
        this.sectionName = sectionName;
        this.memberAmount = memberAmount;
    }

    public long getSectionId() {
        return sectionId;
    }

    public void setSectionId(long sectionId) {
        this.sectionId = sectionId;
    }

    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

    public int getMemberAmount() {
        return memberAmount;
    }

    public void setMemberAmount(int memberAmount) {
        this.memberAmount = memberAmount;
    }
}
