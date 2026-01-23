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

import r.android.annotation.SuppressLint;
import r.android.content.Context;
import r.android.os.Build;
import r.android.view.*;
import r.android.widget.*;
import r.android.view.View.*;

import com.ashera.widget.BaseHasWidgets;

import r.android.annotation.SuppressLint;

import com.ashera.core.IFragment;
import com.ashera.widget.bus.*;
import com.ashera.converter.*;
import com.ashera.widget.bus.Event.*;
import com.ashera.widget.*;
import com.ashera.widget.IWidgetLifeCycleListener.*;
import com.ashera.layout.*;

import org.teavm.jso.dom.html.HTMLElement;

import static com.ashera.widget.IWidget.*;
//end - imports
import com.reginald.swiperefresh.CustomSwipeRefreshLayout;
public class CustomSwipeRefreshLayoutImpl extends BaseHasWidgets {
	//start - body
	private HTMLElement htmlElement;
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
		WidgetFactory.registerAttribute(localName, new WidgetAttribute.Builder().withName("webHeaderZIndex").withType("int"));
	
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
		customSwipeRefreshLayout = new CustomSwipeRefreshLayoutExt();
		
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
		 nativeRemoveView(w);            
		return remove;
	}
	
	@Override
    public boolean remove(int index) {
		IWidget widget = widgets.get(index);
        boolean remove = super.remove(index);

        if (index + 1 <= customSwipeRefreshLayout.getChildCount()) {
            customSwipeRefreshLayout.removeViewAt(index);
            nativeRemoveView(widget);
        }    
        return remove;
    }

	private void nativeRemoveView(IWidget widget) {
		r.android.animation.LayoutTransition layoutTransition = customSwipeRefreshLayout.getLayoutTransition();
		if (layoutTransition != null && (
				layoutTransition.isTransitionTypeEnabled(r.android.animation.LayoutTransition.CHANGE_DISAPPEARING) ||
				layoutTransition.isTransitionTypeEnabled(r.android.animation.LayoutTransition.DISAPPEARING)
				)) {
			addToBufferedRunnables(() -> ViewGroupImpl.nativeRemoveView(widget));          
		} else {
			ViewGroupImpl.nativeRemoveView(widget);
		}
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
		private List<IWidget> overlays;
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

		public CustomSwipeRefreshLayoutExt() {
			super();
			
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
			ViewImpl.setDrawableBounds(CustomSwipeRefreshLayoutImpl.this, l, t, r, b);
			if (!isOverlay()) {
			ViewImpl.nativeMakeFrame(asNativeWidget(), l, t, r, b);
			}
			replayBufferedEvents();
	        ViewImpl.redrawDrawables(CustomSwipeRefreshLayoutImpl.this);
	        overlays = ViewImpl.drawOverlay(CustomSwipeRefreshLayoutImpl.this, overlays);
			
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
		public void execute(String method, Object... canvas) {
			
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
        private Map<String, IWidget> templates;
    	@Override
    	public r.android.view.View inflateView(java.lang.String layout) {
    		if (templates == null) {
    			templates = new java.util.HashMap<String, IWidget>();
    		}
    		IWidget template = templates.get(layout);
    		if (template == null) {
    			template = (IWidget) quickConvert(layout, "template");
    			templates.put(layout, template);
    		}
    		
    		IWidget widget = template.loadLazyWidgets(CustomSwipeRefreshLayoutImpl.this);
			return (View) widget.asWidget();
    	}   
        
    	@Override
		public void remeasure() {
    		if (getFragment() != null) {
    			getFragment().remeasure();
    		}
		}
    	
        @Override
		public void removeFromParent() {
        	CustomSwipeRefreshLayoutImpl.this.getParent().remove(CustomSwipeRefreshLayoutImpl.this);
		}
        @Override
        public void getLocationOnScreen(int[] appScreenLocation) {
        	appScreenLocation[0] = htmlElement.getBoundingClientRect().getLeft();
        	appScreenLocation[1] = htmlElement.getBoundingClientRect().getTop();
        }
        @Override
        public void getWindowVisibleDisplayFrame(r.android.graphics.Rect displayFrame){
        	
        	org.teavm.jso.dom.html.TextRectangle boundingClientRect = htmlElement.getBoundingClientRect();
			displayFrame.top = boundingClientRect.getTop();
        	displayFrame.left = boundingClientRect.getLeft();
        	displayFrame.bottom = boundingClientRect.getBottom();
        	displayFrame.right = boundingClientRect.getRight();
        }
        @Override
		public void offsetTopAndBottom(int offset) {
			super.offsetTopAndBottom(offset);
			ViewImpl.nativeMakeFrame(asNativeWidget(), getLeft(), getTop(), getRight(), getBottom());
		}
		@Override
		public void offsetLeftAndRight(int offset) {
			super.offsetLeftAndRight(offset);
			ViewImpl.nativeMakeFrame(asNativeWidget(), getLeft(), getTop(), getRight(), getBottom());
		}
		@Override
		public void setMyAttribute(String name, Object value) {
			if (name.equals("state0")) {
				setState0(value);
				return;
			}
			if (name.equals("state1")) {
				setState1(value);
				return;
			}
			if (name.equals("state2")) {
				setState2(value);
				return;
			}
			if (name.equals("state3")) {
				setState3(value);
				return;
			}
			if (name.equals("state4")) {
				setState4(value);
				return;
			}
			CustomSwipeRefreshLayoutImpl.this.setAttribute(name, value, !(value instanceof String));
		}
        @Override
        public void setVisibility(int visibility) {
            super.setVisibility(visibility);
            ((HTMLElement)asNativeWidget()).getStyle().setProperty("display", visibility != View.VISIBLE ? "none" : "block");
            
        }
        
    	public void setState0(Object value) {
    		ViewImpl.setState(CustomSwipeRefreshLayoutImpl.this, 0, value);
    	}
    	public void setState1(Object value) {
    		ViewImpl.setState(CustomSwipeRefreshLayoutImpl.this, 1, value);
    	}
    	public void setState2(Object value) {
    		ViewImpl.setState(CustomSwipeRefreshLayoutImpl.this, 2, value);
    	}
    	public void setState3(Object value) {
    		ViewImpl.setState(CustomSwipeRefreshLayoutImpl.this, 3, value);
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
     
		@Override
		public void endViewTransition(r.android.view.View view) {
			super.endViewTransition(view);
			runBufferedRunnables();
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
			case "webHeaderZIndex": {


		setWebHeaderZIndex(objValue);



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
        return htmlElement;
    }
    private void nativeCreate(Map<String, Object> params) {
    	htmlElement = org.teavm.jso.dom.html.HTMLDocument.current().createElement("div");
    	htmlElement.getStyle().setProperty("box-sizing", "border-box");
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
	
    
    @Override
    public void setVisible(boolean b) {
        ((View)asWidget()).setVisibility(b ? View.VISIBLE : View.GONE);
    }

		//end - body
    
    private static int id = 0;
    private final static String DELLOC_EVENT = com.ashera.widget.bus.Event.StandardEvents.dealloc.toString();
    private int action;
    private MotionEvent event = new MotionEvent();

    @Override
    public void initialized() {
    	super.initialized();
    	
		org.teavm.jso.dom.events.EventListener<ViewImpl.HtmlMouseEvent> flistener = (event) -> {
			switch (event.getType()) {
			case "mousedown":
				action = 1;
				processTouchEvent(MotionEvent.ACTION_DOWN, event.getClientX(), event.getClientY());
				break;
			case "mouseup":
				if (action == 1) {
					processTouchEvent(MotionEvent.ACTION_UP, event.getClientX(), event.getClientY());
					action = 0;
				}
				break;
			case "mousemove":
				if (action == 1) {
					processTouchEvent(MotionEvent.ACTION_MOVE, event.getClientX(), event.getClientY());
				}
				break;
			default:
				break;
			}
		};
		String eventId = "cwrlmouseup" + id;
		ViewImpl.setOnListener(this, org.teavm.jso.browser.Window.current(), flistener, eventId, "mouseup");
		ViewImpl.setOnListener(this, asNativeWidget(), flistener, "mousedown", "mousedown");
		ViewImpl.setOnListener(this, asNativeWidget(), flistener, "mousemove", "mousemove");
		addDellocHandler(eventId);
		id++;
    }
    
	private void processTouchEvent(int action, int x, int y) {
		event.setX(x);
		event.setY(y);
		event.setAction(action);
		customSwipeRefreshLayout.onInterceptTouchEvent(event);
		
	}
    
    @com.google.j2objc.annotations.WeakOuter
    class DallocHandler extends com.ashera.widget.bus.EventBusHandler {
    	String eventId;
    	public DallocHandler(String type, String id) {
    		super(type);
    		eventId = id;
    	}

    	@Override
    	protected void doPerform(Object payload) {
    		ViewImpl.removeListener(CustomSwipeRefreshLayoutImpl.this, org.teavm.jso.browser.Window.current(), eventId, "mouseup");
    	}
    	
    }
    private void addDellocHandler(String id) {
    	fragment.getEventBus().on(DELLOC_EVENT, new DallocHandler(DELLOC_EVENT, id));
    }

	private void setWebHeaderZIndex(Object objValue) {
		customSwipeRefreshLayout.setWebHeaderZIndex((int) objValue);
	}
}
