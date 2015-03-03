/**
 * 
 */
package com.webwalker.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.webwalker.data.MyDataHelper;
import com.webwalker.utils.AppConstants;
import com.webwalker.wblogger.R;

/**
 * @author Administrator
 * 
 */
public class AppController extends BaseController {

	public AppController() {
	}

	public AppController(Context context) {
		super(context);
	}

	public int getAppIcon(int appId) {
		switch (appId) {
		case AppConstants.OAuth.sina:
			return R.drawable.sina_icon;
		case AppConstants.OAuth.tencent:
			return R.drawable.tencent_icon;
		}

		return R.drawable.sina_icon;
	}

	public String getStatusDesc(String status) {
		if (status == "1")
			status = "运行中";
		else
			status = "未运行";
		return status;
	}

	// 获取applist
	public List<Map<String, ?>> getAppList() {
		List<Map<String, ?>> list = new ArrayList<Map<String, ?>>();

		MyDataHelper helper = new MyDataHelper(context);

		try {
			Cursor cursor = helper.getAppTypes();

			while (cursor.moveToNext()) {
				Map<String, Object> m = new TreeMap<String, Object>();
				m.put(AppConstants.MapValue,
						cursor.getString(cursor.getColumnIndex("app_id")));
				// 用于显示文本
				m.put(AppConstants.MapKey,
						cursor.getString(cursor.getColumnIndex("app_name")));
				list.add(m);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			helper.close();
		}

		return list;
	}
}
