package com.xogrp.xoapp;

@SuppressWarnings("serial")
public class XOMissingConfigurationException 
extends XOConfigurationException {
	XOMissingConfigurationException(String propertyName) {
		super(String.format("Missing configuration property: %s", propertyName));
	}
}
