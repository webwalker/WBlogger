/**
 * 
 */
package com.webwalker.mediator;

import android.content.Context;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnCreateContextMenuListener;

import com.webwalker.entity.AppInfo;
import com.webwalker.utility.PackageUtil;
import com.webwalker.utils.AppConstants;
import com.webwalker.utils.MyContext;
import com.webwalker.wblogger.AppListActivity;
import com.webwalker.wblogger.R;

/**
 * @author Administrator
 * 
 */
public class NormalAppFunction extends GridViewFunction {

	public NormalAppFunction(Context context) {
		super(context);
	}

	public NormalAppFunction(Context context, AppInfo app) {
		super(context);
		this.app = app;
	}

	@Override
	public OnClickListener getOnClickListener() {
		return new OnClickListener() {
			public void onClick(View v) {
				Log.v("WBlogger", "start app " + app.packageName);
				PackageUtil.startAppByPackageName(context, app.packageName);
				Log.v("WBlogger", "start over!" + app.packageName);
			}
		};
	}

	@Override
	public OnCreateContextMenuListener getOnContextMenuClickListener() {
		return new OnCreateContextMenuListener() {
			@Override
			public void onCreateContextMenu(ContextMenu menu, View v,
					ContextMenuInfo menuInfo) {

				MyContext.Add(AppConstants.Keys.CurrentApp, app);

				MenuInflater inflate = new MenuInflater(context);
				inflate.inflate(R.menu.app_context_item, menu);
			}
		};
	}
}
