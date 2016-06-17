package com.xogrp.tkgz.Widget;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.xogrp.tkgz.R;
import com.xogrp.tkgz.TKGZApplication;
import com.xogrp.tkgz.View.CircleImageView;
import com.xogrp.tkgz.model.ProductProfile;

import java.util.HashMap;
import java.util.List;

/**
 * Created by ayu on 12/24/2015 0024.
 */
public class ProductExpandableListAdapter extends BaseExpandableListAdapter {

    private String[] mParents;
    private HashMap<String, List<ProductProfile>> mChildren;
    private Context mContext;
    private boolean mIsAllVisibility;
    private int mCurrentGroupPosition;
    private final int mDefaultChildrenVisibilityNum = 3;
    private OnCallExchangeListener mListener;
    private DisplayImageOptions mGiftOption;

    public ProductExpandableListAdapter(Context context, OnCallExchangeListener listener, DisplayImageOptions options) {
        mContext = context;
        mListener = listener;
        mParents = new String[]{};
        mGiftOption = options;
        mChildren = new HashMap<>();
    }

    public void setParents(String[] parents) {
        mParents = parents;
    }

    public void setChildren(HashMap<String, List<ProductProfile>> children) {
        mChildren = children;
    }

    @Override
    public int getGroupCount() {
        return mParents.length;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        int childrenCount = 0;
        List<ProductProfile> productProfileList = mChildren.get(mParents[groupPosition]);
        if (mCurrentGroupPosition != groupPosition) {
            mIsAllVisibility = false;
        }
        if (productProfileList != null) {
            childrenCount = productProfileList.size();
            if (childrenCount > mDefaultChildrenVisibilityNum) {
                childrenCount = mIsAllVisibility ? childrenCount : mDefaultChildrenVisibilityNum;
            }
        }
        return childrenCount;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return mParents[groupPosition];
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return mChildren.get(mParents[groupPosition]).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View parentView, ViewGroup parent) {
        ParentViewHolder parentViewHolder = null;
        if (parentView == null) {
            parentViewHolder = new ParentViewHolder();
            parentView = LayoutInflater.from(mContext).inflate(R.layout.expandable_list_view_parent_product, parent, false);
            parentViewHolder.mTvName = (TextView) parentView.findViewById(R.id.tv_name);
            parentView.setTag(parentViewHolder);
        } else {
            parentViewHolder = (ParentViewHolder) parentView.getTag();
        }
        parentViewHolder.mTvName.setText(mParents[groupPosition]);
        parentViewHolder.mTvName.setCompoundDrawablesWithIntrinsicBounds(0, 0, (isExpanded ? R.drawable.icon_arrow_down : R.drawable.icon_arrow_right), 0);

        if (!isExpanded && mCurrentGroupPosition == groupPosition) {
            mIsAllVisibility = false;
        }
        return parentView;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View childView, ViewGroup parent) {
        final ProductProfile product = (ProductProfile) getChild(groupPosition, childPosition);
        ChildViewHolder childViewHolder = null;
        if (childView == null) {
            childView = LayoutInflater.from(mContext).inflate(R.layout.product_item, parent, false);
            childViewHolder = new ChildViewHolder();
            childViewHolder.mCivIcon = (CircleImageView) childView.findViewById(R.id.iv_icon);
            childViewHolder.mTvName = (TextView) childView.findViewById(R.id.tv_name);
            childViewHolder.mTvIntegral = (TextView) childView.findViewById(R.id.tv_year_integral);
            childViewHolder.mBtnExchange = (Button) childView.findViewById(R.id.btn_exchange);
            childViewHolder.mRlMore = (RelativeLayout) childView.findViewById(R.id.rl_even_more);
            childView.setTag(childViewHolder);
        } else {
            childViewHolder = (ChildViewHolder) childView.getTag();
        }

        childViewHolder.mTvName.setText(product.getName());
        int integral = product.getIntegral();
        childViewHolder.mTvIntegral.setText(String.valueOf(integral));

        if (integral > TKGZApplication.getInstance().getUserProfile().getIntegralForYear()) {
            childViewHolder.mBtnExchange.setBackgroundResource(R.drawable.btn_gray_style);
        } else {
            childViewHolder.mBtnExchange.setBackgroundResource(R.drawable.btn_green_style);
            childViewHolder.mBtnExchange.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new TKGZCustomHomeMenuDialog(mContext, R.string.dialog_title_reminder, R.string.dialog_content_ensure_exchange,
                            product.getName(), "", R.string.dialog_ok_button, R.string.dialog_cancel_button, new TKGZCustomDialog.OnDialogActionCallback() {
                        @Override
                        public void onConfirmSelected() {
                            mListener.onCallExchange(product.getId());
                        }
                    }, null).show();
                }
            });
        }

        if (childPosition == mDefaultChildrenVisibilityNum - 1 && !mIsAllVisibility) {
            childViewHolder.mRlMore.setVisibility(View.VISIBLE);
            childViewHolder.mRlMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mCurrentGroupPosition = groupPosition;
                    mIsAllVisibility = true;
                    notifyDataSetChanged();
                }
            });
        } else {
            childViewHolder.mRlMore.setVisibility(View.GONE);
        }
        ImageLoader.getInstance().displayImage(product.getAvatarUrl(), childViewHolder.mCivIcon,mGiftOption );
        return childView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    @Override
    public boolean areAllItemsEnabled() {
        return super.areAllItemsEnabled();
    }

    private static class ParentViewHolder {
        TextView mTvName;
    }

    private static class ChildViewHolder {
        private CircleImageView mCivIcon;
        private TextView mTvName;
        private TextView mTvIntegral;
        private Button mBtnExchange;
        private RelativeLayout mRlMore;
    }

    public interface OnCallExchangeListener {
        void onCallExchange(String productId);
    }
}
