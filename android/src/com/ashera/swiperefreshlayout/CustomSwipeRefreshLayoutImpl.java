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
package com.ashera.swiperefreshlayout;
// start - imports
import java.util.*;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.view.*;
import android.widget.*;
import android.view.View.*;

import com.ashera.widget.BaseHasWidgets;

import android.annotation.SuppressLint;

import com.ashera.core.IFragment;
import com.ashera.widget.bus.*;
import com.ashera.converter.*;
import com.ashera.widget.bus.Event.*;
import com.ashera.widget.*;
import com.ashera.widget.IWidgetLifeCycleListener.*;
import com.ashera.layout.*;

import android.graphics.Canvas;
import android.widget.*;
import androidx.core.view.*;
import android.view.*;

import static com.ashera.widget.IWidget.*;
//end - imports

import com.reginald.swiperefresh.CustomSwipeRefreshLayout;

public class CustomSwipeRefreshLayoutImpl extends BaseHasWidgets {
	//start - body
	public final static String LOCAL_NAME = "com.reginald.swiperefresh.CustomSwipeRefreshLayout"; 
	public final static String GROUP_NAME = "com.reginald.swiperefresh.CustomSwipeRefreshLayout";
	private com.reginald.swiperefresh.CustomSwipeRefreshLayout customSwipeRefreshLayout;
	

	
		@SuppressLint("NewApi")
		final static class RefreshMode extends AbstractEnumToIntConverter{
		private Map<String, Integer> mapping = new HashMap<>();
				{
				mapping.put("swipe_mode",  0x1);
				mapping.put("pull_mode",  0x2);
				}
		@Override
		public Map<String, Integer> getMapping() {
				return mapping;
				}

		@Override
		public Integer getDefault() {
				return 0;
				}
				}
	@Override
	public void loadAttributes(String localName) {
		ViewGroupImpl.register(localName);

		WidgetFactory.registerAttribute(localName, new WidgetAttribute.Builder().withName("customHeadLayout").withType("string"));
		ConverterFactory.register("com.reginald.swiperefresh.CustomSwipeRefreshLayout.refreshMode", new RefreshMode());
		WidgetFactory.registerAttribute(localName, new WidgetAttribute.Builder().withName("refresh_mode").withType("com.reginald.swiperefresh.CustomSwipeRefreshLayout.refreshMode"));
		WidgetFactory.registerAttribute(localName, new WidgetAttribute.Builder().withName("onRefresh").withType("string"));
		WidgetFactory.registerAttribute(localName, new WidgetAttribute.Builder().withName("enableHorizontalScroll").withType("boolean"));
		WidgetFactory.registerAttribute(localName, new WidgetAttribute.Builder().withName("enable_top_progress_bar").withType("boolean"));
		WidgetFactory.registerAttribute(localName, new WidgetAttribute.Builder().withName("keep_refresh_head").withType("boolean"));
		WidgetFactory.registerAttribute(localName, new WidgetAttribute.Builder().withName("time_out_return_to_top").withType("int"));
		WidgetFactory.registerAttribute(localName, new WidgetAttribute.Builder().withName("time_out_refresh_complete").withType("int"));
		WidgetFactory.registerAttribute(localName, new WidgetAttribute.Builder().withName("return_to_top_duration").withType("int"));
		WidgetFactory.registerAttribute(localName, new WidgetAttribute.Builder().withName("return_to_header_duration").withType("int"));
		WidgetFactory.registerAttribute(localName, new WidgetAttribute.Builder().withName("resistanceFactor").withType("float"));
		WidgetFactory.registerAttribute(localName, new WidgetAttribute.Builder().withName("progressBarHeight").withType("dimension"));
		WidgetFactory.registerAttribute(localName, new WidgetAttribute.Builder().withName("triggerDistance").withType("int"));
		WidgetFactory.registerAttribute(localName, new WidgetAttribute.Builder().withName("top_progress_bar_color_scheme").withType("resourcestring"));
	
	}
	
	public CustomSwipeRefreshLayoutImpl() {
		super(GROUP_NAME, LOCAL_NAME);
	}
	public  CustomSwipeRefreshLayoutImpl(String localname) {
		super(GROUP_NAME, localname);
	}
	public  CustomSwipeRefreshLayoutImpl(String groupName, String localname) {
		super(groupName, localname);
	}

	@Override
	public IWidget newInstance() {
		return new CustomSwipeRefreshLayoutImpl(groupName, localName);
	}
	
