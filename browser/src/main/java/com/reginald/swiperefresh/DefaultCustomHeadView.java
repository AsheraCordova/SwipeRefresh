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
import r.android.view.Gravity;
import r.android.view.LayoutInflater;
import r.android.view.View;
import r.android.view.ViewGroup;
import r.android.view.animation.Animation;
import r.android.view.animation.RotateAnimation;
import r.android.widget.ImageView;
import r.android.widget.LinearLayout;
import r.android.widget.ProgressBar;
import r.android.widget.TextView;

import com.reginald.swiperefresh.CustomSwipeRefreshLayout.State;

import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Created by liu on 2014/9/15.
 */

/**
 * The DefaultCustomHeadViewLayout is a refresh head view provided as default.
 * You can also make your own head view layout which must implement
 * CustomSwipeRefreshHeadview.CustomSwipeRefreshHeadLayout interface.
 */
public class DefaultCustomHeadView extends LinearLayout implements CustomSwipeRefreshLayout.CustomSwipeRefreshHeadLayout {

    private LinearLayout mContainer;

    private TextView mMainTextView;
    private TextView mSubTextView;
    private ImageView mImageView;
    private ProgressBar mProgressBar;

    private Animation mRotateUpAnim;
    private Animation mRotateDownAnim;
    private final int ROTATE_ANIM_DURATION = 180;
    private Animation.AnimationListener animationListener;

    public DefaultCustomHeadView() {setWillNotDraw(false);} public DefaultCustomHeadView(Context context) {
        super(context);
        setWillNotDraw(false);
        setupLayout(null);
    }

    public void setupLayout(String layout) {if (layout == null) {layout = "@layout/default_swiperefresh_head_layout";}
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        mContainer = (LinearLayout) LayoutInflater.from(getContext()).inflate(layout, this, false);
        addView(mContainer, lp);
        setGravity(Gravity.BOTTOM);
        mImageView = (ImageView) mContainer.getOuterWidget().findWidgetById("@+id/default_header_arrow").asWidget();
        mMainTextView = (TextView) mContainer.getOuterWidget().findWidgetById("@+id/default_header_textview").asWidget();
        mSubTextView = (TextView) mContainer.getOuterWidget().findWidgetById("@+id/default_header_time").asWidget();
        mProgressBar = (ProgressBar) mContainer.getOuterWidget().findWidgetById("@+id/default_header_progressbar").asWidget();

        setupAnimation();

    }

    public void setupAnimation() {

        mRotateUpAnim = new RotateAnimation(0.0f, -180.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        Animation.AnimationListener mRotateUpAnimListener = animationListener;
        mRotateUpAnim.setAnimationListener(mRotateUpAnimListener);
        mRotateUpAnim.setDuration(ROTATE_ANIM_DURATION);
        mRotateUpAnim.setFillAfter(true);

        mRotateDownAnim = new RotateAnimation(-180.0f, 0.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        mRotateDownAnim.setDuration(ROTATE_ANIM_DURATION);
        mRotateDownAnim.setFillAfter(true);
    }

    @Override
    public void onStateChange(State state, State lastState) {
        int stateCode = state.getRefreshState();
        int lastStateCode = lastState.getRefreshState();
        if (stateCode == lastStateCode) {
            return;
        }
        if (stateCode == CustomSwipeRefreshLayout.State.STATE_COMPLETE) {
            mImageView.clearAnimation();
            mImageView.setVisibility(View.INVISIBLE);
            mProgressBar.setVisibility(View.INVISIBLE);
        } else if (stateCode == CustomSwipeRefreshLayout.State.STATE_REFRESHING) {
            // show progress
            mImageView.clearAnimation();
            mImageView.setVisibility(View.INVISIBLE);
            mProgressBar.setVisibility(View.VISIBLE);
        } else {
            // show arrow
            mImageView.setVisibility(View.VISIBLE);
            mProgressBar.setVisibility(View.INVISIBLE);
        }

        switch (stateCode) {
            case CustomSwipeRefreshLayout.State.STATE_NORMAL:
                if (lastStateCode == CustomSwipeRefreshLayout.State.STATE_READY) {
                    mImageView.startAnimation(mRotateDownAnim);
                }
                if (lastStateCode == CustomSwipeRefreshLayout.State.STATE_REFRESHING) {
                    mImageView.clearAnimation();
                }
                mMainTextView.setText("@string/csr_text_state_normal");
                break;
            case CustomSwipeRefreshLayout.State.STATE_READY:
                if (lastStateCode != CustomSwipeRefreshLayout.State.STATE_READY) {
                    mImageView.clearAnimation();
                    mImageView.startAnimation(mRotateUpAnim);
                    mMainTextView.setText("@string/csr_text_state_ready");
                }
                break;
            case CustomSwipeRefreshLayout.State.STATE_REFRESHING:
                mMainTextView.setText("@string/csr_text_state_refresh");
                updateData();
                break;

            case CustomSwipeRefreshLayout.State.STATE_COMPLETE:
                mMainTextView.setText("@string/csr_text_state_complete");
                updateData();
                break;
            default:}mProgressBar.remeasure();
    }

    public void updateData() {

        String time = fetchData();
        if (time != null) {
            mSubTextView.setVisibility(VISIBLE);
            mSubTextView.setText(time);
        } else {
            mSubTextView.setVisibility(GONE);
        }

    }

    public String fetchData() {
        return  (com.ashera.widget.PluginInvoker.getConverter("resourcestring").convertFrom("@string/csr_text_last_refresh", null, getOuterWidget().getFragment())) + " " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    }

}