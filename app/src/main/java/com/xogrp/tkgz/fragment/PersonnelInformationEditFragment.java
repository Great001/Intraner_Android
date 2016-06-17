package com.xogrp.tkgz.fragment;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.xogrp.tkgz.R;
import com.xogrp.tkgz.Widget.RadioButtonDialog;
import com.xogrp.tkgz.listener.DatePickerController;
import com.xogrp.tkgz.listener.FragmentController;
import com.xogrp.tkgz.model.DialogSelectedProfile;
import com.xogrp.tkgz.model.UserProfile;

import java.util.ArrayList;

import static com.xogrp.tkgz.util.TKGZUtil.getStringDate;
import static com.xogrp.tkgz.util.TKGZUtil.setDateDialog;


/**
 * Created by ayu on 9/23/2015 0023.
 */
public class PersonnelInformationEditFragment extends AbstractTKGZFragment implements View.OnClickListener, FragmentController, DatePickerController {

    private String[] mSupervisorName = {"Deon Gao", "Alex Qiu", "Piano Chen", "Alpha Yu",
            "Jerry Li", "Ethan Hunt", "Jane Li", "Will", "Ashley Han", "Claire Li", "Emily Ling", "Minnie"};
    private String[] mTeamName = {"Personnel Dept.", "Finance Dept.", "R&D Center", "Marketing Dept.", "Sales Dept."};

    private EditText mEtName;
    private EditText mEtPhone;
    private EditText mEtPosition;
    private EditText mEtJobId;
    private EditText mEtRemark;
    private TextView mTvEntryTime;
    private TextView mTvTeam;
    private TextView mTvSupervisor;
    private Button mBtnSave;
    private RadioButton mRbIsLeader;

    private ArrayList<DialogSelectedProfile> mTeamList = new ArrayList<>();
    private ArrayList<DialogSelectedProfile> mSupervisorList = new ArrayList<>();

    private UserProfile mPerson;

    public static PersonnelInformationEditFragment newInstance(UserProfile person) {

        Bundle args = new Bundle();
        args.putSerializable("person", person);
        PersonnelInformationEditFragment fragment = new PersonnelInformationEditFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_personnel_information_edit;
    }

    @Override
    protected void onTKGZCreateView(View rootView) {
        mEtName = (EditText) rootView.findViewById(R.id.et_person_name);
        mEtPhone = (EditText) rootView.findViewById(R.id.et_phone_number);
        mEtPosition = (EditText) rootView.findViewById(R.id.et_position);
        mEtJobId = (EditText) rootView.findViewById(R.id.et_job_id);
        mEtRemark = (EditText) rootView.findViewById(R.id.et_remark);
        mTvEntryTime = (TextView) rootView.findViewById(R.id.tv_entry_time);
        mTvTeam = (TextView) rootView.findViewById(R.id.tv_team);
        mTvSupervisor = (TextView) rootView.findViewById(R.id.tv_supervisor);
        mBtnSave = (Button) rootView.findViewById(R.id.btn_save);
        mRbIsLeader = (RadioButton) rootView.findViewById(R.id.rb_select_team);

        mBtnSave.setOnClickListener(this);
        mTvTeam.setOnClickListener(this);
        mTvSupervisor.setOnClickListener(this);
        mTvEntryTime.setOnClickListener(this);

        mPerson = (UserProfile) getArguments().getSerializable("person");
    }

    @Override
    protected void onTKGZActivityCreated() {
        //Demo data
        DialogSelectedProfile team;
        int index = 0;
        for (String name : mTeamName) {
            team = new DialogSelectedProfile();
            team.setName(name);
            team.setId(String.valueOf(index++));
            team.setSelected(false);
            mTeamList.add(team);
        }
        DialogSelectedProfile supervisor;
        index = 0;
        for (String name : mSupervisorName) {
            supervisor = new DialogSelectedProfile(name, String.valueOf(index), false);
            mSupervisorList.add(supervisor);
        }
        if (mPerson != null) {
            String date = getStringDate(mPerson.getEntryTime());
            mEtName.setText(mPerson.getUsername());
            mEtPosition.setText(mPerson.getPosition());
            mEtPhone.setText(mPerson.getPhoneNumber());
            mEtJobId.setText(mPerson.getJobId());
            mEtRemark.setText(mPerson.getRemark());
            mTvEntryTime.setText(date);
//            mTvTeam.setText(mPerson.getTeamName());
            mTvSupervisor.setText(mPerson.getSupervisorName());
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
        if (mPerson == null) {
            mPerson = new UserProfile();
            return getString(R.string.actionbar_title_add_personnel);
        } else return getString(R.string.actionbar_title_edit_personnel_information);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_save:
                savePersonnelInformation();
                break;
            case R.id.tv_entry_time:
                setDateDialog(R.id.tv_entry_time, getActivity(), this);
                break;
            case R.id.tv_team:
                RadioButtonDialog teamDialog = new RadioButtonDialog(getActivity(), R.id.tv_team, mTeamList, R.layout.alert_dialog_select_team, this);
                teamDialog.setCancelable(false);
                teamDialog.show();
                break;
            case R.id.tv_supervisor:
                RadioButtonDialog supervisorDialog = new RadioButtonDialog(getActivity(), R.id.tv_supervisor, mSupervisorList, R.layout.alert_dialog_select_supervisor, this);
                supervisorDialog.setCancelable(false);
                supervisorDialog.show();
                break;
            default:
                break;
        }
    }

    private void savePersonnelInformation() {
        if (TextUtils.isEmpty(mEtName.getText())
                || TextUtils.isEmpty(mEtJobId.getText())
                || TextUtils.isEmpty(mEtPhone.getText())
                || TextUtils.isEmpty(mEtPosition.getText())
                || mPerson.getEntryTime() != 0
                || mPerson.getSupervisorId() != null
                || mPerson.getTeamId() != null) {
            Toast.makeText(getActivity(), "Some Values are Empty !", Toast.LENGTH_SHORT).show();
            return;
        }
        mPerson.setUsername(mEtName.getText().toString());
        mPerson.setJobId(mEtJobId.getText().toString());
        mPerson.setPhoneNumber(mEtPhone.getText().toString());
        mPerson.setPosition(mEtPosition.getText().toString());
        mPerson.setRemark(mEtRemark.getText().toString());
        mPerson.setIsLeader(mRbIsLeader.isSelected());
    }

    public void setSectionText(String str) {
        if (str != null)
            mTvTeam.setText(str);
    }

    @Override
    public void setSelectedItem(int viewId, ArrayList<DialogSelectedProfile> list) {
        switch (viewId) {
            case R.id.tv_team:
                for (DialogSelectedProfile team : list) {
                    if (team.isSelected()) {
                        mTvTeam.setText(team.getName());
                        mPerson.setTeamId(team.getId());
                    }
                }
                break;
            case R.id.tv_supervisor:
                for (DialogSelectedProfile supervisor : list) {
                    if (supervisor.isSelected()) {
                        mTvSupervisor.setText(supervisor.getName());
                        mPerson.setSupervisorId(supervisor.getId());
                    }
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void setDateString(int viewId, long time) {
        switch (viewId) {
            case R.id.tv_entry_time:
                mTvEntryTime.setText(getStringDate(time));
                mPerson.setEntryTime(time);
                break;
            default:
                break;

        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }
}
