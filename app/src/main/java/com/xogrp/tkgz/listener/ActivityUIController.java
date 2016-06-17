package com.xogrp.tkgz.listener;

import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;

import com.xogrp.tkgz.Widget.TKGZCustomDialog.OnDialogActionCallback;
import com.xogrp.tkgz.Widget.TKGZCustomDialog.OnDismissDialogCallback;
import com.xogrp.tkgz.Widget.TKGZCustomEditDialog;


/**
 * Created by dgao on 7/29/2015.
 * please add more if you need!
 */
public interface ActivityUIController {
    public void hideKeyboard();
    public void showKeyboard();
    public void showDialog(int titleResId,int messageResId);
//    public void showSpinner();
    public ActionBar getTKActionBar();
    public Toolbar getTKToolBar();
    public void showCancelButtonDialog(int titleResId, int contentTitleLeft, String contentTitleRight, String message, int okButtonMessageResId,
                    int cancelButtonMessageResId, OnDialogActionCallback onDialogActionCallback, OnDismissDialogCallback onDismissDialogCallback);
    public void showEditMessageDialog(int titleResId, String message, int okButtonMessageResId,
                                       int cancelButtonMessageResId, OnDialogActionCallback onDialogActionCallback, OnDismissDialogCallback onDismissDialogCallback);
    public void showEditMessageDialog(int titleResId, String message, int okButtonMessageResId,
                                       int cancelButtonMessageResId, OnDialogActionCallback onDialogActionCallback, OnDismissDialogCallback onDismissDialogCallback,TKGZCustomEditDialog.OnCustomEditDialogListener listener);
}
