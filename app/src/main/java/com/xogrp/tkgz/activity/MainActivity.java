package com.xogrp.tkgz.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.LoaderManager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.baidu.mapapi.SDKInitializer;
import com.xogrp.tkgz.R;
import com.xogrp.tkgz.TKGZApplication;
import com.xogrp.tkgz.View.ProgressView;
import com.xogrp.tkgz.Widget.TKGZCustomDialog;
import com.xogrp.tkgz.Widget.TKGZCustomDialog.OnDialogActionCallback;
import com.xogrp.tkgz.Widget.TKGZCustomDialog.OnDismissDialogCallback;
import com.xogrp.tkgz.Widget.TKGZCustomEditDialog;
import com.xogrp.tkgz.Widget.TKGZCustomHomeMenuDialog;
import com.xogrp.tkgz.fragment.AboutTheKnotFragment;
import com.xogrp.tkgz.fragment.AbstractCropImageFragment;
import com.xogrp.tkgz.fragment.AbstractTKGZFragment;
import com.xogrp.tkgz.fragment.ActivityManage;
import com.xogrp.tkgz.fragment.ActivitytypeAdminifragment;
import com.xogrp.tkgz.fragment.AddActTypeFragment;
import com.xogrp.tkgz.fragment.AddUsedAddressFragment;
import com.xogrp.tkgz.fragment.AddressSearchFragment;
import com.xogrp.tkgz.fragment.AdministratorHomeFragment;
import com.xogrp.tkgz.fragment.ChangePasswordFragment;
import com.xogrp.tkgz.fragment.CommonAdressFragment;
import com.xogrp.tkgz.fragment.CreateActivityPage2Fragment;
import com.xogrp.tkgz.fragment.CreateActivityPage1Fragment;
import com.xogrp.tkgz.fragment.EventDetailFragment;
import com.xogrp.tkgz.fragment.EventManagerFragment;
import com.xogrp.tkgz.fragment.EventTypeManageFragment;
import com.xogrp.tkgz.fragment.FeedbackFragment;
import com.xogrp.tkgz.fragment.GetActivityTypeFragment;
import com.xogrp.tkgz.fragment.GetCommonAddressFragment;
import com.xogrp.tkgz.fragment.GetInitiatorFragment;
import com.xogrp.tkgz.fragment.IntegralExchangeFragment;
import com.xogrp.tkgz.fragment.IntegralManagerFragment;
import com.xogrp.tkgz.fragment.IntegralRankFragment;
import com.xogrp.tkgz.fragment.MasterHomeFragment;
import com.xogrp.tkgz.fragment.MessageDetailsFragment;
import com.xogrp.tkgz.fragment.MessageManageFragment;
import com.xogrp.tkgz.fragment.MonthStarsFragment;
import com.xogrp.tkgz.fragment.MyAccountFragment;
import com.xogrp.tkgz.fragment.MyEventsFragment;
import com.xogrp.tkgz.fragment.MyIntegralFragment;
import com.xogrp.tkgz.fragment.MyProductsFragment;
import com.xogrp.tkgz.fragment.NewComerFragment;
import com.xogrp.tkgz.fragment.NewsDetailsFragment;
import com.xogrp.tkgz.fragment.NewsListFragment;
import com.xogrp.tkgz.fragment.PersonnelInformationFragment;
import com.xogrp.tkgz.fragment.PersonnelManageFragment;
import com.xogrp.tkgz.fragment.PersonnelManageSectionFragment;
import com.xogrp.tkgz.fragment.PhotoWallFragment;
import com.xogrp.tkgz.fragment.ProductDetailsFragment;
import com.xogrp.tkgz.fragment.SearchEmployeeFragment;
import com.xogrp.tkgz.fragment.SearchIntegralByAdminFragment;
import com.xogrp.tkgz.fragment.SearchMemberIntegralResultFragment;
import com.xogrp.tkgz.fragment.SearchMyIntegralFragment;
import com.xogrp.tkgz.fragment.SearchTeamMemberIntegralFragment;
import com.xogrp.tkgz.fragment.SelfIntroductionFragment;
import com.xogrp.tkgz.fragment.ShowEventsFragment;
import com.xogrp.tkgz.fragment.SplashPageFragment;
import com.xogrp.tkgz.fragment.TeamInquireResultFragment;
import com.xogrp.tkgz.fragment.TeamIntegralFragment;
import com.xogrp.tkgz.fragment.TeamsPersonalSearchResultsFragment;
import com.xogrp.tkgz.fragment.UserHomeFragment;
import com.xogrp.tkgz.fragment.UserLoginFragment;
import com.xogrp.tkgz.listener.ActivityUIController;
import com.xogrp.tkgz.listener.OnRefreshUIListener;
import com.xogrp.tkgz.listener.OnScreenNavigationListener;
import com.xogrp.tkgz.model.ActivityType;
import com.xogrp.tkgz.model.MessageForUser;
import com.xogrp.tkgz.model.MessageProfile;
import com.xogrp.tkgz.model.UserProfile;
import com.xogrp.xoapp.provider.XOAbstractRESTLoader;

