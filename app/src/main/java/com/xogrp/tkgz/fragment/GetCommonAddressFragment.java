package com.xogrp.tkgz.fragment;

import android.app.Activity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.xogrp.tkgz.R;
import com.xogrp.tkgz.TKGZApplication;
import com.xogrp.tkgz.adapter.CommonAddressAdapter;
import com.xogrp.tkgz.model.CommonAdressProfile;
import com.xogrp.tkgz.provider.CommonAddressProvider;
import com.xogrp.tkgz.spi.CommonAddressCallback;

import java.util.List;

/**
 * Created by jdeng on 5/23/2016.
 */
public class GetCommonAddressFragment extends AbstractTKGZFragment {
    private ListView mLvCommonAddress;
    private List<CommonAdressProfile> mCommonAddressList;
    private CommonAddressAdapter mCommonAddressAdapter;
    private GetAddressListener getAddressListener;


    public static   GetCommonAddressFragment newInstance(GetAddressListener getAddressListener){
        GetCommonAddressFragment getCommonAddressFragment;
        getCommonAddressFragment=new GetCommonAddressFragment();
        if (getCommonAddressFragment!=null){
            getCommonAddressFragment.setGetAddressListener(getAddressListener);
        }
        return getCommonAddressFragment;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_commonaddresses;
    }

    @Override
    protected void onTKGZCreateView(View rootView) {
        mLvCommonAddress= (ListView) rootView.findViewById(R.id.lv_common_addresses);

    }

    @Override
    protected void onTKGZActivityCreated() {
        showSpinner();
        initLoader(CommonAddressProvider.getCommonAddressProvider(this, TKGZApplication.getInstance().getUserProfile(), new CommonAddressCallback.OnGetCommonAddressListener() {
            @Override
            public void getAddressSuccess(List<CommonAdressProfile> listCommonAddress) {
                mCommonAddressList = listCommonAddress;
                final Activity activity = getActivity();
                if (activity != null) {
                   activity.runOnUiThread(new Runnable() {
                       @Override
                       public void run() {
                           mCommonAddressAdapter = new CommonAddressAdapter(getContext(), mCommonAddressList);
                           mLvCommonAddress.setAdapter(mCommonAddressAdapter);
                       }
                   });
                }
            }

            @Override
            public void getAddressFailed(String message) {

            }
        }));

        mLvCommonAddress.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                view.findViewById(R.id.img_common_address).setVisibility(View.VISIBLE);
                Toast.makeText(getActivity(),mCommonAddressList.get(position).getAddress_name(),Toast.LENGTH_SHORT).show();
                mCommonAddressList.get(position);
                getAddressListener.getAddress(mCommonAddressList.get(position));
                getOnScreenNavigationListener().goBackFromCurrentPage();
            }
        });

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
        return getString(R.string.usedAddress);
    }

    public void setGetAddressListener(GetAddressListener getAddressListener){
        this.getAddressListener=getAddressListener;
    }


    public interface GetAddressListener{
       public void getAddress(CommonAdressProfile commonAdressProfile);
    }

}