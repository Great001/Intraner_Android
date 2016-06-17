package com.xogrp.tkgz.Widget;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xogrp.tkgz.R;
import com.xogrp.tkgz.fragment.PersonnelManageFragment;
import com.xogrp.tkgz.model.PersonnelSectionSwipe;

import java.util.ArrayList;


/**
 * Created by ayu on 10/26/2015 0026.
 */
public class SectionAdapter extends BaseAdapter implements View.OnClickListener {
    private ArrayList<PersonnelSectionSwipe> mSectionList;
    private Context mContext;
    private View mSingleView;
    private PersonnelManageFragment mFrag;
    private boolean mIsClick = true;
    private int mDownX = 0;
    private int mUpX = 0;
    private int mPosition;
    private int mTouchSlop;

    public SectionAdapter(Context context) {
        mContext = context;
    }

    public SectionAdapter(Context context, PersonnelManageFragment mFrag) {
        mContext = context;
        this.mFrag = mFrag;
        ViewConfiguration vc = ViewConfiguration.get(mContext);
        mTouchSlop = vc.getScaledTouchSlop();
    }

    @Override
    public int getCount() {
        return mSectionList.size();
    }

    public void setData(ArrayList<PersonnelSectionSwipe> list) {
        mSectionList = list;
    }

    @Override
    public Object getItem(int position) {
        return mSectionList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View view, final ViewGroup parent) {
        mPosition = position;
        ViewHolder holder = null;
        PersonnelSectionSwipe section = mSectionList.get(position);
        if (view == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.list_section_item, parent, false);
            holder = new ViewHolder();
            holder.mLlFrontView = (LinearLayout) view.findViewById(R.id.ll_frontView);
            holder.mHsvContainer = (HorizontalScrollView) view.findViewById(R.id.hsv_container);
            holder.mLlBehindView = (LinearLayout) view.findViewById(R.id.ll_behindView);
            holder.mTvSectionName = (TextView) view.findViewById(R.id.tv_sectionName);
            holder.mTvAmount = (TextView) view.findViewById(R.id.tv_memberAmount);
            holder.mIvEdit = (ImageView) view.findViewById(R.id.iv_edit);
            holder.mIvDelete = (ImageView) view.findViewById(R.id.iv_delete);
            DisplayMetrics dm = mContext.getResources().getDisplayMetrics();
            LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) holder.mLlFrontView.getLayoutParams();
            lp.width = dm.widthPixels;
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        holder.mTvSectionName.setText(section.getSectionName());
        holder.mTvAmount.setText(String.format("%s%s", section.getMemberAmount(), mContext.getResources().getString(R.string.txt_people)));
        holder.mLlFrontView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        mDownX = (int) event.getX();
                        if (mSingleView != null && ((ViewHolder) mSingleView.getTag()).mLlFrontView != v) {
                            ViewHolder viewHolder = (ViewHolder) mSingleView.getTag();
                            viewHolder.mHsvContainer.smoothScrollTo(0, 0);
                            mSingleView = null;
                            return true;
                        } else if (mSingleView != null) {
                            return true;
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        mUpX = (int) event.getX();
                        if (mSingleView != null && ((ViewHolder) mSingleView.getTag()).mLlFrontView == v && (Math.abs(mDownX - mUpX) <= mTouchSlop)) {
                            ViewHolder viewHolder = (ViewHolder) mSingleView.getTag();
                            viewHolder.mHsvContainer.smoothScrollTo(0, 0);
                            mSingleView = null;
                            return true;
                        }
                        break;
                    default:
                        break;
                }
                return false;
            }
        });


        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_UP:
                        ViewHolder viewHolder = (ViewHolder) v.getTag();
                        int scrollX = viewHolder.mHsvContainer.getScrollX();
                        int actionW = viewHolder.mLlBehindView.getWidth();

                        if (scrollX < actionW / 2) {
                            viewHolder.mHsvContainer.smoothScrollTo(0, 0);
                        } else {
                            mSingleView = v;
                            viewHolder.mHsvContainer.smoothScrollTo(actionW, 0);
                        }
                        return true;
                    default:
                        break;
                }
                return false;
            }
        });

        parent.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        if (mSingleView != null) {
                            ViewHolder viewHolder = (ViewHolder) mSingleView.getTag();
                            viewHolder.mHsvContainer.smoothScrollTo(0, 0);
                            mSingleView = null;
                            return true;
                        }
                        break;
                    default:
                        break;
                }
                return false;
            }
        });

        holder.mLlFrontView.setOnClickListener(this);
        holder.mIvEdit.setOnClickListener(this);
        holder.mIvDelete.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        mIsClick = true;
        if (mSingleView != null) {
            mIsClick = false;
            ViewHolder viewHolder = (ViewHolder) mSingleView.getTag();
            viewHolder.mHsvContainer.smoothScrollTo(0, 0);
            mSingleView = null;
        }
        switch (v.getId()) {
            case R.id.iv_edit:
                mFrag.editSectionName(mPosition);
                break;
            case R.id.iv_delete:
                mFrag.deleteSection(mPosition);
                break;
            case R.id.ll_frontView:
                if (mIsClick) {
                    mFrag.navigateToPersonnelPage(mPosition);
                }
                break;
            default:
                break;
        }
    }

    private int calculateDistanceX() {
        return (mDownX - mUpX) < 0 ? (mUpX - mDownX) : (mDownX - mUpX);
    }


    private static class ViewHolder {
        private HorizontalScrollView mHsvContainer;
        private LinearLayout mLlFrontView;
        private LinearLayout mLlBehindView;
        private ImageView mIvEdit;
        private ImageView mIvDelete;
        private TextView mTvSectionName;
        private TextView mTvAmount;
    }
}
