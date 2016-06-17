package com.xogrp.xoapp.provider;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;

import com.xogrp.xoapp.NetworkNotAvailableException;
import com.xogrp.xoapp.R;
import com.xogrp.xoapp.spi.RESTResponse;
import com.xogrp.xoapp.spi.RESTfulApiClient;
import com.xogrp.xoapp.spi.RESTfulApiClient.RESTfulApiCallback;

import org.slf4j.Logger;

import java.net.HttpURLConnection;

public abstract class XOAbstractRESTLoader
		extends AsyncTaskLoader<RESTResponse> implements LoaderManager.LoaderCallbacks<RESTResponse> {
	private Logger mLogger;

	private RESTfulApiCallback mRESTfulApiCallback;
	private OnRESTLoaderListener mOnRESTLoaderListener;

	public XOAbstractRESTLoader(RESTfulApiCallback callback, OnRESTLoaderListener onRESTLoaderListener) {
		super(onRESTLoaderListener.getContext());
		mRESTfulApiCallback = callback;
		mOnRESTLoaderListener = onRESTLoaderListener;
		setLogger(onRESTLoaderListener.getLogger());
	}

	public void setLogger(Logger logger) {
		mLogger = logger;
	}

	@Override
	protected void onStartLoading() {
		forceLoad();
	}

	@Override
	public RESTResponse loadInBackground() {
		RESTResponse restResponse = null;
		mRESTfulApiCallback.setLogger(mLogger);
		if (!mOnRESTLoaderListener.isWifiAvailable()) {
			restResponse = new RESTResponse("Network is not available", new NetworkNotAvailableException());
			mOnRESTLoaderListener.showToast("Network is not available");
		} else {
			restResponse = getRESTfulApiClient().sendRequest(getUrl(), mRESTfulApiCallback, mLogger);
		}

		if (mOnRESTLoaderListener.getContext() != null) {
			mOnRESTLoaderListener.hideSpinner();
			mOnRESTLoaderListener.getXOLoaderManager().destroyLoader(getLoaderId());
		}
		return restResponse;
	}

	public void cancel() {
		mRESTfulApiCallback.cancel();
	}

	protected abstract String getUrl();

	protected abstract RESTfulApiClient getRESTfulApiClient();

	public int getLoaderId() {
		return getUrl().hashCode();
	}

	@Override
	public Loader<RESTResponse> onCreateLoader(int id, Bundle args) {
		return this;
	}

	@Override
	public void onLoadFinished(Loader<RESTResponse> loader, RESTResponse restResponse) {
		if (restResponse.getStatusCode() == HttpURLConnection.HTTP_CLIENT_TIMEOUT) {
			mOnRESTLoaderListener.showToast(getContext().getResources().getString(R.string.toast_time_out));
		}
	}

	@Override
	public void onLoaderReset(Loader<RESTResponse> loader) {

	}

	public interface OnRESTLoaderListener {
		Context getContext();

		void showToast(String message);

		void showSpinner();

		void hideSpinner();

		void showMessage(String message);

		void showMessage(String title, String message);

		LoaderManager getXOLoaderManager();

		Logger getLogger();

		boolean isWifiAvailable();
	}

}
