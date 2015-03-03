package com.webwalker.utility.example;

import java.util.Properties;

import android.content.Context;

import com.webwalker.utility.AbstractBaseConfig;
import com.webwalker.utility.Config;

public class ConfigExample extends AbstractBaseConfig {

	String apkInfoUrl;
	public String hostUrl;
	public boolean isDebug;
	private static ConfigExample config;

	public ConfigExample(Context context) {
		super(context);
	}

	public ConfigExample(Context context, String file) {
		this(context);
		Properties properties = super.getAssets(file);

		apkInfoUrl = (String) properties.get("apkinfo_url");
		hostUrl = (String) properties.get("host_url");
		isDebug = Boolean.valueOf(properties.get("debug").toString());
	}

	public synchronized static ConfigExample Instance(Context context,
			String file) {
		if (config == null) {
			config = new ConfigExample(context, file);
		}

		return config;
	}
}
