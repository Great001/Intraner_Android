package com.xogrp.tkgz.fragment;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.xogrp.tkgz.R;
import com.xogrp.tkgz.TKGZApplication;
import com.xogrp.tkgz.View.CalendarDataDialog;
import com.xogrp.tkgz.adapter.IntegralDialogAdapter;
import com.xogrp.tkgz.model.ActivityType;
import com.xogrp.tkgz.model.CommonAdressProfile;
import com.xogrp.tkgz.model.Initiator;
import com.xogrp.tkgz.model.IntegralTypeProfile;
import com.xogrp.tkgz.provider.GetIntegralListProvider;
import com.xogrp.tkgz.spi.GetIntegralListCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by jdeng on 5/17/2016.
 */
public class CreateActivityPage2Fragment extends AbstractTKGZFragment implements GetCommonAddressFragment.GetAddressListener,GetActivityTypeFragment.GetActivityTypeListener ,GetInitiatorFragment.InitiatorListener{
    private EditText mEtInitiator;
    private EditText mEtIntegralType;
    private EditText mEtNumber;
    private EditText mEtInvite;
    private EditText mEtStaff;
    private TextView mTvInner;
    private TextView mTvOut;
    private TextView mTvNeed;
    private TextView mTvNotneed;
    private EditText mEtBeginSign;
    private EditText mEtFinishSign;
    private Button mBtnRelease;
    private CommonAdressProfile commonAdressProfile;
    private ActivityType activityType;
    private List<IntegralTypeProfile> mIntegralTypeProfileList;
    private int index;
    private Initiator mlnitiator;
    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_create_activity_second;
    }

    @Override
    protected void onTKGZCreateView(View rootView) {
        mEtInitiator= (EditText) rootView.findViewById(R.id.et_activity_initiator);
        mEtIntegralType= (EditText) rootView.findViewById(R.id.et_choose_integral_type);
        mEtNumber= (EditText) rootView.findViewById(R.id.et_choose_number);
        mEtInvite= (EditText) rootView.findViewById(R.id.et_invites);
        mEtStaff= (EditText) rootView.findViewById(R.id.et_staffs);
        mTvInner= (TextView) rootView.findViewById(R.id.tv_activity_to_inner);
        mTvOut= (TextView) rootView.findViewById(R.id.tv_activity_to_out);
        mTvNeed= (TextView) rootView.findViewById(R.id.tv_activity_need_sigin);
        mTvNotneed= (TextView) rootView.findViewById(R.id.tv_activity_no_sign);
        mEtBeginSign= (EditText) rootView.findViewById(R.id.et_begin_sign_time);
        mEtFinishSign= (EditText) rootView.findViewById(R.id.et_finish_sign_time);
        mBtnRelease= (Button) rootView.findViewById(R.id.btn_release);

    }

    @Override
    protected void onTKGZActivityCreated() {
         showSpinner();
         initLoader(GetIntegralListProvider.getIntegralListProvider(CreateActivityPage2Fragment.this, TKGZApplication.getInstance().getUserProfile(), new GetIntegralListCallback.onGetIntegralListListener() {
             @Override
             public void getIntegralListSuccess(List<IntegralTypeProfile> integralTypeProfilesList) {
                 mIntegralTypeProfileList = integralTypeProfilesList;
             }

             @Override
             public void getIntegralListError(String message) {

             }
         }));
        mEtInitiator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getOnScreenNavigationListener().navigateToGetInitiator(CreateActivityPage2Fragment.this);
            }
        });
        mEtIntegralType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setAdapter(new IntegralDialogAdapter(getContext(),mIntegralTypeProfileList), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mEtIntegralType.setText(mIntegralTypeProfileList.get(which).getName());
                        index=which;
                    }
                });
                builder.show();
            }
        });
        mBtnRelease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getOnScreenNavigationListener().navigateToGetActivityTypePage(CreateActivityPage2Fragment.this);
            }
        });

        mEtBeginSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CalendarDataDialog calendarDataDialog = new CalendarDataDialog(getContext(), getString(R.string.activity_begin_sign_date), new CalendarDataDialog.CalendarAndTimeListener() {
                    @Override
                    public void GetCalendarAndTime(String string) {
                        mEtBeginSign.setText(string);
                    }
                });
                calendarDataDialog.show();
            }
        });

        mEtFinishSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CalendarDataDialog calendarDataDialog = new CalendarDataDialog(getContext(), getString(R.string.activity_end_sign_date), new CalendarDataDialog.CalendarAndTimeListener() {
                    @Override
                    public void GetCalendarAndTime(String string) {
                        mEtFinishSign.setText(string);
                    }
                });
                calendarDataDialog.show();
            }
        });

        mEtNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                int number = 0;
                if (!TextUtils.isEmpty(mEtNumber.getText().toString())) {
                    number = Integer.valueOf(mEtNumber.getText().toString());
                }
                if (number > 1000) {
                    mEtNumber.setText(getString(R.string.max_activity_number));
                } else if (number < 1) {
                    mEtNumber.setText(getString(R.string.activity_default_min_number));
                }
            }
        });

        mTvInner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTvInner.setBackgroundColor(getResources().getColor(R.color.APP_GREEN));
                mTvOut.setBackgroundColor(getResources().getColor(R.color.APP_GRAY));
                mTvInner.setClickable(false);
                mTvOut.setClickable(true);
            }
        });

        mTvOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTvInner.setBackgroundColor(getResources().getColor(R.color.APP_GRAY));
                mTvOut.setBackgroundColor(getResources().getColor(R.color.APP_GREEN));
                mTvInner.setClickable(true);
                mTvOut.setClickable(false);
            }
        });

        mTvNeed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTvNeed.setBackgroundColor(getResources().getColor(R.color.APP_GREEN));
                mTvNotneed.setBackgroundColor(getResources().getColor(R.color.APP_GRAY));
                mTvNeed.setClickable(false);
                mTvNotneed.setClickable(true);
            }
        });
        mTvNotneed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTvNeed.setBackgroundColor(getResources().getColor(R.color.APP_GRAY));
                mTvNotneed.setBackgroundColor(getResources().getColor(R.color.APP_GREEN));
                mTvNeed.setClickable(true);
                mTvNotneed.setClickable(false);
            }
        });




    }

    @Override
    public String getTransactionTag() {
        return "create_activity_page_two";
    }

    @Override
    public boolean isHideActionBar() {
        return false;
    }

    @Override
    public String getActionBarTitle() {
        return getString(R.string.create_activity_second);
    }


    @Override
    public void getAddress(CommonAdressProfile commonAdressProfile) {
        this.commonAdressProfile=commonAdressProfile;
        mEtInitiator.setText(this.commonAdressProfile.getAddress_name());
    }

    @Override
    public void getActivity(ActivityType activityType) {
        this.activityType=activityType;
        mEtIntegralType.setText(activityType.getName());
    }

    @Override
    public void setInitiator(Initiator initiator) {
        mlnitiator=initiator;
        mEtInitiator.setText(mlnitiator.getName().toString());
    }


    public static CreateActivityPage2Fragment newInstance(JSONObject jsonObject){
        CreateActivityPage2Fragment createActivityPage2Fragment=new CreateActivityPage2Fragment();
        Bundle bundle=new Bundle();
        String jsonStr=jsonObject.toString();
        bundle.putString("JsonStr",jsonStr);
        createActivityPage2Fragment.setArguments(bundle);
        return createActivityPage2Fragment;
    }
}
