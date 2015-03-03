package com.webwalker.utility.example;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MotionEvent;

import com.webwalker.wblogger.R;

/**
 * @author Administrator
 *         为了指代方便，下面将MyLinearLayout简称为L，将MyTextView简称为T，L.onInterceptTouchEvent
 *         =true 表示的含义为MyLinearLayout中的onInterceptTouchEvent方法返回值为true，
 *         通过程序运行时输出的Log来说明调用时序。 第1种情况 L.onInterceptTouchEvent=false&&
 *         L.onTouchEvent=true &&T.onTouchEvent=true 输出下面的Log：
 *         D/MyLinearLayout(11865): dispatchTouchEvent action:ACTION_DOWN
 *         D/MyLinearLayout(11865): onInterceptTouchEvent action:ACTION_DOWN
 *         D/MyTextView(11865): dispatchTouchEvent action:ACTION_DOWN
 *         D/MyTextView(11865): ---onTouchEvent action:ACTION_DOWN
 *         D/MyLinearLayout(11865): dispatchTouchEvent action:ACTION_MOVE
 *         D/MyLinearLayout(11865): onInterceptTouchEvent action:ACTION_MOVE
 *         D/MyTextView(11865): dispatchTouchEvent action:ACTION_MOVE
 *         D/MyTextView(11865): ---onTouchEvent action:ACTION_MOVE
 *         ...........省略其他的ACTION_MOVE事件Log D/MyLinearLayout(11865):
 *         dispatchTouchEvent action:ACTION_UP D/MyLinearLayout(11865):
 *         onInterceptTouchEvent action:ACTION_UP D/MyTextView(11865):
 *         dispatchTouchEvent action:ACTION_UP D/MyTextView(11865):
 *         ---onTouchEvent action:ACTION_UP 结论：TouchEvent完全由TextView处理。 第2种情况
 *         L.onInterceptTouchEvent=false&& L.onTouchEvent=true
 *         &&T.onTouchEvent=false 输出下面的Log： D/MyLinearLayout(13101):
 *         dispatchTouchEvent action:ACTION_DOWN D/MyLinearLayout(13101):
 *         onInterceptTouchEvent action:ACTION_DOWN D/MyTextView(13101):
 *         dispatchTouchEvent action:ACTION_DOWN D/MyTextView(13101):
 *         ---onTouchEvent action:ACTION_DOWN D/MyLinearLayout(13101):
 *         ---onTouchEvent action:ACTION_DOWN D/MyLinearLayout(13101):
 *         dispatchTouchEvent action:ACTION_MOVE D/MyLinearLayout(13101):
 *         ---onTouchEvent action:ACTION_MOVE ...........省略其他的ACTION_MOVE事件Log
 *         D/MyLinearLayout(13101): dispatchTouchEvent action:ACTION_UP
 *         D/MyLinearLayout(13101): ---onTouchEvent action:ACTION_UP
 *         结论：TextView只处理了ACTION_DOWN事件，LinearLayout处理了所有的TouchEvent。 第3种情况
 *         L.onInterceptTouchEvent=true&& L.onTouchEvent=true 输出下面的Log：
 *         D/MyLinearLayout(13334): dispatchTouchEvent action:ACTION_DOWN
 *         D/MyLinearLayout(13334): onInterceptTouchEvent action:ACTION_DOWN
 *         D/MyLinearLayout(13334): ---onTouchEvent action:ACTION_DOWN
 *         D/MyLinearLayout(13334): dispatchTouchEvent action:ACTION_MOVE
 *         D/MyLinearLayout(13334): ---onTouchEvent action:ACTION_MOVE
 *         ...........省略其他的ACTION_MOVE事件Log D/MyLinearLayout(13334):
 *         dispatchTouchEvent action:ACTION_UP D/MyLinearLayout(13334):
 *         ---onTouchEvent action:ACTION_UP 结论：LinearLayout处理了所有的TouchEvent。
 *         第4种情况 L.onInterceptTouchEvent=true&& L.onTouchEvent=false 输出下面的Log：
 *         D/MyLinearLayout(13452): dispatchTouchEvent action:ACTION_DOWN
 *         D/MyLinearLayout(13452): onInterceptTouchEvent action:ACTION_DOWN
 *         D/MyLinearLayout(13452): ---onTouchEvent action:ACTION_DOWN
 *         结论：LinearLayout只处理了ACTION_DOWN事件
 *         ，那么其他的TouchEvent被谁处理了呢？答案是LinearLayout最外层的Activity处理了TouchEvent
 */
public class DispatchActivity extends Activity {
	private final String TAG = "DispatchActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dispatch);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_dispatch, menu);
		return true;
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

			Log.d(TAG, "dispatchTouchEvent action:ACTION_CANCEL");

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
