package com.xogrp.tkgz.View;

import android.animation.LayoutTransition;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xogrp.tkgz.R;
import com.xogrp.tkgz.Widget.CalendarAdapter;

import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

public class CalendarView extends LinearLayout {

    // current displayed month
    private Calendar mCurrentDate;

    //event handling
    private EventHandler mEventHandler;

    // internal components
    private ImageView mIvPrev;
    private ImageView mIvNext;
    private TextView mTvDateText;
    private GridView mGvCalendar;
    private LinearLayout mLlFocus;
    private LinearLayout mLlCalendarCurrentText;
    private TextView mTvCalendarCurrentText;
    private Set<Date> mEventSet;
    private Set<Date> mEnrollStatusSet;
    private Set<Date> mEventStatusSet;
    private final SimpleDateFormat mFormat = new SimpleDateFormat(getResources().getString(R.string.calendar_date_format), Locale.US);
    private CalendarAdapter mAdapter;

    public CalendarView(Context context) {
        super(context);
    }

    public CalendarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initCreateView(context);
    }

    public CalendarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initCreateView(context);
    }

    private void initCreateView(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.tkgz_calendar_layout, this);
        mCurrentDate = Calendar.getInstance();
        mEventSet = new HashSet<>();
        mEnrollStatusSet = new HashSet<>();
        mEventStatusSet = new HashSet<>();

        assignUiElements();
        assignClickHandlers();
        updateCalendar();
    }

    private void assignUiElements() {
        mIvPrev = (ImageView) findViewById(R.id.iv_calendar_prev);
        mIvNext = (ImageView) findViewById(R.id.iv_calendar_next);
        mTvDateText = (TextView) findViewById(R.id.tv_calendar_date_display);
        mGvCalendar = (GridView) findViewById(R.id.gv_calendar);
        mLlFocus = (LinearLayout) findViewById(R.id.ll_focus);
        FrameLayout frameLayout = (FrameLayout) findViewById(R.id.fl_calendar);
        enableChangeTransition(frameLayout.getLayoutTransition());
        enableChangeTransition(mLlFocus.getLayoutTransition());
        mLlCalendarCurrentText = (LinearLayout) findViewById(R.id.ll_calendar_current_text);
        mTvCalendarCurrentText = (TextView) findViewById(R.id.tv_calendar_current_text);
        mAdapter = new CalendarAdapter(getContext());
        mGvCalendar.setAdapter(mAdapter);
    }

    public void enableChangeTransition(LayoutTransition layoutTransition) {
        if (layoutTransition != null) {
            try {
                Method method = LayoutTransition.class.getMethod(getResources().getString(R.string.calendar_transition), new Class[]{int.class});
                Field field = LayoutTransition.class.getField(getResources().getString(R.string.calendar_transition_type));
                method.invoke(layoutTransition, field.get(null));
            } catch (Exception e) {
                LoggerFactory.getLogger("CalendarView").error("CalendarView: enableChangeTransition",e.getMessage());
            }
        }
    }

    private void assignClickHandlers() {
        // add one month and refresh UI
        mIvNext.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                calendarChangePage(1);
                mEventHandler.onDayNext(mAdapter.getCalendarDafaultDate(1));
            }
        });

        // subtract one month and refresh UI
        mIvPrev.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                calendarChangePage(-1);
                mEventHandler.onDayPrev(mAdapter.getCalendarDafaultDate(-1));
            }
        });

        // short-pressing a day
        mGvCalendar.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
                if (mEventHandler != null && view.getTag(R.string.calendar_tag) == true && !mLlFocus.getLayoutTransition().isRunning()) {
                    mLlFocus.layout(view.getLeft(), view.getTop(), view.getRight(), view.getBottom());
                    mLlCalendarCurrentText.layout(view.getLeft(), view.getTop(), view.getRight(), view.getBottom());
                    mTvCalendarCurrentText.setText(((TextView) view.findViewById(R.id.tv_calendar_text)).getText());
                    mEventHandler.onDayShortPress((Date) parent.getItemAtPosition(position));
                }
            }
        });
    }

    public void calendarChangePage(int nextPage) {
        mLlFocus.layout(-50, -50, 0, 0);
        mLlCalendarCurrentText.layout(-50, -50, 0, 0);
        mCurrentDate.add(Calendar.MONTH, nextPage);
        updateCalendar();
    }

    /**
     * Display dates correctly in mGvCalendar
     */
    public void updateCalendar() {
        updateCalendar(mEventSet, mEnrollStatusSet, mEventStatusSet);
    }

    /**
     * Display dates correctly in mGvCalendar
     */
    public void updateCalendar(Set<Date> eventSet, Set<Date> enrollStatusSet, Set<Date> eventStatusSet) {
        ArrayList<Date> cells = new ArrayList<>();
        Calendar calendar = (Calendar) mCurrentDate.clone();
        int dayCount = calendar.getActualMaximum(Calendar.WEEK_OF_MONTH) * 7;

        // determine the cell for current month's beginning
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        int monthBeginningCell = calendar.get(Calendar.DAY_OF_WEEK) - 1;

        // move calendar backwards to the beginning of the week
        calendar.add(Calendar.DAY_OF_MONTH, -monthBeginningCell);

        // fill cells
        while (cells.size() < dayCount) {
            cells.add(calendar.getTime());
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }

        if (!eventSet.isEmpty() && !enrollStatusSet.isEmpty() && !eventStatusSet.isEmpty()) {
            mEventSet = eventSet;
            mEnrollStatusSet = enrollStatusSet;
            mEventStatusSet = eventStatusSet;
        }

        // update mGvCalendar
        mAdapter.updateAdapter(cells, mCurrentDate.getTime(), mEventSet, mEnrollStatusSet, mEventStatusSet);
        mAdapter.notifyDataSetChanged();
        // update title
        mTvDateText.setText(mFormat.format(mCurrentDate.getTime()));
    }

    /**
     * Assign event handler to be passed needed events
     */
    public void setEventHandler(EventHandler eventHandler) {
        this.mEventHandler = eventHandler;
    }

    /**
     * This interface defines what events to be reported to
     * the outside world
     */
    public interface EventHandler {
        void onDayShortPress(Date date);
        void onDayPrev(Date date);
        void onDayNext(Date date);
    }
}
