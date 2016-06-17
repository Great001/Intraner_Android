package com.xogrp.tkgz.View;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.xogrp.tkgz.R;
import com.xogrp.tkgz.adapter.ViewPagerAdapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by jdeng on 5/20/2016.
 */
public class CalendarDataDialog extends Dialog implements View.OnClickListener{
    private View mVCalender;
    private View mVTime;
    private NumberPicker mNpYear;
    private NumberPicker mNpMonth;
    private NumberPicker mNpDay;
    private NumberPicker mNpHour;
    private NumberPicker mNpMinute;
    private NumberPicker mNpPmOrAm;
    private Context mContext;
    private TextView mTvTitle;
    private TextView mTvCalendar;
    private TextView mTvTime;
    private ViewPager mVpDateOrTime;
    private Button mBtnCancel;
    private Button mBtnSure;
    private LinearLayout mLlLine1;
    private LinearLayout mLlLine2;
    private String[] mMonthArray;
    private String[] mDayArray=new String[31];
    private String[] mHourArray;
    private String[] mMinuteArray=new String[60];
    private String[] mAmOrPmArray;
    private List<View> mViewList=new ArrayList<>();
    private ViewPagerAdapter mVpAdapter;
    private String mTitle;
    private Calendar mCalendar;
    private CalendarAndTimeListener mCalendarAndTimeListener;

    public CalendarDataDialog(Context context, String title,CalendarAndTimeListener calendarandtimelistener) {
        super(context);
        this.mContext=context;
        this.mCalendarAndTimeListener=calendarandtimelistener;
        this.mTitle=title;
    }

    public CalendarDataDialog(Context context, int themeResId) {
        super(context, themeResId);
        this.mContext=context;
    }

