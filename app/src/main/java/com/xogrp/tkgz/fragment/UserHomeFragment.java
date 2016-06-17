package com.xogrp.tkgz.fragment;

import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.xogrp.tkgz.R;
import com.xogrp.tkgz.TKGZApplication;
import com.xogrp.tkgz.View.UserProfileView;
import com.xogrp.tkgz.activity.MainActivity;
import com.xogrp.tkgz.activity.MainActivity.OnRefreshIntegralCallBack;
import com.xogrp.tkgz.activity.MainActivity.OnRefreshNewsCountCallBack;
import com.xogrp.tkgz.model.EventTypeProfile;
import com.xogrp.tkgz.model.UserProfile;
import com.xogrp.tkgz.provider.EventTypeProvider;
import com.xogrp.tkgz.provider.UnreadNewsCountProvider;
import com.xogrp.tkgz.spi.EventTypeApiCallBack;
import com.xogrp.tkgz.spi.UnreadNewsCountApiCallBack;

import java.io.FileNotFoundException;
import java.util.ArrayList;


public class UserHomeFragment extends AbstractCropImageFragment
        implements OnClickListener,
        OnRefreshNewsCountCallBack,
        OnRefreshIntegralCallBack,
        MainActivity.OnRefreshAvatarCallBack,
        MainActivity.OnRefreshBackgroundCallBack {
    private TextView mTvMyIntegral;
    private TextView mTvMessage;
    private UserProfile mUserProfile;
    private int mUnreadNewsCount = 0;

    private static class BasicUserProfileView extends UserProfileView {
        public BasicUserProfileView(View rootView) {
            super(rootView);
        }

        @Override
        public int getBackgroundImageViewId() {
            return R.id.iv_background;
        }

        @Override
        public int getAvatarImageViewId() {
            return R.id.iv_icon;
        }

        @Override
        public int getNameTextViewId() {
            return R.id.tv_name;
        }

        @Override
        public int getAnnualRewardPointsTextViewId() {
            return R.id.tv_year_integral;
        }

        @Override
        public int getMonthlyRewardPointsTextViewId() {
            return R.id.tv_month_integral;
        }

        @Override
        public int getRewardPointsGradeTextViewId() {
            return R.id.tv_level;
        }

        @Override
        public int getRewardPointsGradeImageViewId() {
            return R.id.iv_reward_points_grade;
        }

        @Override
        public int getPositionTextViewId() {
            return R.id.tv_position;
        }

        @Override
        public int getTeamRewardPointsTextViewId() {
            return R.id.tv_team_integral;
        }
    }

    private UserProfileView mUserProfileView;

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_user_home;
    }

    @Override
    protected void onTKGZAttach(Activity activity) {
        super.onTKGZAttach(activity);
        ((MainActivity) activity).addOnRefreshNewsCountCallBack(this);
        ((MainActivity) activity).addOnRefreshIntegralCallBack(this);
        ((MainActivity) activity).addOnRefreshAvatarCallBack(this);
        ((MainActivity) activity).addOnRefreshBackgroundCallBack(this);
    }

    @Override
    protected void onTKGZCreateView(View rootView) {
        mUserProfileView = new BasicUserProfileView(rootView);

        mTvMyIntegral = (TextView) rootView.findViewById(R.id.tv_my_integral);

        mTvMessage = (TextView) rootView.findViewById(R.id.tv_message);

        rootView.findViewById(R.id.rl_home_top).setOnClickListener(this);
        rootView.findViewById(R.id.tv_photo_wall).setOnClickListener(this);
        rootView.findViewById(R.id.tv_month_star).setOnClickListener(this);
        rootView.findViewById(R.id.tv_new_staff).setOnClickListener(this);
        rootView.findViewById(R.id.rl_my_integral).setOnClickListener(this);
        rootView.findViewById(R.id.rl_my_activity).setOnClickListener(this);
        rootView.findViewById(R.id.rl_integral_exchange).setOnClickListener(this);
        rootView.findViewById(R.id.rl_message).setOnClickListener(this);
        rootView.findViewById(R.id.ll_rank_list).setOnClickListener(this);
        rootView.findViewById(R.id.ll_activity).setOnClickListener(this);
        mUserProfileView.setOnAvatarClickListener(this);
        rootView.findViewById(R.id.rl_team_integral).setOnClickListener(this);
        mUserProfile = TKGZApplication.getInstance().getUserProfile();
        openOrCreatePicFile();
    }

    @Override
    protected void onTKGZActivityCreated() {
        mUserProfileView.render(getActivity(), mUserProfile);
        mTvMyIntegral.setText(String.valueOf(mUserProfile.getIntegralForYear()));
        getUnreadNewsCount();
        if (TKGZApplication.getEventTypeList().size() == 0) {
            getEventTypeList();
        }
    }

    private void getEventTypeList() {
        initLoader(EventTypeProvider.getEventTypeProvider(this,
                TKGZApplication.getInstance().getUserProfile(), new EventTypeApiCallBack.OnEventTypeProviderApiListener() {
                    @Override
                    public void onGetActivityType(ArrayList<EventTypeProfile> list) {
                        if (list != null) {
                            TKGZApplication.setEventTypeList(list);
                        }
                    }
                }));
    }

    private void getUnreadNewsCount() {
        initLoader(UnreadNewsCountProvider.getUnreadNewsCountProvider(this,
                new UnreadNewsCountApiCallBack.OnUnreadNewsCountApiListener() {
                    @Override
                    public void onGetUnreadNewsCount(final int count) {
                        final Activity activity = getActivity();
                        if (activity != null) {
                            activity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    mUnreadNewsCount = count;
                                    if (mUnreadNewsCount == 0) {
                                        mTvMessage.setVisibility(View.GONE);
                                    } else {
                                        mTvMessage.setVisibility(View.VISIBLE);
                                        mTvMessage.setText(String.valueOf(mUnreadNewsCount));
                                    }
                                }
                            });
                        }
                    }
                }, mUserProfile));
    }

    @Override
    public String getTransactionTag() {
        return "personnel_home_fragment";
    }

    @Override
    public boolean isHideActionBar() {
        return false;
    }

    @Override
    public String getActionBarTitle() {
        return "";
    }

    @Override
    protected int getMenuResourceId() {
        return R.menu.menu_home_manager;
    }

