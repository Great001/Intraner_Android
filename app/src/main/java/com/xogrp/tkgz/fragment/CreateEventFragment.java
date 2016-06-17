package com.xogrp.tkgz.fragment;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.xogrp.tkgz.R;
import com.xogrp.tkgz.Widget.RadioButtonDialog;
import com.xogrp.tkgz.listener.FragmentController;
import com.xogrp.tkgz.model.DialogSelectedProfile;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by ayu on 10/12/2015 0012.
 */
public class CreateEventFragment extends AbstractTKGZFragment implements View.OnClickListener, FragmentController {

    private EditText mEtTitle;
    private EditText mEtCount;
    private EditText mEtSignIntegral;
    private EditText mEtTestIntegral;
    private EditText mEtContent;
    private TextView mTvActivityType;
    private TextView mTvEnrollStartTime;
    private TextView mTvEnrollEndTime;
    private TextView mTvActivityStartTime;
    private TextView mTvActivityEndTime;
    private Button mBtnPublish;
    private ArrayList<DialogSelectedProfile> mList = new ArrayList<>();
    private SimpleDateFormat mDateFormat;
    private SimpleDateFormat mDateTimeFormat;
    private String mDateTemp;

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_add_new_activity;
    }

    @Override
    protected void onTKGZCreateView(View rootView) {
        mEtTitle = (EditText) rootView.findViewById(R.id.et_title);
        mEtCount = (EditText) rootView.findViewById(R.id.et_count);
        mEtSignIntegral = (EditText) rootView.findViewById(R.id.et_signIntegral);
        mEtTestIntegral = (EditText) rootView.findViewById(R.id.et_testIntegral);
        mEtContent = (EditText) rootView.findViewById(R.id.et_content);

        mTvActivityType = (TextView) rootView.findViewById(R.id.tv_activityType);
        mTvEnrollStartTime = (TextView) rootView.findViewById(R.id.tv_enrollStartTime);
        mTvEnrollEndTime = (TextView) rootView.findViewById(R.id.tv_enrollEndTime);
        mTvActivityStartTime = (TextView) rootView.findViewById(R.id.tv_activityStartTime);
        mTvActivityEndTime = (TextView) rootView.findViewById(R.id.tv_activityEndTime);

        mList = getActivityType();
        mBtnPublish = (Button) rootView.findViewById(R.id.btn_publish);

    }

    private ArrayList<DialogSelectedProfile> getActivityType() {
        mDateFormat = new SimpleDateFormat(getString(R.string.date_format), Locale.getDefault());
        mDateTimeFormat = new SimpleDateFormat(getString(R.string.date_time_format), Locale.getDefault());
        String typeArray[] = {getString(R.string.activity_type_active),
                getString(R.string.activity_type_idea),
                getString(R.string.activity_type_train),
                getString(R.string.activity_type_other)};
        ArrayList<DialogSelectedProfile> list = new ArrayList<>();
        DialogSelectedProfile activityType;
        int index = 0;
        for (String type : typeArray) {
            activityType = new DialogSelectedProfile(type, String.valueOf(index), false);
            index++;
            list.add(activityType);
        }
        return list;
    }

    @Override
    protected void onTKGZActivityCreated() {
        mTvActivityType.setOnClickListener(this);
        mTvEnrollStartTime.setOnClickListener(this);
        mTvEnrollEndTime.setOnClickListener(this);
        mTvActivityStartTime.setOnClickListener(this);
        mTvActivityEndTime.setOnClickListener(this);
        mEtCount.addTextChangedListener(watcher);
        mEtSignIntegral.addTextChangedListener(watcher);
        mEtTestIntegral.addTextChangedListener(watcher);
        mBtnPublish.setOnClickListener(this);
    }

    TextWatcher watcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if (s.length() > 0 && s.charAt(0) == '0') {
                s.delete(0, 1);
            }
        }
    };

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
        return getString(R.string.add_new_activity);
    }

    @Override
    public void onClick(View v) {
        mDateTemp = null;
        switch (v.getId()) {
            case R.id.tv_activityType:
                chooseActivityType();
                break;
            case R.id.tv_enrollStartTime:
                showDatePicker(mTvEnrollStartTime);
                break;
            case R.id.tv_enrollEndTime:
                showDatePicker(mTvEnrollEndTime);
                break;
            case R.id.tv_activityStartTime:
                showDateTimePicker(mTvActivityStartTime);
                break;
            case R.id.tv_activityEndTime:
                showDateTimePicker(mTvActivityEndTime);
                break;
            case R.id.btn_publish:
                checkAndPublish();
                break;
            default:
                break;
        }
    }

    // TODO: 12/3/2015 0003 replace by TKGZUtil.class
    private void showDatePicker(final TextView tv) {
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        final int month = c.get(Calendar.MONTH);
        final int day = c.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog dialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar calendar = Calendar.getInstance();
                calendar.set(year, monthOfYear, dayOfMonth);
                mDateTemp = mDateFormat.format(calendar.getTime());
                tv.setText(mDateTemp);
            }
        }, year, month, day);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

    private void showDateTimePicker(final TextView tv) {
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        final int hour = c.get(Calendar.HOUR_OF_DAY);
        final int minute = c.get(Calendar.MINUTE);
        final boolean[] flag = {true};
        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                if (flag[0]) {
                    Calendar calendar = Calendar.getInstance();
                    calendar.set(year, monthOfYear, dayOfMonth);
                    mDateTemp = mDateFormat.format(calendar.getTime());
                    showTimePicker(hour, minute, tv);
                }
                flag[0] = false;
            }
        }, year, month, day);
        datePickerDialog.setCanceledOnTouchOutside(false);
        datePickerDialog.setCancelable(false);
        datePickerDialog.show();
    }

    private void showTimePicker(final int hour, int minute, final TextView tv) {
        final boolean[] flag = {true};

        TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                if (flag[0]) {
                    mDateTemp = String.format("%s %02d:%02d", mDateTemp, hourOfDay, minute);
                    tv.setText(mDateTemp);
                }
                flag[0] = false;
            }
        }, hour, minute, true);
        timePickerDialog.setCanceledOnTouchOutside(false);
        timePickerDialog.setCancelable(false);
        timePickerDialog.show();
    }

    private void chooseActivityType() {
        RadioButtonDialog dialog = new RadioButtonDialog(getActivity(), R.id.tv_activityType, mList, R.layout.alert_dialog_select_team, this);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

    public void setActivityType(String str) {
        if (str != null)
            mTvActivityType.setText(str);
    }

    private void checkAndPublish() {
        if (checkEmpty() && checkTimeSequence()) {
            Toast.makeText(getActivity(), getString(R.string.toast_success), Toast.LENGTH_SHORT).show();
        }
    }

    private boolean checkEmpty() {
        if (TextUtils.isEmpty(mEtTitle.getText()) ||
                TextUtils.isEmpty(mEtContent.getText()) ||
                TextUtils.isEmpty(mEtCount.getText()) ||
                TextUtils.isEmpty(mEtSignIntegral.getText()) ||
                TextUtils.isEmpty(mEtTestIntegral.getText()) ||
                TextUtils.isEmpty(mTvActivityType.getText().toString()) ||
                TextUtils.isEmpty(mTvEnrollStartTime.getText().toString()) ||
                TextUtils.isEmpty(mTvEnrollEndTime.getText().toString()) ||
                TextUtils.isEmpty(mTvActivityStartTime.getText().toString()) ||
                TextUtils.isEmpty(mTvActivityEndTime.getText().toString())
                ) {
            Toast.makeText(getActivity(), "Some Values are Empty!", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private boolean checkTimeSequence() {
        Date enrollStartTime = null;
        Date enrollEndTime = null;
        Date activityStartTime = null;
        Date activityEndTime = null;
        try {
            enrollStartTime = mDateFormat.parse(mTvEnrollStartTime.getText().toString());
            enrollEndTime = mDateFormat.parse(mTvEnrollEndTime.getText().toString());
            activityStartTime = mDateTimeFormat.parse(mTvActivityStartTime.getText().toString());
            activityEndTime = mDateTimeFormat.parse(mTvActivityEndTime.getText().toString());

            if (enrollEndTime.getTime() < enrollStartTime.getTime()) {
                Toast.makeText(getActivity(), getString(R.string.warning_about_time_1), Toast.LENGTH_LONG).show();
                return false;
            } else if (activityStartTime.getTime() < enrollEndTime.getTime()) {
                Toast.makeText(getActivity(), getString(R.string.warning_about_time_2), Toast.LENGTH_LONG).show();
                return false;
            } else if (activityEndTime.getTime() < activityStartTime.getTime()) {
                Toast.makeText(getActivity(), getString(R.string.warning_about_time_3), Toast.LENGTH_LONG).show();
                return false;
            }

        } catch (ParseException e) {
            this.getLogger().error("CreateEventFragment: checkTimeSequence", e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public void setSelectedItem(int viewId, ArrayList<DialogSelectedProfile> list) {
        if (viewId == R.id.tv_activityType)
            for (DialogSelectedProfile item : list) {
                if (item.isSelected()) {
                    mTvActivityType.setText(item.getName());
                }
            }
    }

}
