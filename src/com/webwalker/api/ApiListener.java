package com.webwalker.api;

import java.io.IOException;

import android.util.Log;

import com.weibo.sdk.android.WeiboException;
import com.weibo.sdk.android.net.RequestListener;

public class ApiListener {
	ListenerCallback callback = null;

	public ApiListener(ListenerCallback callback) {
		this.callback = callback;
	}

	private String TAG = "Wblogger";

	public interface ListenerCallback {
		public void callBack(Object data);

		public void onError(Object data);
	}

	public class sinaListener implements RequestListener {

		public sinaListener() {

		}

		/**
		 * "statuses": [ "3523939748125047" ], "marks": [], "hasvisible": false,
		 * "previous_cursor": 0, "next_cursor": 0, "total_number": 238
		 */
		@Override
		public void onComplete(String arg0) {
			Log.v(TAG, arg0);

			if (arg0 != null) {
				if (arg0.indexOf("{") >= 0) {
					if (callback != null) {
						callback.callBack(arg0);
					}

					Log.v(TAG, "sinaListener oncomplete");
				}
			}
		}

		@Override
		public void onError(WeiboException arg0) {
			Log.v(TAG, arg0.getMessage());
			if (callback != null) {
				callback.onError(arg0.getMessage());
			}
		}

		@Override
		public void onIOException(IOException arg0) {
			Log.v(TAG, arg0.getMessage());
			if (callback != null) {
				callback.onError(arg0.getMessage());
			}
		}
	}
}
