/**
 * 
 */
package com.webwalker.mediator;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnCreateContextMenuListener;

import com.webwalker.wblogger.R;

/**
 * @author Administrator
 * 
 */
public class SinaWeiboFunction extends GridViewFunction {

	public SinaWeiboFunction(Context context) {
		super(context);
		this.functionImageRscId = R.drawable.sina_icon;// 本功能的图标资源
		this.functionTextRscId = R.string.fun_sina_tip;// 功能名字的字符串资源
	}

	public OnClickListener getOnClickListener() {
		return new OnClickListener() { // 测试的响应事件
			public void onClick(View v) {

			}
		};
	}

	@Override
	public OnCreateContextMenuListener getOnContextMenuClickListener() {
		// TODO Auto-generated method stub
		return null;
	}

}
