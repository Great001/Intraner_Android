package com.xogrp.tkgz.fragment;

import android.app.Activity;
import android.view.View;
import android.widget.GridView;
import android.widget.Toast;

import com.xogrp.tkgz.R;
import com.xogrp.tkgz.TKGZApplication;
import com.xogrp.tkgz.Widget.NewComerAdapter;
import com.xogrp.tkgz.model.NewComerProfile;
import com.xogrp.tkgz.provider.NewComerProvider;
import com.xogrp.tkgz.spi.NewComerApiCallBack;

import java.util.List;

/**
 * Created by wlao on 1/21/2016.
 */
public class NewComerFragment extends AbstractTKGZFragment {

    private GridView mGvNewComer;

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_new_comer_layout;
    }

    @Override
    protected void onTKGZCreateView(View rootView) {
        mGvNewComer = (GridView) rootView.findViewById(R.id.gv_new_comer);
    }

    @Override
    protected void onTKGZActivityCreated() {

        showSpinner();
        initLoader(NewComerProvider.getNewComerProvider(TKGZApplication.getInstance().getUserProfile(), this, new NewComerApiCallBack.OnNewComerApiCallBackListener() {
            @Override
            public void onSuccess(final List<NewComerProfile> list) {
                Activity activity = getActivity();
                if (activity != null) {
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mGvNewComer.setAdapter(new NewComerAdapter(getActivity(), list));
                        }
                    });
                }
            }

            @Override
            public void onFail() {
                final Activity activity = getActivity();
                if (activity != null) {
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(activity, activity.getString(R.string.new_comer_faild), Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        }));

    }

    @Override
    public String getTransactionTag() {
        return "fragment_new_comer_tag";
    }

    @Override
    public boolean isHideActionBar() {
        return false;
    }

    @Override
    public String getActionBarTitle() {
        return getString(R.string.actionbar_title_new_comer_page);
    }
}
