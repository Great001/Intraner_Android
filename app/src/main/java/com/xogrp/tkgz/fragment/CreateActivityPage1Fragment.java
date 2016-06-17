package com.xogrp.tkgz.fragment;

import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.TextOptions;
import com.baidu.mapapi.model.LatLng;
import com.xogrp.tkgz.R;
import com.xogrp.tkgz.model.ActivityProfile;
import com.xogrp.tkgz.model.ActivityType;
import com.xogrp.tkgz.model.CommonAdressProfile;
import com.xogrp.tkgz.util.DateTimeDialogPickerUtil;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by hliao on 5/17/2016.
 */
public class CreateActivityPage1Fragment extends AbstractTKGZFragment implements View.OnClickListener,ActivitytypeAdminifragment.SetActivityType,CommonAdressFragment.SetFreAddress,AddressSearchFragment.ReturnToCreateActicityPage {

    public  static final String TRANSACTION_TAG="create new activity page one";
    private EditText mEtActivityTitle,mEtActivityContent;
    private TextView mTvActivityType,mTvChoosedType,mTvStartTime,mTvEndTime,mTvFreAddress, mTvBaiduAddr, mTvChoosedAddr,
            mTvWordCountTitle,mTvWordCountContent;
    private Button mBtnNextStep;
    private RadioButton mRbtnFreAddr, mRbtnBaiduAddr;
    private RadioGroup mRgAddress;

    private MapView mMvBaidu;
    private BaiduMap mBaiduMap;
    private ActivityProfile mActProfile;

    @Override
    protected int getLayoutResId() {
        return R.layout.create_activity_layout_one;
    }

    @Override
    protected void onTKGZCreateView(View rootView) {
        mEtActivityContent=(EditText)rootView.findViewById(R.id.et_newactivity_content);
        mEtActivityTitle=(EditText)rootView.findViewById(R.id.et_newactivity_title);
        mTvBaiduAddr =(TextView)rootView.findViewById(R.id.tv_newactivity_baiduAddr);
        mTvActivityType=(TextView)rootView.findViewById(R.id.tv_newactivity_type);
        mTvChoosedType=(TextView)rootView.findViewById(R.id.tv_newactivity_chooseType);
        mTvStartTime=(TextView)rootView.findViewById(R.id.tv_newactivity_starttime);
        mTvEndTime=(TextView)rootView.findViewById(R.id.tv_newactivity_endtime);
        mTvFreAddress=(TextView)rootView.findViewById(R.id.tv_choose_freaddress);
        mTvChoosedAddr =(TextView)rootView.findViewById(R.id.tv_newactivity_chooseAddr);
        mTvWordCountContent=(TextView)rootView.findViewById(R.id.tv_wordcount_content);
        mTvWordCountTitle=(TextView)rootView.findViewById(R.id.tv_wordcount_title);

        mRbtnFreAddr =(RadioButton)rootView.findViewById(R.id.rbtn_freaddress);
        mRbtnBaiduAddr =(RadioButton)rootView.findViewById(R.id.rbtn_baiduAddr);
        mRgAddress=(RadioGroup)rootView.findViewById(R.id.rg_address);
        mMvBaidu=(MapView)rootView.findViewById(R.id.mv_baidumap);
        mBtnNextStep=(Button)rootView.findViewById(R.id.btn_newactivity_nextstep);
    }

