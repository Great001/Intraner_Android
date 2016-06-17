package com.xogrp.tkgz.Widget;

import android.support.v4.view.ViewPager;

public interface PagerIndicator
extends ViewPager.OnPageChangeListener {
	public void setViewPager(ViewPager viewPager);
	
	public void setViewPager(ViewPager viewPager, int initialPosition);
	
	public void setCurrentItem(int item);
	
	public void setOnPageChangeListener(ViewPager.OnPageChangeListener onPageChangeListener);
}
