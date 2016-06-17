package com.xogrp.xoapp;

import android.app.Application;
import android.graphics.Bitmap;
import android.text.TextUtils;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.android.LogcatAppender;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.rolling.RollingFileAppender;
import ch.qos.logback.core.rolling.TimeBasedRollingPolicy;

public abstract class XOApplication
        extends Application {
    private DisplayImageOptions mDefaultDisplayImageOptions;
    private DisplayImageOptions mDisplayImageOptionsWithoutCache;

    public static boolean isTextEmptyOrNull(String text) {
        return TextUtils.isEmpty(text) || "null".equalsIgnoreCase(text);
    }

    @Override
    public final void onCreate() {
        super.onCreate();
        initConfiguration();
        configureLogBack();

        mDefaultDisplayImageOptions = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .imageScaleType(ImageScaleType.EXACTLY)
                .resetViewBeforeLoading(true)
//                .displayer(new FadeInBitmapDisplayer(500))
                .considerExifParams(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();
        mDisplayImageOptionsWithoutCache = new DisplayImageOptions.Builder()
                .imageScaleType(ImageScaleType.EXACTLY)
                .resetViewBeforeLoading(true)
                .considerExifParams(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();
        ImageLoaderConfiguration imageLoaderConfiguration = new ImageLoaderConfiguration
                .Builder(getApplicationContext())
                .threadPoolSize(5)
//                .defaultDisplayImageOptions(mDefaultDisplayImageOptions)
                .denyCacheImageMultipleSizesInMemory()
                .discCacheFileNameGenerator(new Md5FileNameGenerator())
                .build();
        ImageLoader.getInstance().init(imageLoaderConfiguration);

        onXOCreate();
    }

    protected abstract String getConfigurationAssetsPath();

    protected abstract XOConfiguration getConfiguration();

    protected abstract void initConfiguration();

    protected void onXOCreate() {
    }

    public Properties loadConfiguration()
            throws XOConfigurationException {
        InputStream inStream = null;
        Properties properties = new Properties();
        String configurationAssetsPath = getConfigurationAssetsPath();
        try {
            inStream = getAssets().open(configurationAssetsPath);
            properties.load(inStream);
        } catch (IOException ioe) {
            throw new XOConfigurationException(String.format("Cannot load configuration file (%s): %s", configurationAssetsPath, ioe.getMessage()));
        } finally {
            if (inStream != null) {
                try {
                    inStream.close();
                } catch (IOException ioe) {
                    // no harm to ignore exception here
                }
            }
        }

        return properties;
    }

    public DisplayImageOptions getDefaultDisplayImageOptions() {
        return mDefaultDisplayImageOptions;
    }

    public DisplayImageOptions getDisplayImageOptionsWithoutCache() {
        return mDisplayImageOptionsWithoutCache;
    }

    private void configureLogBack() {
        XOConfiguration xoConfiguration = getConfiguration();

        LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
        loggerContext.reset();

        PatternLayoutEncoder rollingFilePatternEncoder = new PatternLayoutEncoder();
        rollingFilePatternEncoder.setContext(loggerContext);
        rollingFilePatternEncoder.setPattern(xoConfiguration.getLogBackEncoderPattern());
        rollingFilePatternEncoder.start();

        RollingFileAppender<ILoggingEvent> rollingFileAppender = new RollingFileAppender<ILoggingEvent>();
        rollingFileAppender.setContext(loggerContext);
        rollingFileAppender.setAppend(true);

        TimeBasedRollingPolicy<ILoggingEvent> rollingPolicy = new TimeBasedRollingPolicy<ILoggingEvent>();
        rollingPolicy.setFileNamePattern(xoConfiguration.getLogBackLogFilenamePattern());
        rollingPolicy.setMaxHistory(xoConfiguration.getLogBackLogMaxHistory());
        rollingPolicy.setParent(rollingFileAppender);
        rollingPolicy.setContext(loggerContext);
        rollingPolicy.start();

        rollingFileAppender.setRollingPolicy(rollingPolicy);
        rollingFileAppender.setEncoder(rollingFilePatternEncoder);
        rollingFileAppender.start();

        PatternLayoutEncoder logcatPatternLayoutEncoder = new PatternLayoutEncoder();
        logcatPatternLayoutEncoder.setContext(loggerContext);
        logcatPatternLayoutEncoder.setPattern(xoConfiguration.getLogBackEncoderPattern());
        logcatPatternLayoutEncoder.start();

        LogcatAppender logcatAppender = new LogcatAppender();
        logcatAppender.setContext(loggerContext);
        logcatAppender.setEncoder(logcatPatternLayoutEncoder);
        logcatAppender.start();
        ch.qos.logback.classic.Logger rootLogger = (ch.qos.logback.classic.Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
        rootLogger.setLevel(Level.valueOf(xoConfiguration.getLogBackLogLevel()));
        rootLogger.addAppender(rollingFileAppender);
        rootLogger.addAppender(logcatAppender);
    }
}
