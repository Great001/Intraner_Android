package com.xogrp.tkgz.fragment;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.xogrp.tkgz.R;
import com.xogrp.tkgz.Widget.TKGZCustomDialog;
import com.xogrp.tkgz.Widget.TKGZCustomEditDialog;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ayu on 10/10/2015 0010.
 */
public class EventTypeManageFragment extends AbstractTKGZFragment implements AdapterView.OnItemClickListener, TKGZCustomEditDialog.OnCustomEditDialogListener {

    private ListView mLvActivityType;
    private EventTypeAdapter mAdapter;
    private List<String> mActivityTypeList;
    private int mCurrentItem;
    private String mCallBackString;

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_event_type_manage;
    }

    @Override
    protected void onTKGZCreateView(View rootView) {
        mActivityTypeList = new ArrayList<>();
        mActivityTypeList.add(getString(R.string.demo_txt_activity_type_training));
        mActivityTypeList.add(getString(R.string.demo_txt_activity_type_activity));
        mActivityTypeList.add(getString(R.string.demo_txt_activity_type_originality));
        mActivityTypeList.add(getString(R.string.demo_txt_activity_type_other));
        mLvActivityType = (ListView) rootView.findViewById(R.id.lv_activityType);

        mLvActivityType.setOnItemClickListener(this);

        mAdapter = new EventTypeAdapter();
        mAdapter.getActivityType(mActivityTypeList);
        mLvActivityType.setAdapter(mAdapter);
    }

    @Override
    protected void onTKGZActivityCreated() {

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
        return getString(R.string.actionbar_title_activity_type_manage);
    }

    @Override
    protected int getMenuResourceId() {
        return R.menu.menu_add_activity_type;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.menu_add_activity_type:
                showEditMessageDialog(R.string.add_activity_type, "", R.string.dialog_finish, R.string.dialog_cancel_button,
                        new TKGZCustomDialog.OnDialogActionCallback() {
                            @Override
                            public void onConfirmSelected() {
                                if (mCallBackString != null) {
                                    mActivityTypeList.add(mCallBackString);
                                }
                                mAdapter.getActivityType(mActivityTypeList);
                                mAdapter.notifyDataSetChanged();
                            }
                        }, null, this);
                break;
            default:
                break;
        }
        return false;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
        mCurrentItem = position;
        showEditMessageDialog(R.string.edit_activity_type, mActivityTypeList.get(position), R.string.dialog_finish, R.string.dialog_cancel_button,
                new TKGZCustomDialog.OnDialogActionCallback() {
                    @Override
                    public void onConfirmSelected() {

                        if (mCallBackString != null) {
                            mActivityTypeList.set(mCurrentItem, mCallBackString);
                        }
                        mAdapter.getActivityType(mActivityTypeList);
                        mAdapter.notifyDataSetChanged();
                    }
                }, null, this);
    }

    @Override
    public void callBack(String str) {
        mCallBackString = str;
    }


    private class EventTypeAdapter extends BaseAdapter {
        private List<String> mTypeList;

        @Override
        public int getCount() {
            return mTypeList.size();
        }

        public void getActivityType(List<String> list) {
            mTypeList = list;
        }

        @Override
        public Object getItem(int position) {
            return mTypeList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View view, ViewGroup parent) {
            final String str = mTypeList.get(position);
            ViewHolder holder = null;
            if (view == null) {
                view = LayoutInflater.from(getActivity()).inflate(R.layout.list_activity_type_layout, parent, false);
                holder = new ViewHolder();
                holder.mTvType = (TextView) view.findViewById(R.id.tv_activity_type);
                holder.mIvDelete = (ImageView) view.findViewById(R.id.iv_delete);
                view.setTag(holder);
            } else {
                holder = (ViewHolder) view.getTag();
            }
            holder.mTvType.setText(str);
            final ViewHolder finalHolder = holder;
            holder.mIvDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showCancelButtonDialog(R.string.delete_activity_type, R.string.determine_delete_activity_type, finalHolder.mTvType.getText().toString(), "", R.string.dialog_ok_button, R.string.dialog_cancel_button, new TKGZCustomDialog.OnDialogActionCallback() {
                        @Override
                        public void onConfirmSelected() {
                            mActivityTypeList.remove(position);
                            getActivityType(mActivityTypeList);
                            notifyDataSetChanged();
                        }
                    }, null);
                }
            });
            return view;
        }

        private class ViewHolder {
            TextView mTvType;
            ImageView mIvDelete;
        }
    }
}
