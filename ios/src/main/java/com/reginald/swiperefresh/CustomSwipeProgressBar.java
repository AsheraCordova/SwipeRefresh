//start - license
/*
 * Copyright (c) 2025 Ashera Cordova
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 */
//end - license
package com.reginald.swiperefresh;

import com.google.j2objc.annotations.Property;

import r.android.view.View;

/*-[
#include <UIKit/UIKit.h>
#include "LineSwipeProgressBar.h"
]-*/
public class CustomSwipeProgressBar {
	private @Property Object lineSwipeProgressBar;
	private View parent;
	
	public CustomSwipeProgressBar(View parent) {
		this.parent = parent;	
	}
	
	public native void nativeSetTriggerPercentage(float f, Object view)/*-[
		[(LineSwipeProgressBar*)view setTriggerPercentage: f];
	]-*/;
	public void setTriggerPercentage(float f) {
		nativeSetTriggerPercentage(f, lineSwipeProgressBar);
	}

	public void stop() {
		if (lineSwipeProgressBar != null) {
			nativeStop(lineSwipeProgressBar);
		}
	}
	
	public native void nativeStop(Object view)/*-[
		[(LineSwipeProgressBar*)view stop];
	]-*/;

	public void start() {
		init();
		nativeStart(lineSwipeProgressBar);
	}

	public native void nativeStart(Object view)/*-[
		[(LineSwipeProgressBar*)view start];
	]-*/;
	public void setBounds(int left, int top, int right, int bottom) {
		init();
		com.ashera.layout.ViewImpl.nativeMakeFrame(lineSwipeProgressBar, left, top, right, bottom);
	}
	
	private void init() {
		if (lineSwipeProgressBar == null) {
			nativeCreate();
			com.ashera.layout.ViewGroupImpl.nativeAddView(parent.getOuterWidget().asNativeWidget(), lineSwipeProgressBar);
		}
	}
	
    public native void nativeCreate()/*-[
		LineSwipeProgressBar* uiView = [LineSwipeProgressBar new];
		lineSwipeProgressBar_ = uiView;
	]-*/;
	public void setProgressBarColor(Object color1, Object color2, Object color3, Object color4) {
		init();
		nativesetProgressBarColor(color1, color2, color3, color4, lineSwipeProgressBar);
		
	}
	
	public native void nativesetProgressBarColor(Object c1, Object c2, Object c3, Object c4, Object view)/*-[
		[(LineSwipeProgressBar*)view setColorScheme: (UIColor*) c1 :(UIColor*)c2 :(UIColor*)c3 :(UIColor*)c4];
	]-*/;

}
