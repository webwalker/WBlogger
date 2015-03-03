package com.webwalker.utility;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import android.content.Context;
import android.util.Log;

public class Config {
	private boolean isDebug;
	private String apkInfoUrl;
	private String hostUrl;
	private Properties description;
	private boolean showTestBank;

	public Config(InputStream configIS, InputStream descriptionIS) {
		Properties configP = new Properties();
		try {
			configP.load(configIS);
		} catch (IOException e) {
			throw new RuntimeException("加载配置文件失败");
		}
		isDebug = Boolean.valueOf(configP.getProperty("debug"));
		apkInfoUrl = configP.getProperty("apkinfo_url");
		hostUrl = configP.getProperty("host_url");
		showTestBank =Boolean.valueOf(configP.getProperty("showTestBank"));
		description = new Properties();
		try {
			description.load(descriptionIS);
		} catch (IOException e) {
			throw new RuntimeException("加载配置文件失败");
		}
	}

	public String getHostUrl() {
		return hostUrl;
	}

	public boolean isDebug() {
		return isDebug;
	}

	public String getApkInfoUrl() {
		return apkInfoUrl;
	}
	
	public boolean isShowTestBank() {
        return showTestBank;
    }


    // 静态单例变量
	private static Config config;

	/**
	 * 单例创建Config配置信息
	 * 
	 * @param context
	 * @return
	 */
	public synchronized static Config getInstance(Context context) {
		if (config == null) {
			InputStream configIS = null;
			InputStream descriptionIS = null;
			try {
				configIS = context.getAssets().open("config.properties");
				descriptionIS = context.getAssets().open(
						"description.properties");
				config = new Config(configIS, descriptionIS);
			} catch (IOException e) {
				Log.w("", e);
			} finally {
				try {
					configIS.close();
				} catch (Exception e) {
				}
				try {
					descriptionIS.close();
				} catch (Exception e) {
				}
			}

		}
		return config;
	}

	public Properties getDescription() {
		return description;
	}
}
