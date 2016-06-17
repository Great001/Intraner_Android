package com.xogrp.tkgz.activity;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.xogrp.tkgz.fragment.AbstractTKGZFragment;

/**
 * Created by dgao on 7/27/2015.
 */
public abstract class AbstractTKGZActivity extends AppCompatActivity {

    protected void addFragmentAndAdd2BackStack(AbstractTKGZFragment fragment) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(getContainerResId(), fragment, fragment.getTransactionTag());
        ft.addToBackStack(fragment.getTransactionTag());
        ft.commitAllowingStateLoss();
    }

    protected void addFragment(AbstractTKGZFragment fragment) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(getContainerResId(), fragment, fragment.getTransactionTag());
        ft.commitAllowingStateLoss();
    }


    protected void replaceFragment(AbstractTKGZFragment fragment) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(getContainerResId(), fragment, fragment.getTransactionTag());
        ft.commitAllowingStateLoss();
    }

    protected void replaceFragmentAndAdd2BackStack(AbstractTKGZFragment fragment) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(getContainerResId(), fragment, fragment.getTransactionTag());
        ft.addToBackStack(fragment.getTransactionTag());
        ft.commitAllowingStateLoss();
    }

    protected abstract int getContainerResId();

    protected boolean isPopBackStack() {
        boolean isBackSuccess = false;
        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            getSupportFragmentManager().popBackStack();
            isBackSuccess = true;
        }
        return isBackSuccess;
    }

    protected void popBackStack() {
        getSupportFragmentManager().popBackStack();
    }

    protected void logOut(){
        getSupportFragmentManager().popBackStackImmediate();
    }

}
