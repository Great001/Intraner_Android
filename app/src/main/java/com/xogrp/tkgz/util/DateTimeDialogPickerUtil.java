package com.xogrp.tkgz.util;

import android.app.AlertDialog;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;
import android.widget.TimePicker;
import com.xogrp.tkgz.R;
import java.lang.reflect.Field;
import java.util.Calendar;

/**
 * Created by hliao on 6/7/2016.
 */
public class DateTimeDialogPickerUtil {
    public static String sDateTimeStrFormat;

    public static void showDateTimePickerDialog(final Context context, int flag, final TextView tvSetTime) {
        sDateTimeStrFormat = "";
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.date_time_pickerdialog_layout, null);
        TextView tvTitle = (TextView) view.findViewById(R.id.tv_dialog_title);
        tvTitle.setText(context.getString(flag == 0 ? R.string.date_time_dialogtitle_start : R.string.date_time_dialogtitle_end));

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        final AlertDialog dialog = builder.create();
        dialog.show();
        dialog.getWindow().setContentView(view);

        TabHost tabHost = (TabHost) view.findViewById(R.id.th_date_time_picker);
        tabHost.setup();
        tabHost.addTab(tabHost.newTabSpec("date").setIndicator(context.getString(R.string.tab_datepicker), null).setContent(R.id.ll_tab_date));
        tabHost.addTab(tabHost.newTabSpec("time").setIndicator(context.getString(R.string.tab_timepicker), null).setContent(R.id.ll_tab_time));

        int count = tabHost.getTabWidget().getChildCount();
        TabWidget tabWidget = tabHost.getTabWidget();
        for (int i = 0; i < count; i++) {
            TextView tvTabTitle = (TextView) tabWidget.getChildAt(i).findViewById(android.R.id.title);
            tvTabTitle.setTextSize(15);
            tvTabTitle.setTextColor(Color.BLACK);
            tvTabTitle.setGravity(Gravity.CENTER);
            tabWidget.getChildAt(i).setBackgroundResource(R.drawable.tab_indicator_color);
        }

        final Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        final DatePicker datePicker = (DatePicker) view.findViewById(R.id.dp_date);
        final TimePicker timePicker = (TimePicker) view.findViewById(R.id.tp_time);
        datePicker.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), null);
        timePicker.setCurrentHour(calendar.get(Calendar.HOUR_OF_DAY));
        timePicker.setCurrentMinute(calendar.get(Calendar.MINUTE));

        findNumberPickerandSetDivider(datePicker, context);
        findNumberPickerandSetDivider(timePicker,context);

        Button btnConfirm = (Button) view.findViewById(R.id.btn_dialog_comfirm);
        Button btnCancel = (Button) view.findViewById(R.id.btn_dialog_cancel);


        btnConfirm.setOnClickListener(new View.OnClickListener() {
            int year, month, day, hour, minute;
            String AM_PM;

            @Override
            public void onClick(View v) {
                timePicker.setIs24HourView(true);
                int hour_24 = timePicker.getCurrentHour();
                AM_PM = (hour_24 > 12) ? "PM" : "AM";
                timePicker.setIs24HourView(false);
                year = datePicker.getYear();
                month = datePicker.getMonth() + 1;
                day = datePicker.getDayOfMonth();
                hour = timePicker.getCurrentHour();
                minute = timePicker.getCurrentMinute();

                String strHour, strMin;
                strHour = hour > 9 ? String.format("%d", hour) : String.format("0%d", hour);
                strMin = minute > 9 ? String.format("%d", minute) : String.format("0%d", minute);
                sDateTimeStrFormat = String.format("%d/%d/%d %s:%s %S", month, day, year, strHour, strMin, AM_PM);
                if (tvSetTime != null) {
                    tvSetTime.setText(DateTimeDialogPickerUtil.sDateTimeStrFormat);
                }
                dialog.dismiss();
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    public static void  findNumberPickerandSetDivider(ViewGroup viewGroup,Context context){
        NumberPicker numberPicker;
        View vChildLl,vChildNp;
        if(viewGroup!=null) {
            LinearLayout layout = (LinearLayout) viewGroup.getChildAt(0);
            for (int i = 0; i < 2; i++) {
                vChildLl = layout.getChildAt(i);
                if (vChildLl instanceof NumberPicker) {
                    numberPicker = (NumberPicker) vChildLl;
                    setNumberPickerDividerColor(numberPicker, context);
                }
                else if(vChildLl instanceof LinearLayout){
                    int count=((LinearLayout) vChildLl).getChildCount();
                    for(int j=0;j<count;j++){
                        vChildNp=((LinearLayout)vChildLl).getChildAt(j);
                        if(vChildNp instanceof NumberPicker){
                            numberPicker = (NumberPicker) vChildNp;
                            setNumberPickerDividerColor(numberPicker, context);
                        }
                    }
                }
            }
        }
    }


    public static void setNumberPickerDividerColor(NumberPicker numberPicker, Context context) {
        NumberPicker picker = numberPicker;
        Field[] pickerFields = NumberPicker.class.getDeclaredFields();
        for (Field pf : pickerFields) {
            if (pf.getName().equals("mSelectionDivider")) {
                pf.setAccessible(true);
                try {
                    pf.set(picker, new ColorDrawable(context.getResources().getColor(R.color.c_line_color)));
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (Resources.NotFoundException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                break;
            }
        }
    }

}
