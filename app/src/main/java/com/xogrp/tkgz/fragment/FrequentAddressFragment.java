package com.xogrp.tkgz.fragment;

import android.app.Activity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.xogrp.tkgz.R;
import com.xogrp.tkgz.TKGZApplication;
import com.xogrp.tkgz.adapter.AddressListAdapter;
import com.xogrp.tkgz.provider.FreAddressProvider;
import com.xogrp.tkgz.spi.FreAddressApiCallback;

import java.util.List;

/**
 * Created by hliao on 5/6/2016.
 */
public class FrequentAddressFragment extends AbstractTKGZFragment implements AddUsedAddressFragment.RefreshData{
    public  final static String TRANSACTION_TAG ="address_list_tag";
    private ListView mLvAddress;
    private static List<String> mListAddresss ;
    private Activity mActivity;
    private SetAddress mSetAddress;
    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_address_list;
    }

    @Override
    protected void onTKGZCreateView(View rootView) {
        mLvAddress=(ListView)rootView.findViewById(R.id.lv_fre_address);
        loadFrequentAddress();

        mLvAddress.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String address=mListAddresss.get(position);
                getOnScreenNavigationListener().goBackFromCurrentPage();
                mSetAddress=(SetAddress)getActivity().getSupportFragmentManager().findFragmentByTag(CreateActivityPage1Fragment.TRANSACTION_TAG);
                mSetAddress.setAddress(address);
            }
        });
    }

    private void  loadFrequentAddress() {
        showSpinner();
        initLoader(FreAddressProvider.getFreAddressProvider(TKGZApplication.getInstance().getUserProfile(), new FreAddressApiCallback.FreAddressApiListner() {
            @Override
            public void onSuccess(final List<String> list) {
                mListAddresss = list;
                if (list.size() == 0) {
                    list.add(getString(R.string.add_address_tip));
                }
                if (mActivity != null) {
                    mActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mLvAddress.setAdapter(new AddressListAdapter(getActivity(), list));
                        }
                    });
                } else {
                    Error();
                }
            }

            @Override
            public void onFailed(String message) {
                showToast(message);
            }
        }, this));
    }

    @Override
    protected void onTKGZActivityCreated() {
        mActivity =getActivity();
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
        return getString(R.string.address_actionbar_title);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        menu.clear();
        menuInflater.inflate(R.menu.menu_frequent_address, menu);
        MenuItem item=menu.findItem(R.id.menu_enter_add_address);
        item.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                getOnScreenNavigationListener().navigateToAddAddressPage();
                return false;
            }
        });
    }

    @Override
    public void OnRefresh(String address) {
        mListAddresss.add(address);
        if(mActivity !=null) {
            mActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mLvAddress.setAdapter(new AddressListAdapter(getActivity(), mListAddresss));
                }
            });
        }
        else{
            Error();
        }
    }

    private void Error(){
        getLogger().error("Get mActivity failed");
    }

    public interface SetAddress {
        void setAddress(String address);
    }
}
