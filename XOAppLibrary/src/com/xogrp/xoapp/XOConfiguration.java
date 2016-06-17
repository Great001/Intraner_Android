package com.xogrp.xoapp;

import java.util.Properties;

public abstract class XOConfiguration {
    protected String getPropertyAsString(Properties properties, String propertyName)
            throws XOMissingConfigurationException {
        return getPropertyAsString(properties, propertyName, true);
    }

    protected String getPropertyAsString(Properties properties, String propertyName, boolean isCompulsory)
            throws XOMissingConfigurationException {
        String value = properties.getProperty(propertyName);
        if (isCompulsory && (value == null || value.trim().length() == 0)) {
            throw new XOMissingConfigurationException(propertyName);
        }

        return value;
    }

    protected int getPropertyAsInt(Properties properties, String propertyName)
            throws XOMissingConfigurationException, XOConfigurationException {
        int propertyValue = 0;
        String stringValue = null;
        try {
            stringValue = getPropertyAsString(properties, propertyName);
            propertyValue = Integer.parseInt(stringValue);
        } catch (NumberFormatException nfe) {
            throw new XOConfigurationException(String.format("Exception integer value of property %s: %s", propertyName, stringValue));
        }

        return propertyValue;
    }

    public abstract int getConnectTimeout();
	public abstract String getLogBackEncoderPattern();
    public abstract String getLogBackLogFilenamePattern();
    public abstract int getLogBackLogMaxHistory();
    public abstract String getLogBackLogLevel();
}
