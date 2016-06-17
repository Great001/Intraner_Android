package com.xogrp.tkgz.fragment;


import android.app.Activity;
import android.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.ListView;

import com.xogrp.tkgz.R;
import com.xogrp.tkgz.TKGZApplication;
import com.xogrp.tkgz.Widget.NewsListAdapter;
import com.xogrp.tkgz.activity.MainActivity;
import com.xogrp.tkgz.listener.OnRefreshUIListener;
import com.xogrp.tkgz.model.MessageForUser;
import com.xogrp.tkgz.provider.ChangeNewsStatusProvider;
import com.xogrp.tkgz.provider.NewsListProvider;
import com.xogrp.tkgz.spi.ChangeNewsStatusApiCallBack;
import com.xogrp.tkgz.spi.NewsListApiCallBack;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewsListFragment extends AbstractTKGZFragment implements MainActivity.OnRefreshNewsListCallBack {
    private ListView mLvNewsList;
    private List<MessageForUser> mMessageForUserList;
    private SwipeRefreshLayout mRefreshLayout;
    private OnRefreshUIListener mOnRefreshUIListener;
    private NewsListAdapter mAdapter;

    private SwipeRefreshLayout.OnRefreshListener mListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            initLoader(NewsListProvider.getAllNewsProvider(TKGZApplication.getInstance().getUserProfile(), NewsListFragment.this, new NewsListApiCallBack.OnNewsListApiCallBackListener() {
                @Override
                public void onGetNewsList(List<MessageForUser> list) {
                    if (list != null) {
                        mMessageForUserList = list;
                        mAdapter.setMessageList(mMessageForUserList);
                        final Activity activity = getActivity();
                        if (activity != null) {
                            activity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    mAdapter.notifyDataSetChanged();
                                    mRefreshLayout.setRefreshing(false);
                                }
                            });
                        }
                    }
                }
            }));
        }
    };

    @Override
    protected void onTKGZAttach(Activity activity) {
        super.onTKGZAttach(activity);
        if (activity instanceof OnRefreshUIListener) {
            mOnRefreshUIListener = (OnRefreshUIListener) activity;
        }
        ((MainActivity) activity).addOnRefreshNewsListCallBack(this);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_news_list;
    }

    @Override
    protected void onTKGZCreateView(View rootView) {
        mMessageForUserList = new ArrayList<>();
        mLvNewsList = (ListView) rootView.findViewById(R.id.lv_news_list);
        mLvNewsList.setEmptyView(rootView.findViewById(R.id.tv_empty));
        mRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.refresh_layout);
        mRefreshLayout.setDistanceToTriggerSync(100);
        mRefreshLayout.setSize(SwipeRefreshLayout.DEFAULT);
        mRefreshLayout.setColorSchemeColors(getResources().getIntArray(R.array.refresh_color));
    }


    @Override
    protected void onTKGZActivityCreated() {
        mAdapter = new NewsListAdapter(getActivity());
        mAdapter.setMessageList(mMessageForUserList);
        mLvNewsList.setAdapter(mAdapter);

        OnAutoRefresh();

        mRefreshLayout.setOnRefreshListener(mListener);

        mLvNewsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final MessageForUser message = mMessageForUserList.get(position);
                if (!message.isStatus()) {
                    initLoader(ChangeNewsStatusProvider.getChangeNewsStatusProvider(message.getId(), TKGZApplication.getInstance().getUserProfile(), new ChangeNewsStatusApiCallBack.OnChangeNewsStatusApiListener() {
                        @Override
                        public void onGetChangeResult() {
                            final Activity activity = getActivity();
                            if (activity != null) {
                                activity.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        message.setStatus(true);
                                        mAdapter.setMessageList(mMessageForUserList);
                                        mAdapter.notifyDataSetChanged();
                                    }
                                });
                            }
                        }
                    }, NewsListFragment.this));
                }
                getOnScreenNavigationListener().navigateToNewsDetailsPage(message);
            }
        });
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
    public void onRefreshNewsList() {
        OnAutoRefresh();
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
        return getString(R.string.actionbar_title_news_list_page);
    }

    @Override
    public void onDestroy() {
        mOnRefreshUIListener.callRefreshNewsCount();
        final Activity activity = getActivity();
        if (activity != null) {
            ((MainActivity) activity).removeOnRefreshNewsListCallBack();
        }
        super.onDestroy();
    }
}
