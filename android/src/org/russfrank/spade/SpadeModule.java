/**
 * This file was auto-generated by the Titanium Module SDK helper for Android
 * Appcelerator Titanium Mobile
 * Copyright (c) 2009-2010 by Appcelerator, Inc. All Rights Reserved.
 * Licensed under the terms of the Apache Public License
 * Please see the LICENSE included with this distribution for details.
 *
 */
package org.russfrank.spade;

import org.appcelerator.kroll.KrollDict;
import org.appcelerator.kroll.KrollModule;
import org.appcelerator.kroll.annotations.Kroll;

import ti.modules.titanium.ui.ViewProxy;

import org.appcelerator.titanium.view.TiUIView;
import org.appcelerator.titanium.TiApplication;
import org.appcelerator.titanium.proxy.TiViewProxy; 
import org.appcelerator.kroll.common.Log;
import org.appcelerator.kroll.common.TiConfig;
import org.appcelerator.titanium.util.TiConvert;

import android.app.Activity;
import android.view.MotionEvent;
import android.view.KeyEvent;
import android.os.SystemClock;
import android.view.View;
import android.app.Instrumentation;
import android.test.TouchUtils;
import android.test.InstrumentationTestCase;
import android.widget.EditText;

import android.graphics.Rect;
import android.graphics.Point;

import com.jayway.android.robotium.solo.Solo;
import com.jayway.android.robotium.solo.Clicker;
import com.jayway.android.robotium.solo.ViewFetcher;
import com.jayway.android.robotium.solo.Scroller;
import com.jayway.android.robotium.solo.RobotiumUtils;
import com.jayway.android.robotium.solo.Sleeper;
import com.jayway.android.robotium.solo.Waiter;
import com.jayway.android.robotium.solo.ActivityUtils;
import com.jayway.android.robotium.solo.Searcher;
import com.jayway.android.robotium.solo.TextEnterer;

@Kroll.module(name="Spade", id="org.russfrank.spade")
public class SpadeModule extends KrollModule
{

  // Standard Debugging variables
  private static final String LCAT = "SpadeModule";
  private static final boolean DBG = TiConfig.LOGD;

  Solo mSolo;
  Clicker mClicker;
  Instrumentation mInst;
  ViewFetcher mViewFetcher;
  Scroller mScroller;
  RobotiumUtils mRobotiumUtils;
  Sleeper mSleeper;
  Waiter mWaiter;
  ActivityUtils mActivityUtils;
  Searcher mSearcher;
  TextEnterer mTextEnterer;

  // You can define constants with @Kroll.constant, for example:
  // @Kroll.constant public static final String EXTERNAL_NAME = value;

  public SpadeModule()
  {
    super();

    TiApplication appContext = TiApplication.getInstance();
    mInst = new Instrumentation();
    Activity activity = appContext.getCurrentActivity();

    mSolo = new Solo(mInst, activity);

    mSleeper = new Sleeper();
    mActivityUtils = new ActivityUtils(mInst, activity, mSleeper);
    mViewFetcher = new ViewFetcher(mActivityUtils);
    mScroller = new Scroller(mInst, mActivityUtils, mViewFetcher, mSleeper);
    mSearcher = new Searcher(mViewFetcher, mScroller, mSleeper);
    mClicker = new Clicker(mViewFetcher, mScroller, mRobotiumUtils, mInst, mSleeper, mWaiter);
    mTextEnterer = new TextEnterer(mInst, mClicker);
  }

  @Kroll.onAppCreate
  public static void onAppCreate(TiApplication app)
  {
  }

  @Kroll.method
  public void tap(ViewProxy proxy) {
    TiUIView tiView = proxy.peekView();
    View view;

    if (tiView != null) {
      view = tiView.getNativeView();
    } else {
      return;
    }

    mClicker.clickOnScreen(view);
  }

  @Kroll.method
  public void tapAt(ViewProxy proxy, KrollDict point) throws IllegalArgumentException {
    View view = proxy.peekView().getNativeView();

    int[] position = new int[2];
    view.getLocationOnScreen(position);

    int[] dest = new int[2];

    dictToPoint(point, dest);

    float x = position[0] + (float) dest[0];
    float y = position[1] + (float) dest[1];

    mClicker.clickOnScreen(x, y);
  }

  @Kroll.method
  public void drag(ViewProxy proxy, KrollDict fromDict, KrollDict toDict) {
    View view = proxy.peekView().getNativeView();

    int[] from = new int[2];
    dictToPoint(fromDict, from);

    int[] to = new int[2];
    dictToPoint(toDict, to);

    int[] position = new int[2];
    view.getLocationOnScreen(position);

    mScroller.drag(
      (float) (from[0] + position[0]),
      (float) (to[0] + position[0]), 
      (float) (from[1] + position[1]), 
      (float) (to[1] + position[1]),
      5
    );
  }

  @Kroll.method
  public boolean visible(ViewProxy proxy) {
    TiUIView tiView = proxy.peekView();
    View view;

    if (tiView != null) {
      view = tiView.getNativeView();
    } else {
      return false;
    }

    Rect rect = new Rect();
    Point point = new Point();
    return view.getGlobalVisibleRect(rect, point);
  }

  @Kroll.method
  public boolean type(ViewProxy proxy, String text) {
    TiUIView tiView = proxy.peekView();
    View view;

    if (tiView != null) {
      view = tiView.getNativeView();
    } else {
      return false;
    }

    if (!(view instanceof EditText)) {
      Log.e("spade", "type: expected TextArea or TextField for first argument");
      return false;
    }

    mInst.sendStringSync(text);
    return true;
  }

  @Kroll.method
  public void tapText(String text) {
    mSolo.clickOnText(text);
  }

  private void dictToPoint(KrollDict point, int[] pointOut) throws IllegalArgumentException {
    if (!point.containsKey("x")) {
      throw new IllegalArgumentException("spade: required property \"x\" not found in point");
    }
    if (!point.containsKey("y")) {
      throw new IllegalArgumentException("spade: required property \"y\" not found in point");
    }

    int x = TiConvert.toInt(point, "x");
    int y = TiConvert.toInt(point, "y");
    pointOut[0] = x;
    pointOut[1] = y;
  }

}

