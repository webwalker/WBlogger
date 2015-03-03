/**
 * 
 */
package com.webwalker.api;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.webwalker.entity.RequestBody;
import com.webwalker.entity.SchedueExecutorEntity;
import com.webwalker.utility.BeanUtil;

/**
 * 工厂、抽象工厂
 * 
 * @author Administrator
 * 
 */
public abstract class WeiboCreator<T> implements ApiContainer {
	protected T executor;
	protected Context context = null;

	public WeiboCreator(Context context, T s) {
		this.context = context;
		this.executor = s;
	}

	// public abstract ApiContainer getContainer();



}
