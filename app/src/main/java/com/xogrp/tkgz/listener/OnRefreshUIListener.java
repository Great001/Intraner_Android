package com.xogrp.tkgz.listener;

/**
 * Created by ayu on 12/23/2015 0023.
 */
public interface OnRefreshUIListener {
    void callRefreshNewsList();

    void callRefreshNewsCount();

    void callRefreshIntegral();

    void callRefreshAvatar(String currentFragmentName);

    void callRefreshBackground(String currentFragmentName);
}
