package com.webwalker.utility;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ServiceInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;

/**
 * 配置文件基础类
 * 
 * @author Administrator
 * 
 */
public abstract class AbstractBaseConfig {

	// private AbstractLogger logger = SysLogger.getInstance(Consts.LOG_PREFIX);

	private Context _Context = null;

	public AbstractBaseConfig(Context context) {
		_Context = context;
	}

	public Properties getAssets(String properties) {
		Properties pro = new Properties();
		InputStream inputStream = null;
		try {
			inputStream = _Context.getAssets().open(properties);
			pro.load(inputStream);
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("加载配置文件失败");
		} finally {
			try {
				inputStream.close();
			} catch (Exception e) {
			}
		}

		return pro;
	}
	
	/**
	 * 获取Asserts配置信息 properties在手机上的放置的路径为/data/data/packagename/files
	 * 
	 * @param context
	 * @param properties
	 * @return
	 */
	public Properties getProperties(String properties) {
		Properties pro = new Properties();
		InputStream inputStream = null;
		try {
			inputStream = this.GetStream(properties);
			pro.load(inputStream);
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("加载配置文件失败");
		} finally {
			try {
				inputStream.close();
			} catch (Exception e) {
			}
		}

		return pro;
	}

	/**
	 * Properties prop = newProperties(); prop.put("prop1", "abc");
	 * prop.put("prop2", 1); prop.put("prop3", 3.14); saveConfig(this,
	 * "/sdcard/config.dat", prop);
	 * 
	 * @param context
	 * @param file
	 * @param properties
	 */
	public void saveProperties(String file, Properties properties) {
		try {
			FileOutputStream s = new FileOutputStream(file, false);
			properties.store(s, "");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public InputStream GetStream(String file) {
		FileInputStream fileInputStream = null;
		try {
			// /data/data/com.jansun.activity/setting.properties
			// /assets/test.properties
			// fileInputStream = context.getAssets().open(properties);
			// fileInputStream =
			// context.getResources().openRawResource(R.raw.test);
			// fileInputStream = ClassLoader.getSystemResourceAsStream(file);
			// fileInputStream = context.openFileInput(file);
			fileInputStream = new FileInputStream(file);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return fileInputStream;
	}

	/*
	 * 系统Meta类型
	 */
	public enum MetaType {
		Application, Activity, Service, Receiver
	}

	/*
	 * 获取系统meta-data
	 */
	public Bundle getMetaData(MetaType metaType) {
		Activity context = (Activity) _Context;
		Bundle metaData = null;
		try {
			switch (metaType) {
			case Application:
				ApplicationInfo appInfo = _Context.getPackageManager()
						.getApplicationInfo(_Context.getPackageName(),
								PackageManager.GET_META_DATA);
				return appInfo.metaData;
			case Activity:
				ActivityInfo actInfo = context.getPackageManager()
						.getActivityInfo(context.getComponentName(),
								PackageManager.GET_META_DATA);
				return actInfo.metaData;
			case Service:
				ComponentName cn = new ComponentName(_Context,
						_Context.getClass());
				ServiceInfo sInfo = _Context.getPackageManager()
						.getServiceInfo(cn, PackageManager.GET_META_DATA);
				return sInfo.metaData;
			case Receiver:
				cn = new ComponentName(_Context, _Context.getClass());
				actInfo = _Context.getPackageManager().getReceiverInfo(cn,
						PackageManager.GET_META_DATA);
				return actInfo.metaData;
			default:
				return null;
			}
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return metaData;
	}
}
