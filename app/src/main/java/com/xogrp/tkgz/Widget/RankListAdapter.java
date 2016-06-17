package com.xogrp.tkgz.Widget;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.xogrp.tkgz.R;
import com.xogrp.tkgz.TKGZApplication;
import com.xogrp.tkgz.model.RankingItemProfile;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wlao on 8/11/2015.
 */
public class RankListAdapter extends BaseAdapter {
    private List<RankingItemProfile> mRankList;
    private Context mContext;
    private String mUserId;
    private DisplayImageOptions mAvatarOption;
    private final int[] mBadge = new int[]{R.drawable.icon_rank1, R.drawable.icon_rank2, R.drawable.icon_rank3};


    public RankListAdapter(Context context, DisplayImageOptions options) {
        this.mContext = context;
        this.mRankList = new ArrayList<>();
        mAvatarOption = options;
        mUserId = TKGZApplication.getInstance().getUserProfile().getId();
    }

    public void setRankList(List<RankingItemProfile> list) {
        mRankList = list;
    }

    @Override
    public int getCount() {
        return mRankList.size();
    }

    @Override
    public Object getItem(int position) {
        return mRankList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        RankingItemProfile item = (RankingItemProfile) getItem(position);
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.rank_message_item, viewGroup, false);
            holder = new ViewHolder();
            holder.mTvName = (TextView) convertView.findViewById(R.id.tv_name);
            holder.mTvScore = (TextView) convertView.findViewById(R.id.tv_year_integral);
            holder.mTvRanking = (TextView) convertView.findViewById(R.id.tv_ranking);
            holder.mIvIcon = (ImageView) convertView.findViewById(R.id.iv_icon);
            holder.mTvApostrophe = (TextView) convertView.findViewById(R.id.tv_apostrophe);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.mTvApostrophe.setVisibility(View.GONE);
        holder.mTvName.setText(item.getName());
        holder.mTvScore.setText(String.valueOf(item.getIntegral()));

        int ranking = item.getRanking();
        if (ranking < 4) {
            holder.mTvRanking.setText("");
            holder.mTvName.setTextColor(mContext.getResources().getColor(R.color.tv_top_three_rank_color));
            holder.mTvRanking.setBackgroundResource(mBadge[ranking - 1]);
        } else {
            holder.mTvRanking.setText(String.valueOf(ranking));
            holder.mTvName.setTextColor(mContext.getResources().getColor(android.R.color.black));
            holder.mTvRanking.setBackgroundResource(R.drawable.icon_rank_graybg);
        }

        if (mUserId.equals(item.getId())) {
            holder.mTvName.setTextColor(mContext.getResources().getColor(R.color.tv_my_rank_color));
            if (ranking >= 4) {
                holder.mTvRanking.setBackgroundResource(R.drawable.icon_rank_greenbg);
            }
            if (ranking > 10) {
                holder.mTvApostrophe.setVisibility(View.VISIBLE);
            }
        }

        ImageLoader.getInstance().displayImage(item.getAvatarUrl(), holder.mIvIcon, mAvatarOption);

        return convertView;
    }

    private static class ViewHolder {
        private ImageView mIvIcon;
        private TextView mTvName;
        private TextView mTvScore;
        private TextView mTvRanking;
        private TextView mTvApostrophe;
    }

}