	@SuppressLint("NewApi")
	@Override
	public void create(IFragment fragment, Map<String, Object> params) {
		super.create(fragment, params);
Context context = (Context) fragment.getRootActivity();
	Object systemStyle = params.get("systemStyle");
	Object systemAndroidAttrStyle = params.get("systemAndroidAttrStyle");
	
	if (systemStyle == null && systemAndroidAttrStyle == null) {
		customSwipeRefreshLayout = new CustomSwipeRefreshLayoutExt(context);
	} else {
		int defStyleAttr = 0;
		int defStyleRes = 0;
		
		if (systemStyle != null) {
			defStyleRes = context.getResources().getIdentifier((String) systemStyle, "style", context.getPackageName());	
		}
		
		if (systemAndroidAttrStyle != null) {
			defStyleAttr = context.getResources().getIdentifier((String) systemAndroidAttrStyle, "attr", "android");	
		}
		
		if (defStyleRes == 0) {
			customSwipeRefreshLayout = new CustomSwipeRefreshLayoutExt(context, null, defStyleAttr);	
		} else {
		}
		
	}

		
		nativeCreate(params);
		
		
		ViewGroupImpl.registerCommandConveter(this);

	}

	@Override
	public Object asWidget() {
		return customSwipeRefreshLayout;
	}

	@Override
	public boolean remove(IWidget w) {
		boolean remove = super.remove(w);
		customSwipeRefreshLayout.removeView((View) w.asWidget());
		return remove;
	}
	
	@Override
    public boolean remove(int index) {
		IWidget widget = widgets.get(index);
        boolean remove = super.remove(index);

        if (index + 1 <= customSwipeRefreshLayout.getChildCount()) {
            customSwipeRefreshLayout.removeViewAt(index);
        }    
        return remove;
    }

	
	@Override
	public void add(IWidget w, int index) {
		if (index != -2) {
			View view = (View) w.asWidget();
			createLayoutParams(view);
			    if (index == -1) {
			        customSwipeRefreshLayout.addView(view);
			    } else {
			        customSwipeRefreshLayout.addView(view, index);
			    }
		}
		
		ViewGroupImpl.nativeAddView(asNativeWidget(), w.asNativeWidget());
		super.add(w, index);
	}
	
	private void createLayoutParams(View view) {
		com.reginald.swiperefresh.CustomSwipeRefreshLayout.LayoutParams layoutParams = (com.reginald.swiperefresh.CustomSwipeRefreshLayout.LayoutParams) view.getLayoutParams();
		
		layoutParams = (com.reginald.swiperefresh.CustomSwipeRefreshLayout.LayoutParams) view.getLayoutParams();
		if (layoutParams == null) {
			layoutParams = new com.reginald.swiperefresh.CustomSwipeRefreshLayout.LayoutParams(-2, -2);
			view.setLayoutParams(layoutParams);
		}  else {
			layoutParams.height = -2;
			layoutParams.width = -2;
		}
	}
	
	private com.reginald.swiperefresh.CustomSwipeRefreshLayout.LayoutParams getLayoutParams(View view) {
		return (com.reginald.swiperefresh.CustomSwipeRefreshLayout.LayoutParams) view.getLayoutParams();		
	}
	
	@SuppressLint("NewApi")
	@Override
	public void setChildAttribute(IWidget w, WidgetAttribute key, String strValue, Object objValue) {
		View view = (View) w.asWidget();
		com.reginald.swiperefresh.CustomSwipeRefreshLayout.LayoutParams layoutParams = getLayoutParams(view);
		ViewGroupImpl.setChildAttribute(w, key, objValue, layoutParams);		
		
		switch (key.getAttributeName()) {
		case "layout_width":
			layoutParams.width = (int) objValue;
			break;	
		case "layout_height":
			layoutParams.height = (int) objValue;
			break;
		default:
			break;
		}
		
		
		view.setLayoutParams(layoutParams);		
	}
	
