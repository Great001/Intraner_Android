package com.xogrp.tkgz.fragment;

import android.app.Activity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.xogrp.tkgz.R;
import com.xogrp.tkgz.TKGZApplication;
import com.xogrp.tkgz.adapter.ActivityTypeAdapter;
import com.xogrp.tkgz.model.ActivityType;
import com.xogrp.tkgz.provider.ActivityTypeProvider;
import com.xogrp.tkgz.spi.ActivityTypeCallback;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jdeng on 5/24/2016.
 */
public class GetActivityTypeFragment extends AbstractTKGZFragment {
    private ListView mLvActivityType;
    private ActivityTypeAdapter mActivityTypeAdapter;
    private List<ActivityType> mActivityTypeList;
    private GetActivityTypeListener getActivityTypeListener;
    public  static GetActivityTypeFragment newInstance(GetActivityTypeListener getActivityTypeListener){
        GetActivityTypeFragment getActivityTypeFragment;
        getActivityTypeFragment=new GetActivityTypeFragment();
        if (getActivityTypeFragment!=null){
            getActivityTypeFragment.setGetActivityTypeListener(getActivityTypeListener);
        }
        return  getActivityTypeFragment;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.get_activitytype_fragment;
    }

    @Override
    protected void onTKGZCreateView(View rootView) {
        mLvActivityType= (ListView) rootView.findViewById(R.id.lv_get_activity_type);

    }

    @Override
    protected void onTKGZActivityCreated() {
        showSpinner();
        initLoader(ActivityTypeProvider.getActivityTypeProvider(GetActivityTypeFragment.this, TKGZApplication.getInstance().getUserProfile(), new ActivityTypeCallback.OnActivityTypeListner() {
            @Override
            public void GetActivityType(ArrayList<ActivityType> list) {
                mActivityTypeList = list;
                final Activity activity = getActivity();
                if (activity != null) {
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mActivityTypeAdapter = new ActivityTypeAdapter(getContext(), (ArrayList<ActivityType>) mActivityTypeList);
                            mLvActivityType.setAdapter(mActivityTypeAdapter);
                        }
                    });
                }
            }

            @Override
            public void Error(String message) {

            }
        }));
        mLvActivityType.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                view.findViewById(R.id.img_activity_type).setVisibility(View.VISIBLE);
                getActivityTypeListener.getActivity(mActivityTypeList.get(position));
            }
        });

    }

    @Override
    public String getTransactionTag() {
        return "get_activity_type";
    }

    @Override
    public boolean isHideActionBar() {
        return false;
    }

    @Override
    public String getActionBarTitle() {
        return getString(R.string.activity_type);
    }

    public void setGetActivityTypeListener(GetActivityTypeListener getActivityTypeListener){
        this.getActivityTypeListener=getActivityTypeListener;
    }
    public interface GetActivityTypeListener{
        public void getActivity(ActivityType activityType);
    }

}
