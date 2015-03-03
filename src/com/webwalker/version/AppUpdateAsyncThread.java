/**
 * 
 */
package com.webwalker.version;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.util.Log;
import android.widget.Toast;

import com.webwalker.utility.Consts;
import com.webwalker.utility.PackageUtil;
import com.webwalker.utility.entity.DownCallbackEntity;
import com.webwalker.utility.entity.DownEntity;
import com.webwalker.utility.network.DownCallback;
import com.webwalker.utility.network.HttpDownload;

/**
 * @author Administrator
 * 
 */
public class AppUpdateAsyncThread {
	private static final int PROGRESS_MAX = 100;
	private ProgressDialog progressDialog;
	private Context context;
	private String downloadUrl, fileName;
	private HttpDownload down = null;
	private boolean hasExecuted = false;
	private boolean isInstall = true;

	public AppUpdateAsyncThread(Context context, String downloadUrl,
			String fileName) {
		this(context, downloadUrl, fileName, true);
	}

	public AppUpdateAsyncThread(Context context, String downloadUrl,
			String fileName, boolean isInstall) {
		this.context = context;
		this.downloadUrl = downloadUrl;
		this.fileName = fileName;
		this.isInstall = isInstall;
	}

	public void startUpdate() {
		ShowDialog("下载安装包", "");

		DownEntity entity = new DownEntity();
		entity.setUrl(downloadUrl);
		entity.setThreadCount(5);
		// entity.setDownPath("/");
		entity.setFileName(fileName);

		DownCallback callback = new DownCallback() {
			@Override
			public void callBack(DownCallbackEntity entity) {
				int progress = entity.getProgress();

				Log.v("download callback",
						entity.getProgress() + "," + entity.getDownloadedSize());
				progressDialog.setProgress(entity.getProgress());
				progressDialog.setMessage("已下载" + entity.getProgress() + "%");

				try {
					// 安装
					if (progress >= 100) {
						Log.v(Consts.DOWN_TAG, "下载完毕,开始安装...");
						Toast.makeText(context, "下载完毕...",
								Toast.LENGTH_SHORT).show();

						progressDialog.dismiss();

						if (isInstall) {
							InstallApk(entity.getFileName());
						}
					}
				} catch (Exception e) {
					Log.e(Consts.DOWN_TAG, e.getMessage());
				}
			}
		};

		down = new HttpDownload(entity, callback);
		down.Start();
	}

	void InstallApk(String fileName) {
		if (hasExecuted == false) {
			hasExecuted = true;
			PackageUtil.installApk(context, fileName);
		}
	}

	public void ShowDialog(String title, String message) {
		if (progressDialog == null) {
			progressDialog = new ProgressDialog(context);
			progressDialog.setOnCancelListener(new OnCancelListener() {
				@Override
				public void onCancel(DialogInterface dialog) {
					Log.i("update", "stoptask");
					new AlertDialog.Builder(context)
							.setIcon(android.R.drawable.ic_dialog_alert)
							.setTitle("取消下载")
							.setMessage("是否取消下载安装包？")
							.setPositiveButton("是",
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog,
												int whichButton) {
											down.Cancel();
											Toast.makeText(context, "下载已取消",
													Toast.LENGTH_SHORT).show();
										}
									})
							.setNegativeButton("否",
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog,
												int whichButton) {
											progressDialog.setCancelable(true);// 允许用户取消下载
											progressDialog.setTitle("下载安装包");
											progressDialog.show();
										}
									}).show();
				}
			});
			progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
			progressDialog.setCancelable(true);
			progressDialog.setTitle(title);
			progressDialog.setMessage(message);
			progressDialog.setMax(PROGRESS_MAX);
			progressDialog.show();
			progressDialog.setProgress(0);
		}
	}
}
