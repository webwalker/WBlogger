/**
 * 
 */
package com.webwalker.controller.service;

import java.util.List;

import android.content.Intent;
import android.os.IBinder;

import com.webwalker.controller.executor.Task;
import com.webwalker.controller.executor.TaskController;

/**
 * 生成计划
 * 
 * @author Administrator
 * 
 */
public class PlanService extends TaskController {

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
		return super.onStartCommand(intent, flags, startId);
	}
}
