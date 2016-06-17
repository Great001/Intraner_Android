package com.xogrp.tkgz.View;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xogrp.tkgz.R;
import com.xogrp.tkgz.Widget.TKGZCustomDialog;
import com.xogrp.tkgz.Widget.TKGZCustomEditDialog;
import com.xogrp.tkgz.model.PersonnelSectionSwipe;

public class PersonnelSwipeView extends LinearLayout
        implements View.OnTouchListener, OnGestureListener {
    private static final int HORIZONTAL_TOUCH_OFFSET = 20;
    private int mLastX, mBackViewWidth;
    private LinearLayout mLlBackView;
    private RelativeLayout mRlFrontView;
    private ImageView mIvEdit, mIvDelete;
    private boolean mIsBackViewShow;
    private boolean mIsSwipeAble = true;
    private TextView mTvName, mTvPersonnelCount;
    private GestureDetector mDetector = null;
    private OnPersonnelSectionButtonClickListener mOnPersonnelSectionClickListener;
    private OnClickListener mFrontViewClickListener;

    public PersonnelSwipeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        inflate(context, R.layout.personnel_swipe_item, this);
    }

    public void initView(final PersonnelSectionSwipe personnelSectionSwipe) {
        mDetector = new GestureDetector(getContext(), this);
        mLlBackView = (LinearLayout) findViewById(R.id.ll_personnel_back);
        mIvEdit = (ImageView) findViewById(R.id.iv_edit);
        mIvDelete = (ImageView) findViewById(R.id.iv_delete);

        mRlFrontView = (RelativeLayout) findViewById(R.id.rl_personnel_front);
        mTvName = (TextView) findViewById(R.id.tv_sectionName);
        mTvPersonnelCount = (TextView) findViewById(R.id.tv_memberAmount);
        mRlFrontView.setOnTouchListener(this);
        mTvName.setText(personnelSectionSwipe.getSectionName());
        mTvPersonnelCount.setText(String.valueOf(personnelSectionSwipe.getMemberAmount()));

        mIvEdit.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                mOnPersonnelSectionClickListener.OnEditImageViewClick(personnelSectionSwipe.getSectionId());
                TKGZCustomEditDialog dialog = new TKGZCustomEditDialog(getContext(),
                        R.string.dialog_edit_department,
                        mTvName.getText().toString().trim(),
                        R.string.dialog_finish,
                        R.string.dialog_cancel,
                        new TKGZCustomDialog.OnDialogActionCallback() {
                            @Override
                            public void onConfirmSelected() {
                                //request
                            }
                        }, null);
                dialog.show();
            }

        });

        mIvDelete.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                mOnPersonnelSectionClickListener.OnDeleteImageViewClick(personnelSectionSwipe.getSectionId());
                TKGZCustomDialog dialog = new TKGZCustomDialog(getContext(),
                        R.string.dialog_warning,
                        R.string.dialog_delete_department, new TKGZCustomDialog.OnDialogActionCallback() {
                    @Override
                    public void onConfirmSelected() {
                        // request
                    }
                }, true);
                dialog.show();
            }

        });
    }

    public void setOnPersonnelSectionClickListener(OnPersonnelSectionButtonClickListener onPersonnelSectionClickListener) {
        mOnPersonnelSectionClickListener = onPersonnelSectionClickListener;
    }

    public void setOnFrontViewClickListener(OnClickListener onClickListener) {
        mFrontViewClickListener = onClickListener;
    }

    private int mFrontRight, mFrontLeft;
    private OnTouchListener mFrontViewTouchListener = new OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if (mIsSwipeAble) {
                int action = event.getAction();
                if (action == MotionEvent.ACTION_MOVE) {
                    int frontDx = mLastX - (int) event.getRawX();
                    int currentFrontLeft = mFrontLeft - frontDx;
                    int currentFrontRight = mFrontRight - frontDx;
                    if (currentFrontLeft < 0) {
                        mRlFrontView.layout(currentFrontLeft,
                                mRlFrontView.getTop(), currentFrontRight,
                                mRlFrontView.getBottom());
                        getParent().requestDisallowInterceptTouchEvent(true);
                    }
                }
            }
            return false;
        }
    };

    @Override
    public boolean onDown(MotionEvent event) {
        mLastX = (int) event.getRawX();
        mBackViewWidth = mIvEdit.getWidth() + mIvDelete.getWidth();
        mFrontRight = mRlFrontView.getRight();
        mFrontLeft = mRlFrontView.getLeft();
        return true;
    }

    @Override
    public void onShowPress(MotionEvent e) {
    }

    @Override
    public boolean onSingleTapUp(MotionEvent event) {
        boolean isEventConsumed = false;
        if (mFrontViewClickListener != null) {
            mFrontViewClickListener.onClick(mRlFrontView);
            isEventConsumed = true;
        }
        return isEventConsumed;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
                            float distanceY) {
        if (Math.abs(e1.getRawX() - e2.getRawX()) > HORIZONTAL_TOUCH_OFFSET) {
            mFrontViewTouchListener.onTouch(mRlFrontView, e2);
            return true;
        }
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
                           float velocityY) {
        return false;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            if (mIsSwipeAble) {
                int frontDxUp = mLastX - (int) event.getRawX();
                if (frontDxUp > mBackViewWidth / 2
                        || (frontDxUp > 0 && mIsBackViewShow)) {
                    mRlFrontView.layout(-mBackViewWidth, mRlFrontView.getTop(),
                            mIvEdit.getLeft() + (int) mLlBackView.getX(), mRlFrontView.getBottom());
                    mIsBackViewShow = true;
                } else if (frontDxUp != 0) {
                    mRlFrontView.layout((int) mLlBackView.getX(), mRlFrontView.getTop(),
                            mLlBackView.getRight(), mRlFrontView.getBottom());
                    mIsBackViewShow = false;
                }
            }
        }
        return mDetector.onTouchEvent(event);
    }

    public interface OnPersonnelSectionButtonClickListener {
        void OnEditImageViewClick(long sectionId);

        void OnDeleteImageViewClick(long sectionId);
    }

}
