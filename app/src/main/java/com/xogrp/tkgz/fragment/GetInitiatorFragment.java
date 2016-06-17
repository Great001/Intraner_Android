package com.xogrp.tkgz.fragment;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.xogrp.tkgz.R;
import com.xogrp.tkgz.TKGZApplication;
import com.xogrp.tkgz.adapter.InitiatorAdapter;
import com.xogrp.tkgz.model.Initiator;
import com.xogrp.tkgz.model.UserProfile;
import com.xogrp.tkgz.provider.GetAllUsersProvider;
import com.xogrp.tkgz.spi.GetAllUsersCallback;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jdeng on 5/31/2016.
 */
public class GetInitiatorFragment extends AbstractTKGZFragment {
    private EditText mEdtSearcher;
    private ImageView mIvDelete;
    private ListView mLvInitiator;
    private List<Initiator> mInitiatorList;
    private List<Initiator> mKeyList;
    private InitiatorAdapter mInitiatorAdapter;
    private InitiatorListener mInitiatorListener;

    public static GetInitiatorFragment newInstance(InitiatorListener initiatorListener){
        GetInitiatorFragment getInitiatorFragment=new GetInitiatorFragment();
        if (initiatorListener!=null){
            getInitiatorFragment.setInitiatorListener(initiatorListener);
        }
        return getInitiatorFragment;
    }


    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_choose_initiator;
    }

    @Override
    protected void onTKGZCreateView(View rootView) {
        mEdtSearcher= (EditText) rootView.findViewById(R.id.et_search_initiator);
        mIvDelete= (ImageView) rootView.findViewById(R.id.iv_delete_initiator);
        mLvInitiator= (ListView) rootView.findViewById(R.id.lv_initiator);

    }

    @Override
    protected void onTKGZActivityCreated() {
        showSpinner();
        initLoader(GetAllUsersProvider.getGetAllUsersProvider(GetInitiatorFragment.this, TKGZApplication.getInstance().getUserProfile(), new GetAllUsersCallback.OnGetAllUsersListener() {
            @Override
            public void getAllUsersSuccess(List<Initiator> initiatorList) {
                mInitiatorList = initiatorList;

            }

            @Override
            public void getAllUsersError(String message) {

            }
        }));
        mKeyList=new ArrayList<>();
        mInitiatorAdapter=new InitiatorAdapter(getContext(),mKeyList);
        mLvInitiator.setAdapter(mInitiatorAdapter);
        mEdtSearcher.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                searcherInitiator();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mIvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEdtSearcher.setText("");

            }
        });
        mLvInitiator.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mInitiatorListener.setInitiator(mKeyList.get(position));
                getOnScreenNavigationListener().goBackFromCurrentPage();

            }
        });

    }

    @Override
    public String getTransactionTag() {
        return "choose_the_initiator";
    }

    @Override
    public boolean isHideActionBar() {
        return false;
    }

    @Override
    public String getActionBarTitle() {
        return getString(R.string.choose_the_initiator);
    }

    private void searcherInitiator(){
        String key=mEdtSearcher.getText().toString();
        int size= mInitiatorList.size();
        mKeyList.clear();
        Initiator initiator;
        if (!TextUtils.isEmpty(key)&&key.length()!=0){
            for (int i=0;i<size;++i){
                initiator=mInitiatorList.get(i);
                if ( initiator.getName().contains(key)){
                    mKeyList.add(initiator);
                    Log.d(key,initiator.getName());
                }
            }
        }
      mInitiatorAdapter.notifyDataSetChanged();


    }

    private void setInitiatorListener(InitiatorListener initiatorListener){
        this.mInitiatorListener=initiatorListener;
    }
    public interface InitiatorListener{
        public void setInitiator(Initiator initiator);
    }


}
