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
import android.widget.Toast;


import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.xogrp.tkgz.R;
import com.xogrp.tkgz.TKGZApplication;
import com.xogrp.tkgz.View.MySwipeMenuItem;
import com.xogrp.tkgz.adapter.ActivityTypeAdapter;
import com.xogrp.tkgz.model.ActivityType;
import com.xogrp.tkgz.provider.ActivityTypeProvider;
import com.xogrp.tkgz.provider.DeleteActivityTypeProvider;
import com.xogrp.tkgz.spi.ActivityTypeCallback;
import com.xogrp.tkgz.spi.DeleteActivityTypeCallBack;

import java.util.ArrayList;
import java.util.List;

/**
 * created by jdeng 4/26/2016.
 */
public class ActivitytypeAdminifragment extends AbstractTKGZFragment implements View.OnClickListener{
    public final static String TRANSACTION_TAG="activity_type_list_tag";
    private ActivityTypeAdapter mActivityTypeAdapter;
    private List<ActivityType> mListActivityType;
    private View mView;
    private SwipeMenuListView mLvActivityType;

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_activity_types;
    }

    @Override
    protected void onTKGZCreateView(View rootView) {
        mLvActivityType= (SwipeMenuListView) rootView.findViewById(R.id.smlv_activity_types);
        mView=rootView.findViewById(R.id.tv_no_activity_type);

    }

    @Override
    protected void onTKGZActivityCreated() {

        showSpinner();
        initLoader(ActivityTypeProvider.getActivityTypeProvider(ActivitytypeAdminifragment.this, TKGZApplication.getInstance().getUserProfile(), new ActivityTypeCallback.OnActivityTypeListner() {
            @Override
            public void GetActivityType(ArrayList<ActivityType> list) {
                mListActivityType = list;
                final Activity activity = getActivity();
                if (activity != null) {
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            setSwipeMenuAndAdapter();
                        }
                    });
                }
            }

            @Override
            public void Error(String message) {
                final Activity activity = getActivity();
                if (activity != null) {
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mLvActivityType.setEmptyView(mView);
                        }
                    });
                }
            }
        }));

        mLvActivityType.setOnItemClickListener(itemClickListener);
    }

    private  void loadTypes(){
        int size=mListActivityType.size();
        for(int i=4;i<size;i++){
            ActivityType type=mListActivityType.get(i);
            mListActivityType.add(0,type);
        }

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
        return getString(R.string.activity_type);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){

        }
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
                getOnScreenNavigationListener().navigateToEditActivityTypePage(mListActivityType);
                menuItem.setVisible(true);
                return true;
            }
        });
    }

    void showAlertDialog(final int position){
    AlertDialog alertDialog=new AlertDialog.Builder(getContext()).setTitle(getString(R.string.sure_to_delete)).
            setMessage(String.format(getString(R.string.is_sure_to_delete), mListActivityType.get(position).getName())).
            setPositiveButton(getString(R.string.sure_to_delete), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    initLoader(DeleteActivityTypeProvider.getDeleteActivityTypeProvider(ActivitytypeAdminifragment.this, TKGZApplication.getInstance().getUserProfile(), mListActivityType.get(position).getId(), new DeleteActivityTypeCallBack.OnDeleteActivityListner() {
                        @Override
                        public void deletesuccessful(final String message) {
                            final Activity activity = getActivity();
                            if (activity != null) {
                                activity.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
                                        mListActivityType.remove(position);
                                        mActivityTypeAdapter.notifyDataSetChanged();
                                    }
                                });
                            }
                        }

                        @Override
                        public void deletefail(final String message) {
                            final Activity activity = getActivity();
                            if (activity != null) {
                                activity.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
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

    void setSwipeMenuAndAdapter(){
        SwipeMenuCreator swipeMenuCreator = new SwipeMenuCreator() {
            @Override
            public void create(SwipeMenu swipeMenu) {
                MySwipeMenuItem editItem=new MySwipeMenuItem(getContext(),getString(R.string.btn_edit),Color.rgb(0xa2, 0xcf, 0x6c));
                swipeMenu.addMenuItem(editItem);
                MySwipeMenuItem deleteItem=new MySwipeMenuItem(getContext(),getString(R.string.btn_delete),Color.BLACK);
                swipeMenu.addMenuItem(deleteItem);
            }
        };
        mLvActivityType.setMenuCreator(swipeMenuCreator);
        mLvActivityType.setSwipeDirection(SwipeMenuListView.DIRECTION_LEFT);
        mActivityTypeAdapter = new ActivityTypeAdapter(getActivity(), (ArrayList<ActivityType>) mListActivityType);
        mLvActivityType.setAdapter(mActivityTypeAdapter);
        mLvActivityType.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu swipeMenu, int index) {
                switch (index) {
                    case 0:
                        break;
                    case 1:
                        showAlertDialog(position);
                        break;
                }
                return false;
            }
        });
    }


    AdapterView.OnItemClickListener itemClickListener=new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            SetActivityType setType=(SetActivityType)getActivity().getSupportFragmentManager().findFragmentByTag(CreateActivityPage1Fragment.TRANSACTION_TAG);
            ActivityType activityType=mListActivityType.get(position);
            setType.setActivityType(activityType);
            getOnScreenNavigationListener().goBackFromCurrentPage();
        }
    };

public interface SetActivityType {
    void setActivityType(ActivityType activityType);
}
}
