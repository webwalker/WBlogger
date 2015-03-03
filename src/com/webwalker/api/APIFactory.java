/**
 * 
 */
package com.webwalker.api;

import android.content.Context;

import com.webwalker.entity.ApiCommonEntity;
import com.webwalker.entity.SchedueExecutorEntity;
import com.webwalker.utils.AppConstants;

/**
 * API简单工厂
 * 
 * @author Administrator
 * 
 */
public class APIFactory {

	private static APIFactory _instance;

	public synchronized static APIFactory getInstance() {
		if (_instance == null) {
			_instance = new APIFactory();
		}
		return _instance;
	}

	public ApiContainer getExecutorContainer(Context context,
			SchedueExecutorEntity s) {

		// 动态代理
		// MyInvocationHandler handler = new MyInvocationHandler();
		// return (ApiContainer) handler.bind(new StatusesAPI(
		// (Oauth2AccessToken) binder.getData()));

		switch (s.getAppid()) {
		case AppConstants.OAuth.sina:
			return new SinaExecutor(context, s);
		case AppConstants.OAuth.tencent:
			break;
		}

		return null;
	}

	public ApiContainer getCommonContainer(Context context, ApiCommonEntity a) {

		// 动态代理
		// MyInvocationHandler handler = new MyInvocationHandler();
		// return (ApiContainer) handler.bind(new StatusesAPI(
		// (Oauth2AccessToken) binder.getData()));

		switch (a.getAppid()) {
		case AppConstants.OAuth.sina:
			return new SinaCommonCreator(context, a);
		case AppConstants.OAuth.tencent:
			break;
		}

		return null;
	}
}
