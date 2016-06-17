package com.xogrp.tkgz.Widget;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import com.xogrp.tkgz.listener.DialogController;
import com.xogrp.tkgz.model.DialogSelectedProfile;

import java.util.ArrayList;

/**
 * Created by ayu on 11/19/2015 0019.
 */
public abstract class BaseChooseDialog extends Dialog implements DialogController, View.OnClickListener {

    public BaseChooseDialog(Context context) {
        super(context);
    }


    abstract protected int getLayoutResId();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(getLayoutResId());
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        initView();
    }

    abstract protected void initView();

    @Override
    public void setItemList(ArrayList<DialogSelectedProfile> list) {
    }

}