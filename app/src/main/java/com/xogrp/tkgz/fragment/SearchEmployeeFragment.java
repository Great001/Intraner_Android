package com.xogrp.tkgz.fragment;

import android.content.Context;
import android.text.InputType;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ListView;

import com.xogrp.tkgz.R;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by ayu on 11/24/2015 0024.
 */
public class SearchEmployeeFragment extends AbstractTKGZFragment {

    private EditText mEtSearch;
    private ListView mLvResult;

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_search_employee;
    }

    @Override
    protected void onTKGZCreateView(View rootView) {
        mEtSearch = (EditText) rootView.findViewById(R.id.et_search_employee);
        mLvResult = (ListView) rootView.findViewById(R.id.lv_search_result);
    }

    @Override
    protected void onTKGZActivityCreated() {
        mEtSearch.requestFocus();
        mEtSearch.setInputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE);
        mEtSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = mEtSearch.getText().toString();
            }
        });

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {

            public void run() {
                InputMethodManager inputManager = (InputMethodManager) mEtSearch.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.showSoftInput(mEtSearch, 0);
            }

        }, 500);

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
        return getString(R.string.actionbar_title_search_employee);
    }
}
