package com.xogrp.tkgz.Widget;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.xogrp.tkgz.R;
import com.xogrp.tkgz.listener.DialogController;
import com.xogrp.tkgz.listener.FragmentController;
import com.xogrp.tkgz.model.DialogSelectedProfile;

import java.util.ArrayList;

/**
 * Created by ayu on 11/23/2015 0023.
 */
public class RadioButtonDialog extends BaseChooseDialog {

    private ArrayList<DialogSelectedProfile> mItemList;
    private int mLayoutResId;
    private FragmentController mController;
    private RadioButtonSelectedAdapter mAdapter;
    private int mViewId;

    public RadioButtonDialog(Context context, int viewId, ArrayList<DialogSelectedProfile> list, int resId, FragmentController controller) {
        super(context);
        mViewId = viewId;
        mLayoutResId = resId;
        mItemList = list;
        mController = controller;
    }

    @Override
    protected int getLayoutResId() {
        return mLayoutResId;
    }

    @Override
    protected void initView() {
        ListView lvSelectedItem = (ListView) findViewById(R.id.lv_select_item);
        TextView tvEnsure = (TextView) findViewById(R.id.tv_ensure);
        if (mLayoutResId == R.layout.alert_dialog_select_supervisor) {
            EditText mEtSearch = (EditText) findViewById(R.id.et_search);
            mEtSearch.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (s != "") {
                        ArrayList<DialogSelectedProfile> list = new ArrayList<>();
                        for (DialogSelectedProfile supervisor : mItemList) {
                            if (supervisor.getName().contains(s)) {
                                list.add(supervisor);
                            }
                        }
                        mAdapter.setItemList(list);
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {
                }
            });
        }
        tvEnsure.setOnClickListener(this);
        mAdapter = new RadioButtonSelectedAdapter(getContext(), mItemList, this);
        mAdapter.setItemList(mItemList);
        lvSelectedItem.setAdapter(mAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_ensure:
                mController.setSelectedItem(mViewId, mItemList);
                dismiss();
                break;
            default:
                break;
        }
    }

    @Override
    public void setItemList(ArrayList<DialogSelectedProfile> list) {
        mItemList = list;
    }

    private static class RadioButtonSelectedAdapter extends BaseAdapter {

        private Context mContext;
        private ArrayList<DialogSelectedProfile> mList;
        private DialogController mControl;
        private ArrayList<DialogSelectedProfile> mItemList;

        public RadioButtonSelectedAdapter(Context context, ArrayList<DialogSelectedProfile> mItemList, DialogController mControl) {
            mList = new ArrayList<>();
            mContext = context;
            this.mControl = mControl;
            this.mItemList = mItemList;
        }

        public void setItemList(ArrayList<DialogSelectedProfile> list) {
            mList = list;
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return mList.size();
        }

        @Override
        public Object getItem(int position) {
            return mList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View view, ViewGroup parent) {
            final DialogSelectedProfile dialog = mList.get(position);
            ViewHolder holder = null;
            if (view == null) {
                view = LayoutInflater.from(mContext).inflate(R.layout.alert_dialog_radio_button_item, parent, false);
                holder = new ViewHolder();
                holder.mRbTeam = (RadioButton) view.findViewById(R.id.rb_select_team);
                view.setTag(holder);
            } else {
                holder = (ViewHolder) view.getTag();
            }
            holder.mRbTeam.setText(dialog.getName());
            holder.mRbTeam.setChecked(dialog.isSelected());
            holder.mRbTeam.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!dialog.isSelected()) {
                        for (DialogSelectedProfile item : mItemList) {
                            item.setSelected(false);
                        }
                        dialog.setSelected(true);
                        mControl.setItemList(mItemList);
                    }
                    notifyDataSetChanged();
                }
            });
            return view;
        }

        private static class ViewHolder {
            private RadioButton mRbTeam;
        }
    }
}
