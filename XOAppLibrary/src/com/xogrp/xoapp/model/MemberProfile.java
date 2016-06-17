package com.xogrp.xoapp.model;

import java.io.Serializable;

public abstract class MemberProfile implements Serializable {
	private String id;
	private String tokenValue;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTokenValue() {
		return tokenValue;
	}

	public void setTokenValue(String tokenValue) {
		this.tokenValue = tokenValue;
	}
	
	public abstract String getTokenName();
}