//    Please keep these lines, we need to use them in next version.

//    @Override
//    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
//        super.onCreateOptionsMenu(menu, menuInflater);
//        MenuItem menuItem = menu.findItem(R.id.home_manager_item);
//        if (mUserProfile.isLeader() && menuItem != null && menuItem.getActionView() != null) {
//            Button mBtnMenuItem = (Button) menuItem.getActionView();
//            mBtnMenuItem.setText(getString(R.string.actionbar_title_master_page));
//            mBtnMenuItem.setOnClickListener(new View.OnClickListener() {
//
//                @Override
//                public void onClick(View view) {
//                    getOnScreenNavigationListener().navigateToMasterHomePage();
//                }
//            });
//        }
//    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_icon:
//                getOnScreenNavigationListener().navigateToMyAccountPage(TKGZApplication.getInstance().getUserProfile(), null);
                getOnScreenNavigationListener().navigateToMasterHomePage();
                break;
            case R.id.rl_home_top:
                getOnScreenNavigationListener().navigateToMyAccountPage(TKGZApplication.getInstance().getUserProfile(), null);
                break;
            case R.id.tv_photo_wall:
                getOnScreenNavigationListener().navigateToPhotoWallPage();
                break;
            case R.id.tv_month_star:
                getOnScreenNavigationListener().navigateToMonthStarsPage();
                break;
            case R.id.tv_new_staff:
                getOnScreenNavigationListener().navigateToNewComerPage();
                break;
            case R.id.ll_rank_list:
                getOnScreenNavigationListener().navigateToIntegralRankPage();
                break;
            case R.id.ll_activity:
//                getOnScreenNavigationListener().navigateToEventListViewPage(getString(R.string.actionbar_title_event), false);
                getOnScreenNavigationListener().navigateToShowEventsPage();
                break;
            case R.id.rl_my_integral:
                getOnScreenNavigationListener().navigateToMyIntegralPage();
                break;
            case R.id.rl_my_activity:
                getOnScreenNavigationListener().navigateToMyEventsPage();
                break;
            case R.id.rl_team_integral:
                getOnScreenNavigationListener().navigateToTeamIntegralPage();
                break;
            case R.id.rl_integral_exchange:
                getOnScreenNavigationListener().navigateToIntegralExchangePage();
                break;
            case R.id.rl_message:
                getOnScreenNavigationListener().navigateToNewsListPage();
                break;
        }
    }

    @Override
    public void onDestroy() {
        final Activity activity = getActivity();
        if (activity != null) {
            ((MainActivity) activity).removeOnRefreshNewsCountCallBack();
            ((MainActivity) activity).removeOnRefreshIntegralCallBack(this);
            ((MainActivity) activity).removeOnRefreshAvatarCallBack(this);
        }
        super.onDestroy();
    }

    public void onRefreshNewsCount() {
        getUnreadNewsCount();
    }

    @Override
    public boolean isEmptyTopMargin() {
        return true;
    }

    @Override
    public void onRefreshIntegral() {
        final Activity activity = getActivity();
        if (activity != null) {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mUserProfile = TKGZApplication.getInstance().getUserProfile();
                    int integral = mUserProfile.getIntegralForYear();
                    mTvMyIntegral.setText(String.valueOf(integral));
                    mUserProfileView.onRewardPointsUpdate();
                }
            });
        }
    }

    @Override
    public void onRefreshAvatar() {
        final Activity activity = getActivity();
        if (activity != null) {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    try {
                        mUserProfileView.onAvatarUpdate(getActivity(), getAvatarFile());
                    } catch (FileNotFoundException fnfe) {
                        getLogger().error("UserHomeFragment: decodeUriAsBitmap", fnfe.getMessage());
                    }
                }
            });
        }
    }

    @Override
    public void onRefreshBackground() {
        final Activity activity = getActivity();
        if (activity != null) {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    try {
                        mUserProfileView.onBackgroundUpdate(getActivity(), getBackgroundFile());
                    } catch (FileNotFoundException fnfe) {
                        getLogger().error("UserHomeFragment: decodeUriAsBitmap", fnfe.getMessage());
                    }
                }
            });
        }
    }
}
