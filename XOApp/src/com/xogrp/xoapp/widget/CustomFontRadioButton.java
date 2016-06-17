package com.xogrp.xoapp.widget;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.RadioButton;

public abstract class CustomFontRadioButton 
		extends RadioButton {
	
	public CustomFontRadioButton(Context context) {
		super(context);
		init();
	}
	
	public CustomFontRadioButton(Context context, AttributeSet attributeSet) {
		super(context, attributeSet);
		if (!isInEditMode()) {
			init();
		}
	}
	
	public CustomFontRadioButton(Context context, AttributeSet attributeSet, int defaultStyle) {
		super(context, attributeSet, defaultStyle);
		init();
	}
	
	protected abstract Typeface getCustomTypeface();
	
	protected void init() {
		setTypeface(getCustomTypeface());
	}
}
