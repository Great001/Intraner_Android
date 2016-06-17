package com.xogrp.tkgz.fragment;


import android.view.View;
import android.widget.Toast;


import com.xogrp.tkgz.R;
import com.xogrp.tkgz.View.CalendarDataDialog;

/*
 *created by jdeng 4/26/2016
 */
public class ActivityManage extends AbstractTKGZFragment implements View.OnClickListener {


    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_activity_manage;
    }

    @Override
    protected void onTKGZCreateView(View rootView) {
        rootView.findViewById(R.id.tv_create_activity).setOnClickListener(this);
        rootView.findViewById(R.id.tv_activity_type).setOnClickListener(this);
        rootView.findViewById(R.id.tv_commontly_address).setOnClickListener(this);
        rootView.findViewById(R.id.tv_activity_list).setOnClickListener(this);
    }

    @Override
    protected void onTKGZActivityCreated() {

    }

    @Override
    public String getTransactionTag() {
        return "Activity_management";
    }

    @Override
    public boolean isHideActionBar() {
        return false;
    }

    @Override
    public String getActionBarTitle() {
        return getString(R.string.manageAct);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_create_activity:{
                getOnScreenNavigationListener().navigateToCreateActivityPage2();
                break;
            }
            case R.id.tv_activity_type:{
                getOnScreenNavigationListener().navigateToActivityTypePage();
                break;
            }
            case R.id.tv_commontly_address:{
                getOnScreenNavigationListener().navigateToCommonAddressPage();
                break;
            }
            case R.id.tv_activity_list:{
                CalendarDataDialog calendarDataDialog=new CalendarDataDialog(getContext(),getString(R.string.activity_begin_sign_date), new CalendarDataDialog.CalendarAndTimeListener() {
                    @Override
                    public void GetCalendarAndTime(String string) {
                        Toast.makeText(getContext(), string,Toast.LENGTH_SHORT).show();
                    }
                });
                calendarDataDialog.show();
                break;
            }
        }

    }
}
