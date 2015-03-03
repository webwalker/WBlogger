/**
 * 
 */
package com.webwalker.controller.service;

import java.util.List;

import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.webwalker.controller.executor.Task;
import com.webwalker.controller.executor.TaskController;

/**
 * Token过期提醒、系统错误提醒、版本更新提醒
 * 
 * @author Administrator
 */
public class RemindService extends TaskController {

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.v("3", flags + "," + startId);
		// 发送Notification，通知版本更新

		return super.onStartCommand(intent, flags, startId);
	}
}