	@SuppressLint("NewApi")
	@Override
	public Object getChildAttribute(IWidget w, WidgetAttribute key) {
		Object attributeValue = ViewGroupImpl.getChildAttribute(w, key);		
		if (attributeValue != null) {
			return attributeValue;
		}
		View view = (View) w.asWidget();
		com.reginald.swiperefresh.CustomSwipeRefreshLayout.LayoutParams layoutParams = getLayoutParams(view);

		switch (key.getAttributeName()) {
		case "layout_width":
			return layoutParams.width;
		case "layout_height":
			return layoutParams.height;
		}
		
		return null;

	}
	
		
	public class CustomSwipeRefreshLayoutExt extends com.reginald.swiperefresh.CustomSwipeRefreshLayout implements ILifeCycleDecorator, com.ashera.widget.IMaxDimension{
		private MeasureEvent measureFinished = new MeasureEvent();
		private OnLayoutEvent onLayoutEvent = new OnLayoutEvent();
		
		public IWidget getWidget() {
			return CustomSwipeRefreshLayoutImpl.this;
		}
		private int mMaxWidth = -1;
		private int mMaxHeight = -1;
		@Override
		public void setMaxWidth(int width) {
			mMaxWidth = width;
		}
		@Override
		public void setMaxHeight(int height) {
			mMaxHeight = height;
		}
		@Override
		public int getMaxWidth() {
			return mMaxWidth;
		}
		@Override
		public int getMaxHeight() {
			return mMaxHeight;
		}

		public CustomSwipeRefreshLayoutExt(Context context, android.util.AttributeSet attrs, int defStyleAttr) {
	        super(context, attrs, defStyleAttr);
	    }

		public CustomSwipeRefreshLayoutExt(Context context) {
			super(context);
			
		}
		
		@Override
		public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

			if(mMaxWidth > 0) {
	        	widthMeasureSpec = MeasureSpec.makeMeasureSpec(mMaxWidth, MeasureSpec.AT_MOST);
	        }
	        if(mMaxHeight > 0) {
	            heightMeasureSpec = MeasureSpec.makeMeasureSpec(mMaxHeight, MeasureSpec.AT_MOST);

	        }

	        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
			IWidgetLifeCycleListener listener = (IWidgetLifeCycleListener) getListener();
			if (listener != null) {
			    measureFinished.setWidth(getMeasuredWidth());
			    measureFinished.setHeight(getMeasuredHeight());
				listener.eventOccurred(EventId.measureFinished, measureFinished);
			}
		}
		
		@Override
		protected void onLayout(boolean changed, int l, int t, int r, int b) {
			super.onLayout(changed, l, t, r, b);
			
			ViewImpl.nativeMakeFrame(asNativeWidget(), l, t, r, b);
			
			replayBufferedEvents();
			
			IWidgetLifeCycleListener listener = (IWidgetLifeCycleListener) getListener();
			if (listener != null) {
				onLayoutEvent.setB(b);
				onLayoutEvent.setL(l);
				onLayoutEvent.setR(r);
				onLayoutEvent.setT(t);
				onLayoutEvent.setChanged(changed);
				listener.eventOccurred(EventId.onLayout, onLayoutEvent);
			}
			
			if (isInvalidateOnFrameChange() && isInitialised()) {
				CustomSwipeRefreshLayoutImpl.this.invalidate();
			}
		}	
		
		@Override
		public void onDraw(Canvas canvas) {
			Runnable runnable = () -> super.onDraw(canvas);
			executeMethodListeners("onDraw", runnable, canvas);
		}

		@Override
		public void draw(Canvas canvas) {
			Runnable runnable = () -> super.draw(canvas);
			executeMethodListeners("draw", runnable, canvas);
		}

		@SuppressLint("WrongCall")
		@Override
		public void execute(String method, Object... args) {
			switch (method) {
				case "onDraw":
					setOnMethodCalled(true);
					super.onDraw((Canvas) args[0]);
					break;

				case "draw":
					setOnMethodCalled(true);
					super.draw((Canvas) args[0]);
					break;

				default:
					break;
			}
		}

		public void updateMeasuredDimension(int width, int height) {
			setMeasuredDimension(width, height);
		}


		@Override
		public ILifeCycleDecorator newInstance(IWidget widget) {
			throw new UnsupportedOperationException();
		}

		@Override
		public void setAttribute(WidgetAttribute widgetAttribute,
				String strValue, Object objValue) {
			throw new UnsupportedOperationException();
		}		
		

		@Override
		public List<String> getMethods() {
			throw new UnsupportedOperationException();
		}
		
		@Override
		public void initialized() {
			throw new UnsupportedOperationException();
		}
		
        @Override
        public Object getAttribute(WidgetAttribute widgetAttribute) {
            throw new UnsupportedOperationException();
        }
        @Override
        public void drawableStateChanged() {
        	super.drawableStateChanged();
        	if (!isWidgetDisposed()) {
        		ViewImpl.drawableStateChanged(CustomSwipeRefreshLayoutImpl.this);
        	}
        }
        
