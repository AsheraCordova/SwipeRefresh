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

import org.teavm.jso.browser.Window;
import org.teavm.jso.dom.html.HTMLDocument;
import org.teavm.jso.dom.html.HTMLElement;

public final class LineSwipeProgressBar {
	private final HTMLElement bar;

	public LineSwipeProgressBar(HTMLElement parent) {
        HTMLDocument doc = Window.current().getDocument();

        HTMLElement style = doc.createElement("style");
        style.setInnerHTML(buildCss());
        parent.appendChild(style);

        bar = doc.createElement("div");
        bar.setAttribute("id", "top-progress");
        bar.getStyle().setProperty("position", "absolute");
        bar.getStyle().setProperty("z-index", "9999");
        bar.getStyle().setProperty("overflow", "hidden");
        bar.getStyle().setProperty("pointer-events", "none");

        setProgressBarColor("#000", "#fff", "#000", "#fff");
        parent.appendChild(bar);
    }

	/* ================= API ================= */

	/** 0..1 pull progress */
	public void setTriggerPercentage(float percent) {
		percent = clamp(percent);
		bar.setClassName("trigger");
		bar.getStyle().setProperty("--progress", String.valueOf(percent));
		bar.getStyle().setProperty("display", "block");
	}

	/** Indeterminate animation */
	public void start() {
		bar.setClassName("running");
		bar.getStyle().setProperty("display", "block");
	}

	/** Hide */
	public void stop() {
		bar.setClassName("");
		bar.getStyle().setProperty("display", "none");
	}

	/** Remove from DOM */
	public void dispose() {
		if (bar.getParentNode() != null) {
			bar.getParentNode().removeChild(bar);
		}
	}

	/* ================= Colors ================= */

	public void setProgressBarColor(Object c1, Object c2, Object c3, Object c4) {
		bar.getStyle().setProperty("--c1", (String) c1);
		bar.getStyle().setProperty("--c2", (String) c2);
		bar.getStyle().setProperty("--c3", (String) c3);
		bar.getStyle().setProperty("--c4", (String) c4);
	}

	/* ================= Helpers ================= */

	private static float clamp(float v) {
		return Math.max(0f, Math.min(1f, v));
	}

	/* ================= CSS ================= */

	private String buildCss() {
		return "#top-progress::before {" + " content: '';" + " position: absolute;" + " top: 0;" + " left: 50%;"
				+ " width: 100%;" + " height: 100%;" + " transform: translateX(-50%) scaleX(0);"
				+ " transform-origin: center;" + " background: linear-gradient(" + "   90deg," + "   var(--c1),"
				+ "   var(--c2)," + "   var(--c3)," + "   var(--c4)" + " );" + "}" 
				+ "#top-progress.trigger::before {" + " animation: none;"
				+ " transform: translateX(-50%) scaleX(var(--progress));" + "}" 
				+ "#top-progress.running::before {" + " animation: swipe 2s infinite ease-in-out;" + "}" 
				+ "@keyframes swipe {" + " 0%   { transform: translateX(-75%) scaleX(0.25); }"
				+ " 50%  { transform: translateX(0%)   scaleX(0.5); }"
				+ " 100% { transform: translateX(75%)  scaleX(0.25); }" + "}";
	}

	public void setBounds(int left, int top, int right, int bottom) {
        bar.getStyle().setProperty("top", top + "px");
        bar.getStyle().setProperty("left", left + "px");
        bar.getStyle().setProperty("width", (right - left) + "px");
        bar.getStyle().setProperty("height", (bottom - top) + "px");
		
	}
}
