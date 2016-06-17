package com.xogrp.tkgz.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.xogrp.tkgz.R;
import com.xogrp.tkgz.model.MessageProfile;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

/**
 * Created by ayu on 11/11/2015 0011.
 */
public class MessageListFragment extends AbstractTKGZFragment implements AdapterView.OnItemClickListener {

    private SwipeRefreshLayout mRefreshLayout;
    private ListView mLvMessage;
    private int mPosition;
    private List<MessageProfile> mMessageList;
    private static final String BUNDLE_KEY_POSITION = "position";

    public static MessageListFragment newInstance(int position) {

        Bundle args = new Bundle();
        args.putInt(BUNDLE_KEY_POSITION, position);
        MessageListFragment fragment = new MessageListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.list_message_item;
    }

    @Override
    protected void onTKGZCreateView(View rootView) {
        mRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.refresh_layout);
        mLvMessage = (ListView) rootView.findViewById(R.id.lv_message);
    }

    @Override
    protected void onTKGZActivityCreated() {
        mMessageList = new ArrayList<>();
        if (getArguments() != null) {
            mPosition = getArguments().getInt(BUNDLE_KEY_POSITION);
        }
        if (mPosition == 0) {
            mMessageList = getDemoPublishedData();
        } else {
            mMessageList = getDemoDraftsData();
        }
        MessageListAdapter mAdapter = new MessageListAdapter(getActivity());
        mAdapter.setMessageList(mMessageList);
        mLvMessage.setAdapter(mAdapter);
        mLvMessage.setOnItemClickListener(this);
        mRefreshLayout.setDistanceToTriggerSync(100);
        mRefreshLayout.setSize(SwipeRefreshLayout.LARGE);
        mRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright, android.R.color.holo_green_light,
                android.R.color.holo_orange_light, android.R.color.holo_red_light);
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //just for a better UI
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mRefreshLayout.setRefreshing(false);
                    }
                }, 5000);
            }
        });
    }

    private List<MessageProfile> getDemoPublishedData() {
        final List<MessageProfile> messageList = new ArrayList<>();

        for (int i = 0; i < 20; i++) {
            Calendar c = Calendar.getInstance();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
            messageList.add(new MessageProfile(getString(R.string.messages_published) + i, getString(R.string.message_published_content),
                    dateFormat.format(c.getTime())));
        }
        return messageList;
    }

    private List<MessageProfile> getDemoDraftsData() {
        final List<MessageProfile> messageList = new ArrayList<>();

        for (int i = 0; i < 20; i++) {
            Calendar c = Calendar.getInstance();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
            messageList.add(new MessageProfile(getString(R.string.messages_drafts) + i, getString(R.string.message_drafts_content),
                    dateFormat.format(c.getTime())));
        }
        return messageList;
    }

    @Override
    public String getTransactionTag() {
        return null;
    }

    @Override
    protected boolean hasTKGZOptionsMenu() {
        return false;
    }

    @Override
    public boolean isHideActionBar() {
        return false;
    }

    @Override
    public String getActionBarTitle() {
        return null;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        MessageProfile message = mMessageList.get(position);
        getOnScreenNavigationListener().navigateToMessageDetailsPage(message, mPosition);
    }

    private static class MessageListAdapter extends BaseAdapter {
        private List<MessageProfile> mList;
        private Context mContext;

        public MessageListAdapter(Context context) {
            mContext = context;
        }

        public void setMessageList(List<MessageProfile> list) {
            mList = list;
        }

        @Override
        public int getCount() {
            return mList.size();
        }

        @Override
        public Object getItem(int position) {
            return mList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder = null;
            if (convertView == null) {
                convertView = LayoutInflater.from(mContext).inflate(R.layout.fragment_message_item, parent, false);
                viewHolder = new ViewHolder();
                viewHolder.mTvMessageContent = (TextView) convertView.findViewById(R.id.tv_messageContent);
                viewHolder.mTvMessageCreateTime = (TextView) convertView.findViewById(R.id.tv_messageCreateTime);
                viewHolder.mTvMessageTitle = (TextView) convertView.findViewById(R.id.tv_messageTitle);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            MessageProfile message = mList.get(position);
            viewHolder.mTvMessageContent.setText(message.getContent());
            viewHolder.mTvMessageTitle.setText(message.getTitle());
            viewHolder.mTvMessageCreateTime.setText(message.getCreateTime());
            return convertView;
        }


        private static class ViewHolder {
            TextView mTvMessageTitle;
            TextView mTvMessageContent;
            TextView mTvMessageCreateTime;
        }
    }
}
