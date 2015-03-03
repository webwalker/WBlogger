package com.webwalker.version;

import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;

import com.webwalker.utility.ApkConfig;
import com.webwalker.utility.PackageUtil;
import com.webwalker.utility.entity.UpdateEntity;
import com.webwalker.utility.logger.AbstractLogger;
import com.webwalker.utility.logger.SysLogger;

public class UpdateController {
	private static final String LOG_TAG = "UpdateController";
	private int currentVersionCode = 0;
	private Context context;
	public UpdateEntity update = null;
	private AbstractLogger logger = SysLogger.getInstance("");

	public enum UpdateType {
		Dialog, Activity,
	}

	/**
	 * This is update package name.
	 */
	public static final String NEW_PACKAGE_SAVE_NAME = "wblogger.apk";

	public UpdateController(Context context) {
		this.context = context;
		this.currentVersionCode = PackageUtil.getVersionCode(context);

		refreshLatestApk();
	}

	public void refreshLatestApk() {
		ApkConfig config = new ApkConfig(context, "config.properties");
		logger.i(LOG_TAG, "configread");
		logger.i(LOG_TAG, "apkInfoUrl" + config.apkInfoUrl);
		String apkInfo = AppUpdateAsyncTask.getLatestApkInfo(config.apkInfoUrl);
		if (apkInfo == null) {
			return;
		}
		try {
			JSONObject obj = new JSONObject(apkInfo);

			/**
			 * { "status":"ok", "version-code":"205", "min-version-code":"150",
			 * "url-release"
			 * :"http://61.152.90.44:8081/spm/ShengPay-release.apk",
			 * "url-debug":"http://61.152.90.44:8081/spm/ShengPay-debug.apk",
			 * "version-name":"2.0.1-11111", "update-time":"111111111",
			 * "version-info":"客户端应用名称和排序调整-||-主界面显示模式变化-||-更多易用性方面的改进" }
			 */

			update = new UpdateEntity();
			update.status = obj.getString("status");
			update.versionCode = obj.getInt("version-code");
			update.minVersionCode = obj.getInt("min-version-code");
			update.versionName = obj.getString("version-name");
			update.versionInfo = obj.getString("version-info");
			update.updateTime = obj.getString("update-time");
			update.debugUrl = obj.getString("url-debug");
			update.releaseUrl = obj.getString("url-release");
			logger.i(LOG_TAG, "fetch latest apk successfully!");
		} catch (Exception ex) {
			logger.e(LOG_TAG,
					"Error occurred on refreshLatestApk()," + ex.getMessage());
		}
	}

	public void startUpdate(UpdateType type) {
		if (!isNeedUpdate())
			return;

		switch (type) {
		case Dialog:
			// new AppUpdateAsyncTask(context,
			// UpdateController.NEW_PACKAGE_SAVE_NAME)
			// .execute(update.releaseUrl);
			new AppUpdateAsyncThread(context, update.releaseUrl,
					UpdateController.NEW_PACKAGE_SAVE_NAME).startUpdate();
			break;
		case Activity:
			Intent intent = new Intent(this.context, AppUpdateActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
			intent.putExtra("update_data", this.getUpdateEntity());
			this.context.startActivity(intent);
			break;
		}
	}

	public boolean isMustUpdate() {
		int currentVersionCode = PackageUtil.getVersionCode(context);
		if (currentVersionCode < update.minVersionCode) {
			return true;
		}
		return false;
	}

	public boolean isNeedUpdate() {
		if (currentVersionCode < update.versionCode) {
			return true;
		} else {
			return false;
		}
	}

	public UpdateEntity getUpdateEntity() {
		update.isMustUpdate = this.isMustUpdate();
		update.isNeedUpdate = this.isNeedUpdate();
		return update;
	}
}
