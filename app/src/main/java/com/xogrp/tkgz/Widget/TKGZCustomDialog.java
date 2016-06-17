package com.xogrp.tkgz.Widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.xogrp.tkgz.R;

/**
 * Created by dgao on 7/27/2015.
 */
public class TKGZCustomDialog extends Dialog implements View.OnClickListener {

    private String mTitle, mMessage;
    private OnDismissDialogCallback mOnDismissDialogCallback;
    private boolean mIsShowCancelButton = false;
    private OnDialogActionCallback mDialogActionCallback;

    public TKGZCustomDialog(Context context, int titleResourceId, int messageResourceId) {
        this(context, titleResourceId, messageResourceId, false);
    }

    public TKGZCustomDialog(Context context, int titleResourceId, String message) {
        this(context, titleResourceId, message, false);
    }

    public TKGZCustomDialog(Context context, int titleResourceId, String message, boolean shouldShowCancelButton) {
        super(context, R.style.TKGZDialogTheme);

        mTitle = context.getString(titleResourceId);
        mMessage = message;
        this.mIsShowCancelButton = shouldShowCancelButton;
    }

    public TKGZCustomDialog(Context context, int titleResourceId, int messageResourceId, boolean shouldShowCancelButton) {
        this(context, titleResourceId, context.getString(messageResourceId), shouldShowCancelButton);
    }

    public TKGZCustomDialog(Context context, int titleResourceId, int messageResourceId, OnDialogActionCallback dialogActionCallback,
                            boolean shouldShowCancelButton) {
        this(context, titleResourceId, messageResourceId, shouldShowCancelButton);
        mDialogActionCallback = dialogActionCallback;
    }

    public TKGZCustomDialog(Context context, int titleResourceId, int messageResourceId, OnDialogActionCallback dialogActionCallback,
                            OnDismissDialogCallback onDismissDialogCallback, boolean shouldShowCancelButton) {
        this(context, titleResourceId, messageResourceId, dialogActionCallback, shouldShowCancelButton);
        mOnDismissDialogCallback = onDismissDialogCallback;
    }

    public TKGZCustomDialog(Context context, int titleResourceId, String message, OnDialogActionCallback dialogActionCallback,
                            OnDismissDialogCallback onDismissDialogCallback, boolean shouldShowCancelButton) {
        this(context, titleResourceId, message, shouldShowCancelButton);
        mDialogActionCallback = dialogActionCallback;
        mOnDismissDialogCallback = onDismissDialogCallback;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setContentView(R.layout.tkgz_dialog_layout);

        TextView tvTitle = (TextView) findViewById(R.id.tv_title);
        TextView tvMessage = (TextView) findViewById(R.id.tv_content);
        TextView tvOk = (TextView) findViewById(R.id.tv_ok);
        TextView tvCancel = (TextView) findViewById(R.id.tv_cancel);
        if (mIsShowCancelButton) {
            tvCancel.setVisibility(View.VISIBLE);
        } else {
            tvCancel.setVisibility(View.GONE);
        }

        tvTitle.setText(mTitle);
        tvMessage.setText(mMessage);
        tvOk.setOnClickListener(this);
        tvCancel.setOnClickListener(mOnCancelClickListener);
    }

    @Override
    public void onClick(View v) {
        dismiss();
        if (mOnDismissDialogCallback != null) {
            mOnDismissDialogCallback.onDialogDismiss();
        }
        if (mDialogActionCallback != null) {
            mDialogActionCallback.onConfirmSelected();
        }
    }

    @Override
    public void onBackPressed() {
        dismiss();
        if (mOnDismissDialogCallback != null) {
            mOnDismissDialogCallback.onDialogDismiss();
        }
    }


    public static interface OnDismissDialogCallback {
        public void onDialogDismiss();
    }

    public static interface OnDialogActionCallback {
        public void onConfirmSelected();
    }

    private View.OnClickListener mOnCancelClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            dismiss();
        }
    };
}
