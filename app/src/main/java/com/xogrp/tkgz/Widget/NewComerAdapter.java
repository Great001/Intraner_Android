package com.xogrp.tkgz.Widget;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.xogrp.tkgz.R;
import com.xogrp.tkgz.TKGZApplication;
import com.xogrp.tkgz.View.CircleImageView;
import com.xogrp.tkgz.fragment.AbstractTKGZFragment;
import com.xogrp.tkgz.model.NewComerProfile;
import com.xogrp.tkgz.util.TKGZUtil;

import java.util.List;

/**
 * Created by wlao on 1/25/2016.
 */
public class NewComerAdapter extends BaseAdapter {
    private Context mContext;
    private List<NewComerProfile> mNewComerProfileList;
    private LayoutInflater mLayoutInflater;

    public NewComerAdapter(Context context, List<NewComerProfile> list) {
        this.mContext = context;
        this.mNewComerProfileList = list;
        this.mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mNewComerProfileList.size();
    }

    @Override
    public Object getItem(int position) {
        return mNewComerProfileList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null){
            convertView = mLayoutInflater.inflate(R.layout.new_comer_item, parent, false);
            holder = new ViewHolder();
            holder.mCvNewComer = (CardView)convertView.findViewById(R.id.cv_new_comer);
            holder.mIvAvatar = (CircleImageView)convertView.findViewById(R.id.iv_avatar);
            holder.mTvMemberName = (TextView)convertView.findViewById(R.id.tv_member_name);
            holder.mTvPosition = (TextView)convertView.findViewById(R.id.tv_position);
            holder.mTvDapartment = (TextView)convertView.findViewById(R.id.tv_dapartment);
            holder.mTvEntryTime = (TextView)convertView.findViewById(R.id.tv_entry_time);

            convertView.setTag(holder);
        }
        holder = (ViewHolder)convertView.getTag();

        boolean isMyAvatar = TKGZApplication.getInstance().getUserProfile().getId().equalsIgnoreCase(mNewComerProfileList.get(position).getId());
        String avatar = isMyAvatar ? TKGZApplication.getInstance().getUserProfile().getAvatar() : mNewComerProfileList.get(position).getAvatar();
        ImageLoader.getInstance().displayImage(avatar, holder.mIvAvatar);
        holder.mTvMemberName.setText(mNewComerProfileList.get(position).getMemberName());
        holder.mTvPosition.setText(mNewComerProfileList.get(position).getPosition());
        String[] team = mNewComerProfileList.get(position).getTeamName();
        if (team.length > 0){
            holder.mTvDapartment.setText(team.length > 1 ? String.format("%s ...",team[0]) : team[0]);
        } else {
            holder.mTvDapartment.setText("");
        }
        holder.mTvEntryTime.setText(String.format("%s %s", mContext.getResources().getString(R.string.new_comer_entry_time_text), TKGZUtil.getStringDate(mNewComerProfileList.get(position).getEntryTime())));
        holder.mCvNewComer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TKGZApplication.getInstance().getUserProfile().getId().equalsIgnoreCase(mNewComerProfileList.get(position).getId())){
                    AbstractTKGZFragment.getOnScreenNavigationListener().navigateToMyAccountPage(TKGZApplication.getInstance().getUserProfile(), null);
                }else {
                    AbstractTKGZFragment.getOnScreenNavigationListener().navigateToSelfIntroductionPage(mNewComerProfileList.get(position));
                }
            }
        });

        return convertView;
    }

    private static class ViewHolder{
        private CardView mCvNewComer;
        private CircleImageView mIvAvatar;
        private TextView mTvMemberName;
        private TextView mTvPosition;
        private TextView mTvDapartment;
        private TextView mTvEntryTime;
    }

}
