package com.xogrp.xoapp.model;

import java.util.regex.Pattern;

public class Validator {
	private static final Pattern PATTERN_EMAIL = Pattern.compile("^([a-z0-9A-Z]+[-|+|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+)(\\.[a-zA-Z]{2,})(\\.[a-zA-Z]{2,})?$");
	private static final Pattern PATTERN_WEBSITE_URL = Pattern.compile("^[a-z0-9A-Z]+([-]?[a-z0-9A-Z]+)*$");
	private static final Pattern PATTERN_PURE_NUMBER = Pattern.compile("^[0-9]*$");

	public static boolean isEmailAddressFormatValid(String emailAddress) {
		return PATTERN_EMAIL.matcher(emailAddress).matches();
	}
	
	public static boolean isWebsiteUrlVaild(String websiteUrl) {
		return PATTERN_WEBSITE_URL.matcher(websiteUrl).matches();
	}
	
	public static boolean isNameContainSpecialCharacter(String name) {  
        return !name.matches("^[0-9a-zA-Z]*$");
    }

	public static boolean isUrlOnlyNumber(String url) {
		return PATTERN_PURE_NUMBER.matcher(url).matches();
	}
}