    protected CalendarDataDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        this.mContext=context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dateandtime_layout);
        mTvTitle= (TextView) findViewById(R.id.tv_title);
        mTvTitle.setText(mTitle);
        mTvCalendar= (TextView) findViewById(R.id.tv_data);
        mTvTime= (TextView) findViewById(R.id.tv_time);
        mVpDateOrTime= (ViewPager) findViewById(R.id.vp_data_time);
        mBtnCancel= (Button) findViewById(R.id.btn_cancel);
        mBtnSure= (Button) findViewById(R.id.btn_sure);
        mLlLine1= (LinearLayout) findViewById(R.id.ll_line1);
        mLlLine2= (LinearLayout) findViewById(R.id.ll_line2);
        mCalendar=Calendar.getInstance();
        initCalendar();
        initTime();
        setListener();
        mViewList.add(mVCalender);
        mViewList.add(mVTime);
        mVpAdapter=new ViewPagerAdapter(mViewList);
        mVpDateOrTime.setAdapter(mVpAdapter);
        mVpDateOrTime.setCurrentItem(0);
    }
    public void setDialogTitle(String title){
        mTvTitle.setText(title);
    }

    void initCalendar(){
        mMonthArray=mContext.getResources().getStringArray(R.array.month_chinese);
        for (int i=0;i<31;++i){
            mDayArray[i]=String.valueOf(i+1);
        }
        mVCalender=LayoutInflater.from(mContext).inflate(R.layout.calendar_layout,null);
        mNpYear= (NumberPicker) mVCalender.findViewById(R.id.np_year);
        mNpMonth= (NumberPicker) mVCalender.findViewById(R.id.np_month);
        mNpDay= (NumberPicker)mVCalender.findViewById(R.id.np_day);
        mNpMonth.setDisplayedValues(mMonthArray);
        mNpDay.setDisplayedValues(mDayArray);
        initNumberPicker(mNpYear,2969,1969,mCalendar.get(Calendar.YEAR));
        initNumberPicker(mNpMonth,11,0,mCalendar.get(Calendar.MONTH));
        initNumberPicker(mNpDay,30,0,mCalendar.get(Calendar.DAY_OF_MONTH)-1);
        mNpMonth.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                if (newVal == 1) {
                    int number = mNpYear.getValue();
                    if ((number % 4 == 0 && number % 100 != 0) || (number % 400 == 0)) {
                        mNpDay.setMaxValue(28);
                    } else {
                        mNpDay.setMaxValue(27);
                    }

                } else if (newVal == 3 || newVal == 5 || newVal == 8 || newVal == 10) {
                    mNpDay.setMaxValue(29);
                }
            }
        });
        mNpYear.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                int number = mNpYear.getValue();
                if (mNpMonth.getValue()==1){
                    if ((number % 4 == 0 && number % 100 != 0) || (number % 400 == 0)) {
                        mNpDay.setMaxValue(28);
                    } else {
                        mNpDay.setMaxValue(27);
                    }
                }
            }
        });
    }
    public String getCalendar(){
         String dataStr="%d/%d/%d";
        return String.format(dataStr,mNpMonth.getValue()+1,mNpDay.getValue()+1,mNpYear.getValue());
    }

    public void initTime(){
        mHourArray=mContext.getResources().getStringArray(R.array.hours_in_day);
        for (int i=0;i<60;++i){
            mMinuteArray[i]=i>9?String.valueOf(i):("0"+String.valueOf(i));
        }
        mAmOrPmArray=mContext.getResources().getStringArray(R.array.AmOrPm);
        mVTime=LayoutInflater.from(mContext).inflate(R.layout.time_layout, null);
        mNpHour= (NumberPicker) mVTime.findViewById(R.id.np_hour);
        mNpMinute= (NumberPicker) mVTime.findViewById(R.id.np_minute);
        mNpPmOrAm= (NumberPicker) mVTime.findViewById(R.id.np_PmOrAm);
        mNpHour.setDisplayedValues(mHourArray);
        mNpPmOrAm.setDisplayedValues(mAmOrPmArray);
        mNpMinute.setDisplayedValues(mMinuteArray);
        initNumberPicker(mNpHour,11,0,mCalendar.get(Calendar.HOUR)-1);
        initNumberPicker(mNpMinute,59,0,mCalendar.get(Calendar.MINUTE));
        initNumberPicker(mNpPmOrAm,1,0,mCalendar.get(Calendar.AM_PM));
    }

    public String getTime(){
        String timeStr="%s:%s %s";
        return String.format(timeStr, mHourArray[mNpHour.getValue()],mMinuteArray[ mNpMinute.getValue()], mAmOrPmArray[mNpPmOrAm.getValue()]);
    }

    public void setListener(){
        mTvTime.setOnClickListener(this);
        mTvCalendar.setOnClickListener(this);
        mBtnSure.setOnClickListener(this);
        mBtnCancel.setOnClickListener(this);
    }



    @Override
    public void onClick(View v) {
        String str="%s %s";
        switch (v.getId()){
            case R.id.btn_cancel:{
                dismiss();
                break;
            }
            case R.id.btn_sure:{
                mCalendarAndTimeListener.GetCalendarAndTime(String.format(str,getCalendar(),getTime()));
                dismiss();
                break;
            }
            case R.id.tv_time:{
                mVpDateOrTime.setCurrentItem(1);
                mLlLine1.setVisibility(View.INVISIBLE);
                mLlLine2.setVisibility(View.VISIBLE);
                break;
            }
            case R.id.tv_data:{
                mVpDateOrTime.setCurrentItem(0);
                mLlLine1.setVisibility(View.VISIBLE);
                mLlLine2.setVisibility(View.INVISIBLE);
                break;
            }
        }
    }

    public interface CalendarAndTimeListener{
       public void GetCalendarAndTime(String string);
    }

    void initNumberPicker(NumberPicker numberPicker,int max,int min,int current){
        numberPicker.setMaxValue(max);
        numberPicker.setMinValue(min);
        numberPicker.setValue(current);

    }


}
