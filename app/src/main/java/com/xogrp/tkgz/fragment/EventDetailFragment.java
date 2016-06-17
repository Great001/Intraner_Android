package com.xogrp.tkgz.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.xogrp.tkgz.R;
import com.xogrp.tkgz.TKGZApplication;
import com.xogrp.tkgz.listener.OnRefreshUIListener;
import com.xogrp.tkgz.model.EventProfile;
import com.xogrp.tkgz.model.IntegralTypeProfile;
import com.xogrp.tkgz.model.PeopleProfile;
import com.xogrp.tkgz.model.UserProfile;
import com.xogrp.tkgz.provider.CheckInEventProvider;
import com.xogrp.tkgz.provider.EnrollEventProvider;
import com.xogrp.tkgz.provider.EventProvider;
import com.xogrp.tkgz.spi.CheckInEventApiCallBack;
import com.xogrp.tkgz.spi.EnrollEventApiCallBack;
import com.xogrp.tkgz.spi.EventDetailApiCallBack;
import com.xogrp.tkgz.util.TKGZUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lzhou on 2015/8/7.
 */
public class EventDetailFragment extends AbstractTKGZFragment {
    private int mDefaultHeight;
    private boolean mClickToEnrollEvent;
    private String mEventId;
    private Button mBtnEnrollOrCheckIn;
    private TextView mTvEnroll;
    private TextView mTvTitle;
    private TextView mTvInitiator;
    private TextView mTvEventType;
    private TextView mTvIntegralType;
    private TextView mTvAddress;
    private TextView mTvRecipients;
    private TextView mTvLecturer;
    private TextView mTvTxtLecturer;
    private TextView mTvEnrollTime;
    private TextView mTvEventTime;
    private TextView mTvContent;
    private TextView mTvCount;
    private TextView mTvTrainStatus;
    private EventProfile mEventProfile;
    private boolean mHasMenu;
    private final static String BUNDLE_KEY_EVENT_ID = "eventId";
    private final static String BUNDLE_KEY_HAS_MENU = "has_menu";
    private OnUpdateEventStatusListener mOnUpdateEventStatusListener;
    private OnRefreshUIListener mOnRefreshUIListener;


    public static EventDetailFragment newInstance(OnUpdateEventStatusListener onUpdateEventStatusListener, String eventId, boolean has_menu) {
        EventDetailFragment fragment = new EventDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putBoolean(BUNDLE_KEY_HAS_MENU, has_menu);
        bundle.putString(BUNDLE_KEY_EVENT_ID, eventId);
        fragment.setArguments(bundle);
        if (onUpdateEventStatusListener != null) {
            fragment.setOnUpdateEventStatusListener(onUpdateEventStatusListener);
        }
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
        return R.layout.fragment_event_detail;
    }

    @Override
    protected void onTKGZCreateView(final View rootView) {
        mTvTrainStatus = (TextView) rootView.findViewById(R.id.tv_event_status);
        mTvTitle = (TextView) rootView.findViewById(R.id.tv_event_title);
        mTvInitiator = (TextView) rootView.findViewById(R.id.tv_initiator);
        mTvEventType = (TextView) rootView.findViewById(R.id.tv_event_type);
        mTvEnrollTime = (TextView) rootView.findViewById(R.id.tv_enroll_time);
        mTvEventTime = (TextView) rootView.findViewById(R.id.tv_event_time);
        mTvRecipients = (TextView) rootView.findViewById(R.id.tv_recipients);
        mTvContent = (TextView) rootView.findViewById(R.id.tv_train_content);
        mTvAddress = (TextView) rootView.findViewById(R.id.tv_address);
        mTvCount = (TextView) rootView.findViewById(R.id.tv_enroll_count);
        mBtnEnrollOrCheckIn = (Button) rootView.findViewById(R.id.btn_registration);
        mTvLecturer = (TextView) rootView.findViewById(R.id.tv_lecturer);
        mTvIntegralType = (TextView) rootView.findViewById(R.id.tv_integral_type);
        mTvTxtLecturer = (TextView) rootView.findViewById(R.id.tv_txt_lecturer);
        mTvEnroll = (TextView) rootView.findViewById(R.id.tv_txt_enroll);
    }

