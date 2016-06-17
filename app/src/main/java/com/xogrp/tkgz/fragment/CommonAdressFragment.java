package com.xogrp.tkgz.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.xogrp.tkgz.R;
import com.xogrp.tkgz.TKGZApplication;
import com.xogrp.tkgz.View.MySwipeMenuItem;
import com.xogrp.tkgz.adapter.CommonAddressAdapter;
import com.xogrp.tkgz.model.CommonAdressProfile;
import com.xogrp.tkgz.provider.CommonAddressProvider;
import com.xogrp.tkgz.provider.DeleteCommonAddressProvider;
import com.xogrp.tkgz.spi.CommonAddressCallback;
import com.xogrp.tkgz.spi.DeleteCommonAddressCallback;

import java.util.List;

/**
 *created by jdeng 5/10/2016.
 */
public class CommonAdressFragment extends AbstractTKGZFragment{
    private SwipeMenuListView mLvCommonAddress;
    private TextView mTvEmptyAddress;
    private List<CommonAdressProfile> mCommonAddressesList;
    private CommonAddressAdapter mCommonAddressAdapter;

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_common_adress;
    }

    @Override
    protected void onTKGZCreateView(View rootView) {
        mLvCommonAddress= (SwipeMenuListView) rootView.findViewById(R.id.smlv_common_address);
        mTvEmptyAddress= (TextView) rootView.findViewById(R.id.tv_empty_address);
    }

    @Override
    protected void onTKGZActivityCreated() {
        mLvCommonAddress.setOnItemClickListener(itemClickListener);
        showSpinner();
        initLoader(CommonAddressProvider.getCommonAddressProvider(this, TKGZApplication.getInstance().getUserProfile(), new CommonAddressCallback.OnGetCommonAddressListener() {
            @Override
            public void getAddressSuccess(List<CommonAdressProfile> listCommonAddress) {
                mCommonAddressesList=listCommonAddress;
                final Activity activity=getActivity();
                if (activity!=null)
                {
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            setAdapterAndSwipeMenu();
                        }
                    });
                }

            }

            @Override
            public void getAddressFailed(final String message) {
                final Activity activity=getActivity();
                if (activity!=null){
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(activity,message,Toast.LENGTH_SHORT).show();
                            mLvCommonAddress.setEmptyView(mTvEmptyAddress);
                        }
                    });
                }

            }
        }));

    }

    @Override
    public String getTransactionTag() {
        return "common_address";
    }

    @Override
    public boolean isHideActionBar() {
        return false;
    }

    @Override
    public String getActionBarTitle() {
        return getString(R.string.usedAddress);
    }
    void setAdapterAndSwipeMenu(){
        mCommonAddressAdapter=new CommonAddressAdapter(getActivity(),mCommonAddressesList);
        mLvCommonAddress.setAdapter(mCommonAddressAdapter);
        SwipeMenuCreator creator=new SwipeMenuCreator() {
            @Override
            public void create(SwipeMenu swipeMenu) {
                MySwipeMenuItem editItem=new MySwipeMenuItem(getContext(),getString(R.string.btn_edit),Color.rgb(0xa2, 0xcf, 0x6c));
                swipeMenu.addMenuItem(editItem);
                MySwipeMenuItem deleteItem=new MySwipeMenuItem(getContext(),getString(R.string.btn_delete),Color.BLACK);
                swipeMenu.addMenuItem(deleteItem);
            }
        };
        mLvCommonAddress.setMenuCreator(creator);
        mLvCommonAddress.setSwipeDirection(SwipeMenuListView.DIRECTION_LEFT);
        mLvCommonAddress.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu swipeMenu, int index) {
                switch (index){
                    case 0:break;//edit
                    case 1:{
                        showAlertDialog(position);
                        break;//delete;
                    }
                }
                return false;
            }
        });

    }

    void showAlertDialog(final int position){
        AlertDialog alertDialog=new AlertDialog.Builder(getContext()).setTitle(getString(R.string.really_to_delete)).
                setMessage(String.format(getString(R.string.is_sure_to_delete), mCommonAddressesList.get(position).getAddress_name())).
                setPositiveButton(getString(R.string.sure_to_delete), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        initLoader(DeleteCommonAddressProvider.getDeleteCommonAddressProvider(CommonAdressFragment.this, TKGZApplication.getInstance().getUserProfile(),
                                mCommonAddressesList.get(position).getAddress_id(), new DeleteCommonAddressCallback.OnDeleteCommonAddressListener() {
                                    @Override
                                    public void deleteSuccess(final String message) {
                                        final Activity activity = getActivity();
                                        if (activity!=null)
                                        activity.runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                Toast.makeText(activity, message, Toast.LENGTH_LONG).show();
                                                mCommonAddressesList.remove(position);
                                                mCommonAddressAdapter.notifyDataSetChanged();
                                            }
                                        });
                                    }
                                    @Override
                                    public void deleteFailed(final String message) {
                                        final Activity activity=getActivity();
                                        if (activity!=null)
                                        activity.runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                Toast.makeText(activity, message, Toast.LENGTH_LONG).show();
                                            }
                                        });

                                    }
                                }));
                    }
                }).setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).create();
        alertDialog.show();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        super.onCreateOptionsMenu(menu, menuInflater);
        menu.clear();
        menuInflater.inflate(R.menu.menu_master_add_activitytype,menu);
        final MenuItem menuItem= menu.findItem(R.id.menu_add_activity_type);
        menuItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                menuItem.setVisible(true);
                return true;
            }
        });
    }

    @Override
    public void onRefreshData() {
        super.onRefreshData();
        mCommonAddressAdapter.notifyDataSetChanged();
    }

    AdapterView.OnItemClickListener itemClickListener=new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            CommonAdressProfile commonAdressProfile=mCommonAddressesList.get(position);
            SetFreAddress setAddress=(SetFreAddress)getActivity().getSupportFragmentManager().findFragmentByTag(CreateActivityPage1Fragment.TRANSACTION_TAG);
            setAddress.chooseFrequentAddress(commonAdressProfile);
            getOnScreenNavigationListener().goBackFromCurrentPage();
        }
    };
    public interface SetFreAddress {
        void chooseFrequentAddress(CommonAdressProfile commonAdressProfile);
    }



}
