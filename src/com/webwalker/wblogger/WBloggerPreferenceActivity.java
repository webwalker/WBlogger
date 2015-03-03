package com.webwalker.wblogger;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.preference.PreferenceScreen;

public class WBloggerPreferenceActivity extends PreferenceActivity {
	public static String AUTO_UPDATE_KEY = "auto_update";
	public static String LOCAL_PASS = "local_pass";
	public static String MONITOR_PERIOD = "monitor_period";
	public static String KEY_START_WHEN_BOOT_COMPLETED = "start_when_boot_completed";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.preferences);
	}

	/**
	 * 是否启用自动更新
	 * 
	 * @param context
	 * @return
	 */
	public static boolean IsAutoUpdate(Context context) {
		return PreferenceManager.getDefaultSharedPreferences(context)
				.getBoolean(AUTO_UPDATE_KEY, false);
	}

	/**
	 * 是否设置了本地密码
	 * 
	 * @param context
	 * @return
	 */
	public static boolean hasetPassword(Context context) {
		String pass = PreferenceManager.getDefaultSharedPreferences(context)
				.getString(LOCAL_PASS, "");

		if (!pass.equals("")) {
			return true;
		}
		return false;
	}

	public static boolean login(Context context, String userPass) {
		String pass = PreferenceManager.getDefaultSharedPreferences(context)
				.getString(LOCAL_PASS, "");

		if (userPass.equals(pass)) {
			return true;
		}
		return false;
	}

	@Override
	public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen,
			Preference preference) {
		return false;
	}
}
