package com.webwalker.utility;

import java.util.Hashtable;

import android.app.Application;
import android.content.ComponentName;
import android.content.Intent;
import com.webwalker.utility.data.IStoreProvider;
import com.webwalker.utility.data.SharedPreference;
import com.webwalker.utility.exception.UncaughtException;
import com.webwalker.utility.logger.AbstractLogger;
import com.webwalker.utility.logger.SysLogger;

/**
 * 应用全局处理（变量、上下文对象、生命周期）
 * 
 * @author Administrator
 * 
 */
public abstract class AbstractApplication extends Application {

	/**
	 * 上下文对象
	 */
	private Hashtable<String, Object> contextInfo;
	private Intent referIntent;
	private UncaughtException ueHandler;

	private AbstractLogger logger = SysLogger.getInstance(Consts.LOG_PREFIX);
	private IStoreProvider preference = new SharedPreference(this);

	@Override
	public void onCreate() {
		super.onCreate();
		logger.i("MyApplication",
				"MyApplication.onCreate......................................");
		contextInfo = new Hashtable<String, Object>();
		// 设置异常处理实例
		ueHandler = new UncaughtException(this);
		Thread.setDefaultUncaughtExceptionHandler(ueHandler);
	}

	/*
	 * 状态变换时不保证onTerminate被调用，eg：系统内存不足可能强制杀掉进程， 这个时候onterminate就不被调
	 * 
	 * @see android.app.Application#onTerminate()
	 */
	@Override
	public void onTerminate() {
		this.clearAll();
		super.onTerminate();
	}

	public Object get(String key) {
		if (key != null) {
			return contextInfo.get(key);
		} else {
			new IllegalArgumentException(
					"null pointer value for key via get(String key)");
		}
		return null;
	}

	public void Add(String key, Object value) {
		if (key != null && value != null) {
			contextInfo.put(key, value);
		} else {
			new IllegalArgumentException(
					"null pointer value for key or value via Add(String key, Object value)");
		}
	}

	public Object Remove(String key) {
		return contextInfo.remove(key);
	}

	public Intent getReferIntent() {
		if (referIntent == null) {
			// 获取当前包LAUNCHER的Activity
			referIntent = new Intent(Intent.ACTION_MAIN);
			referIntent.addCategory(Intent.CATEGORY_LAUNCHER);
			referIntent.setPackage(this.getPackageName());
			ComponentName ac = referIntent.resolveActivity(this
					.getPackageManager());
			referIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			referIntent.setClassName(this, ac.getClassName());
		}
		return referIntent;
	}

	public void setReferIntent(Intent referIntent) {
		this.referIntent = referIntent;
	}

	/**
	 * 清除当前缓存的所有信息
	 */
	public void Exit() {
		setReferIntent(null);
		contextInfo.clear();

		// 停止后台注册的服务
	}

	/**
	 * 清除当前应用的所有的信息
	 */
	public void clearAll() {
		contextInfo.clear();
		preference.Clear();
	}
}
