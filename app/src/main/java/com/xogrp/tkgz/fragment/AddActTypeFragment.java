package com.xogrp.tkgz.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.xogrp.tkgz.R;
import com.xogrp.tkgz.TKGZApplication;
import com.xogrp.tkgz.model.ActivityType;
import com.xogrp.tkgz.provider.AddTypesProvider;
import com.xogrp.tkgz.spi.AddTypesApiCallBcak;

import java.io.Serializable;
import java.util.List;

/**
 * Created by hliao on 4/26/2016.
 */
public class AddActTypeFragment extends AbstractTKGZFragment {
    public final static String TRANSACTION_TAG="add_activity_type_tag";
    private EditText mEtType;
    private TextView mTvCountDown;
    private Button mBtnSave,mBtnCancel;
    private List<ActivityType> mActivityTypeList;

    public  static AddActTypeFragment  newInstance(List<ActivityType> list){
        AddActTypeFragment addActTypeFragment=new AddActTypeFragment();
       if (list!=null){
           addActTypeFragment.setActivityList(list);
       }
        return addActTypeFragment;
    }


    @Override
    protected int getLayoutResId() {
        return R.layout.add_activitytype_layout;
    }
    @Override
    protected void onTKGZCreateView(View rootView) {

        mEtType =(EditText)rootView.findViewById(R.id.et_actType);
        mTvCountDown =(TextView)rootView.findViewById(R.id.tv_type_wordCounts);
        mBtnCancel=(Button)rootView.findViewById(R.id.btn_type_save);
        mBtnSave=(Button)rootView.findViewById(R.id.btn_type_cancle);

        mEtType.addTextChangedListener(mTwCountDown);


        mBtnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String activityType = mEtType.getText().toString();
                addType(activityType);
            }
        });

        mBtnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEtType.setText("");
                getOnScreenNavigationListener().goBackFromCurrentPage();
            }
        });
    }


     private TextWatcher mTwCountDown =new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            int countdown=50-s.length();
            String str=Integer.toString(countdown);
            mTvCountDown.setText(str);
            if(countdown<0){
                mBtnSave.setClickable(false);
                mTvCountDown.setTextColor(getResources().getColor(R.color.holo_red_light));
                mBtnSave.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_gray_style));
            } else{
                mBtnSave.setClickable(true);
                mTvCountDown.setTextColor(getResources().getColor(R.color.label_tip_color));
                mBtnSave.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_green_style));
            }
        }
        @Override
        public void afterTextChanged(Editable s) {
        }
    };

    private void addType( final String type){
       initLoader(AddTypesProvider.getAddTypesProvider(this, new AddTypesApiCallBcak.OnAddTypesApiListener() {
           @Override
           public void onSuccess() {
               getOnScreenNavigationListener().goBackFromCurrentPage();
               Activity activity=getActivity();
               if (activity!=null)
               activity.runOnUiThread(new Runnable() {
                   @Override
                   public void run() {
                       mActivityTypeList.add(0, new ActivityType(0, type));
                   }
               });

           }

           @Override
           public void onFailed(String message) {
               showToast(message);
           }

       }, TKGZApplication.getInstance().getUserProfile(), type));
    }

    @Override
    protected void onTKGZActivityCreated() {

    }
    @Override
    public String getTransactionTag() {
        return TRANSACTION_TAG;
    }

    @Override
    public boolean isHideActionBar() {
        return false;
    }

    @Override
    public String getActionBarTitle() {
        return getString(R.string.addType);
    }

  public void setActivityList(List<ActivityType> list){
      this.mActivityTypeList=list;
  }


}