    	public void setState0(float value) {
    		ViewImpl.setState(CustomSwipeRefreshLayoutImpl.this, 0, value);
    	}
    	public void setState0(int value) {
    		ViewImpl.setState(CustomSwipeRefreshLayoutImpl.this, 0, value);
    	}
    	public void setState0(double value) {
    		ViewImpl.setState(CustomSwipeRefreshLayoutImpl.this, 0, value);
    	}
    	
    	public void setState0(Float value) {
    		ViewImpl.setState(CustomSwipeRefreshLayoutImpl.this, 0, value);
    	}
    	public void setState0(Integer value) {
    		ViewImpl.setState(CustomSwipeRefreshLayoutImpl.this, 0, value);
    	}
    	public void setState0(Double value) {
    		ViewImpl.setState(CustomSwipeRefreshLayoutImpl.this, 0, value);
    	}
    	public void setState0(Object value) {
    		ViewImpl.setState(CustomSwipeRefreshLayoutImpl.this, 0, value);
    	}
    	public void setState1(float value) {
    		ViewImpl.setState(CustomSwipeRefreshLayoutImpl.this, 1, value);
    	}
    	public void setState1(int value) {
    		ViewImpl.setState(CustomSwipeRefreshLayoutImpl.this, 1, value);
    	}
    	public void setState1(double value) {
    		ViewImpl.setState(CustomSwipeRefreshLayoutImpl.this, 1, value);
    	}
    	
    	public void setState1(Float value) {
    		ViewImpl.setState(CustomSwipeRefreshLayoutImpl.this, 1, value);
    	}
    	public void setState1(Integer value) {
    		ViewImpl.setState(CustomSwipeRefreshLayoutImpl.this, 1, value);
    	}
    	public void setState1(Double value) {
    		ViewImpl.setState(CustomSwipeRefreshLayoutImpl.this, 1, value);
    	}
    	public void setState1(Object value) {
    		ViewImpl.setState(CustomSwipeRefreshLayoutImpl.this, 1, value);
    	}
    	public void setState2(float value) {
    		ViewImpl.setState(CustomSwipeRefreshLayoutImpl.this, 2, value);
    	}
    	public void setState2(int value) {
    		ViewImpl.setState(CustomSwipeRefreshLayoutImpl.this, 2, value);
    	}
    	public void setState2(double value) {
    		ViewImpl.setState(CustomSwipeRefreshLayoutImpl.this, 2, value);
    	}
    	
    	public void setState2(Float value) {
    		ViewImpl.setState(CustomSwipeRefreshLayoutImpl.this, 2, value);
    	}
    	public void setState2(Integer value) {
    		ViewImpl.setState(CustomSwipeRefreshLayoutImpl.this, 2, value);
    	}
    	public void setState2(Double value) {
    		ViewImpl.setState(CustomSwipeRefreshLayoutImpl.this, 2, value);
    	}
    	public void setState2(Object value) {
    		ViewImpl.setState(CustomSwipeRefreshLayoutImpl.this, 2, value);
    	}
    	public void setState3(float value) {
    		ViewImpl.setState(CustomSwipeRefreshLayoutImpl.this, 3, value);
    	}
    	public void setState3(int value) {
    		ViewImpl.setState(CustomSwipeRefreshLayoutImpl.this, 3, value);
    	}
    	public void setState3(double value) {
    		ViewImpl.setState(CustomSwipeRefreshLayoutImpl.this, 3, value);
    	}
    	
    	public void setState3(Float value) {
    		ViewImpl.setState(CustomSwipeRefreshLayoutImpl.this, 3, value);
    	}
    	public void setState3(Integer value) {
    		ViewImpl.setState(CustomSwipeRefreshLayoutImpl.this, 3, value);
    	}
    	public void setState3(Double value) {
    		ViewImpl.setState(CustomSwipeRefreshLayoutImpl.this, 3, value);
    	}
    	public void setState3(Object value) {
    		ViewImpl.setState(CustomSwipeRefreshLayoutImpl.this, 3, value);
    	}
    	public void setState4(float value) {
    		ViewImpl.setState(CustomSwipeRefreshLayoutImpl.this, 4, value);
    	}
    	public void setState4(int value) {
    		ViewImpl.setState(CustomSwipeRefreshLayoutImpl.this, 4, value);
    	}
    	public void setState4(double value) {
    		ViewImpl.setState(CustomSwipeRefreshLayoutImpl.this, 4, value);
    	}
    	
