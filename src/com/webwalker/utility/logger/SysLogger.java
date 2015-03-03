/**
 * 
 */
package com.webwalker.utility.logger;

import com.webwalker.utility.Consts;

import android.content.Context;
import android.util.Log;

/**
 * @author Administrator
 * 
 */
public class SysLogger extends AbstractLogger {

	private static SysLogger _Instance = null;

	public synchronized static SysLogger getInstance(String prefix) {
		if (_Instance == null) {
			_Instance = new SysLogger();
		}

		if (prefix.equals(""))
			prefix = Consts.LOG_PREFIX;

		_Instance.setLogPrefix(prefix);
		return _Instance;
	}

	@Override
	public void setLogPrefix(String prefix) {
		// TODO Auto-generated method stub
		super.setLogPrefix(prefix);
	}

	@Override
	public void i(String tag, String message) {
		// TODO Auto-generated method stub
		super.i(tag, message);
	}

	@Override
	public void d(String tag, String message) {
		// TODO Auto-generated method stub
		super.d(tag, message);
	}

	@Override
	public void w(String tag, String message) {
		// TODO Auto-generated method stub
		super.w(tag, message);
	}

	@Override
	public void e(String tag, String message) {
		// TODO Auto-generated method stub
		super.e(tag, message);
	}

	@Override
	public void e(String tag, Throwable e) {
		// TODO Auto-generated method stub
		super.e(tag, e);
	}

	@Override
	public void e(String tag, String message, Throwable e) {
		// TODO Auto-generated method stub
		super.e(tag, message, e);
	}

	@Override
	public void w(String tag, Throwable e) {
		// TODO Auto-generated method stub
		super.w(tag, e);
	}

}