package com.xogrp.tkgz.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.xogrp.tkgz.R;
import com.xogrp.tkgz.model.UserProfile;

import static com.xogrp.tkgz.util.TKGZUtil.getStringDate;


public class PersonnelInformationFragment extends AbstractTKGZFragment {
    private static final String KEY_PERSONNEL_PROFILE = "is_personnel";
    private UserProfile mUser;
    private TextView mTvName, mTvTeam, mTvPosition, mTvSupervisor,
            mTvEntryTime, mTvPhoneNumber, mTvJobNumber, mTvRemark;

    public static PersonnelInformationFragment newInstance(UserProfile user) {
        Bundle args = new Bundle();
        args.putSerializable(KEY_PERSONNEL_PROFILE, user);
        PersonnelInformationFragment fragment = new PersonnelInformationFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void onTKGZAttach(Activity activity) {
        super.onTKGZAttach(activity);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.personnel_information;
    }

    @Override
    protected void onTKGZCreateView(View rootView) {
        mTvName = (TextView) rootView.findViewById(R.id.tv_information_name);
        mTvTeam = (TextView) rootView.findViewById(R.id.tv_information_section);
        mTvPosition = (TextView) rootView.findViewById(R.id.tv_information_position);
        mTvSupervisor = (TextView) rootView.findViewById(R.id.tv_information_superior);
        mTvEntryTime = (TextView) rootView.findViewById(R.id.tv_information_entry_time);
        mTvPhoneNumber = (TextView) rootView.findViewById(R.id.tv_information_phone_number);
        mTvJobNumber = (TextView) rootView.findViewById(R.id.tv_information_job_number);
        mTvRemark = (TextView) rootView.findViewById(R.id.tv_information_remark);

        Bundle bundle = getArguments();
        if (bundle != null) {
            mUser = (UserProfile) bundle.getSerializable(KEY_PERSONNEL_PROFILE);
            if (mUser != null) {
                mTvName.setText(mUser.getUsername());
//                mTvTeam.setText(mUser.getTeamName());
                mTvPosition.setText(mUser.getPosition());
                mTvSupervisor.setText(mUser.getSupervisorName());
                mTvEntryTime.setText(getStringDate(mUser.getEntryTime()));
                mTvPhoneNumber.setText(mUser.getPhoneNumber());
                mTvJobNumber.setText(mUser.getJobId());
                mTvRemark.setText(mUser.getRemark());
            }
        }
    }

    @Override
    protected void onTKGZActivityCreated() {

    }

    @Override
    public String getTransactionTag() {
        return "personnel_information_fragment";
    }

    @Override
    public boolean isHideActionBar() {
        return false;
    }

    @Override
    public String getActionBarTitle() {
        return getString(R.string.actionbar_title_personnel_information_page);
    }

    @Override
    protected int getMenuResourceId() {
        return R.menu.menu_personnel_information;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        boolean isEventConsumed = false;
        switch (menuItem.getItemId()) {
            case R.id.menu_edit_item:
                getOnScreenNavigationListener().navigateToPersonnelInformationEditPage(PersonnelInformationEditFragment.newInstance(mUser));
                isEventConsumed = true;
                break;
            case R.id.menu_delete_item:
                isEventConsumed = true;
                break;
        }
        return isEventConsumed;
    }
}
