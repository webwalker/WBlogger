package com.webwalker.version;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import com.webwalker.utility.MessagesUtil;
import com.webwalker.utility.NetWorkUtil;
import com.webwalker.utility.PackageUtil;
import com.webwalker.utility.logger.AbstractLogger;
import com.webwalker.utility.logger.SysLogger;
import com.webwalker.version.UpdateController.UpdateType;

public class CheckVersionAsyncTask extends AsyncTask<String, Integer, Integer> {
	private Context context;
	private AbstractLogger logger = SysLogger.getInstance("");
	private UpdateController upcontroller;
	private ActionType actionType = ActionType.Auto;
	private boolean hasBreak = false;

	public enum ActionType {
		Auto, Manual
	}

	public CheckVersionAsyncTask(Context context, ActionType actionType) {
		this.context = context;
		this.actionType = actionType;
	}

	@Override
	protected void onPreExecute() {
		boolean isAvailable = NetWorkUtil.checkNetworkStatus(context);
		hasBreak = !isAvailable;
		switch (actionType) {
		case Manual:
			if (hasBreak) {
				NetWorkUtil.guideNetWorkSetting(context); // 引导设置网络
			}
			break;
		default:
			break;
		}
	}

	/**
	 * updatetype:dialog、activity
	 */
	protected Integer doInBackground(String... updatetype) {

		logger.i("update", "doInBackground");

		if (hasBreak)
			return -1;

		String showType = updatetype[0];

		upcontroller = new UpdateController(context);
		if (upcontroller.isNeedUpdate()) {
			// 对话框方式
			if (showType.equals("dialog"))
				return 2;
			else if (showType.equals("activity"))
				return 1; // activity方式
		} else
			return 3; // 没有可更新版本
		return -1;
	}

	@Override
	protected void onPostExecute(Integer result) {
		switch (result) {
		case -1:
			logger.i("updateresult", "-1");
			break;
		case 1:
			logger.i("updateresult update with activity", "1");
			upcontroller.startUpdate(UpdateType.Activity);
			break;
		case 2:
			logger.i("updateresult update with dialog", "2");
			new AlertDialog.Builder(context)
					.setTitle("版本更新")
					.setMessage(
							"已检测到最新版本：" + upcontroller.update.versionName
									+ "\n" + upcontroller.update.versionInfo
									+ "\n" + "是否下载?")
					.setPositiveButton("下载",
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									upcontroller.startUpdate(UpdateType.Dialog);
									dialog.cancel();
								}
							})
					.setNegativeButton("取消",
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									dialog.cancel();
								}
							}).create().show();
			break;
		case 3: {
			try {
				logger.i("updateresult", "3");
				MessagesUtil m = new MessagesUtil(context);
				m.showNoticeDialog(
						"未发现新版本",
						"你使用的已是最新版本" + PackageUtil.getVersionName(this.context),
						"确定");
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		}
		}
	}
}
