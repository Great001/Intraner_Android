package com.xogrp.tkgz.Widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.xogrp.tkgz.R;
import com.xogrp.tkgz.Widget.TKGZCustomDialog.OnDismissDialogCallback;
import com.xogrp.tkgz.Widget.TKGZCustomDialog.OnDialogActionCallback;

public class TKGZCustomHomeMenuDialog extends Dialog implements View.OnClickListener{

    private String mTitle, mContentTitleLeft, mContentTitleRight, mMessage,
            mOkButtonMessage, mCancelButtonMessage;
    private OnDismissDialogCallback mOnDismissDialogCallback;
    private OnDialogActionCallback mDialogActionCallback;

    public TKGZCustomHomeMenuDialog(Context context, int titleResourceId, int contentTitleLeftResId, String contentTitleRight, String message,
                                    int okButtonMessageResId, OnDialogActionCallback dialogActionCallback, OnDismissDialogCallback dismissDialogCallback) {
        super(context, R.style.TKGZDialogTheme);
        mDialogActionCallback = dialogActionCallback;
        mOnDismissDialogCallback = dismissDialogCallback;
        mTitle = context.getString(titleResourceId);
        mContentTitleLeft = context.getString(contentTitleLeftResId);
        mContentTitleRight = contentTitleRight;
        mMessage = message;
        mOkButtonMessage = context.getResources().getString(okButtonMessageResId);
    }

    public TKGZCustomHomeMenuDialog(Context context, int titleResourceId, int contentTitleLeftResId, String contentTitleRight, String message,
               int okButtonMessageResId, int cancelMessageResId, OnDialogActionCallback dialogActionCallback, OnDismissDialogCallback dismissDialogCallback) {
        this(context, titleResourceId, contentTitleLeftResId, contentTitleRight, message, okButtonMessageResId, dialogActionCallback, dismissDialogCallback);
        mCancelButtonMessage = context.getResources().getString(cancelMessageResId);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setContentView(R.layout.tkgz_home_menu_dialog_layout);

        TextView tvTitle = (TextView) findViewById(R.id.tv_title);
        TextView tvMessage = (TextView) findViewById(R.id.tv_content);
        TextView tvContentTitleLeft = (TextView) findViewById(R.id.tv_content_title_left);
        TextView tvContentTitleRight = (TextView) findViewById(R.id.tv_content_title_right);
        TextView tvOk = (TextView) findViewById(R.id.tv_ok);
        TextView tvCancel = (TextView) findViewById(R.id.tv_cancel);

        tvTitle.setText(mTitle);
        tvContentTitleLeft.setText(mContentTitleLeft);
        tvContentTitleRight.setText(mContentTitleRight);
        if(!mMessage.isEmpty()){
            tvMessage.setVisibility(View.VISIBLE);
        }
        tvMessage.setText(mMessage);
        if(!TextUtils.isEmpty(mOkButtonMessage)){
            tvOk.setVisibility(View.VISIBLE);
            tvOk.setText(mOkButtonMessage);
        }
        if(!TextUtils.isEmpty(mCancelButtonMessage)){
            tvCancel.setVisibility(View.VISIBLE);
            tvCancel.setText(mCancelButtonMessage);
        }
        tvOk.setOnClickListener(this);
        tvCancel.setOnClickListener(mOnCancelClickListener);
    }

    @Override
    public void onClick(View v) {
        dismiss();
        if(mOnDismissDialogCallback!=null){
            mOnDismissDialogCallback.onDialogDismiss();
        }
        if(mDialogActionCallback != null){
            mDialogActionCallback.onConfirmSelected();
        }
    }

    @Override
    public void onBackPressed() {
        if(mOnDismissDialogCallback != null){
            mOnDismissDialogCallback.onDialogDismiss();
        }
    }

    private View.OnClickListener mOnCancelClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            dismiss();
        }
    };
}
