/**
 * 
 */
package com.webwalker.controller.executor;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

/**
 * 各类型任务的执行实现
 * 
 * @author Administrator
 * 
 */
public abstract class TaskController extends Service implements TaskListener {
	private String TAG = "TaskController";
	protected boolean hasInterupted = false;
	protected List<Task> list;

	protected ExecutorService pool;

	@Override
	public void onCreate() {
		super.onCreate();
		pool = Executors.newCachedThreadPool();
		//pool = Executors.newFixedThreadPool(10);
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.v(TAG, this.getClass().getName() + "已启动");
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		pool.shutdown();
	}

	@Override
	public void onLowMemory() {
		stopForeground(true);
		super.onLowMemory();
	}

	@Override
	public void addTask(List<Task> task) {
		this.list = task;
	}

	@Override
	public void startTask() {
	}

	@Override
	public void stopTask() {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteTask() {
		// TODO Auto-generated method stub

	}

	@Override
	public void clearTask() {
		// TODO Auto-generated method stub

	}

	@Override
	public void taskOver(Task task) {
		// TODO Auto-generated method stub

	}

}