import org.json.JSONObject;
import org.slf4j.Logger;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AbstractTKGZActivity implements OnScreenNavigationListener, ActivityUIController
        , XOAbstractRESTLoader.OnRESTLoaderListener, OnRefreshUIListener {
    private long mExitTime = 0;
    private FrameLayout mFlContainer;
    private List<OnTextUpdatedCallback> mOnTextUpdatedListeners = new ArrayList<>();
    private OnRefreshNewsCountCallBack mOnRefreshNewsCountCallBack;
    private OnRefreshNewsListCallBack mOnRefreshNewsListCallBack;
    private List<OnRefreshIntegralCallBack> mOnRefreshIntegralCallBacks = new ArrayList<>();
    private List<OnRefreshAvatarCallBack> mOnRefreshAvatarCallBacks = new ArrayList<>();
    private List<OnRefreshBackgroundCallBack> mOnRefreshBackgroundCallBacks = new ArrayList<>();
    private Toolbar mToolbar;
    private ProgressDialog mDialog;
    private ProgressView mProgressView;
    private Toast mToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_main);
        mToolbar = (Toolbar) findViewById(R.id.tool_bar);
        mFlContainer = (FrameLayout) findViewById(R.id.container);
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(R.drawable.icon_back);
        mProgressView = new ProgressView(this, "Loading");
        mDialog = new ProgressDialog(this);
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mDialog.setMessage(getString(R.string.loading));
        mDialog.setCanceledOnTouchOutside(false);
        getSupportFragmentManager().addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                int backStackCount = getSupportFragmentManager().getBackStackEntryCount();
                if (backStackCount > 0) {
                    AbstractTKGZFragment currentFragment = (AbstractTKGZFragment) getSupportFragmentManager().getFragments().get(backStackCount);
                    int childrenBackStackCount = currentFragment.getChildFragmentManager().getBackStackEntryCount();
                    if (childrenBackStackCount > 0) {
                        AbstractTKGZFragment currentChildFragment = (AbstractTKGZFragment) currentFragment.getChildFragmentManager().getFragments().get(childrenBackStackCount - 1);
                        currentChildFragment.onRefreshData();
                    }
                    checkFragmentMarginTop(currentFragment);
                    if (currentFragment.isHideActionBar()) {
                        mToolbar.setVisibility(View.GONE);
                    } else {
                        mToolbar.setVisibility(View.VISIBLE);
                        mToolbar.setTitle(currentFragment.getActionBarTitle());
                        if (currentFragment instanceof UserHomeFragment) {
                            mToolbar.setLogo(R.drawable.logo_icon2);
                            mToolbar.setNavigationIcon(null);
                        } else if (currentFragment instanceof AddressSearchFragment) {
                            mToolbar.setNavigationIcon(null);
                        } else {
                            mToolbar.setLogo(null);
                            mToolbar.setNavigationIcon(R.drawable.icon_back);
                        }
                    }
                }
            }
        });
        mToolbar.setVisibility(View.GONE);
        if (TKGZApplication.getInstance().getUserProfile() != null) {
            navigateToSplashPage();
        } else {
            navigateToLoginPage();
        }
    }

    @Override
    public void navigateToAdminFirPage() {

    }

    @Override
    public void navigateToActivityManagePage() {
        addFragmentAndAdd2BackStack(new ActivityManage());

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
    }

    @Override
    protected int getContainerResId() {
        return R.id.container;
    }

    @Override
    public void onBackPressed() {
        if (!isPopBackStack()) {
            if ((System.currentTimeMillis() - mExitTime) > 2000) {
                Toast.makeText(getApplicationContext(), R.string.message_press_once_more_time_to_exit,
                        Toast.LENGTH_SHORT).show();
                mExitTime = System.currentTimeMillis();
            } else {
                System.exit(0);
            }
        }
    }

    public void checkFragmentMarginTop(AbstractTKGZFragment fragment) {
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) mFlContainer.getLayoutParams();
        if (fragment.isEmptyTopMargin()) {
            params.topMargin = 0;
            mToolbar.setBackgroundColor(this.getResources().getColor(android.R.color.transparent));
        } else {
            params.topMargin = (int) this.getResources().getDimension(R.dimen.toolbar_height);
            mToolbar.setBackgroundColor(this.getResources().getColor(android.R.color.black));
        }
        mFlContainer.setLayoutParams(params);
    }

    @Override
    public void goBackFromCurrentPage() {
        popBackStack();
    }

    @Override
    public void logOutFromCurrentPage() {
        int backStackCount = getSupportFragmentManager().getBackStackEntryCount();
        if (backStackCount > 0) {
            for (int count = 0; count < backStackCount; count++) {
                logOut();
            }
        }
    }

    @Override
    public void changeLanguageFromCurrentPage() {
        goBackFromCurrentPage();
        navigateToUserHomePage();
    }

    @Override
    public void navigateToLoginPage() {
        mToolbar.setVisibility(View.GONE);
        replaceFragment(new UserLoginFragment());
    }

    @Override
    public void navigateToIntegralRankPage() {
        addFragmentAndAdd2BackStack(new IntegralRankFragment());
    }

    @Override
    public void navigateToMyIntegralPage() {
        addFragmentAndAdd2BackStack(new MyIntegralFragment());
    }

    public void navigateToUserHomePage() {
        addFragmentAndAdd2BackStack(new UserHomeFragment());
    }


    @Override
    public void navigateToEventDetailsPage(EventDetailFragment.OnUpdateEventStatusListener onUpdateEventStatusListener, String eventId, boolean has_menu) {
        addFragmentAndAdd2BackStack(EventDetailFragment.newInstance(onUpdateEventStatusListener, eventId, has_menu));
    }

    @Override
    public void navigateToMyEventsPage() {
        addFragmentAndAdd2BackStack(new MyEventsFragment());
    }

    @Override
    public void navigateToMyProductsPage() {
        addFragmentAndAdd2BackStack(new MyProductsFragment());
    }

    @Override
    public void navigateToMasterHomePage() {
        addFragmentAndAdd2BackStack(new MasterHomeFragment());
    }

    @Override
    public void navigateToActivityType() {

    }

    @Override
    public void navigateToAdminPage() {
        addFragmentAndAdd2BackStack(new AdministratorHomeFragment());
    }


    @Override
    public void navigateToTeamIntegralPage() {
        addFragmentAndAdd2BackStack(new TeamIntegralFragment());
    }

    @Override
    public void navigateToTeamInquireResultPage() {
        addFragmentAndAdd2BackStack(new TeamInquireResultFragment());
    }

    @Override
    public void navigateToSearchIntegralPage() {
        addFragmentAndAdd2BackStack(new SearchMyIntegralFragment());
    }

    @Override
    public void navigateToTeamsPersonalSearchResultsPage() {
        addFragmentAndAdd2BackStack(new TeamsPersonalSearchResultsFragment());
    }

    @Override
    public void navigateToIntegralExchangePage() {
        addFragmentAndAdd2BackStack(new IntegralExchangeFragment());
    }

    public void navigateToChangePasswordPage() {
        addFragmentAndAdd2BackStack(new ChangePasswordFragment());
    }

    @Override
    public void navigateToFeedbackPage() {
        addFragmentAndAdd2BackStack(new FeedbackFragment());
    }

    @Override
    public void navigateToAboutTheKnotPage() {
        addFragmentAndAdd2BackStack(new AboutTheKnotFragment());
    }

    @Override
    public void navigateToPersonnelManagePage() {
        addFragmentAndAdd2BackStack(new PersonnelManageFragment());
    }

    @Override
    public void navigateToMessageManagerPage() {
        addFragmentAndAdd2BackStack(new MessageManageFragment());
    }

    @Override
    public void navigateToInquireManagerPage() {
        addFragmentAndAdd2BackStack(new IntegralManagerFragment());
    }

    @Override
    public void navigateToMessageEditPage(AbstractTKGZFragment fragment) {
        addFragmentAndAdd2BackStack(fragment);
    }


    @Override
    public void navigateToPersonnelInformationEditPage(AbstractTKGZFragment fragment) {
        addFragmentAndAdd2BackStack(fragment);
    }

    @Override
    public void navigateToShowProductDetailPage(String id) {
        addFragmentAndAdd2BackStack(ProductDetailsFragment.newInstance(id));
    }

    @Override
    public void navigateToEventTypePage() {
        addFragmentAndAdd2BackStack(new EventTypeManageFragment());
    }

    @Override
    public void navigateToCreateNewEventPage() {
        //addFragmentAndAdd2BackStack(new CreateEventFragment());
        addFragmentAndAdd2BackStack(new CreateActivityPage1Fragment());
    }

    @Override
    public void navigateToActivityTrainingPage(String title, boolean hasMenu) {
        addFragmentAndAdd2BackStack(EventManagerFragment.newInstance(title, hasMenu));
    }

    @Override
    public void navigateToMessageDetailsPage(MessageProfile message, int position) {
        addFragmentAndAdd2BackStack(MessageDetailsFragment.newInstance(message, position));
    }

    @Override
    public void navigateToSearchEmployeePage() {
        addFragmentAndAdd2BackStack(new SearchEmployeeFragment());
    }

    @Override
    public void navigateToSearchIntegralByAdminPage() {
        addFragmentAndAdd2BackStack(new SearchIntegralByAdminFragment());
    }

    @Override
    public void navigateToSearchMemberIntegralResultPage() {
        addFragmentAndAdd2BackStack(new SearchMemberIntegralResultFragment());
    }

    @Override
    public void navigateToSearchTeamMemberIntegralPage() {
        addFragmentAndAdd2BackStack(new SearchTeamMemberIntegralFragment());
    }

    @Override
    public void navigateToPhotoWallPage() {
        addFragmentAndAdd2BackStack(new PhotoWallFragment());
    }

    @Override
    public void navigateToSelfIntroductionPage(UserProfile employeeInformation) {
        addFragmentAndAdd2BackStack(SelfIntroductionFragment.newInstance(employeeInformation));
    }

    @Override
    public void navigateToMyAccountPage(UserProfile userProfile, MyAccountFragment.OnRefreshMyAvatarListener onRefreshMyAvatarListener) {
        addFragmentAndAdd2BackStack(MyAccountFragment.newInstance(userProfile, onRefreshMyAvatarListener));
    }

    @Override
    public void navigateToShowEventsPage() {
        addFragmentAndAdd2BackStack(new ShowEventsFragment());
    }

    @Override
    public void navigateToMonthStarsPage() {
        addFragmentAndAdd2BackStack(new MonthStarsFragment());
    }

    @Override
    public void navigateToNewComerPage() {
        addFragmentAndAdd2BackStack(new NewComerFragment());
    }




    @Override
    public void navigateToCreateActivityPage2() {
        addFragmentAndAdd2BackStack(new CreateActivityPage2Fragment());

    }

    @Override
    public void navigateToGetCommonAddressPage(GetCommonAddressFragment.GetAddressListener getAddressListener) {
        addFragmentAndAdd2BackStack(GetCommonAddressFragment.newInstance(getAddressListener));
    }

    @Override
    public void navigateToGetActivityTypePage(GetActivityTypeFragment.GetActivityTypeListener getActivityTypeListener) {
        addFragmentAndAdd2BackStack(GetActivityTypeFragment.newInstance(getActivityTypeListener));
    }

    @Override
    public void navigateToGetInitiator(GetInitiatorFragment.InitiatorListener initiatorListener) {
        addFragmentAndAdd2BackStack(GetInitiatorFragment.newInstance(initiatorListener));
    }

    @Override

    public void navigateToRetrievePasswordPage() {
        Intent intent = new Intent(this, RetrievePasswordActivity.class);
        startActivity(intent);
    }

    @Override
    public void navigateToSplashPage() {
        replaceFragment(new SplashPageFragment());
    }



    @Override
    public void navigateToActivityTypePage() {
        addFragmentAndAdd2BackStack(new ActivitytypeAdminifragment());
    }

    @Override
    public void navigateToEditActivityTypePage(List<ActivityType> activityTypes ) {

         addFragmentAndAdd2BackStack(AddActTypeFragment.newInstance(activityTypes));
    }

    @Override
    public void navigateToCommonAddressPage() {
        addFragmentAndAdd2BackStack(new CommonAdressFragment());

    }

    @Override
    public void navigateToSectionPersonnelPage(String actionBarName) {
        addFragmentAndAdd2BackStack(PersonnelManageSectionFragment.newInstance(actionBarName));
    }

    @Override
    public void navigateToPersonnelInformationPage(UserProfile user) {
        addFragmentAndAdd2BackStack(PersonnelInformationFragment.newInstance(user));
    }

    @Override
    public void navigateToNewsListPage() {
        addFragmentAndAdd2BackStack(new NewsListFragment());
    }

    @Override
    public void navigateToAddActTypePage() {
        addFragmentAndAdd2BackStack(new AddActTypeFragment());
    }

    @Override
    public void navigateToAddAddressPage() {
        addFragmentAndAdd2BackStack(new AddUsedAddressFragment());
    }



    @Override
    public void returnToAddressListPage() {
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void navigateToAddressListPage() {
        addFragmentAndAdd2BackStack(new CommonAdressFragment());
    }

    @Override
    public void navigateToNewsDetailsPage(MessageForUser message) {
        addFragmentAndAdd2BackStack(NewsDetailsFragment.newInstance(message));
    }

    public void addOnTextUpdatedCallback(OnTextUpdatedCallback onTextUpdatedCallback) {
        mOnTextUpdatedListeners.add(onTextUpdatedCallback);
    }

    public void removeOnTextUpdatedCallback(OnTextUpdatedCallback onTextUpdatedCallback) {
        if (mOnTextUpdatedListeners.contains(onTextUpdatedCallback)) {
            mOnTextUpdatedListeners.remove(onTextUpdatedCallback);
        }
    }

    public static interface OnTextUpdatedCallback {
        void onTextUpdated(String text);
    }

    // refresh un-read news count
    public void removeOnRefreshNewsCountCallBack() {
        mOnRefreshNewsCountCallBack = null;
    }

    public void addOnRefreshNewsCountCallBack(OnRefreshNewsCountCallBack onRefreshNewsCountCallBack) {
        mOnRefreshNewsCountCallBack = onRefreshNewsCountCallBack;
    }


    public interface OnRefreshNewsCountCallBack {
        void onRefreshNewsCount();
    }

    public interface OnReFreshActivityTypeListCallBack{
        void onRefreshType(String type);
    }

    //refresh news list
    public void removeOnRefreshNewsListCallBack() {
        mOnRefreshNewsListCallBack = null;
    }

    public void addOnRefreshNewsListCallBack(OnRefreshNewsListCallBack onRefreshNewsListCallBack) {
        mOnRefreshNewsListCallBack = onRefreshNewsListCallBack;
    }

    public interface OnRefreshNewsListCallBack {
        void onRefreshNewsList();
    }

    //refresh user year-integral
    public void removeOnRefreshIntegralCallBack(OnRefreshIntegralCallBack onRefreshIntegralCallBack) {
        if (mOnRefreshIntegralCallBacks.contains(onRefreshIntegralCallBack)) {
            mOnRefreshIntegralCallBacks.remove(onRefreshIntegralCallBack);
        }
    }

    public void addOnRefreshIntegralCallBack(OnRefreshIntegralCallBack onRefreshIntegralCallBack) {
        mOnRefreshIntegralCallBacks.add(onRefreshIntegralCallBack);
    }

    public interface OnRefreshIntegralCallBack {
        void onRefreshIntegral();
    }

    //change user avatar
    public void removeOnRefreshAvatarCallBack(OnRefreshAvatarCallBack onRefreshAvatarCallBack) {
        if (mOnRefreshAvatarCallBacks.contains(onRefreshAvatarCallBack)) {
            mOnRefreshIntegralCallBacks.remove(onRefreshAvatarCallBack);
        }
    }

    public void removeOnRefreshBackgroundCallBack(OnRefreshBackgroundCallBack onRefreshBackgroundCallBack) {
        if (mOnRefreshBackgroundCallBacks.contains(onRefreshBackgroundCallBack)) {
            mOnRefreshBackgroundCallBacks.remove(onRefreshBackgroundCallBack);
        }
    }

    public void addOnRefreshAvatarCallBack(OnRefreshAvatarCallBack onRefreshAvatarCallBack) {
        mOnRefreshAvatarCallBacks.add(onRefreshAvatarCallBack);
    }

    public void addOnRefreshBackgroundCallBack(OnRefreshBackgroundCallBack onRefreshBackgroundCallBack) {
        mOnRefreshBackgroundCallBacks.add(onRefreshBackgroundCallBack);
    }

    public interface OnRefreshAvatarCallBack {
        void onRefreshAvatar();
    }

    public interface OnRefreshBackgroundCallBack {
        void onRefreshBackground();
    }




    @Override
    public void callRefreshNewsList() {
        if (mOnRefreshNewsListCallBack != null) {
            mOnRefreshNewsListCallBack.onRefreshNewsList();
        }
    }

    @Override
    public void callRefreshNewsCount() {
        if (mOnRefreshNewsCountCallBack != null) {
            mOnRefreshNewsCountCallBack.onRefreshNewsCount();
        }
    }

    @Override
    public void callRefreshIntegral() {
        if (mOnRefreshIntegralCallBacks.size() > 0) {
            for (OnRefreshIntegralCallBack onRefreshIntegralCallBack : mOnRefreshIntegralCallBacks) {
                onRefreshIntegralCallBack.onRefreshIntegral();
            }
        }
    }

    @Override
    public void callRefreshAvatar(String currentFragmentName) {
        if (mOnRefreshAvatarCallBacks.size() > 0) {
            for (OnRefreshAvatarCallBack onRefreshAvatarCallBack : mOnRefreshAvatarCallBacks) {
                if (currentFragmentName == null || onRefreshAvatarCallBack.getClass().getName().equals(currentFragmentName)) {
                    onRefreshAvatarCallBack.onRefreshAvatar();
                }
            }
        }
    }

    @Override
    public void callRefreshBackground(String currentFragmentName) {
        if (mOnRefreshBackgroundCallBacks.size() > 0) {
            for (OnRefreshBackgroundCallBack onRefreshBackgroundCallBack : mOnRefreshBackgroundCallBacks) {
                if (currentFragmentName == null || onRefreshBackgroundCallBack.getClass().getName().equals(currentFragmentName)) {
                    onRefreshBackgroundCallBack.onRefreshBackground();
                }
            }
        }
    }

    //ui controller
    @Override
    public void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    @Override
    public void showKeyboard() {
        InputMethodManager imm = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    @Override
    public void showDialog(int titleResId, int messageResId) {
        (new TKGZCustomDialog(this, titleResId, messageResId)).show();
    }

    @Override
    public void showCancelButtonDialog(int titleResId, int contentTitleLeft, String contentTitleRight, String message, int okButtonMessageResId, int cancelButtonMessageResId,
                                       OnDialogActionCallback onDialogActionCallback, OnDismissDialogCallback onDismissDialogCallback) {
        (new TKGZCustomHomeMenuDialog(this, titleResId, contentTitleLeft, contentTitleRight, message, okButtonMessageResId,
                cancelButtonMessageResId, onDialogActionCallback, onDismissDialogCallback)).show();
    }

    @Override
    public void showEditMessageDialog(int titleResId, String message, int okButtonMessageResId, int cancelButtonMessageResId, OnDialogActionCallback onDialogActionCallback, OnDismissDialogCallback onDismissDialogCallback) {
        (new TKGZCustomEditDialog(this, titleResId, message, okButtonMessageResId,
                cancelButtonMessageResId, onDialogActionCallback, onDismissDialogCallback)).show();
    }

    @Override
    public void showEditMessageDialog(int titleResId, String message, int okButtonMessageResId, int cancelButtonMessageResId, OnDialogActionCallback onDialogActionCallback, OnDismissDialogCallback onDismissDialogCallback, TKGZCustomEditDialog.OnCustomEditDialogListener listener) {
        (new TKGZCustomEditDialog(this, titleResId, message, okButtonMessageResId,
                cancelButtonMessageResId, onDialogActionCallback, onDismissDialogCallback, listener)).show();
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void showToast(final String message) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mToast == null) {
                    mToast = Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT);
                } else {
                    mToast.setText(message);
                }
                mToast.show();
            }
        });
    }

    @Override
    public void showSpinner() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
