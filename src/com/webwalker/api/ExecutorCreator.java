/**
 * 
 */
package com.webwalker.api;

import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.webwalker.entity.RequestBody;
import com.webwalker.entity.SchedueExecutorEntity;
import com.webwalker.utility.BeanUtil;
import com.weibo.sdk.android.WeiboException;
import com.weibo.sdk.android.net.RequestListener;

/**
 * 任务计划执行者
 * 
 * @author Administrator
 * @param <T>
 * 
 */
public abstract class ExecutorCreator extends
		WeiboCreator<SchedueExecutorEntity> {

	// 请求信息
	protected RequestBody requestBody = null;

	public ExecutorCreator(Context context, SchedueExecutorEntity s) {
		super(context, s);
		executor = s;
	}

	public abstract RequestBody getRequestBody();

	protected RequestBody getRequest(String request) {
		RequestBody req = getRequestBody();
		try {
			JSONObject jsonObj = new JSONObject(request);
			BeanUtil.initBeans(jsonObj, req);

		} catch (JSONException e) {
			e.printStackTrace();
		}

		return req;
	}

	protected class nullListener implements RequestListener {

		@Override
		public void onComplete(String arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onError(WeiboException arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onIOException(IOException arg0) {
			// TODO Auto-generated method stub
			
		}
		
	}
}
