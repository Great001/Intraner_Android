package com.xogrp.tkgz.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.xogrp.tkgz.R;
import com.xogrp.tkgz.View.CircleImageView;
import com.xogrp.tkgz.model.ProductProfile;
import com.xogrp.xoapp.XOApplication;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lzhou on 2015/8/20.
 */
public class MyProductAdapter extends BaseAdapter {
    private List<ProductProfile> mProductList;
    private Context mContext;
    private UseProductListener mUseProductListener;
    private DisplayImageOptions mGiftOption;

    public MyProductAdapter(Context context, UseProductListener useProductListener, DisplayImageOptions options) {
        mProductList = new ArrayList<>();
        mContext = context;
        mGiftOption = options;
        mUseProductListener = useProductListener;
    }

    public void setProductList(List<ProductProfile> list) {
        mProductList = list;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mProductList.size();
    }

    @Override
    public Object getItem(int i) {
        return mProductList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final ViewHolder viewHolder;
        final ProductProfile productProfile = mProductList.get(i);
        if (view == null) {
            viewHolder = new ViewHolder();
            view = LayoutInflater.from(mContext).inflate(R.layout.my_products_item_layout, viewGroup, false);
            viewHolder.mBtnStatus = (Button) view.findViewById(R.id.btn_goods_use);
            viewHolder.mTvExpiryDate = (TextView) view.findViewById(R.id.tv_expiry_date);
            viewHolder.mTvProductName = (TextView) view.findViewById(R.id.tv_product_name);
            viewHolder.mCIvAvatar = (CircleImageView) view.findViewById(R.id.civ_product_avatar);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        ImageLoader.getInstance().displayImage(productProfile.getAvatarUrl(), viewHolder.mCIvAvatar, mGiftOption);
        viewHolder.mTvProductName.setText(String.valueOf(productProfile.getName()));

        if (!XOApplication.isTextEmptyOrNull(productProfile.getExpiryDate())) {
            viewHolder.mTvExpiryDate.setText(
                    String.format(mContext.getString(R.string.txt_expiry_date), productProfile.getExpiryDate()));
        }

        if (productProfile.isUsed()) {
            viewHolder.mBtnStatus.setBackgroundResource(R.drawable.my_goods_shape_grey);
            viewHolder.mBtnStatus.setText(mContext.getResources().getString(productProfile.isTangible() ?
                    R.string.product_claimed : R.string.product_used));
            viewHolder.mBtnStatus.setClickable(false);
        } else {
            viewHolder.mBtnStatus.setBackgroundResource(R.drawable.my_goods_shape);
            viewHolder.mBtnStatus.setText(productProfile.isTangible() ?
                    R.string.product_unclaimed : R.string.product_use);
            viewHolder.mBtnStatus.setClickable(true);
            viewHolder.mBtnStatus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mUseProductListener.onUseProduct(productProfile.getId());
                }
            });
        }
        return view;
    }

    private static class ViewHolder {
        private CircleImageView mCIvAvatar;
        private TextView mTvProductName;
        private TextView mTvExpiryDate;
        private Button mBtnStatus;
    }

    public interface UseProductListener {
        void onUseProduct(String productId);
    }
}
