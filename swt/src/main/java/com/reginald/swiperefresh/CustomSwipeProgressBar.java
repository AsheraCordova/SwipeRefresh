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

import r.android.view.View;

public class CustomSwipeProgressBar {
	private LineSwipeProgressBar lineSwipeProgressBar;
	private View parent;
	
	public CustomSwipeProgressBar(View parent) {
		this.parent = parent;	
	}
	public void setTriggerPercentage(float f) {
		lineSwipeProgressBar.setTriggerPercentage(f);
	}

	public void stop() {
		if (lineSwipeProgressBar != null) {
			lineSwipeProgressBar.stop();
		}
	}

	public void start() {
		init();
		lineSwipeProgressBar.start();
	}

	public void setBounds(int left, int top, int right, int bottom) {
		init();
		lineSwipeProgressBar.setBounds(left, top, right - left, bottom - top);
		lineSwipeProgressBar.moveAbove(null);
	}
	private void init() {
		if (lineSwipeProgressBar == null) {
			lineSwipeProgressBar = new LineSwipeProgressBar((org.eclipse.swt.widgets.Composite)parent.getOuterWidget().asNativeWidget(), org.eclipse.swt.SWT.NONE);
		}
	}
	public void setProgressBarColor(Object color1, Object color2, Object color3, Object color4) {
		init();
		lineSwipeProgressBar.setProgressBarColor(color1, color2, color3, color4);
		
	}

}
