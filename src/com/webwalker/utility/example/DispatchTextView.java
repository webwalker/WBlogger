/**
 * 
 */
package com.webwalker.utility.example;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.TextView;

/**
 * @author Administrator
 * 
 */
public class DispatchTextView extends TextView {
	private final String TAG = "MyTextView";

	public DispatchTextView(Context context, AttributeSet attrs) {

		super(context, attrs);

	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		int action = ev.getAction();

		switch (action) {

		case MotionEvent.ACTION_DOWN:

			Log.d(TAG, "dispatchTouchEvent action:ACTION_DOWN");

			break;

		case MotionEvent.ACTION_MOVE:

			Log.d(TAG, "dispatchTouchEvent action:ACTION_MOVE");

			break;

		case MotionEvent.ACTION_UP:

			Log.d(TAG, "dispatchTouchEvent action:ACTION_UP");

			break;

		case MotionEvent.ACTION_CANCEL:

			Log.d(TAG, "onTouchEvent action:ACTION_CANCEL");

			break;

		}
		return super.dispatchTouchEvent(ev);
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {

		int action = ev.getAction();

		switch (action) {

		case MotionEvent.ACTION_DOWN:

			Log.d(TAG, "---onTouchEvent action:ACTION_DOWN");

			break;

		case MotionEvent.ACTION_MOVE:

			Log.d(TAG, "---onTouchEvent action:ACTION_MOVE");

			break;

		case MotionEvent.ACTION_UP:

			Log.d(TAG, "---onTouchEvent action:ACTION_UP");

			break;

		case MotionEvent.ACTION_CANCEL:

			Log.d(TAG, "---onTouchEvent action:ACTION_CANCEL");

			break;

		}

		return true;

	}

}
