package com.xogrp.tkgz.fragment;


import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.xogrp.tkgz.R;
import com.xogrp.tkgz.TKGZApplication;
import com.xogrp.tkgz.listener.OnRefreshUIListener;
import com.xogrp.tkgz.model.MessageForUser;
import com.xogrp.tkgz.provider.DeleteNewsProvider;
import com.xogrp.tkgz.spi.DeleteNewsApiCallBack;
import com.xogrp.tkgz.util.TKGZUtil;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewsDetailsFragment extends AbstractTKGZFragment {
    private TextView mTvResultsTitle;
    private TextView mTvCurrentTime;
    private TextView mTvTrainContent;
    private MessageForUser mMessage;
    private OnRefreshUIListener mOnRefreshUIListener;

    public static NewsDetailsFragment newInstance(MessageForUser message) {
        Bundle args = new Bundle();
        args.putSerializable("message", message);
        NewsDetailsFragment fragment = new NewsDetailsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void onTKGZAttach(Activity activity) {
        super.onTKGZAttach(activity);
        if (activity instanceof OnRefreshUIListener) {
            mOnRefreshUIListener = (OnRefreshUIListener) activity;
        }
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_news_details;
    }

    @Override
    protected void onTKGZCreateView(View rootView) {
        mTvResultsTitle = (TextView) rootView.findViewById(R.id.tv_results_title);
        mTvCurrentTime = (TextView) rootView.findViewById(R.id.tv_current_time);
        mTvTrainContent = (TextView) rootView.findViewById(R.id.tv_train_content);
        mMessage = (MessageForUser) getArguments().getSerializable("message");
    }

    @Override
    protected void onTKGZActivityCreated() {
        if (mMessage != null) {
            mTvResultsTitle.setText(mMessage.getTitle());
            mTvTrainContent.setText(mMessage.getContent());
            mTvCurrentTime.setText(TKGZUtil.getStringDateTime(mMessage.getCreateTime()));
        } else {
            getOnScreenNavigationListener().goBackFromCurrentPage();
        }
    }

    @Override
    protected int getMenuResourceId() {
        return R.menu.menu_news_details_page;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.menu_delete:
                initLoader(DeleteNewsProvider.getDeleteNewsProvider(TKGZApplication.getInstance().getUserProfile(), mMessage.getId(), this, new DeleteNewsApiCallBack.OnDeleteNewsApiListener() {
                    @Override
                    public void onGetResult() {
                        getOnScreenNavigationListener().goBackFromCurrentPage();
                        mOnRefreshUIListener.callRefreshNewsList();
                    }
                }));
                return true;
            default:
                break;
        }
        return false;
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
        return getString(R.string.actionbar_title_news_details_page);
    }

}
