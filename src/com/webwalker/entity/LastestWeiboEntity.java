package com.webwalker.entity;

import com.weibo.sdk.android.net.RequestListener;

public class LastestWeiboEntity extends RequestBody {

	private String userId;
	private RequestListener listener;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * @return the listener
	 */
	public RequestListener getListener() {
		return listener;
	}

	/**
	 * @param listener
	 *            the listener to set
	 */
	public void setListener(RequestListener listener) {
		this.listener = listener;
	}
}