    	public void setState4(Float value) {
    		ViewImpl.setState(CustomSwipeRefreshLayoutImpl.this, 4, value);
    	}
    	public void setState4(Integer value) {
    		ViewImpl.setState(CustomSwipeRefreshLayoutImpl.this, 4, value);
    	}
    	public void setState4(Double value) {
    		ViewImpl.setState(CustomSwipeRefreshLayoutImpl.this, 4, value);
    	}
    	public void setState4(Object value) {
    		ViewImpl.setState(CustomSwipeRefreshLayoutImpl.this, 4, value);
    	}
        	public void state0() {
        		ViewImpl.state(CustomSwipeRefreshLayoutImpl.this, 0);
        	}
        	public void state1() {
        		ViewImpl.state(CustomSwipeRefreshLayoutImpl.this, 1);
        	}
        	public void state2() {
        		ViewImpl.state(CustomSwipeRefreshLayoutImpl.this, 2);
        	}
        	public void state3() {
        		ViewImpl.state(CustomSwipeRefreshLayoutImpl.this, 3);
        	}
        	public void state4() {
        		ViewImpl.state(CustomSwipeRefreshLayoutImpl.this, 4);
        	}
                        
        public void stateYes() {
        	ViewImpl.stateYes(CustomSwipeRefreshLayoutImpl.this);
        	
        }
        
        public void stateNo() {
        	ViewImpl.stateNo(CustomSwipeRefreshLayoutImpl.this);
        }
     
	
	}
	@Override
	public Class getViewClass() {
		return CustomSwipeRefreshLayoutExt.class;
	}
	
	@SuppressLint("NewApi")
	@Override
	public void setAttribute(WidgetAttribute key, String strValue, Object objValue, ILifeCycleDecorator decorator) {
				ViewGroupImpl.setAttribute(this,  key, strValue, objValue, decorator);
		Object nativeWidget = asNativeWidget();
		switch (key.getAttributeName()) {
			case "customHeadLayout": {


		setCustomHeadLayout(objValue);



			}
			break;
			case "refresh_mode": {


	customSwipeRefreshLayout.setRefreshMode((int)objValue);



			}
			break;
			case "onRefresh": {


		customSwipeRefreshLayout.setOnRefreshListener(new OnRefreshListener(this, strValue, "onRefresh"));



			}
			break;
			case "enableHorizontalScroll": {


	customSwipeRefreshLayout.setEnableHorizontalScroll((boolean)objValue);



			}
			break;
			case "enable_top_progress_bar": {


	customSwipeRefreshLayout.enableTopProgressBar((boolean)objValue);



			}
			break;
			case "keep_refresh_head": {


	customSwipeRefreshLayout.setKeepTopRefreshingHead((boolean)objValue);



			}
			break;
			case "time_out_return_to_top": {


	customSwipeRefreshLayout.setReturnToOriginalTimeout((int)objValue);



			}
			break;
			case "time_out_refresh_complete": {


	customSwipeRefreshLayout.setRefreshCompleteTimeout((int)objValue);



			}
			break;
			case "return_to_top_duration": {


	customSwipeRefreshLayout.setReturnToTopDuration((int)objValue);



			}
			break;
			case "return_to_header_duration": {


	customSwipeRefreshLayout.setReturnToHeaderDuration((int)objValue);



			}
			break;
			case "resistanceFactor": {


	customSwipeRefreshLayout.setResistanceFactor((float)objValue);



			}
			break;
			case "progressBarHeight": {


	customSwipeRefreshLayout.setProgressBarHeight((int)objValue);



			}
			break;
			case "triggerDistance": {


	customSwipeRefreshLayout.setTriggerDistance((int)objValue);



			}
			break;
			case "top_progress_bar_color_scheme": {


		setTopProgressBarColorScheme(objValue);



			}
			break;
		default:
			break;
		}
		
	}
	
	@Override
	@SuppressLint("NewApi")
	public Object getAttribute(WidgetAttribute key, ILifeCycleDecorator decorator) {
		Object attributeValue = ViewGroupImpl.getAttribute(this, key, decorator);
		if (attributeValue != null) {
			return attributeValue;
		}
		Object nativeWidget = asNativeWidget();
		switch (key.getAttributeName()) {
		}
		return null;
	}


	@Override
    public Object asNativeWidget() {
        return customSwipeRefreshLayout;
    }
    private void nativeCreate(Map<String, Object> params) {
    }
    
    @Override
    public void requestLayout() {
    	if (isInitialised()) {
    		ViewImpl.requestLayout(this, asNativeWidget());
    	}
    }
    
    @Override
    public void invalidate() {
    	if (isInitialised()) {
    		ViewImpl.invalidate(this, asNativeWidget());
    	}
    }
    
	
	@SuppressLint("NewApi")
