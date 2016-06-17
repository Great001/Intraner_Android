package com.xogrp.tkgz.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.xogrp.tkgz.R;
import com.xogrp.tkgz.Widget.SectionPersonnelAdapter;
import com.xogrp.tkgz.model.UserProfile;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class PersonnelManageSectionFragment extends AbstractTKGZFragment {
    private static final String KEY_SECTION_NAME = "section_name";
    private ListView mLvMember;
    private SectionPersonnelAdapter mAdapter;
    private List<UserProfile> mUserList = new ArrayList<>();

    public static PersonnelManageSectionFragment newInstance(String sectionName) {
        Bundle args = new Bundle();
        args.putString(KEY_SECTION_NAME, sectionName);
        PersonnelManageSectionFragment fragment = new PersonnelManageSectionFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.personnel_manage_section;
    }

    @Override
    protected void onTKGZCreateView(View rootView) {
        mLvMember = (ListView) rootView.findViewById(R.id.lv_member);
        UserProfile user;
        for (int i = 0; i < 10; i++) {
            user = new UserProfile();
            user.setUsername("Alpha Yu");
//            user.setTeamName("R&D Center");
            user.setJobId("10010");
//            user.setEntryTime(new Date());
            Calendar calendar = Calendar.getInstance();
            user.setEntryTime(calendar.getTimeInMillis());
            user.setPosition("DEV");
            user.setSupervisorName("Deon");
            user.setRemark("Member remark");
            user.setPhoneNumber("13060640000");
            mUserList.add(user);
        }
        mAdapter = new SectionPersonnelAdapter(getActivity(), mUserList);
        mLvMember.setAdapter(mAdapter);
        mLvMember.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                getOnScreenNavigationListener().navigateToPersonnelInformationPage(mUserList.get(position));
            }
        });
    }

    @Override
    protected void onTKGZActivityCreated() {
    }

    @Override
    public String getTransactionTag() {
        return "personnel_manage_section_fragment";
    }

    @Override
    public boolean isHideActionBar() {
        return false;
    }

    @Override
    public String getActionBarTitle() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            String mSectionName = bundle.getString(KEY_SECTION_NAME);
            if (!TextUtils.isEmpty(mSectionName)) {
                return mSectionName;
            }
        }
        return "";
    }

    @Override
    protected int getMenuResourceId() {
        return R.menu.menu_personnel_manage;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        super.onCreateOptionsMenu(menu, menuInflater);
        MenuItem menuItem = menu.findItem(R.id.menu_add_item);
        if (menuItem != null) {
            menuItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem menuItem) {
                    getOnScreenNavigationListener().navigateToPersonnelInformationEditPage(PersonnelInformationEditFragment.newInstance(null));
                    return true;
                }
            });
        }
    }

}