    @Override
    protected void onTKGZActivityCreated() {
        mActProfile=new ActivityProfile();
        mTvChoosedType.setOnClickListener(this);
        mTvStartTime.setOnClickListener(this);
        mTvEndTime.setOnClickListener(this);
        mTvChoosedAddr.setOnClickListener(this);
        mTvBaiduAddr.setOnClickListener(this);

        mEtActivityTitle.addTextChangedListener(mTwTitle);
        mEtActivityContent.addTextChangedListener(mTwContent);
        mEtActivityTitle.setFilters(new InputFilter[]{new InputFilter.LengthFilter(100)});
        mEtActivityContent.setFilters(new InputFilter[]{new InputFilter.LengthFilter(1000)});

        mRgAddress.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                mTvBaiduAddr.setEnabled(checkedId == R.id.rbtn_baiduAddr);
                mTvChoosedAddr.setClickable(checkedId == R.id.rbtn_freaddress);
                if(checkedId==R.id.rbtn_freaddress) {
                    mMvBaidu.setVisibility(View.GONE);
                }
            }
        });

        mBtnNextStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    nextStep();
                } catch (JSONException jsonException) {
                    getLogger().error("JSONException");
                }
            }
        });
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
        return getString(R.string.actionbar_title_create_activity);
    }

    @Override
    public void onClick(View v) {
        int id=v.getId();
        switch (id){
            case R.id.tv_newactivity_chooseType:
                getOnScreenNavigationListener().navigateToActivityTypePage();
                break;
            case R.id.tv_newactivity_starttime:
                selectDateTime(0);
                break;
            case R.id.tv_newactivity_endtime:
                selectDateTime(1);
                break;
            case R.id.tv_newactivity_chooseAddr:
                getOnScreenNavigationListener().navigateToAddressListPage();
                break;
            case R.id.tv_newactivity_baiduAddr:
               getOnScreenNavigationListener().navigateToAddressSearchPage();
                break;
            default:
                break;
        }
    }

    @Override
    public void setActivityType(ActivityType activityType) {
        mTvChoosedType.setText(activityType.getName());
        mActProfile.setActType(activityType);
    }

    @Override
    public void chooseFrequentAddress(CommonAdressProfile commonAdressProfile) {
        mTvChoosedAddr.setText(commonAdressProfile.getAddress_name());
        mActProfile.setCommonAdressProfile(commonAdressProfile);
    }

    TextWatcher mTwTitle=new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            int wordSum = s.length();
            mTvWordCountTitle.setText(wordSum >= 100 ? getString(R.string.title_max_word) : "");
        }
        @Override
        public void afterTextChanged(Editable s) {
        }
    };

    TextWatcher mTwContent=new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            int wordSum=s.length();
            mTvWordCountContent.setText(wordSum >= 1000 ? getString(R.string.content_max_word) : "");
        }

        @Override
        public void afterTextChanged(Editable s) {
        }

    };

    @Override
    public void locatInBaiduMap(final LatLng locat,final String addrName,final String addrDetail) {
        hideKeyboard();
        mActProfile.setBaiduAddress(addrName);
        mBaiduMap=mMvBaidu.getMap();
        mTvBaiduAddr.setText(addrName);
        mMvBaidu.setVisibility(View.VISIBLE);
        MapStatus status=new MapStatus.Builder().target(locat).zoom(20).build();
        MapStatusUpdate statusUpdate= MapStatusUpdateFactory.newMapStatus(status);
        mBaiduMap.setMapStatus(statusUpdate);
        mBaiduMap.addOverlay(new MarkerOptions()
                .position(locat)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.location1)));
        mBaiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                mBaiduMap.addOverlay(new TextOptions()
                        .text(addrDetail)
                        .fontSize(45)
                        .position(locat));
                return false;
            }
        });

    }


    private void selectDateTime(int flag){
        TextView tvSetTime;
        tvSetTime=(flag==0?mTvStartTime:mTvEndTime);
        DateTimeDialogPickerUtil.showDateTimePickerDialog(getActivity(), flag, tvSetTime);
    }

    private void nextStep() throws JSONException{
        String strActType=mTvChoosedType.getText().toString();
        String title=mEtActivityTitle.getText().toString();
        String content=mEtActivityContent.getText().toString();
        String startTime=mTvStartTime.getText().toString();
        String endTime=mTvEndTime.getText().toString();

        int id = mRgAddress.getCheckedRadioButtonId();
        mActProfile.setIsUseCommonAddress(id == R.id.rbtn_freaddress);
        String address=(id==R.id.rbtn_freaddress?mTvChoosedAddr:mTvBaiduAddr).getText().toString();

        if (title.isEmpty()||content.isEmpty()||startTime.isEmpty()||endTime.isEmpty()||strActType.isEmpty()||address.isEmpty()) {
            showToast(getString(R.string.not_complete_the_activity_info));
        } else {
            mActProfile.setTitle(title);
            mActProfile.setContent(content);
            mActProfile.setStartTime(startTime);
            mActProfile.setEndTime(endTime);
            getOnScreenNavigationListener().navigateToCreateActivityPageTwo(mActProfile.getActivityProfileAsJson());
        }
    }
}
