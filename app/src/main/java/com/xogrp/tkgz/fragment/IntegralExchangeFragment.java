package com.xogrp.tkgz.fragment;


import android.app.Activity;
import android.app.Fragment;
import android.graphics.BitmapFactory;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.xogrp.tkgz.R;
import com.xogrp.tkgz.TKGZApplication;
import com.xogrp.tkgz.Widget.ProductExpandableListAdapter;
import com.xogrp.tkgz.activity.MainActivity;
import com.xogrp.tkgz.listener.OnRefreshUIListener;
import com.xogrp.tkgz.model.ProductProfile;
import com.xogrp.tkgz.provider.ExchangeProductProvider;
import com.xogrp.tkgz.provider.ProductListProvider;
import com.xogrp.tkgz.spi.ExchangeProductApiCallBack;
import com.xogrp.tkgz.spi.ProductListApiCallBack;
import com.xogrp.tkgz.util.TKGZUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class IntegralExchangeFragment extends AbstractTKGZFragment implements MainActivity.OnRefreshIntegralCallBack, ProductExpandableListAdapter.OnCallExchangeListener {

    private TextView mTvYearIntegral;
    private TextView mTvMonthIntegral;
    private TextView mTvGrade;
    private ProgressBar mPbarIntegral;
    private ImageView mIvBadge;
    private TextView mTvTips;
    private ExpandableListView mEListView;
    private OnRefreshUIListener mOnRefreshUIListener;

    private static final String VIRTUAL_TYPE = "virtual";
    private static final String ACTUAL_TYPE = "tangible";
    private String[] mParents;
    private HashMap<String, List<ProductProfile>> mChildren = new HashMap<>();

    private int[] mProgressbarMax;
    private int[] mLevelTotalScore;
    private ProductExpandableListAdapter mAdapter;

    @Override
    protected void onTKGZAttach(Activity activity) {
        super.onTKGZAttach(activity);
        if (activity instanceof OnRefreshUIListener) {
            mOnRefreshUIListener = (OnRefreshUIListener) activity;
        }
        ((MainActivity) activity).addOnRefreshIntegralCallBack(this);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_integral_exchange;
    }

    @Override
    protected void onTKGZCreateView(View rootView) {
        mTvYearIntegral = (TextView) rootView.findViewById(R.id.tv_year_integral);
        mTvMonthIntegral = (TextView) rootView.findViewById(R.id.tv_month_integral);
        mTvGrade = (TextView) rootView.findViewById(R.id.tv_grade);
        mPbarIntegral = (ProgressBar) rootView.findViewById(R.id.pbar_integral);
        mTvTips = (TextView) rootView.findViewById(R.id.tv_tipsscore);
        mIvBadge = (ImageView) rootView.findViewById(R.id.iv_badge);
        mEListView = (ExpandableListView) rootView.findViewById(R.id.elv_container);

        mAdapter = new ProductExpandableListAdapter(getActivity(), this, getGiftOptions());
        mProgressbarMax = getResources().getIntArray(R.array.progressbar_max_item);
        mLevelTotalScore = getResources().getIntArray(R.array.level_total_score_item);
    }

    @Override
    protected void onTKGZActivityCreated() {
        showTheIntegralPage();
        mParents = new String[]{getString(R.string.virtual_product), getString(R.string.actual_product)};
        mAdapter.setParents(mParents);
        mEListView.setAdapter(mAdapter);

        showSpinner();
        getProductList(0);
        getProductList(1);
    }

    private void getProductList(final int id) {
        String productType = (id == 1) ? ACTUAL_TYPE : VIRTUAL_TYPE;
        initLoader(ProductListProvider.getProductListProvider(productType, this, TKGZApplication.getInstance().getUserProfile(), new ProductListApiCallBack.OnProductListApiListener() {
            @Override
            public void getProductList(final ArrayList<ProductProfile> list) {
                final Activity activity = getActivity();
                if (activity != null) {
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mChildren.put(mParents[id], list);
                            mAdapter.setChildren(mChildren);
                            mAdapter.notifyDataSetChanged();

                            mEListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
                                @Override
                                public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                                    if (mChildren.get(mParents[groupPosition]) != null && mChildren.get(mParents[groupPosition]).size() > 0) {
                                        if (parent.isGroupExpanded(groupPosition)) {
                                            parent.collapseGroup(groupPosition);
                                        } else {
                                            parent.expandGroup(groupPosition, false);
                                            int listSize = mParents.length;
                                            for (int i = 0; i < listSize; i++) {
                                                if (groupPosition != i) {
                                                    mEListView.collapseGroup(i);
                                                }
                                            }
                                        }
                                    }
                                    return true;
                                }
                            });

                            mEListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
                                @Override
                                public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                                    getOnScreenNavigationListener().navigateToShowProductDetailPage(mChildren.get(mParents[groupPosition]).get(childPosition).getId());
                                    return false;
                                }
                            });
                        }
                    });
                }
            }
        }));
    }


    @Override
    public void onCallExchange(String productId) {
        showSpinner();
        initLoader(ExchangeProductProvider.getExchangeProductProvider(this, TKGZApplication.getInstance().getUserProfile(), productId, new ExchangeProductApiCallBack.OnExchangeProductApiListener() {
            @Override
            public void onExchangeProduct(final boolean result, final int integral) {
                final Activity activity = getActivity();
                if (activity != null) {
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (result) {
                                showDialog(R.string.dialog_title_reminder, R.string.dialog_content_exchange_success);
                                TKGZApplication.getInstance().getUserProfile().setIntegralForYear(integral);
                                mOnRefreshUIListener.callRefreshIntegral();
                                mAdapter.notifyDataSetChanged();
                            } else {
                                showDialog(R.string.dialog_title_reminder, R.string.dialog_content_exchange_failure);
                            }
                        }
                    });
                }

            }
        }));
    }

    @Override
    public String getTransactionTag() {
        return "integral_exchange_fragment";
    }

    @Override
    public boolean isHideActionBar() {
        return false;
    }

    @Override
    public String getActionBarTitle() {
        return getString(R.string.actionbar_title_integral_exchange_page);
    }

    @Override
    protected int getMenuResourceId() {
        return R.menu.menu_my_goods;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        super.onCreateOptionsMenu(menu, menuInflater);
        MenuItem menuItem = menu.findItem(R.id.home_my_goods_item);
        Button mBtnMenuItem = (Button) menuItem.getActionView();
        mBtnMenuItem.setText(getString(R.string.actionbar_title_my_goods));
        mBtnMenuItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getOnScreenNavigationListener().navigateToMyProductsPage();
            }
        });
    }

    public void showTheIntegralPage() {
        int currentIntegral = TKGZApplication.getInstance().getUserProfile().getIntegralForYear();
        int level = TKGZUtil.getLevel(currentIntegral);

        mTvYearIntegral.setText(String.valueOf(currentIntegral));
        mTvMonthIntegral.setText(String.valueOf(TKGZApplication.getInstance().getUserProfile().getIntegralForMonth()));
        mIvBadge.setImageBitmap(BitmapFactory.decodeResource(getActivity().getResources(), TKGZUtil.getLevelImage(level)));
        mPbarIntegral.setIndeterminate(false);

        if (level < 4) {
            mTvGrade.setText(TKGZUtil.getLevelAppellation(level));
            int tipsScore = (mLevelTotalScore[level]) - currentIntegral;
            mTvTips.setText(String.format("%d/%d", currentIntegral, mLevelTotalScore[level]));
            int progress = mProgressbarMax[level] - tipsScore;
            mPbarIntegral.setMax(mProgressbarMax[level]);
            mPbarIntegral.setProgress(progress);
        } else {
            mTvGrade.setText(TKGZUtil.getLevelAppellation(4));
            mPbarIntegral.setProgress(mLevelTotalScore[3]);

        }
    }

    @Override
    public void onRefreshIntegral() {
        showTheIntegralPage();
    }

    @Override
    public void onDestroy() {
        final Activity activity = getActivity();
        if (activity != null) {
            ((MainActivity) activity).removeOnRefreshIntegralCallBack(this);
        }
        super.onDestroy();
    }
}
