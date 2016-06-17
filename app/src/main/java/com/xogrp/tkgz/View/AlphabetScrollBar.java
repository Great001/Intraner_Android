package com.xogrp.tkgz.View;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.xogrp.tkgz.R;

import java.util.List;

public class AlphabetScrollBar extends View {
    private boolean mPressed;
    private static final int APLHABET_TOTAL = 26;
    private static final int ASCII_A = 65;
    private static final int DEFAULT_MARGIN_BOTH_SIDES = 0;
    private int mCurPosIdx = -1;
    private int mOldPosIdx = -1;
    private OnAlphabetScrollBarTouchListener mTouchListener;
    private List<String> mCurPosIdxList;
    private int mMarginBothSides;

    public AlphabetScrollBar(Context context) {
        this(context, null);
    }

    public AlphabetScrollBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AlphabetScrollBar(Context context, AttributeSet attributeSet, int defaultStyle) {
        super(context, attributeSet, defaultStyle);

        TypedArray ta = context.obtainStyledAttributes(attributeSet, R.styleable.AlphabetScrollBar, defaultStyle, 0);
        mMarginBothSides = ta.getDimensionPixelSize(R.styleable.AlphabetScrollBar_layout_marginBothSides, DEFAULT_MARGIN_BOTH_SIDES);

        ta.recycle();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int width = this.getWidth();
        int singleLetterH = width / APLHABET_TOTAL;

        //change background and the text color
        if (mPressed) {
            canvas.drawColor(getResources().getColor(android.R.color.transparent));
        }

        Paint paint = new Paint();
        int baseline = 0;
        for (int i = 0; i < APLHABET_TOTAL; i++) {
            paint.setColor(getContext().getResources().getColor(R.color.default_text_color));
            paint.setAntiAlias(true);
            paint.setTextSize(getResources().getDimension(R.dimen.photo_wall_alphabetScrollBar_text_size));

            paint.setTextAlign(Paint.Align.CENTER);
            Rect bounds = new Rect();
            paint.getTextBounds(getStringASCII(i + ASCII_A), 0, 1, bounds);
            Paint.FontMetricsInt fontMetricsInt = paint.getFontMetricsInt();
            baseline = (getMeasuredHeight() - fontMetricsInt.bottom + fontMetricsInt.top) / 2 - fontMetricsInt.top;

            if (i == mCurPosIdx) {
                paint.setColor(getContext().getResources().getColor(R.color.focus_text_color));
                paint.setFakeBoldText(true);
            }

            if (mCurPosIdxList != null) {
                int size = mCurPosIdxList.size();
                for (int j = 0; j < size; j++) {
                    if (mCurPosIdxList.get(j).toString().equals(getStringASCII(i + ASCII_A))) {
                        paint.setColor(getContext().getResources().getColor(R.color.focus_text_color));
                        paint.setFakeBoldText(true);
                    }
                }
            }
            canvas.drawText(getStringASCII(i + ASCII_A), singleLetterH * i + singleLetterH, baseline, paint);
            paint.reset();
        }

    }

    private String getStringASCII(int values) {
        return String.valueOf((char) values);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean isTouch = false;
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mPressed = true;
                mCurPosIdx = (int) ((event.getX() + getWidth() / APLHABET_TOTAL / 2) / (this.getWidth() - mMarginBothSides / 3) * APLHABET_TOTAL) - 1;
                if (mCurPosIdx <= -1) {
                    mCurPosIdx = 0;
                }
                if (mTouchListener != null && mOldPosIdx != mCurPosIdx) {
                    if ((mCurPosIdx >= 0) && (mCurPosIdx < APLHABET_TOTAL)) {
                        mTouchListener.onTouch(getStringASCII(ASCII_A + mCurPosIdx));
                        this.invalidate();
                    }
                }
                isTouch = true;
                break;
            case MotionEvent.ACTION_UP:
                mPressed = false;
                mCurPosIdx = -1;
                this.invalidate();
                isTouch = true;
                break;
            case MotionEvent.ACTION_MOVE:
                mCurPosIdx = (int) (event.getX() / (this.getWidth() - mMarginBothSides / 3) * APLHABET_TOTAL);
                if (mTouchListener != null && mCurPosIdx != mOldPosIdx) {
                    if ((mCurPosIdx >= 0) && (mCurPosIdx < APLHABET_TOTAL)) {
                        mTouchListener.onTouch(getStringASCII(ASCII_A + mCurPosIdx));
                        this.invalidate();
                    }
                }
                isTouch = true;
                break;
            default:
                isTouch = super.onTouchEvent(event);
        }
        return isTouch;
    }

    public interface OnAlphabetScrollBarTouchListener {
        void onTouch(String alphabet);
    }

    public void setOnTouchBarListener(OnAlphabetScrollBarTouchListener listener) {
        mTouchListener = listener;
    }

    public void setAlphabetFocus(List<String> curPosIdxList) {
        mCurPosIdxList = curPosIdxList;
        this.invalidate();
    }
}
