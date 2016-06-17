package com.xogrp.tkgz.Widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.xogrp.tkgz.R;
import com.xogrp.tkgz.Widget.TKGZCustomDialog.OnDialogActionCallback;
import com.xogrp.tkgz.Widget.TKGZCustomDialog.OnDismissDialogCallback;

import org.w3c.dom.Text;

public class TKGZCustomEditDialog extends Dialog implements View.OnClickListener {

    private String mTitle, mMessage,
            mOkButtonMessage, mCancelButtonMessage;
    private OnDismissDialogCallback mOnDismissDialogCallback;
    private OnDialogActionCallback mDialogActionCallback;
    private OnCustomEditDialogListener mListener;
    private EditText etMessage;

    public TKGZCustomEditDialog(Context context, int titleResourceId, String message,
                                int okButtonMessageResId, int cancelMessageResId) {
        super(context, R.style.TKGZDialogTheme);
        mTitle = context.getString(titleResourceId);
        mMessage = message;
        mOkButtonMessage = context.getString(okButtonMessageResId);
        mCancelButtonMessage = context.getString(cancelMessageResId);
    }


    public TKGZCustomEditDialog(Context context, int titleResourceId, String message,
                                int okButtonMessageResId, int cancelMessageResId, OnDialogActionCallback dialogActionCallback, OnDismissDialogCallback dismissDialogCallback) {
        super(context, R.style.TKGZDialogTheme);

        mDialogActionCallback = dialogActionCallback;
        mOnDismissDialogCallback = dismissDialogCallback;
        mTitle = context.getString(titleResourceId);
        mMessage = message;
        mOkButtonMessage = context.getString(okButtonMessageResId);
        mCancelButtonMessage = context.getString(cancelMessageResId);
    }

    public TKGZCustomEditDialog(Context context, int titleResourceId, String message,
                                int okButtonMessageResId, int cancelMessageResId, OnDialogActionCallback dialogActionCallback, OnDismissDialogCallback dismissDialogCallback, OnCustomEditDialogListener listener) {
        super(context, R.style.TKGZDialogTheme);

        mDialogActionCallback = dialogActionCallback;
        mOnDismissDialogCallback = dismissDialogCallback;
        mTitle = context.getString(titleResourceId);
        mMessage = message;
        mOkButtonMessage = context.getString(okButtonMessageResId);
        mCancelButtonMessage = context.getString(cancelMessageResId);
        mListener = listener;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setContentView(R.layout.tkgz_edit_dialog_layout);

        TextView tvTitle = (TextView) findViewById(R.id.tv_title);
        etMessage = (EditText) findViewById(R.id.et_content);
        TextView tvOk = (TextView) findViewById(R.id.tv_ok);
        TextView tvCancel = (TextView) findViewById(R.id.tv_cancel);

        tvTitle.setText(mTitle);
        if (!mMessage.isEmpty()) {
            etMessage.setVisibility(View.VISIBLE);
        }
        etMessage.setHint(mMessage);
        if (!mOkButtonMessage.isEmpty()) {
            tvOk.setText(mOkButtonMessage);
        }
        if (!mCancelButtonMessage.isEmpty()) {
            tvCancel.setText(mCancelButtonMessage);
        }
        tvOk.setOnClickListener(this);
        tvCancel.setOnClickListener(mOnCancelClickListener);
    }

    @Override
    public void onClick(View v) {
        dismiss();
        if (mListener == null)
            return;
        if (!TextUtils.isEmpty(etMessage.getText())) {
            mListener.callBack(etMessage.getText().toString());
        } else {
            mListener.callBack(null);
        }
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

    private View.OnClickListener mOnCancelClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            dismiss();
        }
    };

    public interface OnCustomEditDialogListener {
        public void callBack(String str);
    }
}
