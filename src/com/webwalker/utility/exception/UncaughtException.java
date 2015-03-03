/**
 * 
 */
package com.webwalker.utility.exception;

import java.lang.Thread.UncaughtExceptionHandler;

import android.util.Log;

import com.webwalker.utility.AbstractApplication;
import com.webwalker.utility.logger.AbstractLogger;
import com.webwalker.utility.logger.SysLogger;
import com.webwalker.utility.Consts;

/**
 * @author Administrator
 * 
 */
public class UncaughtException implements UncaughtExceptionHandler {

	private AbstractApplication myApplication;
	private AbstractLogger logger = SysLogger.getInstance(Consts.LOG_PREFIX);

	public UncaughtException(AbstractApplication myApplication) {
		this.myApplication = myApplication;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.lang.Thread.UncaughtExceptionHandler#uncaughtException(java.lang
	 * .Thread, java.lang.Throwable)
	 */
	@Override
	public void uncaughtException(Thread thread, Throwable ex) {
		long threadId = thread.getId();
		logger.d("ANDROID_LAB", "Thread.getName()=" + thread.getName() + " id="
				+ threadId + " state=" + thread.getState());
		logger.d("ANDROID_LAB", "Error[" + ex.getMessage() + "]");
		myApplication.Exit();
		myApplication.Add(Consts.EXIT_APP, Boolean.valueOf(true));
	}
}
