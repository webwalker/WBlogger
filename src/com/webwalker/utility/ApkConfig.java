package com.webwalker.utility;

import java.util.Properties;
import android.content.Context;

public class ApkConfig extends AbstractBaseConfig {

	public String apkInfoUrl;
	public String hostUrl;
	public boolean isDebug;

	public ApkConfig(Context context) {
		super(context);
	}

	public ApkConfig(Context context, String file) {
		this(context);
		Properties properties = super.getAssets(file);

		apkInfoUrl = (String) properties.get("apkinfo_url");
		hostUrl = (String) properties.get("host_url");
		isDebug = Boolean.valueOf(properties.get("debug").toString());
	}
}
