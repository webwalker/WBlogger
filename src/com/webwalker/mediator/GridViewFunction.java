/**
 * 
 */
package com.webwalker.mediator;

import android.content.Context;
import android.view.View.OnClickListener;
import android.view.View.OnCreateContextMenuListener;

import com.webwalker.activity.BaseActivity;
import com.webwalker.entity.AppInfo;
import com.webwalker.wblogger.MyApplication;

/**
 * @author Administrator
 * 
 */
public abstract class GridViewFunction {
	protected AppInfo app = null;
	protected int functionImageRscId;
	protected int functionTextRscId;
	protected Context context = null;
	protected MyApplication application;

	public GridViewFunction(Context context) {
		this.context = context; // 获取上下文
		application = ((BaseActivity) context).getMyApplication();
	}

	public abstract OnClickListener getOnClickListener();

	public abstract OnCreateContextMenuListener getOnContextMenuClickListener();

	// 抽象函数，强制子类去继承它，并实现各自的功能

	public int getFunctionImageRscId() {
		return functionImageRscId;
	}

	public int getFunctionTextRscId() {
		return functionTextRscId;
	}
}
