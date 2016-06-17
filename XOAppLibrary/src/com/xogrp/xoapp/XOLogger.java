package com.xogrp.xoapp;

import org.slf4j.Logger;
import org.slf4j.Marker;

public class XOLogger 
implements Logger {
	@Override
	public void info(String message) {
		info(message, null, null);
	}
	
	@Override
	public void info(String message, Object object) {
		info(message, object, null);
	}
	
	@Override
	public void info(String message, Object object1, Object object2) {
	}
	
	@Override
	public void info(String message, Object...objects) {
	}
	
	@Override
	public void info(String message, Throwable throwable) {
	}
	
	@Override
	public void info(Marker marker, String message) {
	}
	
	@Override
	public void info(Marker marker, String message, Object object) {
	}
	
	@Override
	public void info(Marker marker, String message, Object object1, Object object2) {
	}
	
	@Override
	public void info(Marker marker, String message, Object...objects) {
	}
	
	@Override
	public void info(Marker marker, String message, Throwable throwable) {
	}
	
	@Override
	public boolean isTraceEnabled() {
		return false;
	}
	
	@Override
	public boolean isTraceEnabled(Marker marker) {
		return false;
	}
	
	@Override
	public void trace(String message) {
	}

	@Override
	public void debug(String arg0) {
	}

	@Override
	public void debug(String arg0, Object arg1) {
	}

	@Override
	public void debug(String arg0, Object... arg1) {
	}

	@Override
	public void debug(String arg0, Throwable arg1) {
	}

	@Override
	public void debug(Marker arg0, String arg1) {		
	}

	@Override
	public void debug(String arg0, Object arg1, Object arg2) {
	}

	@Override
	public void debug(Marker arg0, String arg1, Object arg2) {
	}

	@Override
	public void debug(Marker arg0, String arg1, Object... arg2) {
	}

	@Override
	public void debug(Marker arg0, String arg1, Throwable arg2) {
	}

	@Override
	public void debug(Marker arg0, String arg1, Object arg2, Object arg3) {
	}

	@Override
	public void error(String arg0) {
	}

	@Override
	public void error(String arg0, Object arg1) {
	}

	@Override
	public void error(String arg0, Object... arg1) {
	}

	@Override
	public void error(String arg0, Throwable arg1) {
	}

	@Override
	public void error(Marker arg0, String arg1) {
	}

	@Override
	public void error(String arg0, Object arg1, Object arg2) {
	}

	@Override
	public void error(Marker arg0, String arg1, Object arg2) {
	}

	@Override
	public void error(Marker arg0, String arg1, Object... arg2) {
	}

	@Override
	public void error(Marker arg0, String arg1, Throwable arg2) {
	}

	@Override
	public void error(Marker arg0, String arg1, Object arg2, Object arg3) {
	}

	@Override
	public String getName() {
		return null;
	}

	@Override
	public boolean isDebugEnabled() {
		return false;
	}

	@Override
	public boolean isDebugEnabled(Marker arg0) {
		return false;
	}

	@Override
	public boolean isErrorEnabled() {
		return false;
	}

	@Override
	public boolean isErrorEnabled(Marker arg0) {
		return false;
	}

	@Override
	public boolean isInfoEnabled() {
		return false;
	}

	@Override
	public boolean isInfoEnabled(Marker arg0) {
		return false;
	}

	@Override
	public boolean isWarnEnabled() {
		return false;
	}

	@Override
	public boolean isWarnEnabled(Marker arg0) {
		return false;
	}

	@Override
	public void trace(String arg0, Object arg1) {
	}

	@Override
	public void trace(String arg0, Object... arg1) {
	}

	@Override
	public void trace(String arg0, Throwable arg1) {
	}

	@Override
	public void trace(Marker arg0, String arg1) {
	}

	@Override
	public void trace(String arg0, Object arg1, Object arg2) {
	}

	@Override
	public void trace(Marker arg0, String arg1, Object arg2) {
	}

	@Override
	public void trace(Marker arg0, String arg1, Object... arg2) {
	}

	@Override
	public void trace(Marker arg0, String arg1, Throwable arg2) {
	}

	@Override
	public void trace(Marker arg0, String arg1, Object arg2, Object arg3) {
	}

	@Override
	public void warn(String arg0) {
	}

	@Override
	public void warn(String arg0, Object arg1) {
	}

	@Override
	public void warn(String arg0, Object... arg1) {
	}

	@Override
	public void warn(String arg0, Throwable arg1) {
	}

	@Override
	public void warn(Marker arg0, String arg1) {
	}

	@Override
	public void warn(String arg0, Object arg1, Object arg2) {
	}

	@Override
	public void warn(Marker arg0, String arg1, Object arg2) {
	}

	@Override
	public void warn(Marker arg0, String arg1, Object... arg2) {
	}

	@Override
	public void warn(Marker arg0, String arg1, Throwable arg2) {
	}

	@Override
	public void warn(Marker arg0, String arg1, Object arg2, Object arg3) {
	}
}
