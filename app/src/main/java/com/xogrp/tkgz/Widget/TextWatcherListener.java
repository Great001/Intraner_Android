package com.xogrp.tkgz.Widget;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

/**
 * Created by wlao on 2/1/2016.
 */
public class TextWatcherListener implements TextWatcher {
    private View mView;
    private OnTextWatcherListener mOnTextWatcherListener;

    public TextWatcherListener(OnTextWatcherListener onTextWatcherListener, View view) {
        this.mView = view;
        this.mOnTextWatcherListener = onTextWatcherListener;
    }

    public interface OnTextWatcherListener {
        void onTextChanged(View view, String text);
        void afterTextChanged(View view);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        mOnTextWatcherListener.onTextChanged(mView, s.toString());
    }

    @Override
    public void afterTextChanged(Editable s) {
        mOnTextWatcherListener.afterTextChanged(mView);
    }

}
