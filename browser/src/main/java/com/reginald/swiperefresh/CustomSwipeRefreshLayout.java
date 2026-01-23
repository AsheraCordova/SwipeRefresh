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
import r.android.content.Context;
import r.android.graphics.Rect;
import androidx.core.view.ViewCompat;
import r.android.util.DisplayMetrics;
import r.android.util.Log;
import r.android.view.MotionEvent;
import r.android.view.View;
import r.android.view.ViewGroup;
import r.android.view.animation.AccelerateInterpolator;
import r.android.view.animation.Animation;
import r.android.view.animation.Animation.AnimationListener;
import r.android.view.animation.DecelerateInterpolator;
import r.android.view.animation.Transformation;
import r.android.widget.AbsListView;
public class CustomSwipeRefreshLayout extends ViewGroup {
  public static final boolean DEBUG=false;
  public static final String TAG="csrl";
  public static final int REFRESH_MODE_SWIPE=1;
  public static final int REFRESH_MODE_PULL=2;
  private static final int RETURN_TO_ORIGINAL_POSITION_TIMEOUT=-1;
  private static final int REFRESH_COMPLETE_POSITION_TIMEOUT=1000;
  private static final int RETURN_TO_TOP_DURATION=500;
  private static final int RETURN_TO_HEADER_DURATION=500;
  private static final float ACCELERATE_INTERPOLATION_FACTOR=1.5f;
  private static final float DECELERATE_INTERPOLATION_FACTOR=2f;
  private static final int PROGRESS_BAR_HEIGHT=4;
  private static final float MAX_SWIPE_DISTANCE_FACTOR=.5f;
  private static final int SWIPE_REFRESH_TRIGGER_DISTANCE=100;
  private static final float RESISTANCE_FACTOR=.5f;
  private final DecelerateInterpolator mDecelerateInterpolator;
  private final AccelerateInterpolator mAccelerateInterpolator;
  private final Animation mAnimateStayComplete=new Animation(){
    public void applyTransformation(    float interpolatedTime,    Transformation t){
    }
  }
;
  boolean enableTopProgressBar=true;
  boolean keepTopRefreshingHead=true;
  int refresshMode=REFRESH_MODE_SWIPE;
  State currentState=new State(State.STATE_NORMAL);
  State lastState=new State(-1);
  private RefreshCheckHandler mRefreshCheckHandler;
  private ScrollUpHandler mScrollUpHandler;
  private ScrollLeftOrRightHandler mScrollLeftOrRightHandler;
  private int mReturnToOriginalTimeout=RETURN_TO_ORIGINAL_POSITION_TIMEOUT;
  private int mRefreshCompleteTimeout=REFRESH_COMPLETE_POSITION_TIMEOUT;
  private float mResistanceFactor=RESISTANCE_FACTOR;
  private int mTriggerDistance=SWIPE_REFRESH_TRIGGER_DISTANCE;
  private int mProgressBarHeight=PROGRESS_BAR_HEIGHT;
  private int mReturnToTopDuration=RETURN_TO_TOP_DURATION;
  private int mReturnToHeaderDuration=RETURN_TO_HEADER_DURATION;
  private int mConvertedProgressBarHeight;
  private CustomSwipeProgressBar mTopProgressBar;
  private View mHeadview;
  private boolean hasHeadview;
  private View mTarget=null;
  private int mTargetOriginalTop;
  private int mOriginalOffsetBottom;
  private OnRefreshListener mListener;
  private MotionEvent mDownEvent;
  private int mFrom;
  private boolean mRefreshing=false;
  private int mTouchSlop;
  private int mDistanceToTriggerSync=-1;
  private float mPrevY;
  private float mFromPercentage=0;
  private float mCurrPercentage=0;
  private final AnimationListener mShrinkAnimationListener=new BaseAnimationListener(){
    public void onAnimationEnd(    Animation animation){
      mCurrPercentage=0;
    }
  }
;
  private boolean enableHorizontalScroll=true;
  private boolean isHorizontalScroll;
  private boolean checkHorizontalMove;
  private boolean mCheckValidMotionFlag=true;
  private int mCurrentTargetOffsetTop=0;
  private final AnimationListener mReturningAnimationListener=new BaseAnimationListener(){
    public void onAnimationEnd(    Animation animation){
      mInReturningAnimation=false;
    }
  }
;
  private boolean mInReturningAnimation;
  private int mTriggerOffset=0;
  private final Runnable mReturnToTrigerPosition=new Runnable(){
    public void run(){
      mInReturningAnimation=true;
      animateOffsetToTrigerPosition(mTarget.getTop(),mReturningAnimationListener);
    }
  }
;
  private final Runnable mReturnToStartPosition=new Runnable(){
    public void run(){
      mInReturningAnimation=true;
      animateOffsetToStartPosition(mTarget.getTop(),mReturningAnimationListener);
    }
  }
;
  private final AnimationListener mStayCompleteListener=new BaseAnimationListener(){
    public void onAnimationEnd(    Animation animation){
      mReturnToStartPosition.run();
      mRefreshing=false;
    }
  }
;
  private final Runnable mStayRefreshCompletePosition=new Runnable(){
    public void run(){
      animateStayComplete(mStayCompleteListener);
    }
  }
;
  private Animation mShrinkTrigger=new Animation(){
    public void applyTransformation(    float interpolatedTime,    Transformation t){
      float percent=mFromPercentage + ((0 - mFromPercentage) * interpolatedTime);
      mTopProgressBar.setTriggerPercentage(percent);
    }
  }
;
  private final Runnable mCancel=new Runnable(){
    public void run(){
      mInReturningAnimation=true;
      if (mTopProgressBar != null && enableTopProgressBar) {
        mFromPercentage=mCurrPercentage;
        mShrinkTrigger.setDuration(mReturnToTopDuration);
        mShrinkTrigger.setAnimationListener(mShrinkAnimationListener);
        mShrinkTrigger.reset();
        mShrinkTrigger.setInterpolator(mDecelerateInterpolator);
        startAnimation(mShrinkTrigger);
      }
      animateOffsetToStartPosition(mTarget.getTop(),mReturningAnimationListener);
    }
  }
;
  private final Animation mAnimateToStartPosition=new Animation(){
    public void applyTransformation(    float interpolatedTime,    Transformation t){
      int targetTop=mTargetOriginalTop;
      if (mFrom != mTargetOriginalTop) {
        targetTop=(mFrom + (int)((mTargetOriginalTop - mFrom) * interpolatedTime));
      }
      int offset=targetTop - mTarget.getTop();
      final int currentTop=mTarget.getTop();
      if (offset + currentTop < 0) {
        offset=0 - currentTop;
      }
      setTargetOffsetTop(offset,true);
    }
  }
;
  private final Animation mAnimateToTrigerPosition=new Animation(){
    public void applyTransformation(    float interpolatedTime,    Transformation t){
      int targetTop=mDistanceToTriggerSync;
      if (mFrom > mDistanceToTriggerSync) {
        targetTop=(mFrom + (int)((mDistanceToTriggerSync - mFrom) * interpolatedTime));
      }
      int offset=targetTop - mTarget.getTop();
      final int currentTop=mTarget.getTop();
      if (offset + currentTop < 0) {
        offset=0 - currentTop;
      }
      setTargetOffsetTop(offset,true);
    }
  }
;
  private void animateStayComplete(  AnimationListener listener){
    mAnimateStayComplete.reset();
    mAnimateStayComplete.setDuration(mRefreshCompleteTimeout);
    mAnimateStayComplete.setAnimationListener(listener);
    mTarget.startAnimation(mAnimateStayComplete);
  }
  private void animateOffsetToTrigerPosition(  int from,  AnimationListener listener){
    mFrom=from;
    mAnimateToTrigerPosition.reset();
    mAnimateToTrigerPosition.setDuration(mReturnToHeaderDuration);
    mAnimateToTrigerPosition.setAnimationListener(listener);
    mAnimateToTrigerPosition.setInterpolator(mDecelerateInterpolator);
    mTarget.startAnimation(mAnimateToTrigerPosition);
  }
  private void animateOffsetToStartPosition(  int from,  AnimationListener listener){
    mFrom=from;
    mAnimateToStartPosition.reset();
    mAnimateToStartPosition.setDuration(mReturnToTopDuration);
    mAnimateToStartPosition.setAnimationListener(listener);
    mAnimateToStartPosition.setInterpolator(mDecelerateInterpolator);
    mTarget.startAnimation(mAnimateToStartPosition);
  }
  private boolean canViewScrollUp(  View view,  MotionEvent event){
    boolean ret;
    event.offsetLocation(view.getScrollX() - view.getLeft(),view.getScrollY() - view.getTop());
    if (mScrollUpHandler != null) {
      boolean canViewScrollUp=mScrollUpHandler.canScrollUp(view);
      if (canViewScrollUp)       return true;
    }
    if (r.android.os.Build.VERSION.SDK_INT < 14) {
      if (view instanceof AbsListView) {
        final AbsListView absListView=(AbsListView)view;
        ret=absListView.getChildCount() > 0 && (/*absListView.getFirstVisiblePosition()*/0 > 0 || absListView.getChildAt(0).getTop() < absListView.getPaddingTop());
      }
 else {
        ret=view.getScrollY() > 0 || canChildrenScroolUp(view,event);
      }
    }
 else {
      ret=ViewCompat.canScrollVertically(view,-1) || canChildrenScroolUp(view,event);
    }
    if (DEBUG)     Log.d(TAG,"canViewScrollUp " + view.getClass().getName() + " "+ ret);
    return ret;
  }
  private boolean canChildrenScroolUp(  View view,  MotionEvent event){
    if (view instanceof ViewGroup) {
      final ViewGroup viewgroup=(ViewGroup)view;
      int count=viewgroup.getChildCount();
      for (int i=0; i < count; ++i) {
        View child=viewgroup.getChildAt(i);
        Rect bounds=new Rect();
        child.getHitRect(bounds);
        if (bounds.contains((int)event.getX(),(int)event.getY())) {
          return canViewScrollUp(child,event);
        }
      }
    }
    return false;
  }
  private boolean canViewScrollHorizontally(  View view,  MotionEvent event,  int direction){
    boolean ret;
    event.offsetLocation(view.getScrollX() - view.getLeft(),view.getScrollY() - view.getTop());
    if (mScrollLeftOrRightHandler != null) {
      boolean canViewScrollLeftOrRight=mScrollLeftOrRightHandler.canScrollLeftOrRight(view,direction);
      if (canViewScrollLeftOrRight)       return true;
    }
    if (r.android.os.Build.VERSION.SDK_INT < 14) {
      if (view instanceof View) {
        ret=((View)view).canScrollHorizontally(direction);
      }
 else {
        ret=view.getScrollX() * direction > 0;
      }
    }
 else {
      ret=ViewCompat.canScrollHorizontally(view,direction);
    }
    ret=ret || canChildrenScroolHorizontally(view,event,direction);
    if (DEBUG)     Log.d(TAG,"canViewScrollHorizontally " + view.getClass().getName() + " "+ ret);
    return ret;
  }
  private boolean canChildrenScroolHorizontally(  View view,  MotionEvent event,  int direction){
    if (view instanceof ViewGroup) {
      final ViewGroup viewgroup=(ViewGroup)view;
      int count=viewgroup.getChildCount();
      for (int i=0; i < count; ++i) {
        View child=viewgroup.getChildAt(i);
        Rect bounds=new Rect();
        child.getHitRect(bounds);
        if (bounds.contains((int)event.getX(),(int)event.getY())) {
          if (DEBUG)           Log.d(TAG,"in child " + child.getClass().getName());
          return canViewScrollHorizontally(child,event,direction);
        }
      }
    }
    return false;
  }
  public void setCustomHeadview(  View customHeadview){
    if (mHeadview != null) {
      if (mHeadview == customHeadview)       return;
      removeView(mHeadview);
    }
    mHeadview=customHeadview;
    addView(mHeadview,new MarginLayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT));
    hasHeadview=true;
  }
  public int getRefreshMode(){
    return refresshMode;
  }
  public void setRefreshMode(  int mode){
switch (mode) {
case REFRESH_MODE_PULL:
      refresshMode=REFRESH_MODE_PULL;
    break;
case REFRESH_MODE_SWIPE:
  refresshMode=REFRESH_MODE_SWIPE;
break;
default :
throw new IllegalStateException("refresh mode " + mode + " is NOT supported in CustomSwipeRefreshLayout");
}
}
public void onAttachedToWindow(){
super.onAttachedToWindow();
removeCallbacks(mCancel);
removeCallbacks(mReturnToStartPosition);
}
public void onDetachedFromWindow(){
super.onDetachedFromWindow();
removeCallbacks(mReturnToStartPosition);
removeCallbacks(mCancel);
}
public void setOnRefreshListener(OnRefreshListener listener){
mListener=listener;
}
private void setTriggerPercentage(float percent){
if (percent == 0f) {
mCurrPercentage=0;
return;
}
mCurrPercentage=percent;
if (enableTopProgressBar) {
mTopProgressBar.setTriggerPercentage(percent);
}
}
private void setRefreshState(int state){
currentState.update(state,mCurrentTargetOffsetTop,mTriggerOffset);
((CustomSwipeRefreshHeadLayout)mHeadview).onStateChange(currentState,lastState);
lastState.update(state,mCurrentTargetOffsetTop,mTriggerOffset);
}
private void updateHeadViewState(boolean changeHeightOnly){
if (changeHeightOnly) {
setRefreshState(currentState.getRefreshState());
}
 else {
if (mTarget.getTop() > mDistanceToTriggerSync) {
setRefreshState(State.STATE_READY);
}
 else {
setRefreshState(State.STATE_NORMAL);
}
}
}
public void refreshComplete(){
setRefreshing(false);
}
public boolean isRefreshing(){
return mRefreshing;
}
protected void setRefreshing(boolean refreshing){
if (mRefreshing != refreshing) {
ensureTarget();
mCurrPercentage=0;
mRefreshing=refreshing;
if (mRefreshing) {
if (enableTopProgressBar) {
mTopProgressBar.start();
}
if (refresshMode == REFRESH_MODE_PULL) {
mReturnToTrigerPosition.run();
}
 else if (refresshMode == REFRESH_MODE_SWIPE) {
mReturnToStartPosition.run();
}
}
 else {
if (enableTopProgressBar) {
mTopProgressBar.stop();
}
if (refresshMode == REFRESH_MODE_PULL) {
mRefreshing=true;
removeCallbacks(mReturnToStartPosition);
removeCallbacks(mCancel);
mStayRefreshCompletePosition.run();
}
 else if (refresshMode == REFRESH_MODE_SWIPE) {
mRefreshing=false;
mReturnToStartPosition.run();
}
setRefreshState(State.STATE_COMPLETE);
}
}
}
private View getContentView(){
return getChildAt(0) == mHeadview ? getChildAt(1) : getChildAt(0);
}
private void ensureTarget(){
if (mTarget == null) {
if (getChildCount() > 2 && !isInEditMode()) {
throw new IllegalStateException("CustomSwipeRefreshLayout can host ONLY one direct child");
}
mTarget=getContentView();
MarginLayoutParams lp=(MarginLayoutParams)mTarget.getLayoutParams();
mTargetOriginalTop=mTarget.getTop();
mOriginalOffsetBottom=mTargetOriginalTop + mTarget.getHeight();
if (DEBUG) {
Log.d(TAG,"mTargetOriginalTop = " + mTargetOriginalTop + ", mOriginalOffsetBottom = "+ mOriginalOffsetBottom);
}
}
if (mDistanceToTriggerSync == -1) {
if (getParent() != null && ((View)getParent()).getHeight() > 0) {
final DisplayMetrics metrics=getResources().getDisplayMetrics();
mTriggerOffset=(int)(mTriggerDistance * metrics.density);
mDistanceToTriggerSync=(int)Math.min(((View)getParent()).getHeight() * MAX_SWIPE_DISTANCE_FACTOR,mTriggerOffset + mTargetOriginalTop);
}
}
}
protected void onLayout(boolean changed,int left,int top,int right,int bottom){
final int width=getMeasuredWidth();
final int height=getMeasuredHeight();
if (enableTopProgressBar) {
if (DEBUG) Log.d(TAG,String.format("mTopProgressBar[%d,%d,%d,%d]",getPaddingLeft(),getPaddingLeft(),getPaddingLeft() + width,getPaddingTop() + mConvertedProgressBarHeight));
mTopProgressBar.setBounds(getPaddingLeft(),getPaddingTop(),getPaddingLeft() + width,getPaddingTop() + mConvertedProgressBarHeight);
}
 else {
mTopProgressBar.setBounds(0,0,0,0);
}
if (getChildCount() == 0) {
return;
}
MarginLayoutParams lp=(MarginLayoutParams)mHeadview.getLayoutParams();
final int headViewLeft=getPaddingLeft() + lp.leftMargin;
final int headViewTop=mCurrentTargetOffsetTop - mHeadview.getMeasuredHeight() + getPaddingTop() + lp.topMargin;
final int headViewRight=headViewLeft + mHeadview.getMeasuredWidth();
final int headViewBottom=headViewTop + mHeadview.getMeasuredHeight();
mHeadview.layout(headViewLeft,headViewTop,headViewRight,headViewBottom);
if (DEBUG) Log.d(TAG,String.format("@@ onLayout() : mHeadview [%d,%d,%d,%d] ",headViewLeft,headViewTop,headViewRight,headViewBottom));
final View content=getContentView();
lp=(MarginLayoutParams)content.getLayoutParams();
final int childLeft=getPaddingLeft() + lp.leftMargin;
final int childTop=mCurrentTargetOffsetTop + getPaddingTop() + lp.topMargin;
final int childRight=childLeft + content.getMeasuredWidth();
final int childBottom=childTop + content.getMeasuredHeight();
content.layout(childLeft,childTop,childRight,childBottom);
if (DEBUG) Log.d(TAG,String.format("@@ onLayout() %d : content [%d,%d,%d,%d] ",getChildAt(0) == mHeadview ? 1 : 0,childLeft,childTop,childRight,childBottom));
}
protected void onMeasure(int widthMeasureSpec,int heightMeasureSpec){
super.onMeasure(widthMeasureSpec,heightMeasureSpec);
if (!hasHeadview) {
com.ashera.widget.IWidget w = com.ashera.widget.WidgetFactory.createWidget(DefaultCustomHeadView.class.getName(), "", (com.ashera.widget.HasWidgets) this.getOuterWidget(), false);DefaultCustomHeadView headView = (DefaultCustomHeadView)w.asWidget();headView.setupLayout(customHeaderLayout);setCustomHeadview(/*new DefaultCustomHeadView(this)*/headView);updateZIndex();
}
if (getChildCount() > 2 && !isInEditMode()) {
throw new IllegalStateException("CustomSwipeRefreshLayout can host one child content view.");
}
measureChildWithMargins(mHeadview,widthMeasureSpec,0,heightMeasureSpec,0);
final View content=getContentView();
if (getChildCount() > 0) {
MarginLayoutParams lp=(MarginLayoutParams)content.getLayoutParams();
content.measure(MeasureSpec.makeMeasureSpec(getMeasuredWidth() - getPaddingLeft() - getPaddingRight()- lp.leftMargin- lp.rightMargin,MeasureSpec.EXACTLY),MeasureSpec.makeMeasureSpec(getMeasuredHeight() - getPaddingTop() - getPaddingBottom()- lp.topMargin- lp.bottomMargin,MeasureSpec.EXACTLY));
}
if (DEBUG) {
Log.d(TAG,String.format("onMeasure(): swiperefreshlayout: width=%d, height=%d",getMeasuredWidth(),getMeasuredHeight()));
Log.d(TAG,String.format("onMeasure(): headview: width=%d, height=%d",mHeadview.getMeasuredWidth(),mHeadview.getMeasuredHeight()));
Log.d(TAG,String.format("onMeasure(): content: width=%d, height=%d",content.getMeasuredWidth(),content.getMeasuredHeight()));
}
}
protected boolean checkLayoutParams(ViewGroup.LayoutParams p){
return p instanceof MarginLayoutParams;
}
protected ViewGroup.LayoutParams generateDefaultLayoutParams(){
return new MarginLayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);
}
public void addView(View child,int index,LayoutParams params){
if (getChildCount() > 1 && !isInEditMode()) {
throw new IllegalStateException("CustomSwipeRefreshLayout can host ONLY one child content view");
}
super.addView(child,index,params);
}
private boolean checkCanDoRefresh(){
if (mRefreshCheckHandler != null) {
return mRefreshCheckHandler.canRefresh();
}
return true;
}
public boolean onInterceptTouchEvent(MotionEvent ev){
if (DEBUG) Log.d(TAG,"onInterceptTouchEvent() start " + ev);
ensureTarget();
boolean handled=false;
float curY=ev.getY();
if (mInReturningAnimation && !isKeepTopRefreshingHead() && ev.getAction() == MotionEvent.ACTION_DOWN) {
mInReturningAnimation=false;
}
if (!isEnabled()) {
return false;
}
if (ev.getAction() == MotionEvent.ACTION_DOWN) {
mCurrPercentage=0;
mDownEvent=MotionEvent.obtain(ev);
mPrevY=mDownEvent.getY();
mCheckValidMotionFlag=true;
checkHorizontalMove=true;
}
 else if (ev.getAction() == MotionEvent.ACTION_MOVE) {
if (mDownEvent != null) {
float yDiff=Math.abs(curY - mDownEvent.getY());
if (enableHorizontalScroll) {
MotionEvent event=MotionEvent.obtain(ev);
int horizontalScrollDirection=ev.getX() > mDownEvent.getX() ? -1 : 1;
float xDiff=Math.abs(ev.getX() - mDownEvent.getX());
if (isHorizontalScroll) {
  if (DEBUG)   Log.d(TAG,"onInterceptTouchEvent(): in horizontal scroll");
  mPrevY=curY;
  checkHorizontalMove=false;
  return false;
}
 else if (xDiff <= mTouchSlop) {
  checkHorizontalMove=true;
}
 else if (canViewScrollHorizontally(mTarget,event,horizontalScrollDirection) && checkHorizontalMove && xDiff > 2 * yDiff) {
  if (DEBUG)   Log.d(TAG,"onInterceptTouchEvent(): start horizontal scroll");
  mPrevY=curY;
  isHorizontalScroll=true;
  checkHorizontalMove=false;
  return false;
}
 else {
  checkHorizontalMove=false;
}
}
if (yDiff < mTouchSlop) {
mPrevY=curY;
return false;
}
}
}
 else if (ev.getAction() == MotionEvent.ACTION_UP) {
if (mDownEvent != null) {
float yDiff=Math.abs(curY - mDownEvent.getY());
if (enableHorizontalScroll && isHorizontalScroll) {
if (DEBUG) Log.d(TAG,"onInterceptTouchEvent(): finish horizontal scroll");
isHorizontalScroll=false;
mPrevY=ev.getY();
return false;
}
 else if (yDiff < mTouchSlop) {
mPrevY=curY;
return false;
}
}
}
MotionEvent event=MotionEvent.obtain(ev);
if (!mInReturningAnimation && !canViewScrollUp(mTarget,event)) {
handled=onTouchEvent(ev);
if (DEBUG) Log.d(TAG,"onInterceptTouchEvent(): handled = onTouchEvent(event);" + handled);
}
 else {
mPrevY=ev.getY();
}
boolean ret=!handled ? false : handled;
if (DEBUG) Log.d(TAG,"onInterceptTouchEvent() " + ret);
return ret;
}
public void requestDisallowInterceptTouchEvent(boolean b){
}
public boolean onTouchEvent(MotionEvent event){
if (DEBUG) Log.d(TAG,"onTouchEvent() start");
if (!isEnabled()) {
return false;
}
final int action=event.getAction();
boolean handled=false;
int curTargetTop=mTarget.getTop();
mCurrentTargetOffsetTop=curTargetTop - mTargetOriginalTop;
switch (action) {
case MotionEvent.ACTION_MOVE:
if (mDownEvent != null && !mInReturningAnimation) {
final float eventY=event.getY();
float yDiff=eventY - mDownEvent.getY();
boolean isScrollUp=eventY - mPrevY > 0;
if (mCheckValidMotionFlag && (yDiff > mTouchSlop || yDiff < -mTouchSlop)) {
mCheckValidMotionFlag=false;
}
if (!keepTopRefreshingHead) {
if (isRefreshing()) {
  if (!isScrollUp) {
    if (curTargetTop <= mTargetOriginalTop) {
      mPrevY=event.getY();
      handled=false;
      updateContentOffsetTop(mTargetOriginalTop,true);
      break;
    }
  }
 else {
    if (curTargetTop >= mDistanceToTriggerSync) {
      mPrevY=event.getY();
      handled=true;
      updateContentOffsetTop(mDistanceToTriggerSync,true);
      break;
    }
  }
  setTargetOffsetTop((int)((eventY - mPrevY)),true);
  mPrevY=event.getY();
  handled=true;
  break;
}
}
 else {
if (isRefreshing()) {
  mPrevY=event.getY();
  handled=false;
  break;
}
}
if (curTargetTop >= mDistanceToTriggerSync) {
if (enableTopProgressBar) mTopProgressBar.setTriggerPercentage(1f);
removeCallbacks(mCancel);
if (refresshMode == REFRESH_MODE_SWIPE) {
  startRefresh();
  handled=true;
  break;
}
}
 else {
setTriggerPercentage(mAccelerateInterpolator.getInterpolation((float)mCurrentTargetOffsetTop / mTriggerOffset));
if (!isScrollUp && (curTargetTop < mTargetOriginalTop + 1)) {
  removeCallbacks(mCancel);
  mPrevY=event.getY();
  handled=false;
  mTopProgressBar.setTriggerPercentage(0f);
  break;
}
 else {
  updatePositionTimeout(true);
}
}
handled=true;
if (curTargetTop >= mTargetOriginalTop && !isRefreshing()) setTargetOffsetTop((int)((eventY - mPrevY) * mResistanceFactor),false);
 else setTargetOffsetTop((int)((eventY - mPrevY)),true);
mPrevY=event.getY();
}
break;
case MotionEvent.ACTION_UP:
if (mRefreshing) break;
if (mCurrentTargetOffsetTop >= mTriggerOffset && refresshMode == REFRESH_MODE_PULL) {
startRefresh();
handled=true;
}
 else {
updatePositionTimeout(false);
handled=true;
}
break;
case MotionEvent.ACTION_CANCEL:
if (mDownEvent != null) {
mDownEvent.recycle();
mDownEvent=null;
}
break;
}
if (DEBUG) Log.d(TAG,"onTouchEvent() " + handled);
return handled;
}
private void startRefresh(){
if (!checkCanDoRefresh()) {
updatePositionTimeout(false);
return;
}
removeCallbacks(mCancel);
setRefreshState(State.STATE_REFRESHING);
setRefreshing(true);
if (mListener != null) mListener.onRefresh();
}
private void updateContentOffsetTop(int targetTop,boolean changeHeightOnly){
final int currentTop=mTarget.getTop();
if (targetTop < mTargetOriginalTop) {
targetTop=mTargetOriginalTop;
}
setTargetOffsetTop(targetTop - currentTop,changeHeightOnly);
}
private void setTargetOffsetTop(int offset,boolean changeHeightOnly){
if (offset == 0) return;
if (mCurrentTargetOffsetTop + offset >= 0) {
mTarget.offsetTopAndBottom(offset);
mHeadview.offsetTopAndBottom(offset);
mCurrentTargetOffsetTop+=offset;
invalidate();
}
 else {
updateContentOffsetTop(mTargetOriginalTop,changeHeightOnly);
}
updateHeadViewState(changeHeightOnly);
}
private void updatePositionTimeout(boolean isDelayed){
removeCallbacks(mCancel);
if (isDelayed && mReturnToOriginalTimeout <= 0) return;
postDelayed(mCancel,isDelayed ? mReturnToOriginalTimeout : 0);
}
public void setEnableHorizontalScroll(boolean isEnable){
enableHorizontalScroll=isEnable;
}
public void enableTopProgressBar(boolean isEnable){
enableTopProgressBar=isEnable;
requestLayout();
}
public void setKeepTopRefreshingHead(boolean isEnable){
keepTopRefreshingHead=isEnable;
}
public boolean isKeepTopRefreshingHead(){
return keepTopRefreshingHead;
}
public void setReturnToOriginalTimeout(int mReturnToOriginalTimeout){
this.mReturnToOriginalTimeout=mReturnToOriginalTimeout;
}
public int getReturnToOriginalTimeout(){
return this.mReturnToOriginalTimeout;
}
public int getRefreshCompleteTimeout(){
return mRefreshCompleteTimeout;
}
public void setRefreshCompleteTimeout(int mRefreshCompleteTimeout){
this.mRefreshCompleteTimeout=mRefreshCompleteTimeout;
}
public void setReturnToTopDuration(int duration){
this.mReturnToTopDuration=duration;
}
public int getReturnToTopDuration(){
return this.mReturnToTopDuration;
}
public void setReturnToHeaderDuration(int duration){
this.mReturnToHeaderDuration=duration;
}
public int getReturnToHeaderDuration(){
return this.mReturnToHeaderDuration;
}
public void setRefreshCheckHandler(RefreshCheckHandler handler){
mRefreshCheckHandler=handler;
}
public void setScroolUpHandler(ScrollUpHandler handler){
mScrollUpHandler=handler;
}
public void setScroolLeftOrRightHandler(ScrollLeftOrRightHandler handler){
mScrollLeftOrRightHandler=handler;
}
public float getResistanceFactor(){
return mResistanceFactor;
}
public void setResistanceFactor(float factor){
mResistanceFactor=factor;
}
public int getProgressBarHeight(){
return mProgressBarHeight;
}
public void setProgressBarHeight(int height){
mProgressBarHeight=height;
mConvertedProgressBarHeight=(int)(getResources().getDisplayMetrics().density * mProgressBarHeight);
}
public int getTriggerDistance(){
return mTriggerDistance;
}
public void setTriggerDistance(int distance){
if (distance < 0) distance=0;
mTriggerDistance=distance;
}
public interface OnRefreshListener {
void onRefresh();
}
public interface RefreshCheckHandler {
boolean canRefresh();
}
public interface ScrollUpHandler {
boolean canScrollUp(View view);
}
public interface ScrollLeftOrRightHandler {
boolean canScrollLeftOrRight(View view,int direction);
}
public interface CustomSwipeRefreshHeadLayout {
void onStateChange(State currentState,State lastState);
}
public static class State {
public final static int STATE_NORMAL=0;
public final static int STATE_READY=1;
public final static int STATE_REFRESHING=2;
public final static int STATE_COMPLETE=3;
private int refreshState=STATE_NORMAL;
private float percent;
private int headerTop;
private int trigger;
public State(int refreshState){
this.refreshState=refreshState;
}
void update(int refreshState,int top,int trigger){
this.refreshState=refreshState;
this.headerTop=top;
this.trigger=trigger;
this.percent=(float)top / trigger;
}
public int getRefreshState(){
return refreshState;
}
public float getPercent(){
return percent;
}
public int getHeaderTop(){
return headerTop;
}
public int getTrigger(){
return trigger;
}
public String toString(){
return "[refreshState = " + refreshState + ", percent = "+ percent+ ", top = "+ headerTop+ ", trigger = "+ trigger+ "]";
}
}
private class BaseAnimationListener implements AnimationListener {
public void onAnimationStart(Animation animation){
}
public void onAnimationEnd(Animation animation){
}
public void onAnimationRepeat(Animation animation){
}
}
private void postDelayed(Runnable runnable,int delay){
new r.android.os.Handler().postDelayed(runnable,delay);
}
public static class LayoutParams extends MarginLayoutParams {
public LayoutParams(int width,int height){
super(width,height);
}
}
public CustomSwipeRefreshLayout(){
mTopProgressBar=new CustomSwipeProgressBar(this);
setProgressBarHeight(PROGRESS_BAR_HEIGHT);
mDecelerateInterpolator=new DecelerateInterpolator(DECELERATE_INTERPOLATION_FACTOR);
mAccelerateInterpolator=new AccelerateInterpolator(ACCELERATE_INTERPOLATION_FACTOR);
}
public void setProgressBarColor(Object color1,Object color2,Object color3,Object color4){
mTopProgressBar.setProgressBarColor(color1,color2,color3,color4);
}
private String customHeaderLayout;
public void setCustomHeaderLayout(String layout){
this.customHeaderLayout=layout;
}
private Integer webHeaderZIndex;
public void setWebHeaderZIndex(Object objValue){
this.webHeaderZIndex=(Integer)objValue;
updateZIndex();
}
private void updateZIndex(){
if (webHeaderZIndex != null && mHeadview != null) {
mHeadview.setMyAttribute("zIndex",webHeaderZIndex);
}
}
}