    @Override
    protected void onTKGZActivityCreated() {
        mBtnEnrollOrCheckIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mClickToEnrollEvent) {
                    setEnrollListener();
                } else {
                    setCheckInListener();
                }
            }
        });
        if (getArguments() != null) {
            mHasMenu = getArguments().getBoolean(BUNDLE_KEY_HAS_MENU);
            mEventId = getArguments().getString(BUNDLE_KEY_EVENT_ID);
            mTvRecipients.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LinearLayout.LayoutParams param = (LinearLayout.LayoutParams) mTvRecipients.getLayoutParams();
                    if (mTvRecipients.getLineCount() == 1) {
                        mTvRecipients.setSingleLine(false);
                        param.height = LinearLayout.LayoutParams.WRAP_CONTENT;
                        mTvRecipients.setLayoutParams(param);
                    } else {
                        mTvRecipients.setSingleLine(true);
                        param.height = mDefaultHeight;
                        mTvRecipients.setLayoutParams(param);
                    }
                }
            });
            getEventProfile();
        }
    }

    private void getEventProfile() {
        showSpinner();
        initLoader(EventProvider.getEventDetailProvider(TKGZApplication.getInstance().getUserProfile(),
                mEventId, this, new EventDetailApiCallBack.OnEventDetailApiListener() {
                    @Override
                    public void onGetEventDetail(final EventProfile eventProfile) {
                        if (eventProfile != null) {
                            final Activity activity = getActivity();
                            if (activity != null) {
                                activity.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        mEventProfile = eventProfile;
                                        onRefreshUI();
                                        mOnRefreshUIListener.callRefreshIntegral();
                                    }
                                });
                            }
                        }
                    }

                    @Override
                    public void onGetEventFailed(final String message) {
                        final Activity activity = getActivity();
                        if (activity != null) {
                            activity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    showMessage(message);
                                }
                            });
                        }
                    }
                }));
    }

    private void onRefreshUI() {
        int statusCode = mEventProfile.getStatus();
        if (statusCode == 1 && !mEventProfile.isAllowEnroll()) {
            mTvTrainStatus.setText(getResources().getString(R.string.event_status_2_not_allowed));
        } else {
            mTvTrainStatus.setText(getResources().getStringArray(R.array.event_status)[statusCode]);
        }
        mTvTrainStatus.setTextColor(getResources().getColor(TKGZUtil.getForeColor(statusCode)));
        mTvTrainStatus.setBackgroundColor(getResources().getColor(TKGZUtil.getBackColor(statusCode)));

        mTvTitle.setText(mEventProfile.getTitle());
        mTvInitiator.setText(mEventProfile.getInitiator().getMemberName());
        StringBuilder stringBuilder = new StringBuilder();

        List<PeopleProfile> lecturer = new ArrayList<>();
        lecturer = mEventProfile.getLecture();
        if (lecturer.isEmpty()) {
            mTvTxtLecturer.setVisibility(View.INVISIBLE);
        } else {
            mTvTxtLecturer.setVisibility(View.VISIBLE);
            int size = lecturer.size();
            for (int index = 0; index < size; index++) {
                stringBuilder.append(lecturer.get(index).getName()).append(", ");
            }
            mTvLecturer.setText(stringBuilder.substring(0, stringBuilder.length() - 2));
            stringBuilder.delete(0, stringBuilder.length());
        }
        mTvEventType.setText(mEventProfile.getEventTypeName());
        mTvAddress.setText(mEventProfile.isUseFrequentAddress() ? mEventProfile.getFrequentAddress().getAddress() : mEventProfile.getAddress());

        ArrayList<UserProfile> invited_users = mEventProfile.getInvitedUsers();
        int size = invited_users.size();
        for (int index = 0; index < size; index++) {
            stringBuilder.append(invited_users.get(index).getMemberName()).append(", ");
        }
        if (size > 0) {
            mTvRecipients.setText(stringBuilder.substring(0, stringBuilder.length() - 2));
            if (mTvRecipients.getLineCount() == 1) {
                mTvRecipients.setSingleLine(false);
                mTvRecipients.setCompoundDrawables(null, null, null, null);
            } else {
                mTvRecipients.setSingleLine(true);
            }
            LinearLayout.LayoutParams param = (LinearLayout.LayoutParams) mTvRecipients.getLayoutParams();
            mDefaultHeight = param.height;
        }

        ArrayList<IntegralTypeProfile> integralTypes = mEventProfile.getIntegralTypes();
        int integralTypeSize = integralTypes.size();
        stringBuilder.delete(0, stringBuilder.length());
        for (int index = 0; index < integralTypeSize; index++) {
            stringBuilder.append(integralTypes.get(index).getName()).append(", ");
        }
        if (integralTypeSize > 0) {
            mTvIntegralType.setText(stringBuilder.substring(0, stringBuilder.length() - 2));
        }

        mTvCount.setText(String.format("%d/%d", mEventProfile.getRegistration(), mEventProfile.getCapacity()));
        if (mEventProfile.isNeedEnroll()) {
            mTvEnrollTime.setText(String.format(getString(R.string.txt_to), TKGZUtil.getStringDateTime(mEventProfile.getEnrollStartTime()), TKGZUtil.getStringDateTime(mEventProfile.getEnrollEndTime())));
        } else {
            mTvEnrollTime.setVisibility(View.GONE);
            mTvEnroll.setVisibility(View.GONE);
        }
        mTvEventTime.setText(String.format(getString(R.string.txt_to), TKGZUtil.getStringDateTime(mEventProfile.getEventStartTime()), TKGZUtil.getStringDateTime(mEventProfile.getEventEndTime())));
        mTvContent.setText(mEventProfile.getContent());

        if (statusCode == 1 && mEventProfile.isAllowEnroll()) {
            mBtnEnrollOrCheckIn.setVisibility(View.VISIBLE);
            mClickToEnrollEvent = true;
            mBtnEnrollOrCheckIn.setText(getResources().getString(R.string.txt_btn_registration));
        } else if (mEventProfile.isAllowCheckIn() && statusCode == 4) {
            mBtnEnrollOrCheckIn.setVisibility(View.VISIBLE);
            mClickToEnrollEvent = false;
            mBtnEnrollOrCheckIn.setText(getResources().getString(R.string.txt_btn_check_in));
        } else {
            mBtnEnrollOrCheckIn.setVisibility(View.GONE);
        }
    }

    private void setEnrollListener() {
        initLoader(EnrollEventProvider.getEnrollEventProvider(EventDetailFragment.this, new EnrollEventApiCallBack.OnEnrollApiListener() {
            @Override
            public void onEnrollSuccess(final String message, final int integral_year, final int integral_month) {
                final Activity activity = getActivity();
                if (activity != null) {
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mOnUpdateEventStatusListener.onUpdateEventStatus(true);
                            //keep code
//                            TKGZApplication.getInstance().getUserProfile().setIntegralForMonth(integral_month);
//                            TKGZApplication.getInstance().getUserProfile().setIntegralForYear(integral_year);
//                            mOnRefreshUIListener.callRefreshIntegral();
                            Toast.makeText(activity, activity.getResources().getString(R.string.text_registration_success), Toast.LENGTH_SHORT).show();
                            getEventProfile();
                        }
                    });
                }
            }

            @Override
            public void onEnrollFailed(final String message) {
                Activity activity = getActivity();
                if (activity != null) {
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            showMessage(message);
                        }
                    });
                }
            }
        }, TKGZApplication.getInstance().getUserProfile(), mEventProfile.getId()));
    }

    private void setCheckInListener() {
        initLoader(CheckInEventProvider.getCheckInEventProvider(EventDetailFragment.this, new CheckInEventApiCallBack.OnCheckInApiListener() {
            @Override
            public void OnCheckInSuccess(String message, final int integral_year, final int integral_month) {
                final Activity activity = getActivity();
                if (activity != null) {
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(activity, activity.getResources().getString(R.string.text_check_in_success), Toast.LENGTH_SHORT).show();
                            TKGZApplication.getInstance().getUserProfile().setIntegralForMonth(integral_month);
                            TKGZApplication.getInstance().getUserProfile().setIntegralForYear(integral_year);
                            mOnRefreshUIListener.callRefreshIntegral();
                            mBtnEnrollOrCheckIn.setVisibility(View.GONE);
                        }
                    });
                }
            }

            @Override
            public void OnCheckInFailed(final String message) {
                Activity activity = getActivity();
                if (activity != null) {
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            showMessage(message);
                        }
                    });

                }
            }
        }, TKGZApplication.getInstance().getUserProfile(), mEventProfile.getId()));
    }

    //TODO keep codes

