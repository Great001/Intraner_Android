package com.xogrp.tkgz.fragment;

import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.xogrp.tkgz.R;

public class FeedbackFragment extends AbstractTKGZFragment {
    private EditText mEtSendName, mEtSendMessage;
    private Button mBtnSend;

    @Override
    protected void onTKGZAttach(Activity activity) {
        super.onTKGZAttach(activity);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.feedback_layout;
    }

    @Override
    protected void onTKGZCreateView(View rootView) {
//        getTKActionbar().setDisplayHomeAsUpEnabled(true);
//        getTKActionbar().setHomeAsUpIndicator(R.drawable.icon_back);
        mEtSendName = (EditText)rootView.findViewById(R.id.et_send_name);
        mEtSendMessage = (EditText)rootView.findViewById(R.id.et_send_message);
        mBtnSend = (Button)rootView.findViewById(R.id.btn_send);
        mBtnSend.setOnClickListener(mOnSendListener);
    }

    @Override
    protected void onTKGZActivityCreated() {

    }

    @Override
    public String getTransactionTag() {
        return "feedback_fragment";
    }

    @Override
    public boolean isHideActionBar() {
        return false;
    }

    @Override
    public String getActionBarTitle() {
        return getString(R.string.actionbar_title_feedback_page);
    }

    @Override
    protected int getMenuResourceId() {
        return -1;
    }

    private View.OnClickListener mOnSendListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
        }
    };

}
