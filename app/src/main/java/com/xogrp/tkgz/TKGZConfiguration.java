package com.xogrp.tkgz;

import android.util.Log;

import com.xogrp.xoapp.XOConfiguration;
import com.xogrp.xoapp.XOConfigurationException;

import org.slf4j.LoggerFactory;

import java.util.Properties;

public class TKGZConfiguration
extends XOConfiguration {
	private static final String PROPERTIES_CONNECT_TIMEOUT = "connect_timeout";
	private static final String PROPERTIES_LOGBACK_ENCODER_PATTERN = "logback_encoder_pattern";
	private static final String PROPERTIES_LOGBACK_LOG_FILENAME_PATTERN = "logback_log_filename_pattern";
	private static final String PROPERTIES_LOGBACK_LOG_MAX_HISTORY = "logback_log_max_history";
	private static final String PROPERTIES_LOGBACK_LOG_LEVEL = "logback_log_level";
    private static final String PROPERTIES_WEB_SERVICE_HOST = "web_service_host";

	private static TKGZConfiguration sInstance;
	private int mConnectTimeout;
	private String mLogBackEncoderPattern;
	private String mLogBackLogFilenamePattern;
	private int mLogBackLogMaxHistory;
	private String mLogBackLogLevel;
    private String mWebServiceHost;

	public static void init(TKGZApplication tkgzApplication) {
		try {
			sInstance = new TKGZConfiguration(tkgzApplication.loadConfiguration());
        } catch(XOConfigurationException xoce) {
			LoggerFactory.getLogger("ShineConfiguration").error(
					String.format("(ShineDebug) error in reading configuration: %s", xoce.getMessage()));
		}
	}
	
	public static TKGZConfiguration getInstance() {
		return sInstance;
	}

    private TKGZConfiguration(Properties properties)
	throws XOConfigurationException {

		mConnectTimeout = getPropertyAsInt(properties, PROPERTIES_CONNECT_TIMEOUT);

		mLogBackEncoderPattern = getPropertyAsString(properties, PROPERTIES_LOGBACK_ENCODER_PATTERN);
		mLogBackLogFilenamePattern = getPropertyAsString(properties, PROPERTIES_LOGBACK_LOG_FILENAME_PATTERN);
		mLogBackLogMaxHistory = getPropertyAsInt(properties, PROPERTIES_LOGBACK_LOG_MAX_HISTORY);
		mLogBackLogLevel = getPropertyAsString(properties, PROPERTIES_LOGBACK_LOG_LEVEL);

        mWebServiceHost = getPropertyAsString(properties, PROPERTIES_WEB_SERVICE_HOST);
	}
	
	@Override
	public String getLogBackEncoderPattern() {
		return mLogBackEncoderPattern;
	}
	
	@Override
	public String getLogBackLogFilenamePattern() {
		return mLogBackLogFilenamePattern;
	}
	
	@Override
	public int getLogBackLogMaxHistory() {
		return mLogBackLogMaxHistory;
	}
	
	@Override
	public String getLogBackLogLevel() {
		return mLogBackLogLevel;
	}
	
	public int getConnectTimeout() {
		return mConnectTimeout;
	}

    public String getWebServiceHost(){
        return mWebServiceHost;
    }
	
}
