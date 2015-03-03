package com.webwalker.utility.logger;

import android.util.Log;

/**
 * 
 * @author webwalker
 * 
 */
public abstract class AbstractLogger {
	private static boolean isOpenLog = true;

	public static void initLog(boolean isOpen) {
		isOpenLog = isOpen;
	}

	private static String LOG_PREFIX = "webwalker_";

	public void setLogPrefix(String prefix) {
		LOG_PREFIX = prefix;
	}
	
	/**
	 * info
	 * 
	 * @param tag
	 * @param message
	 */
	public void i(String tag, String message) {
		if (isOpenLog && message != null) {
			Log.i(LOG_PREFIX + tag, message);
		}
	}

	/**
	 * Debug
	 * 
	 * @param tag
	 * @param message
	 */
	public void d(String tag, String message) {
		if (isOpenLog && message != null) {
			Log.d(LOG_PREFIX + tag, message);
		}
	}	

	/**
	 * warn
	 * 
	 * @param tag
	 * @param message
	 */
	public void w(String tag, String message) {
		if (isOpenLog && message != null) {
			Log.w(LOG_PREFIX + tag, message);
		}
	}
	
	/**
	 * error
	 * 
	 * @param tag
	 * @param message
	 */
	public void e(String tag, String message) {
		if (isOpenLog && message != null) {
			Log.e(LOG_PREFIX + tag, message);
		}
	}

	/**
	 * error
	 * 
	 * @param tag
	 * @param message
	 */
	public void e(String tag, Throwable e) {
		if (isOpenLog && e != null) {
			Log.e(LOG_PREFIX + tag, e.getMessage(), e);
		}
	}

	/**
	 * error
	 * 
	 * @param tag
	 * @param message
	 */
	public void e(String tag, String message, Throwable e) {
		if (isOpenLog && e != null) {
			Log.e(LOG_PREFIX + tag, message, e);
		}
	}

	/**
	 * error
	 * 
	 * @param tag
	 * @param message
	 */
	public void w(String tag, Throwable e) {
		if (isOpenLog && e != null) {
			Log.e(LOG_PREFIX + tag, e.getMessage(), e);
		}
	}
}
