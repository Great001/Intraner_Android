package com.xogrp.tkgz.Widget;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.xogrp.tkgz.R;
import com.xogrp.tkgz.View.PersonnelSwipeView;
import com.xogrp.tkgz.model.PersonnelSectionSwipe;

import java.util.List;

public class PersonnelManageAdapter extends BaseAdapter {
    private Context mContext;
    private List<PersonnelSectionSwipe> mSectionList;
    private OnPersonnelSectionClickListener mOnPersonnelSectionClickListener;
    private OnFrontViewClickListener mFrontViewClickListener;

    public PersonnelManageAdapter(Context context) {
        mContext = context;
    }

    public void setAdapterData(List<PersonnelSectionSwipe> list) {
        mSectionList = list;
    }

    public void setOnPersonnelSectionClickListener(OnPersonnelSectionClickListener onArchiveClickListener) {
        mOnPersonnelSectionClickListener = onArchiveClickListener;
    }

    @Override
    public int getCount() {
        return mSectionList.size() != 0 ? mSectionList.size() : 1;
    }

    @Override
    public Object getItem(int position) {
        return mSectionList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        ViewItemHolder viewItemHolder = null;
        if (view == null) {
            viewItemHolder = new ViewItemHolder();
            view = LayoutInflater.from(mContext).inflate(R.layout.personnel_manage_item, parent, false);
            viewItemHolder.mLvCompletedItem = (PersonnelSwipeView) view.findViewById(R.id.ll_personnel_swipe);
            view.setTag(viewItemHolder);
        } else {
            viewItemHolder = (ViewItemHolder) view.getTag();
        }

        final PersonnelSectionSwipe personnelSection = mSectionList
                .get(position);
        viewItemHolder.mLvCompletedItem
                .initView(personnelSection);
        viewItemHolder.mLvCompletedItem
                .setOnPersonnelSectionClickListener(new PersonnelSwipeView.OnPersonnelSectionButtonClickListener() {
                    @Override
                    public void OnEditImageViewClick(long personnelSectionId) {
                        mOnPersonnelSectionClickListener.OnEditImageViewClick(personnelSectionId, position);
                    }

                    @Override
                    public void OnDeleteImageViewClick(long personnelSectionId) {
                        mOnPersonnelSectionClickListener.OnDeleteImageViewClick(personnelSectionId, position);
                    }
                });
        viewItemHolder.mLvCompletedItem
                .setOnFrontViewClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        mFrontViewClickListener
                                .onItemClick(personnelSection);
                    }
                });
        return view;
    }

    public static class ViewItemHolder {
        public PersonnelSwipeView mLvCompletedItem;
    }

    public void setFrontViewClickListener(OnFrontViewClickListener frontViewClickListener) {
        mFrontViewClickListener = frontViewClickListener;
    }

    public interface OnPersonnelSectionClickListener {
        void OnEditImageViewClick(long personnelSectionId, int position);

        void OnDeleteImageViewClick(long personnelSectionId, int position);
    }

    public interface OnFrontViewClickListener {
        void onItemClick(PersonnelSectionSwipe personnelSectionSwipe);
    }
}
