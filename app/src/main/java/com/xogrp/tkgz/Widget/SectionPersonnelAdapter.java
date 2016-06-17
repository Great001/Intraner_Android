package com.xogrp.tkgz.Widget;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.xogrp.tkgz.R;
import com.xogrp.tkgz.model.UserProfile;

import java.util.List;

public class SectionPersonnelAdapter extends BaseAdapter {
    private Context mContext;
    private List<UserProfile> mUser;

    public SectionPersonnelAdapter(Context context, List<UserProfile> taskList) {
        mContext = context;
        mUser = taskList;
    }

    @Override
    public int getCount() {
        return mUser.size();
    }

    @Override
    public Object getItem(int position) {
        return mUser.get(position);
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
            view = LayoutInflater.from(mContext).inflate(R.layout.personnel_manage_section_item, parent, false);
            viewItemHolder.mIvAvatar = (ImageView) view.findViewById(R.id.iv_avatar);
            viewItemHolder.mTvUserName = (TextView) view.findViewById(R.id.tv_personnel_name);
            viewItemHolder.mTvPosition = (TextView) view.findViewById(R.id.tv_personnel_position);
            viewItemHolder.mTvPhoneNumber = (TextView) view.findViewById(R.id.tv_personnel_phone);
            view.setTag(viewItemHolder);
        } else {
            viewItemHolder = (ViewItemHolder) view.getTag();
        }
        UserProfile personnelProfile = mUser.get(position);
        viewItemHolder.mTvUserName.setText(personnelProfile.getUsername());
        viewItemHolder.mTvPosition.setText(personnelProfile.getPosition());
        viewItemHolder.mTvPhoneNumber.setText(personnelProfile.getPhoneNumber());
//		ImageLoader.getInstance().displayImage(
//				personnelProfile.getPersonnelImageUrl(), viewItemHolder.mIvAvatar);
        return view;
    }

    public static class ViewItemHolder {
        public ImageView mIvAvatar;
        public TextView mTvUserName;
        public TextView mTvPosition;
        public TextView mTvPhoneNumber;
    }

}
