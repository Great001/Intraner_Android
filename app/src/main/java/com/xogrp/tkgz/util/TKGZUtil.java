package com.xogrp.tkgz.util;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.TimePicker;

import com.xogrp.tkgz.R;
import com.xogrp.tkgz.activity.MainActivity;
import com.xogrp.tkgz.listener.DatePickerController;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by ayu on 12/2/2015 0002.
 */
public class TKGZUtil {
    private final static long ONE_DAY_MILLISECONDS = 24 * 60 * 60 * 1000;
    private final static String DATE_FORMAT_STRING = "yyyy-MM-dd";
    private final static String DATE_TIME_FORMAT_STRING = "yyyy-MM-dd HH:mm";
    private final static SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat(DATE_FORMAT_STRING, Locale.getDefault());
    private final static SimpleDateFormat SIMPLE_DATE_TIME_FORMAT = new SimpleDateFormat(DATE_TIME_FORMAT_STRING, Locale.getDefault());

    private final static int[] LEVEL_IMAGES = {R.drawable.icon_metal5, R.drawable.icon_metal4,
            R.drawable.icon_metal3, R.drawable.icon_metal2, R.drawable.icon_metal1};
    private static final int[] LEVEL_APPELLATIONS = {R.string.level_one, R.string.level_two,
            R.string.level_three, R.string.level_four, R.string.level_five};

    private static final int[] FORE_COLORS = {R.color.event_status_0, R.color.event_status_1,
            R.color.event_status_2, R.color.event_status_3, R.color.event_status_4,
            R.color.event_status_5};
    private static final int[] BACK_COLORS = {R.color.event_status_background_0,
            R.color.event_status_background_1, R.color.event_status_background_2,
            R.color.event_status_background_3, R.color.event_status_background_4,
            R.color.event_status_background_5};


    public static int getForeColor(int index) {
        return FORE_COLORS[index];
    }

    public static int getBackColor(int index) {
        return BACK_COLORS[index];
    }

    public static int getLevelImage(int index) {
        return LEVEL_IMAGES[index];
    }

    public static int getLevelAppellation(int index) {
        return LEVEL_APPELLATIONS[index];
    }

    public static int getLevel(int score) {
        return (score < 6800) ? 0 :
                (score < 13800) ? 1 :
                        (score < 21800) ? 2 :
                                (score < 31800) ? 3 : 4;
    }

    public static void setDateDialog(final int viewId, Context context, final DatePickerController pickerController) {
        int year = 0, monthOfYear = 0, dayOfMonth = 0;
        final Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        monthOfYear = calendar.get(Calendar.MONTH);
        dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                calendar.clear();
                calendar.set(year, monthOfYear, dayOfMonth);
                pickerController.setDateString(viewId, calendar.getTimeInMillis());
            }
        }, year, monthOfYear, dayOfMonth);
        datePickerDialog.setCanceledOnTouchOutside(false);
        datePickerDialog.getDatePicker().setDescendantFocusability(DatePicker.FOCUS_BLOCK_DESCENDANTS);
        datePickerDialog.show();
    }

    // TODO: 11/27/2015 0027 maybe useless
    public static void setDataDialogWithoutDay(final int viewId, final Context context, final DatePickerController pickerController) {
        int year = 0, monthOfYear = 0, dayOfMonth = 0;
        final Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        monthOfYear = calendar.get(Calendar.MONTH);
        dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                calendar.clear();
                calendar.set(year, monthOfYear, 0);
                pickerController.setDateString(viewId, calendar.getTimeInMillis());
            }
        }, year, monthOfYear, dayOfMonth);
        ((ViewGroup) datePickerDialog.getDatePicker()).findViewById(Resources.getSystem().getIdentifier("day", "id", "android")).setVisibility(View.GONE);
        datePickerDialog.setCanceledOnTouchOutside(false);
        datePickerDialog.getDatePicker().setDescendantFocusability(DatePicker.FOCUS_BLOCK_DESCENDANTS);
        datePickerDialog.show();
    }

    public static String getStringDate(long millisSecond) {
        return SIMPLE_DATE_FORMAT.format(new Date(millisSecond));
    }

    public static String getStringDateTime(long millisSecond) {
        return SIMPLE_DATE_TIME_FORMAT.format(new Date(millisSecond));
    }

    public static long getDaysSinceUTC(long milliseconds) {
        milliseconds += ONE_DAY_MILLISECONDS / 24 * 8;
        return (milliseconds / ONE_DAY_MILLISECONDS);
    }

}

