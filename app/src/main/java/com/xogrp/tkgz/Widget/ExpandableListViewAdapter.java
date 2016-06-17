package com.xogrp.tkgz.Widget;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.xogrp.tkgz.R;
import com.xogrp.tkgz.model.TeamMembersIntegralAndPropsProfile;
import com.xogrp.tkgz.util.TKGZUtil;

import java.util.List;
import java.util.Map;

/**
 * Created by wlao on 8/10/2015.
 */
public class ExpandableListViewAdapter extends BaseExpandableListAdapter {
    private List<String> mParent;
    private List<Integer> mParentItemScore;
    private Map<String, List<TeamMembersIntegralAndPropsProfile>> mMap;
    private LayoutInflater mLayoutInflater;
    private Context mContext;
    private int mChildViewId;
    private boolean mIsAllInfoVisible;
    private int mClickGroupPosition;
    private int mDefaultItemQuantity;

    public ExpandableListViewAdapter(Context context, List<String> parent, Map<String, List<TeamMembersIntegralAndPropsProfile>> map, int childViewId) {
        this.mContext = context;
        this.mParent = parent;
        this.mMap = map;
        this.mChildViewId = childViewId;
        this.mDefaultItemQuantity = 4;
        this.mLayoutInflater = LayoutInflater.from(context);
    }

    public ExpandableListViewAdapter(Context context, List<String> parent, List<Integer> parentItemScore, Map<String, List<TeamMembersIntegralAndPropsProfile>> map, int childViewId) {
//        this(context, parent, map, childViewId);
        this.mContext = context;
        this.mParent = parent;
        this.mMap = map;
        this.mChildViewId = childViewId;
        this.mDefaultItemQuantity = 4;
        this.mParentItemScore = parentItemScore;
        this.mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getGroupCount() {
        return mParent.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        String key = mParent.get(groupPosition);
        if (mClickGroupPosition != groupPosition) {
            mIsAllInfoVisible = false;
        }
        return (mIsAllInfoVisible ? mMap.get(key).size() : mDefaultItemQuantity);
    }

    @Override
    public Object getGroup(int groupPosition) {
        return mParent.get(groupPosition);
    }

    //The need to obtain children item data associated
    @Override
    public Object getChild(int groupPosition, int childPosition) {
        String key = mParent.get(groupPosition);

        return mMap.get(key).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    //Setting children item ID
    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup viewGroup) {
        ViewParentHolder holder = null;
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.expandablelistview_parent_item, viewGroup, false);
            holder = new ViewParentHolder();
            holder.mTvTitle = (TextView) convertView.findViewById(R.id.tv_title);
            holder.mTeamScores = (TextView) convertView.findViewById(R.id.tv_team_score);

            convertView.setTag(holder);
        }
        holder = (ViewParentHolder) convertView.getTag();
        holder.mTvTitle.setText(mParent.get(groupPosition));

        String text = null;
        if (mChildViewId == R.layout.layout_expandable_list_child) {
            text = Integer.toString(mParentItemScore.get(groupPosition));
        }
        holder.mTeamScores.setText(text);

        if (mClickGroupPosition == groupPosition) {
            if (!isExpanded) {
                mIsAllInfoVisible = false;
            }
        }

        return convertView;
    }

    //Setting children item component
    @Override
    public View getChildView(final int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup viewGroup) {
        String key = mParent.get(groupPosition);
        final TeamMembersIntegralAndPropsProfile teamMembersIntegralAndPropsProfile = mMap.get(key).get(childPosition);
        ViewChildrenHolder holder = null;
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(mChildViewId, null);
            holder = new ViewChildrenHolder();
            holder.mTvName = (TextView) convertView.findViewById(R.id.tv_name);
            holder.mTvScore = (TextView) convertView.findViewById(R.id.tv_year_integral);
            holder.mIvIcon = (ImageView) convertView.findViewById(R.id.iv_icon);
            holder.mRlEvenMore = (RelativeLayout) convertView.findViewById(R.id.rl_even_more);
            if (mChildViewId == R.layout.layout_expandable_list_child) {
                holder.mTvRanking = (TextView) convertView.findViewById(R.id.tv_ranking);
            } else {
                holder.mLlPropsItem = (LinearLayout) convertView.findViewById(R.id.ll_propsItem);
                holder.mBtnExchange = (Button) convertView.findViewById(R.id.btn_exchange);
            }
            convertView.setTag(holder);
        }
        holder = (ViewChildrenHolder) convertView.getTag();
        holder.mTvName.setText(teamMembersIntegralAndPropsProfile.getObjectName());
        holder.mTvScore.setText(String.valueOf(teamMembersIntegralAndPropsProfile.getScore()));

        if (childPosition == mDefaultItemQuantity - 1 && !mIsAllInfoVisible) {
            holder.mRlEvenMore.setVisibility(View.VISIBLE);
        } else {
            holder.mRlEvenMore.setVisibility(View.GONE);
        }

        holder.mRlEvenMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mClickGroupPosition = groupPosition;
                mIsAllInfoVisible = true;
                notifyDataSetChanged();
            }
        });

        int score = teamMembersIntegralAndPropsProfile.getScore();

        int grade = TKGZUtil.getLevel(score);

        if (mChildViewId == R.layout.layout_expandable_list_child) {
            holder.mTvRanking.setBackgroundResource(TKGZUtil.getLevelImage(grade));
        } else {
            holder.mLlPropsItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                }
            });

            //hard code, just a demo
            holder.mBtnExchange.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if ("Props".equals(teamMembersIntegralAndPropsProfile.getType()) || "Physical".equals(teamMembersIntegralAndPropsProfile.getType())) {
                        (new TKGZCustomDialog(mContext, R.string.dialog_content_ensure_exchange, String.format("点击OK兑换 %s", teamMembersIntegralAndPropsProfile.getObjectName()), true)).show();
                    } else {
                        Toast.makeText(mContext, "test", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }

        int iconId = teamMembersIntegralAndPropsProfile.getIconId();
        if (iconId == 0) {
            iconId = R.drawable.ic_launcher;
        }
        holder.mIvIcon.setImageBitmap(BitmapFactory.decodeResource(mContext.getResources(), iconId));

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    private static class ViewChildrenHolder {
        private TextView mTvName;
        private TextView mTvScore;
        private ImageView mIvIcon;
        private TextView mTvRanking;
        private RelativeLayout mRlEvenMore;
        private Button mBtnExchange;
        private LinearLayout mLlPropsItem;
    }

    private static class ViewParentHolder {
        private TextView mTvTitle;
        private TextView mTeamScores;
    }
}
