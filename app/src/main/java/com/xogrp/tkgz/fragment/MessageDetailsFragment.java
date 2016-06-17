package com.xogrp.tkgz.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.xogrp.tkgz.R;
import com.xogrp.tkgz.model.MessageProfile;

/**
 * Created by ayu on 11/11/2015 0011.
 */
public class MessageDetailsFragment extends AbstractTKGZFragment implements View.OnClickListener {
    private TextView mTvTitle;
    private TextView mTvContent;
    private TextView mTvTime;
    private TextView mTvReceiver;
    private Button mBtnDelete;
    private MessageProfile mMessage;
    private Button mBtnEdit;
    private LinearLayout mLlReceiver;
    private int mDefaultHeight;
    private static final String BUNDLE_KEY_MESSAGE = "message";
    private static final String BUNDLE_KEY_POSITION = "position";

    public static MessageDetailsFragment newInstance(MessageProfile message, int position) {

        Bundle args = new Bundle();
        args.putSerializable(BUNDLE_KEY_MESSAGE, message);
        args.putInt(BUNDLE_KEY_POSITION, position);
        MessageDetailsFragment fragment = new MessageDetailsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_message_details;
    }

    @Override
    protected void onTKGZCreateView(View rootView) {
        mTvTitle = (TextView) rootView.findViewById(R.id.tv_title);
        mTvTime = (TextView) rootView.findViewById(R.id.tv_time);
        mLlReceiver = (LinearLayout) rootView.findViewById(R.id.ll_receiver);
        mTvReceiver = (TextView) rootView.findViewById(R.id.tv_receiver);
        mTvContent = (TextView) rootView.findViewById(R.id.tv_content);
        mBtnEdit = (Button) rootView.findViewById(R.id.btn_edit);
        mBtnDelete = (Button) rootView.findViewById(R.id.btn_delete);
    }

    @Override
    protected void onTKGZActivityCreated() {
        if (mMessage != null) {
            mMessage = (MessageProfile) getArguments().getSerializable(BUNDLE_KEY_MESSAGE);
            if (getArguments().getInt(BUNDLE_KEY_POSITION) == 0) {
                mBtnEdit.setVisibility(View.GONE);
            }
            mTvTitle.setText(mMessage.getTitle());
            mTvTime.setText(mMessage.getCreateTime());
            mTvContent.setText(mMessage.getContent());
        }
        mBtnDelete.setOnClickListener(this);
        mBtnEdit.setOnClickListener(this);
        mTvReceiver.setOnClickListener(this);
        LinearLayout.LayoutParams param = (LinearLayout.LayoutParams) mLlReceiver.getLayoutParams();
        mDefaultHeight = param.height;

    }

    @Override
    public String getTransactionTag() {
        return null;
    }

    @Override
    public boolean isHideActionBar() {
        return false;
    }

    @Override
    public String getActionBarTitle() {
        return getString(R.string.actionbar_title_message_details);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_delete:
                Toast.makeText(getActivity(), getString(R.string.toast_delete), Toast.LENGTH_SHORT).show();
                getOnScreenNavigationListener().goBackFromCurrentPage();
                break;
            case R.id.btn_edit:
                getOnScreenNavigationListener().navigateToMessageEditPage(MessageEditFragment.newInstance(mMessage));
                break;
            case R.id.tv_receiver:
                LinearLayout.LayoutParams param = (LinearLayout.LayoutParams) mLlReceiver.getLayoutParams();
                if (mTvReceiver.getLineCount() == 1) {
                    mTvReceiver.setSingleLine(false);
                    param.height = LinearLayout.LayoutParams.WRAP_CONTENT;
                    mLlReceiver.setLayoutParams(param);
                } else {
                    mTvReceiver.setSingleLine(true);
                    param.height = mDefaultHeight;
                    mLlReceiver.setLayoutParams(param);
                }
                break;
            default:
                break;
        }
    }
}
