package com.xogrp.tkgz.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.PermissionChecker;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.baidu.mapapi.SDKInitializer;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.xogrp.tkgz.R;
import com.xogrp.tkgz.TKGZApplication;
import com.xogrp.tkgz.Widget.TKGZCustomDialog.OnDialogActionCallback;
import com.xogrp.tkgz.Widget.TKGZCustomDialog.OnDismissDialogCallback;
import com.xogrp.tkgz.Widget.TKGZCustomEditDialog;
import com.xogrp.tkgz.listener.ActivityUIController;
import com.xogrp.tkgz.listener.OnScreenNavigationListener;
import com.xogrp.xoapp.fragment.XOAbstractFragment;
import com.xogrp.xoapp.provider.XOAbstractRESTLoader;

import org.slf4j.Logger;

import java.io.File;
import java.util.ArrayList;


/**
 * Created by dgao on 7/27/2015.
 */
public abstract class AbstractTKGZFragment extends XOAbstractFragment implements View.OnTouchListener,
        XOAbstractRESTLoader.OnRESTLoaderListener {

    private View mRootView;
    private static OnScreenNavigationListener mOnScreenNavigationListener;
    private ActivityUIController mActivityUIController;
    private XOAbstractRESTLoader.OnRESTLoaderListener mOnRESTLoaderListener;

    private DisplayImageOptions mDefaultDisplayOptions = new DisplayImageOptions.Builder()
            .cacheOnDisk(true)
            .cacheInMemory(true)
            .considerExifParams(true)
            .resetViewBeforeLoading(false)
            .bitmapConfig(Bitmap.Config.RGB_565)
            .imageScaleType(ImageScaleType.EXACTLY)
            .build();
    private DisplayImageOptions mAvatarOptions = new DisplayImageOptions.Builder()
            .cacheOnDisk(true)
            .cacheInMemory(true)
            .considerExifParams(true)
            .resetViewBeforeLoading(false)
            .bitmapConfig(Bitmap.Config.RGB_565)
            .imageScaleType(ImageScaleType.EXACTLY)
            .showImageOnLoading(R.drawable.header_big_default)
            .showImageForEmptyUri(R.drawable.header_big_default)
            .showImageOnFail(R.drawable.header_big_default)
            .build();
    private DisplayImageOptions mBackgroundOptions = new DisplayImageOptions.Builder()
            .cacheOnDisk(true)
            .cacheInMemory(true)
            .considerExifParams(true)
            .resetViewBeforeLoading(false)
            .bitmapConfig(Bitmap.Config.RGB_565)
            .imageScaleType(ImageScaleType.EXACTLY)
            .showImageOnLoading(R.drawable.home_bg)
            .showImageForEmptyUri(R.drawable.home_bg)
            .showImageOnFail(R.drawable.home_bg)
            .build();
    private DisplayImageOptions mGiftOptions = new DisplayImageOptions.Builder()
            .cacheOnDisk(true)
            .cacheInMemory(true)
            .considerExifParams(true)
            .resetViewBeforeLoading(false)
            .bitmapConfig(Bitmap.Config.RGB_565)
            .imageScaleType(ImageScaleType.EXACTLY)
            .showImageOnLoading(R.drawable.gif_default)
            .showImageForEmptyUri(R.drawable.gif_default)
            .showImageOnFail(R.drawable.gif_default)
            .build();

    public DisplayImageOptions getDefaultDisplayOptions() {
        return mDefaultDisplayOptions;
    }

    public DisplayImageOptions getAvatarOptions() {
        return mAvatarOptions;
    }

    public DisplayImageOptions getBackgroundOptions() {
        return mBackgroundOptions;
    }

    public DisplayImageOptions getGiftOptions() {
        return mGiftOptions;
    }

    protected abstract int getLayoutResId();

    protected abstract void onTKGZCreateView(View rootView);

    protected abstract void onTKGZActivityCreated();

    public abstract String getTransactionTag();

    public abstract boolean isHideActionBar();

    public abstract String getActionBarTitle();

    public boolean isEmptyTopMargin() {
        return false;
    }

    public void onRefreshData() {
    }

    protected View getRootView() {
        return mRootView;
    }

    @Override
    protected void onXOAttach(Activity activity) {
        if (activity instanceof OnScreenNavigationListener) {
            mOnScreenNavigationListener = (OnScreenNavigationListener) activity;
        }
        if (activity instanceof ActivityUIController) {
            mActivityUIController = (ActivityUIController) activity;
        }
        mOnRESTLoaderListener = (XOAbstractRESTLoader.OnRESTLoaderListener) activity;
        onTKGZAttach(activity);
    }

    protected void onTKGZAttach(Activity activity) {
    }

    protected void onTKGZCreate(Bundle savedInstanceState) {
    }

    @Override
    protected final void onXOCreate(Bundle savedInstanceState) {
        setHasOptionsMenu(hasTKGZOptionsMenu());
        onTKGZCreate(savedInstanceState);
    }

    protected boolean hasTKGZOptionsMenu() {
        return true;
    }

    @Override
    protected View onXOCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView = inflater.inflate(getLayoutResId(), container, false);
        onTKGZCreateView(mRootView);
        return mRootView;
    }

    @Override
    protected void onXOViewCreated(View view, Bundle savedInstanceState) {
        view.setOnTouchListener(this);
    }

    @Override
    protected void onXOActivityCreated(Bundle savedInstanceState) {
        onTKGZActivityCreated();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        int menuResourceId = getMenuResourceId();
        menu.clear();
        if (menuResourceId > 0) {
            menuInflater.inflate(menuResourceId, menu);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        boolean isEventConsumed = false;
        switch (menuItem.getItemId()) {
            case R.id.admin_entry:
                getOnScreenNavigationListener().navigateToCreateNewEventPage();
                isEventConsumed=true;
                break;

            case android.R.id.home:
                hideKeyboard();
                getOnScreenNavigationListener().goBackFromCurrentPage();
                isEventConsumed = true;
                break;
            case R.id.change_password:
                getOnScreenNavigationListener().navigateToChangePasswordPage();
                isEventConsumed = true;
                break;
            case R.id.change_language:
                Resources resources = getResources();
                Configuration configuration = resources.getConfiguration();
                if (configuration.locale.getLanguage().equals("zh")) {
                    TKGZApplication.getInstance().switchLanguage("en");
                } else {
                    TKGZApplication.getInstance().switchLanguage("zh");
                }
                getOnScreenNavigationListener().changeLanguageFromCurrentPage();
                isEventConsumed = true;
                break;
            case R.id.about_the_knot:
                getOnScreenNavigationListener().navigateToAboutTheKnotPage();
                isEventConsumed = true;
                break;
            case R.id.exit_login:
                getOnScreenNavigationListener().navigateToLoginPage();
                getOnScreenNavigationListener().logOutFromCurrentPage();
                TKGZApplication.getInstance().clearUserProfile();
                isEventConsumed = true;
                break;
        }

        return isEventConsumed;
    }

    public static OnScreenNavigationListener getOnScreenNavigationListener() {
        return mOnScreenNavigationListener;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return true;
    }


    //for activity ui controller
    protected void showDialog(int titleResId, int messageResId) {
        if (mActivityUIController != null) {
            mActivityUIController.showDialog(titleResId, messageResId);
        }
    }

    protected void showCancelButtonDialog(int titleResId, int contentTitleLeft, String contentTitleRight, String message, int okButtonMessageResId, int cancelButtonMessageResId,
                                          OnDialogActionCallback onDialogActionCallback, OnDismissDialogCallback onDismissDialogCallback) {
        if (mActivityUIController != null) {
            mActivityUIController.showCancelButtonDialog(titleResId, contentTitleLeft, contentTitleRight, message, okButtonMessageResId, cancelButtonMessageResId,
                    onDialogActionCallback, onDismissDialogCallback);
        }
    }

    protected void showEditMessageDialog(int titleResId, String message, int okButtonMessageResId, int cancelButtonMessageResId,
                                         OnDialogActionCallback onDialogActionCallback, OnDismissDialogCallback onDismissDialogCallback) {
        if (mActivityUIController != null) {
            mActivityUIController.showEditMessageDialog(titleResId, message, okButtonMessageResId, cancelButtonMessageResId,
                    onDialogActionCallback, onDismissDialogCallback);
        }
    }

    //create by ayu, user for demo. maybe useless for finally app.
    protected void showEditMessageDialog(int titleResId, String message, int okButtonMessageResId, int cancelButtonMessageResId,
                                         OnDialogActionCallback onDialogActionCallback, OnDismissDialogCallback onDismissDialogCallback, TKGZCustomEditDialog.OnCustomEditDialogListener listener) {
        if (mActivityUIController != null) {
            mActivityUIController.showEditMessageDialog(titleResId, message, okButtonMessageResId, cancelButtonMessageResId,
                    onDialogActionCallback, onDismissDialogCallback, listener);
        }
    }

    protected void hideKeyboard() {
        if (mActivityUIController != null) {
            mActivityUIController.hideKeyboard();
        }
    }

    protected void showKeyboard() {
        if (mActivityUIController != null) {
            mActivityUIController.showKeyboard();
        }
    }

    protected int getMenuResourceId() {
        return -1;
    }

    protected OnDialogActionCallback getDialogActionCallback() {
        return null;
    }

    protected OnDismissDialogCallback getDismissDialogCallback() {
        return null;
    }

    protected void initLoader(XOAbstractRESTLoader xoAbstractRESTLoader) {
        if (getLoaderManager().getLoader(xoAbstractRESTLoader.getLoaderId()) != null) {
            getLoaderManager().destroyLoader(xoAbstractRESTLoader.getLoaderId());
        }
        getLoaderManager().initLoader(xoAbstractRESTLoader.getLoaderId(), null, xoAbstractRESTLoader);
    }

    //OnRESTLoaderListener
    @Override
    public Context getContext() {
        return getActivity();
    }

    @Override
    public void showToast(String message) {
        mOnRESTLoaderListener.showToast(message);
    }

    @Override
    public void showSpinner() {
        mOnRESTLoaderListener.showSpinner();
    }

    @Override
    public void hideSpinner() {
        mOnRESTLoaderListener.hideSpinner();
    }

    @Override
    public void showMessage(String message) {
        mOnRESTLoaderListener.showMessage(message);
    }

    @Override
    public void showMessage(String title, String message) {
        mOnRESTLoaderListener.showMessage(title, message);
    }

    @Override
    public boolean isWifiAvailable() {
        return mOnRESTLoaderListener.isWifiAvailable();
    }

    public boolean isPermissionGranted(String permission, int requestCode, String message) {
        boolean permissionGranted = true;
        if (ContextCompat.checkSelfPermission(getContext(), permission) != PackageManager.PERMISSION_GRANTED) {
            permissionGranted = false;
            if (shouldShowRequestPermissionRationale(permission)) {
                showToast(message);
            } else {
                requestPermissions(new String[]{permission}, requestCode);
            }
        }
        return permissionGranted;
    }

    @Override
    public LoaderManager getXOLoaderManager() {
        return getLoaderManager();
    }

    @Override
    public Logger getLogger() {
        return mLogger;
    }

    protected boolean folderIsExist(File folder) {
        return (!folder.exists() || folder.isFile()) && folder.mkdir();
    }

}
