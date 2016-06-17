package com.xogrp.tkgz.fragment;

import android.app.Activity;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ListView;

import com.xogrp.tkgz.R;
import com.xogrp.tkgz.TKGZApplication;
import com.xogrp.tkgz.adapter.MyProductAdapter;
import com.xogrp.tkgz.model.ProductProfile;
import com.xogrp.tkgz.provider.MyProductProvider;
import com.xogrp.tkgz.provider.UseProductProvider;
import com.xogrp.tkgz.spi.MyProductApiCallBack;
import com.xogrp.tkgz.spi.UseProductApiCallBack;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lzhou on 2015/8/20.
 */
public class MyProductsFragment extends AbstractTKGZFragment implements MyProductAdapter.UseProductListener {
    private ListView mLvProductList;
    private MyProductAdapter mAdapter;
    private List<ProductProfile> mProductList;
    private SwipeRefreshLayout mRefreshLayout;

    private SwipeRefreshLayout.OnRefreshListener mListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            getProductList();
        }
    };

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_my_products;
    }

    @Override
    protected void onTKGZCreateView(View rootView) {
        mLvProductList = (ListView) rootView.findViewById(R.id.lv_display_my_products);
        mLvProductList.setEmptyView(rootView.findViewById(R.id.tv_empty));
        mRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.refresh_layout);
        mRefreshLayout.setDistanceToTriggerSync(100);
        mRefreshLayout.setSize(SwipeRefreshLayout.DEFAULT);
        mRefreshLayout.setColorSchemeColors(getResources().getIntArray(R.array.refresh_color));
    }


    @Override
    protected void onTKGZActivityCreated() {
        OnAutoRefresh();
        mRefreshLayout.setOnRefreshListener(mListener);
        mProductList = new ArrayList<>();
        mAdapter = new MyProductAdapter(getActivity(), this, getGiftOptions());
        mAdapter.setProductList(mProductList);
        mLvProductList.setAdapter(mAdapter);
    }

    private void getProductList() {
        initLoader(MyProductProvider.getMyProductProvider(this, TKGZApplication.getInstance().getUserProfile(), new MyProductApiCallBack.OnMyProductApiListener() {
            @Override
            public void onGetMyProductList(final List<ProductProfile> list) {

                final Activity activity = getActivity();
                if (activity != null) {
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mProductList = list;
                            mAdapter.setProductList(mProductList);
                            mRefreshLayout.setRefreshing(false);
                        }
                    });
                }
            }
        }));
    }

    private void OnAutoRefresh() {
        mRefreshLayout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mRefreshLayout.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                mRefreshLayout.setRefreshing(true);
                mListener.onRefresh();
            }
        });
    }

    @Override
    public String getTransactionTag() {
        return "my_goods_fragment";
    }

    @Override
    public boolean isHideActionBar() {
        return false;
    }

    @Override
    public String getActionBarTitle() {
        return getString(R.string.txt_my_goods);
    }

    @Override
    public void onUseProduct(String productId) {
        showSpinner();
        initLoader(UseProductProvider.getUseProductProvider(TKGZApplication.getInstance().getUserProfile(), this, productId, new UseProductApiCallBack.OnUseProductApiListener() {
            @Override
            public void onUseProductSuccess(final String message) {
                final Activity activity = getActivity();
                if (activity != null) {
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            showMessage(message);
                            mRefreshLayout.setRefreshing(true);
                            mListener.onRefresh();
                        }
                    });
                }
            }

            @Override
            public void onUseProductFailed(final String message) {
                final Activity activity = getActivity();
                if (activity != null) {
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            showMessage(message);
                        }
                    });
                }
            }
        }));
    }
}