//                mDialog.show();
                mProgressView.show();
            }
        });
    }

    @Override
    public void hideSpinner() {
        if (mProgressView != null) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
//                    mDialog.dismiss();

                    mProgressView.dismiss();
                }
            });
        }
    }

    @Override
    public void showMessage(String message) {
        (new TKGZCustomDialog(this, R.string.txt_btn_title, message)).show();
    }

    @Override
    public void showMessage(String title, String message) {

    }

    @Override
    public LoaderManager getXOLoaderManager() {
        return getSupportLoaderManager();
    }

    @Override
    public Logger getLogger() {
        return null;
    }

    @Override
    public boolean isWifiAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifiInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobileInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        return (wifiInfo != null && wifiInfo.isConnected() && wifiInfo.isAvailable()) || (mobileInfo != null && mobileInfo.isConnected() && mobileInfo.isAvailable());
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        boolean isClickMenu;
        switch (keyCode) {
            case KeyEvent.KEYCODE_MENU:
                isClickMenu = true;
                break;
            default:
                isClickMenu = super.onKeyDown(keyCode, event);
                break;
        }
        return isClickMenu;
    }

    @Override
    public ActionBar getTKActionBar() {
        return getSupportActionBar();
    }

    @Override
    public Toolbar getTKToolBar() {
        return mToolbar;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            String currentFragmentName = getSupportFragmentManager().getFragments().get(getSupportFragmentManager().getBackStackEntryCount()).getClass().getName();
            switch (requestCode) {
                case AbstractCropImageFragment.SELECT_A_PICTURE_FOR_AVATAR:
                    AbstractCropImageFragment.cropImageUri(this, getUri(data), 100, 100, AbstractCropImageFragment.REFRESH_AVATAR);
                    break;
                case AbstractCropImageFragment.SELECT_A_PICTURE_FOR_BACKGROUND:
                    int width = getResources().getDisplayMetrics().widthPixels;
                    int height = getResources().getDimensionPixelOffset(R.dimen.rl_self_introduction_height);
                    AbstractCropImageFragment.cropImageUri(this, getUri(data), width, height, AbstractCropImageFragment.REFRESH_BACKGROUND);
                    break;
                case AbstractCropImageFragment.REFRESH_AVATAR:
                    callRefreshAvatar(currentFragmentName);
                    break;
                case AbstractCropImageFragment.REFRESH_BACKGROUND:
                    callRefreshBackground(currentFragmentName);
                    break;
                default:
                    break;
            }
        }
    }

    private Uri getUri(Intent data) {
        String albumPicturePath;
        Uri uri = null;
        if (data != null) {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
                uri = data.getData();
            } else {

                albumPicturePath = getPath(this.getApplicationContext(), data.getData());
                if (albumPicturePath != null) {
                    uri = Uri.fromFile(new File(albumPicturePath));
                }
            }
        }
        return uri;
    }

    private String getPath(final Context context, final Uri uri) {
        String path = null;
        // DocumentProvider
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT &&
                DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                String[] split = DocumentsContract.getDocumentId(uri).split(":");
                if ("primary".equalsIgnoreCase(split[0])) {
                    path = Environment.getExternalStorageDirectory() + "/" + split[1];
                }
                // TODO handle non-primary volumes
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {
                Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"),
                        Long.valueOf(DocumentsContract.getDocumentId(uri)));
                path = getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                String[] split = DocumentsContract.getDocumentId(uri).split(":");
                String type = split[0];
                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }
                String[] selectionArgs = new String[]{split[1]};
                path = getDataColumn(context, contentUri, "_id=?", selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {
            // Return the remote address
            path = isGooglePhotosUri(uri) ? uri.getLastPathSegment() :
                    getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            path = uri.getPath();
        }
        return path;
    }

    /**
     * ?* Get the value of the data column for this Uri. This is useful for
     * ?* MediaStore Uris, and other file-based ContentProviders.
     * ?*
     * ?* @param context The context.
     * ?* @param uri The Uri to query.
     * ?* @param selection (Optional) Filter used in the query.
     * ?* @param selectionArgs (Optional) Selection arguments used in the query.
     * ?* @return The value of the _data column, which is typically a file path.
     * ?
     */
    private String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {
        Cursor cursor = null;
        String column = "_data";
        String dataColumn = null;
        try {
            cursor = context.getContentResolver().query(uri, new String[]{column}, selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                dataColumn = cursor.getString(cursor.getColumnIndexOrThrow(column));
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return dataColumn;
    }

    /**
     * ?* @param uri The Uri to check.
     * ?* @return Whether the Uri authority is ExternalStorageProvider.
     * ?
     */
    private boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * ?* @param uri The Uri to check.
     * ?* @return Whether the Uri authority is DownloadsProvider.
     * ?
     */
    private boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * ?* @param uri The Uri to check.
     * ?* @return Whether the Uri authority is MediaProvider.
     * ?
     */
    private boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    /**
     * ?* @param uri The Uri to check.
     * ?* @return Whether the Uri authority is Google Photos.
     * ?
     */
    private boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }


    @Override
    public void navigateToCreateActivityPageTwo(JSONObject jsonObject) {
        addFragmentAndAdd2BackStack(CreateActivityPage2Fragment.newInstance(jsonObject));
    }

    @Override
    public void navigateToAddressSearchPage() {
        addFragmentAndAdd2BackStack(new AddressSearchFragment());
    }
}
