package com.xogrp.tkgz.fragment;

import android.app.Activity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.xogrp.tkgz.R;
import com.xogrp.tkgz.TKGZApplication;
import com.xogrp.tkgz.provider.addUsedAddressProvider;
import com.xogrp.tkgz.spi.AddUsedAddressApiCallback;

/**
 * Created by hliao on 5/5/2016.
 */

public class AddUsedAddressFragment extends AbstractTKGZFragment {
    public final static String TRANSACTION_TAG ="add_frequent_address";

    private EditText mEtAddress;
    private TextView mTvCountDown;
    private Button  mBtnSave,mBtnCancle;
    private String mAddress;
    private RefreshData mRefreshData;

    @Override
    protected int getLayoutResId() {
        return R.layout.add_usedaddress_layout;
    }

    @Override
    protected void onTKGZCreateView(View rootView) {
        mEtAddress =(EditText)rootView.findViewById(R.id.et_usedAddress);
        mTvCountDown =(TextView)rootView.findViewById(R.id.tv_address_wordCounts);
        mBtnCancle=(Button)rootView.findViewById(R.id.btn_address_cancle);
        mBtnSave=(Button)rootView.findViewById(R.id.btn_address_save);

        mEtAddress.addTextChangedListener(mTwCountDown);
        mBtnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAddress = mEtAddress.getText().toString();
                AddFrequentAddress();
            }
        });

        mBtnCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEtAddress.setText("");
                getOnScreenNavigationListener().goBackFromCurrentPage();
            }
        });
    }
    @Override
    protected void onTKGZActivityCreated() {

    }

    private TextWatcher mTwCountDown =new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            int len=s.length();
            int countdown=150-len;
            String str=Integer.toString(countdown);
            mTvCountDown.setText(str);
            mBtnSave.setClickable(countdown<0?false:true);
            mBtnSave.setBackgroundDrawable(getResources().getDrawable(countdown<0?R.drawable.btn_gray_style:R.drawable.btn_green_style));
            mTvCountDown.setTextColor(getResources().getColor(countdown<0?R.color.holo_red_light:R.color.tv_detail_title_text_color));
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };


    private void AddFrequentAddress(){
        showSpinner();
        initLoader(addUsedAddressProvider.getAddUsedAddressProvider(TKGZApplication.getInstance().getUserProfile(),new AddUsedAddressApiCallback.OnAddAddressApiListener(){
            @Override
            public void onSuccess(String message) {
                getOnScreenNavigationListener().goBackFromCurrentPage();
                AbstractTKGZFragment freAddressFragmemt=(AbstractTKGZFragment)getActivity().getSupportFragmentManager().findFragmentByTag(FrequentAddressFragment.TRANSACTION_TAG);
                mRefreshData=(RefreshData)freAddressFragmemt;
                mRefreshData.OnRefresh(mAddress);
            }
            @Override
            public void onFailed(String message) {
                showToast(message);
            }
        },this, mAddress));
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
        return getString(R.string.add_used_address);
    }

    @Override
    protected void onTKGZAttach(Activity activity) {
        super.onTKGZAttach(activity);
    }

    public interface RefreshData{
        void OnRefresh(String address);
    }
}
