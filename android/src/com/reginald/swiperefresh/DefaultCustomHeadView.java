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

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

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

    public DefaultCustomHeadView(Context context, String customLayout) {
        super(context);
        setWillNotDraw(false);
        setupLayout(customLayout);
    }

    public void setupLayout(String customLayout) {
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        if (customLayout == null) {
        	customLayout = "default_swiperefresh_head_layout";
        } else {
        	customLayout = customLayout.replace("@layout/", "");
        }
        mContainer = (LinearLayout) LayoutInflater.from(getContext()).inflate(getContext().getResources().getIdentifier(customLayout, "layout", getContext().getPackageName()), null);
        addView(mContainer, lp);
        setGravity(Gravity.BOTTOM);
        mImageView = (ImageView) findViewById(getContext().getResources().getIdentifier("default_header_arrow", "id", getContext().getPackageName()));
        mMainTextView = (TextView) findViewById(getContext().getResources().getIdentifier("default_header_textview", "id", getContext().getPackageName()));
        mSubTextView = (TextView) findViewById(getContext().getResources().getIdentifier("default_header_time", "id", getContext().getPackageName()));
        mProgressBar = (ProgressBar) findViewById(getContext().getResources().getIdentifier("default_header_progressbar", "id", getContext().getPackageName()));

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
                mMainTextView.setText(getContext().getResources().getIdentifier("csr_text_state_normal", "string", getContext().getPackageName()));
                break;
            case CustomSwipeRefreshLayout.State.STATE_READY:
                if (lastStateCode != CustomSwipeRefreshLayout.State.STATE_READY) {
                    mImageView.clearAnimation();
                    mImageView.startAnimation(mRotateUpAnim);
                    mMainTextView.setText(getContext().getResources().getIdentifier("csr_text_state_ready", "string", getContext().getPackageName()));
                }
                break;
            case CustomSwipeRefreshLayout.State.STATE_REFRESHING:
                mMainTextView.setText(getContext().getResources().getIdentifier("csr_text_state_refresh", "string", getContext().getPackageName()));
                updateData();
                break;

            case CustomSwipeRefreshLayout.State.STATE_COMPLETE:
                mMainTextView.setText(getContext().getResources().getIdentifier("csr_text_state_complete", "string", getContext().getPackageName()));
                updateData();
                break;
            default:
        }
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
        return getResources().getString(
                getContext().getResources().getIdentifier("csr_text_last_refresh", "string", getContext().getPackageName())) + " " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    }

}