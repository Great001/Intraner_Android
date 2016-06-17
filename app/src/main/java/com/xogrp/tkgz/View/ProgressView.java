package com.xogrp.tkgz.View;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.xogrp.tkgz.R;

/**
 * Created by ayu on 3/11/2016 0011.
 */
public class ProgressView {

    private AlertDialog mDialog;
    private AlertDialog.Builder mBuilder;

    public ProgressView(Context context, String message) {
        mDialog = new AlertDialog.Builder(context).create();
        onCreate(context, message);
    }

    private void onCreate(Context context, String message) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_progress_view_dialog, null);
        TextView tv = (TextView) view.findViewById(R.id.tv_message);
        tv.setText(message);
//        mBuilder.create().requestWindowFeature(Window.FEATURE_NO_TITLE);
        mDialog.setView(view);
        mDialog.setCanceledOnTouchOutside(false);
    }

    public void show() {
//        mBuilder.create().show();
        mDialog.show();
    }

    public void dismiss() {
//        mBuilder.create().dismiss();
        mDialog.dismiss();
    }

    public boolean isShowing() {
        return mDialog.isShowing();
    }


}