//    @Override
//    protected int getMenuResourceId() {
//        return mHasMenu ? R.menu.menu_delete_activity : -1;
//    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem menuItem) {
//        boolean isEventConsumed;
//        switch (menuItem.getItemId()) {
//            case R.id.menu_delete_activity:
//                showCancelButtonDialog(R.string.dialog_warning, R.string.dialog_content_delete_activity,
//                        "", "", R.string.dialog_ok_button, R.string.dialog_cancel_button, null,
//                        new TKGZCustomDialog.OnDismissDialogCallback() {
//                            @Override
//                            public void onDialogDismiss() {
//                                Toast.makeText(getActivity(), "Test:Delete This Activity", Toast.LENGTH_SHORT).show();
//                            }
//                        });
//                isEventConsumed = true;
//                break;
//            default:
//                isEventConsumed = super.onOptionsItemSelected(menuItem);
//                break;
//        }
//        return isEventConsumed;
//    }

    @Override
    public String getTransactionTag() {
        return "event_detail_fragment";
    }

    @Override
    public boolean isHideActionBar() {
        return false;
    }

    @Override
    public String getActionBarTitle() {
        return getString(R.string.txt_activity_detail);
    }

    public void setOnUpdateEventStatusListener(OnUpdateEventStatusListener onUpdateEventStatusListener) {
        this.mOnUpdateEventStatusListener = onUpdateEventStatusListener;
    }

    public interface OnUpdateEventStatusListener {
        void onUpdateEventStatus(boolean enrollSuccess);
    }

}
