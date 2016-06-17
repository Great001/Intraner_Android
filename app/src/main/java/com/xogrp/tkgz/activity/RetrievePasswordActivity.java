package com.xogrp.tkgz.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.xogrp.tkgz.R;
import com.xogrp.tkgz.Widget.TKGZCustomDialog;
import com.xogrp.tkgz.provider.RetrievePasswordProvider;
import com.xogrp.tkgz.spi.RetrievePasswordApiCallBack;
import com.xogrp.xoapp.model.Validator;
import com.xogrp.xoapp.provider.XOAbstractRESTLoader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by ayu on 2/18/2016 0018.
 */
public class RetrievePasswordActivity extends FragmentActivity implements XOAbstractRESTLoader.OnRESTLoaderListener {

	private Logger mLogger = LoggerFactory.getLogger(this.getClass());
	private ProgressDialog mDialog;
	private XOAbstractRESTLoader.OnRESTLoaderListener mOnRESTLoaderListener;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_retrieve_password);
		final EditText etEmail = (EditText) findViewById(R.id.et_email);
		mOnRESTLoaderListener = this;

		mDialog = new ProgressDialog(this);
		mDialog.setTitle(getString(R.string.loading));
		mDialog.setCanceledOnTouchOutside(false);

		Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
		toolbar.setNavigationOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});

		findViewById(R.id.btn_ensure).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String email = etEmail.getText().toString();
				if (TextUtils.isEmpty(email)) {
					showMessage(getString(R.string.dialog_message_space));
				} else if (Validator.isEmailAddressFormatValid(email)) {
					showSpinner();
					initLoader(RetrievePasswordProvider.getRetrievePasswordProvider(email, RetrievePasswordActivity.this, new RetrievePasswordApiCallBack.OnRetrievePasswordApiListener() {
						@Override
						public void retrievePasswordResult(final String message) {
							final Activity activity = RetrievePasswordActivity.this;
							activity.runOnUiThread(new Runnable() {
								@Override
								public void run() {
									showMessage(message);
								}
							});
						}
					}));
				} else {
					showMessage(getString(R.string.dialog_message_format_error));
				}
			}
		});
	}

	@Override
	public Context getContext() {
		return this;
	}

	@Override
	public void showToast(String message) {

	}

	@Override
	public void showSpinner() {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				mDialog.show();
			}
		});

	}

	@Override
	public void hideSpinner() {
		if (mDialog.isShowing()) {
			runOnUiThread(new Runnable() {
				@Override
				public void run() {
					mDialog.dismiss();
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
		return mLogger;
	}

	@Override
	public boolean isWifiAvailable() {
		ConnectivityManager connectivityManager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo wifiInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		return wifiInfo != null && wifiInfo.isConnected() && wifiInfo.isAvailable();
	}

	protected void initLoader(XOAbstractRESTLoader xoAbstractRESTLoader) {
		if (getSupportLoaderManager().getLoader(xoAbstractRESTLoader.getLoaderId()) != null) {
			getSupportLoaderManager().destroyLoader(xoAbstractRESTLoader.getLoaderId());
		}
		getSupportLoaderManager().initLoader(xoAbstractRESTLoader.getLoaderId(), null, xoAbstractRESTLoader);
	}
}
