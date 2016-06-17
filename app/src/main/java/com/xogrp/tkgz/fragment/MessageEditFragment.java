package com.xogrp.tkgz.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.xogrp.tkgz.R;
import com.xogrp.tkgz.Widget.CheckBoxDialog;
import com.xogrp.tkgz.model.DialogSelectedProfile;
import com.xogrp.tkgz.model.MessageProfile;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by ayu on 9/18/2015 0018.
 */
public class MessageEditFragment extends AbstractTKGZFragment implements View.OnClickListener, CheckBoxDialog.ReceiverController {

    private String[] RECEIVERS = {"Admin Team", "Local Team", "Mobile Team",
            "Alex Qiu", "Deon Gao", "Piano Chen", "Almin Chou", "Ashley Han",
            "Will", "Jerry", "Jane Li", "Ethan Yu", "Claire Li", "Emily Ling", "Minnie Chiu"};
    private MessageProfile mMessageProfile;
    private EditText mEtTitle;
    private EditText mEtContent;
    private TextView mTvRecipients;
    private Calendar mCalendar;
    private ArrayList<String> mRecipientsId = new ArrayList<>();
    private ArrayList<DialogSelectedProfile> mItemList = new ArrayList<>();
    private static final String BUNDLE_KEY_MESSAGE = "message";

    public static MessageEditFragment newInstance(MessageProfile message) {

        Bundle args = new Bundle();
        args.putSerializable(BUNDLE_KEY_MESSAGE, message);
        MessageEditFragment fragment = new MessageEditFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private ArrayList<DialogSelectedProfile> initData() {
        DialogSelectedProfile recipient = null;
        ArrayList<DialogSelectedProfile> list = new ArrayList<>();
        int index = 0;
        for (String name : RECEIVERS) {
            recipient = new DialogSelectedProfile(name, String.valueOf(index), false);
            index++;
            list.add(recipient);
        }
        return list;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_message_edit;
    }

    @Override
    protected void onTKGZCreateView(View rootView) {
        mEtTitle = (EditText) rootView.findViewById(R.id.et_title);
        mEtContent = (EditText) rootView.findViewById(R.id.et_content);
        mTvRecipients = (TextView) rootView.findViewById(R.id.tv_receiver);

        Button btnSave = (Button) rootView.findViewById(R.id.btn_save);
        Button btnSend = (Button) rootView.findViewById(R.id.btn_send);
        mTvRecipients.setOnClickListener(this);
        btnSend.setOnClickListener(this);
        btnSave.setOnClickListener(this);
    }

    @Override
    protected void onTKGZActivityCreated() {
        mCalendar = Calendar.getInstance();
        mItemList = initData();
        mMessageProfile = (MessageProfile) getArguments().getSerializable(BUNDLE_KEY_MESSAGE);
        if (mMessageProfile != null) {
            mEtTitle.setText(mMessageProfile.getTitle());
            mEtContent.setText(mMessageProfile.getContent());
        } else {
            mMessageProfile = new MessageProfile();
        }
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
        return getString(R.string.actionbar_title_message_add_new);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_receiver:
                CheckBoxDialog dialog = new CheckBoxDialog(getActivity(), mItemList, this);
                dialog.setCanceledOnTouchOutside(false);
                dialog.show();
                break;
            case R.id.btn_save:
                if (TextUtils.isEmpty(mEtTitle.getText()) || TextUtils.isEmpty(mEtContent.getText())) {
                    Toast.makeText(getActivity(), getString(R.string.toast_empty_value), Toast.LENGTH_SHORT).show();
                    break;
                }
                mMessageProfile.setTitle(mEtTitle.getText().toString());
                mMessageProfile.setContent(mEtContent.getText().toString());
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
                mMessageProfile.setCreateTime(dateFormat.format(mCalendar.getTime()));
                Toast.makeText(getActivity(), getString(R.string.toast_save), Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_send:
                Toast.makeText(getActivity(), getString(R.string.toast_send), Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }

    }

    private void setReceiversName(ArrayList<String> list) {
        String str = "";
        for (String name : list) {
            str = String.format("%s%s;", str, name);
        }
        mTvRecipients.setText(str);
    }


    @Override
    public void setReceiverList(ArrayList<DialogSelectedProfile> list) {
        ArrayList<String> nameList = new ArrayList<>();
        mRecipientsId.clear();
        for (DialogSelectedProfile receiver : list) {
            if (receiver.isSelected()) {
                nameList.add(receiver.getName());
                mRecipientsId.add(receiver.getId());
            }
        }
        setReceiversName(nameList);
    }
}
