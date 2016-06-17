package com.xogrp.tkgz.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ListView;

import com.xogrp.tkgz.R;
import com.xogrp.tkgz.TKGZApplication;
import com.xogrp.tkgz.Widget.RankListAdapter;
import com.xogrp.tkgz.model.RankingItemProfile;
import com.xogrp.tkgz.provider.RankingListProvider;
import com.xogrp.tkgz.spi.RankingListApiCallBack;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ayu on 11/6/2015 0006.
 */
public class IntegralRankViewFragment extends AbstractTKGZFragment {

    private static final String RANK_TYPE = "rank_type";
    private String mRankType;
    private SwipeRefreshLayout mRefreshLayout;
    private ListView mLvRank;
    private List<RankingItemProfile> mRankList = new ArrayList<>();
    private RankListAdapter mAdapter;
    private OnRankingDataListener mSavaListener;

    private SwipeRefreshLayout.OnRefreshListener mListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            getRankList();
        }
    };

    public static IntegralRankViewFragment newInstance(int id) {
        Bundle args = new Bundle();
        args.putInt(RANK_TYPE, id);
        IntegralRankViewFragment fragment = new IntegralRankViewFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_scoreboard;
    }

    @Override
    protected void onTKGZCreateView(View view) {
        mLvRank = (ListView) view.findViewById(R.id.lv_rank);
        mLvRank.setEmptyView(view.findViewById(R.id.tv_empty));
        mRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_container);

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


    private void getRankList() {
        initLoader(RankingListProvider.getRankingListProvider(this, TKGZApplication.getInstance().getUserProfile(), new RankingListApiCallBack.OnRankingListApiListener() {
            @Override
            public void onGetRankingList(final ArrayList<RankingItemProfile> list) {
                mRankList = list;
                mAdapter.setRankList(mRankList);
                final Activity activity = getActivity();
                if (activity != null) {
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mAdapter.notifyDataSetChanged();
                            mRefreshLayout.setRefreshing(false);
//                            IntegralRankFragment.setRankingListByRankType(mRankType, list);
                            if (mSavaListener != null) {
                                mSavaListener.setRankingListByRankType(mRankType, list);
                            }
                        }
                    });
                }
            }
        }, mRankType));
    }

    @Override
    protected boolean hasTKGZOptionsMenu() {
        return false;
    }

    @Override
    protected void onTKGZActivityCreated() {
        mRefreshLayout.setDistanceToTriggerSync(100);
        mRefreshLayout.setSize(SwipeRefreshLayout.DEFAULT);
        mRefreshLayout.setColorSchemeColors(getResources().getIntArray(R.array.refresh_color));
        String[] rankTitle = getResources().getStringArray(R.array.ranking_title);
        if (getArguments() != null) {
            mRankType = rankTitle[getArguments().getInt(RANK_TYPE)];
        } else {
            mRankType = getResources().getString(R.string.ranking_title_1);
        }
        if (mSavaListener != null) {
            mRankList = mSavaListener.getRankingListByRankType(mRankType);
        }
        mAdapter = new RankListAdapter(getActivity(), getAvatarOptions());
        if (mRankList == null || mRankList.size() == 0) {
            mRankList = new ArrayList<>();
            OnAutoRefresh();
        }
        mAdapter.setRankList(mRankList);
        mLvRank.setAdapter(mAdapter);
        mRefreshLayout.setOnRefreshListener(mListener);

    }

    public void setListener(OnRankingDataListener listener) {
        mSavaListener = listener;
    }

    @Override
    public String getTransactionTag() {
        return null;
    }

    @Override
    public boolean isHideActionBar() {
        return false;
    }

    @Override
    public String getActionBarTitle() {
        return null;
    }

    public interface OnRankingDataListener {
        void setRankingListByRankType(String key, List<RankingItemProfile> values);

        List<RankingItemProfile> getRankingListByRankType(String key);
    }

}
