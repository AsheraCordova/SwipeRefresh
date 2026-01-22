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

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public final class LineSwipeProgressBar extends Canvas {

	private static final int ANIMATION_DURATION_MS = 2000;
	private static final int FINISH_DURATION_MS = 1000;
	private static final int FRAME_DELAY = 16; // ~60fps
	private static final int LINE_HEIGHT = 6;

	private boolean running;
	private float triggerPercent;
	private long startTime;
	private long finishTime;

	private Color c1, c2, c3, c4;

	public LineSwipeProgressBar(Composite parent, int style) {
		super(parent, style | SWT.DOUBLE_BUFFERED);

		Display d = getDisplay();
		c1 = new Color(d, 0, 0, 0); // blue
		c2 = new Color(d, 255, 255, 255); // red
		c3 = new Color(d, 0, 0, 0); // yellow
		c4 = new Color(d, 255, 255, 255); // green

		addPaintListener(this::onPaint);

		addDisposeListener(e -> {
			c1.dispose();
			c2.dispose();
			c3.dispose();
			c4.dispose();
		});
	}

	/* ================= Public API ================= */

	public void setTriggerPercentage(float pct) {
		triggerPercent = clamp(pct);
		startTime = 0;
		redraw();
	}

	public void start() {
		if (!running) {
			running = true;
			triggerPercent = 0;
			startTime = now();
			schedule();
		}
	}

	public void stop() {
		if (running) {
			running = false;
			finishTime = now();
			schedule();
		}
	}

	public boolean isRunning() {
		return running || finishTime > 0;
	}

	@Override
	public void redraw() {
		if (!this.isDisposed()) {
			super.redraw();
		}
	}
	
	/* ================= Rendering ================= */

	private void onPaint(PaintEvent e) {
		GC gc = e.gc;
		Rectangle b = getClientArea();

		int cy = b.y + b.height / 2;
		int halfWidth = b.width / 2;

		long t = now();

		if (running || finishTime > 0) {
			float progress = ((t - startTime) % ANIMATION_DURATION_MS) / (float) ANIMATION_DURATION_MS;

			if (!running) {
				long elapsed = t - finishTime;
				if (elapsed >= FINISH_DURATION_MS) {
					finishTime = 0;
					return;
				}

				int clear = (int) (halfWidth * Interpolator.ease(elapsed / (float) FINISH_DURATION_MS));

				gc.setClipping(b.x + halfWidth - clear, b.y, clear * 2, b.height);
			}

			drawBackground(gc, b);
			drawWaves(gc, b, cy, progress);

			schedule();
		} else if (triggerPercent > 0) {
			drawTrigger(gc, b, cy);
		}
	}

	private void drawBackground(GC gc, Rectangle b) {
		gc.setBackground(c4);
		gc.fillRectangle(b);
	}

	private void drawWaves(GC gc, Rectangle b, int cy, float p) {
		drawSegment(gc, b, cy, c1, (p + 0.25f) * 2);
		drawSegment(gc, b, cy, c2, p * 2);
		drawSegment(gc, b, cy, c3, (p - 0.25f) * 2);
		drawSegment(gc, b, cy, c4, (p - 0.5f) * 2);
	}

	private void drawSegment(GC gc, Rectangle b, int cy, Color c, float pct) {
		pct = clamp(pct);
		if (pct == 0)
			return;

		int half = (int) (b.width / 2 * Interpolator.ease(pct));

		gc.setBackground(c);
		gc.fillRectangle(b.x + b.width / 2 - half, cy - LINE_HEIGHT / 2, half * 2, LINE_HEIGHT);
	}

	private void drawTrigger(GC gc, Rectangle b, int cy) {
		int half = (int) (b.width / 2 * triggerPercent);
		gc.setBackground(c1);
		gc.fillRectangle(b.x + b.width / 2 - half, cy - LINE_HEIGHT / 2, half * 2, LINE_HEIGHT);
	}

	private void schedule() {
		if (!isDisposed()) {
			getDisplay().timerExec(FRAME_DELAY, this::redraw);
		}
	}

	private static float clamp(float v) {
		return Math.max(0f, Math.min(1f, v));
	}

	private static long now() {
		return System.currentTimeMillis();
	}

	/* ================= Interpolator ================= */

	static final class Interpolator {
		static float ease(float t) {
			t = clamp(t);
			return (float) (1 - Math.pow(1 - t, 3)); // fast-out slow-in
		}
	}

	public static void main(String[] args) {
		Display display = new Display();
		Shell shell = new Shell(display);
		shell.setText("Top Line Progress");
		shell.setSize(500, 300);

		GridLayout layout = new GridLayout();
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		layout.verticalSpacing = 0;
		shell.setLayout(layout);

		/* --- Progress bar at top --- */
		LineSwipeProgressBar progress = new LineSwipeProgressBar(shell, SWT.NONE);

		GridData gd = new GridData(SWT.FILL, SWT.TOP, true, false);
		gd.heightHint = 4; // thin Material-style bar
		progress.setLayoutData(gd);

		/* --- Rest of content --- */
		Composite content = new Composite(shell, SWT.NONE);
		content.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		content.setLayout(new FillLayout());

		shell.open();
		progress.start();

		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		display.dispose();
	}

	public void setProgressBarColor(Object color1, Object color2, Object color3, Object color4) {
		c1 = (Color) color1;
		c2 = (Color) color2;
		c3 = (Color) color3;
		c4 = (Color) color4;
	}
}
