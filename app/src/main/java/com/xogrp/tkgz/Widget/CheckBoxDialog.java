package com.xogrp.tkgz.Widget;

import android.content.Context;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import com.xogrp.tkgz.R;
import com.xogrp.tkgz.listener.DialogController;
import com.xogrp.tkgz.model.DialogSelectedProfile;

import java.util.ArrayList;

/**
 * Created by ayu on 11/23/2015 0023.
 */
public class CheckBoxDialog extends BaseChooseDialog {


    private ArrayList<DialogSelectedProfile> mSelectedList = new ArrayList<>();
    private TextView mTvShowAmount;
    private CheckBox mCbSelectedAll;
    private ReceiverAdapter mAdapter;
    private ReceiverController mController;
    private ArrayList<String> mReceiverId = new ArrayList<>();
    private ArrayList<String> mReceiverName = new ArrayList<>();

    public CheckBoxDialog(Context context, ArrayList<DialogSelectedProfile> list, ReceiverController controller) {
        super(context);
        mController = controller;
        mSelectedList = list;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.alert_dialog_select_receiver;
    }

    @Override
    protected void initView() {
        mCbSelectedAll = (CheckBox) findViewById(R.id.cb_selected_all);
        ListView lvReceiver = (ListView) findViewById(R.id.lv_select_item);
        TextView tvEnsure = (TextView) findViewById(R.id.tv_ensure);
        mTvShowAmount = (TextView) findViewById(R.id.tv_receiver_amount);
        tvEnsure.setOnClickListener(this);
        mCbSelectedAll.setOnClickListener(this);
        mAdapter = new ReceiverAdapter(getContext(), this);
        mAdapter.setList(mSelectedList);
        lvReceiver.setAdapter(mAdapter);
        setItemList(mSelectedList);
    }

    @Override
    public void setItemList(ArrayList<DialogSelectedProfile> list) {
        mSelectedList = list;
        int amount = 0;
        int size = list.size();
        for (DialogSelectedProfile receiver : list) {
            if (receiver.isSelected())
                amount++;
        }
        if (amount == size) {
            mCbSelectedAll.setChecked(true);
        } else
            mCbSelectedAll.setChecked(false);

        String source = String.format(getContext().getResources().getString(R.string.dialog_show_selected_amount), amount);
        SpannableStringBuilder style = new SpannableStringBuilder(source);
        style.setSpan(new ForegroundColorSpan(Color.RED), 2, 5, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        mTvShowAmount.setText(style);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_ensure:
                mController.setReceiverList(mSelectedList);
                dismiss();
                break;
            case R.id.cb_selected_all:
                for (DialogSelectedProfile item : mSelectedList) {
                    item.setSelected(mCbSelectedAll.isChecked());
                }
                mAdapter.setList(mSelectedList);
                setItemList(mSelectedList);
                break;
            default:
                break;
        }
    }

    private static class ReceiverAdapter extends BaseAdapter {
        private ArrayList<DialogSelectedProfile> mList;
        private Context mContext;
        private DialogController mController;

        public ReceiverAdapter(Context context, DialogController controller) {
            mList = new ArrayList<>();
            mContext = context;
            mController = controller;
        }

        @Override
        public int getCount() {
            return mList.size();
        }

        public void setList(ArrayList<DialogSelectedProfile> list) {
            mList = list;
            notifyDataSetChanged();
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
            final DialogSelectedProfile receiver = mList.get(position);
            ViewHolder holder = null;
            if (view == null) {
                view = LayoutInflater.from(mContext).inflate(R.layout.alert_dialog_check_box_item, parent, false);
                holder = new ViewHolder();
                holder.mCbReceiver = (CheckBox) view.findViewById(R.id.cb_selected);
                view.setTag(holder);
            } else {
                holder = (ViewHolder) view.getTag();
            }
            holder.mCbReceiver.setChecked(receiver.isSelected());
            holder.mCbReceiver.setText(receiver.getName());
            holder.mCbReceiver.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    receiver.setSelected(!receiver.isSelected());
                    mController.setItemList(mList);
                    notifyDataSetChanged();
                }
            });
            return view;
        }

        private static class ViewHolder {
            private CheckBox mCbReceiver;
        }
    }

    public interface ReceiverController {

        public void setReceiverList(ArrayList<DialogSelectedProfile> list);

    }


}
