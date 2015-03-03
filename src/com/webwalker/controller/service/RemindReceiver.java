/**
 * 
 */
package com.webwalker.controller.service;

import android.R;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.webwalker.utility.MessagesUtil;
import com.webwalker.utils.AppConstants;

/**
 * @author Administrator
 * 
 */
public class RemindReceiver extends BroadcastReceiver {

	public static final String RemindReceiver = "com.webwalker.receiver.reminder";

	@Override
	public void onReceive(Context arg0, Intent arg1) {
		String title = arg1.getStringExtra(AppConstants.Keys.NotifyTitle);
		String body = arg1.getStringExtra(AppConstants.Keys.NotifyBody);

		MessagesUtil.showNotify(arg0, R.drawable.stat_notify_more, title, body);
	}
}
