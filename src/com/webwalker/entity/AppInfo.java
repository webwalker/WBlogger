/**
 * 
 */
package com.webwalker.entity;

import android.graphics.drawable.Drawable;
import android.util.Log;

/**
 * @author Administrator
 * 
 */
public class AppInfo {
	public String appName = "";
	public Drawable appIcon = null;
	public String packageName = "";
	public int versionCode = 0;
	public String versionName = "";
	public PackageSizeInfo sizeInfo = null;
	public String updateDate = "";
	public String cacheDir = "";
	public String dataDir = "";

	public void print() {
		Log.v("app", "Name:" + appName + " Package:" + packageName);
		Log.v("app", "Name:" + appName + " versionName:" + versionName);
		Log.v("app", "Name:" + appName + " versionCode:" + versionCode);
	}
}
