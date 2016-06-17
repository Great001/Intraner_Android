package com.xogrp.tkgz.fragment;

import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.xogrp.tkgz.R;
import com.xogrp.tkgz.Widget.TKGZCustomDialog;
import com.xogrp.tkgz.Widget.TKGZCustomDialog.OnDialogActionCallback;

public class AboutTheKnotFragment extends AbstractTKGZFragment
                    implements OnDialogActionCallback{
    private TextView mTvWelcomePage, mTvCheckNewVersions;

    @Override
    protected void onTKGZAttach(Activity activity) {
        super.onTKGZAttach(activity);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.about_the_knot_layout;
    }

    @Override
    protected void onTKGZCreateView(View rootView) {
//        getTKActionbar().setDisplayHomeAsUpEnabled(true);
//        getTKActionbar().setHomeAsUpIndicator(R.drawable.icon_back);
        mTvWelcomePage = (TextView)rootView.findViewById(R.id.tv_welcome_page);
        mTvCheckNewVersions = (TextView)rootView.findViewById(R.id.tv_check_new_versions);
        mTvCheckNewVersions.setOnClickListener(onCheckNewVersionsListener);
    }

    @Override
    protected void onTKGZActivityCreated() {

    }

    @Override
    public String getTransactionTag() {
        return "about_the_knot_fragment";
    }

    @Override
    public boolean isHideActionBar() {
        return false;
    }

    @Override
    public String getActionBarTitle() {
        return getString(R.string.actionbar_title_about_the_knot_page);
    }

    @Override
    protected int getMenuResourceId() {
        return -1;
    }

    private OnClickListener onCheckNewVersionsListener = new OnClickListener() {
        @Override
        public void onClick(View view) {
            showCancelButtonDialog(R.string.update_version_dialog_title, R.string.dialog_content_title,"V 1.1.0", getString(R.string.dialog_update_message),
                    R.string.dialog_download, R.string.dialog_cancel_button, getDialogActionCallback(), getDismissDialogCallback());
        }
    };

    @Override
    public void onConfirmSelected() {
    }

    @Override
    protected TKGZCustomDialog.OnDialogActionCallback getDialogActionCallback() {
        return this;
    }

}