private static class OnRefreshListener implements CustomSwipeRefreshLayout.OnRefreshListener, com.ashera.widget.IListener{
private IWidget w; private View view; private String strValue; private String action;
public String getAction() {return action;}
public OnRefreshListener(IWidget w, String strValue)  {
this.w = w; this.strValue = strValue;
}
public OnRefreshListener(IWidget w, String strValue, String action)  {
this.w = w; this.strValue = strValue;this.action=action;
}
public void onRefresh(){
    
	if (action == null || action.equals("onRefresh")) {
		// populate the data from ui to pojo
		w.syncModelFromUiToPojo("onRefresh");
	    java.util.Map<String, Object> obj = getOnRefreshEventObj();
	    String commandName =  (String) obj.get(EventExpressionParser.KEY_COMMAND_NAME);
	    
	    // execute command based on command type
	    String commandType = (String)obj.get(EventExpressionParser.KEY_COMMAND_TYPE);
		switch (commandType) {
		case "+":
		    if (EventCommandFactory.hasCommand(commandName)) {
		    	 EventCommandFactory.getCommand(commandName).executeCommand(w, obj);
		    }

			break;
		default:
			break;
		}
		
		if (obj.containsKey("refreshUiFromModel")) {
			Object widgets = obj.remove("refreshUiFromModel");
			com.ashera.layout.ViewImpl.refreshUiFromModel(w, widgets, true);
		}
		if (w.getModelUiToPojoEventIds() != null) {
			com.ashera.layout.ViewImpl.refreshUiFromModel(w, w.getModelUiToPojoEventIds(), true);
		}
		if (strValue != null && !strValue.isEmpty() && !strValue.trim().startsWith("+")) {
		    com.ashera.core.IActivity activity = (com.ashera.core.IActivity)w.getFragment().getRootActivity();
		    if (activity != null) {
		    	activity.sendEventMessage(obj);
		    }
		}
	}
    return;
}//#####

public java.util.Map<String, Object> getOnRefreshEventObj( ) {
	java.util.Map<String, Object> obj = com.ashera.widget.PluginInvoker.getJSONCompatMap();
    obj.put("action", "action");
    obj.put("eventType", "refresh");
    obj.put("fragmentId", w.getFragment().getFragmentId());
    obj.put("actionUrl", w.getFragment().getActionUrl());
    obj.put("namespace", w.getFragment().getNamespace());
    
    if (w.getComponentId() != null) {
    	obj.put("componentId", w.getComponentId());
    }
    
    PluginInvoker.putJSONSafeObjectIntoMap(obj, "id", w.getId());
     
    
    // parse event info into the map
    EventExpressionParser.parseEventExpression(strValue, obj);
    
    // update model data into map
    w.updateModelToEventMap(obj, "onRefresh", (String)obj.get(EventExpressionParser.KEY_EVENT_ARGS));
    return obj;
}
}


	@Override
	public void setId(String id){
		if (id != null && !id.equals("")){
			super.setId(id);
			customSwipeRefreshLayout.setId((int) quickConvert(id, "id"));
		}
	}
	
    

		//end - body

	//start - copycode
	private void setCustomHeadLayout(Object objValue) {
		customSwipeRefreshLayout.setCustomHeaderLayout((String) objValue);
	}	
	
	private void setTopProgressBarColorScheme(Object objValue) {
		String[] colorSchemes = ((String) objValue).split(",");
		if (colorSchemes.length != 4) {
			throw new RuntimeException("Invalid format " + objValue + " for color scheme. Example format : #000,#fff,#000,#fff");
		}
		customSwipeRefreshLayout.setProgressBarColor(quickConvert(colorSchemes[0].trim(), "color"), quickConvert(colorSchemes[1].trim(), "color"), quickConvert(colorSchemes[2].trim(), "color"), quickConvert(colorSchemes[3].trim(), "color"));
	}
	//end - copycode
}
