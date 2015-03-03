package com.webwalker.version;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import org.apache.http.util.ByteArrayBuffer;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

public class AppUpdateAsyncTask extends AsyncTask<String, Integer, Uri> {
	private static final int PROGRESS_MAX = 100;
	private Context context;
	private String apkName;
	private ProgressDialog progressDialog;
	boolean stoptask = false;

	public AppUpdateAsyncTask(Context context, String apkName) {
		this.context = context;
		this.apkName = apkName;
	}

	protected Uri doInBackground(String... urls) {
		return downloadApkFromURL(urls[0]);
	}

	@Override
	protected void onProgressUpdate(Integer... progress) {
		if (progressDialog == null) {
			progressDialog = new ProgressDialog(context);
			progressDialog.setOnCancelListener(new OnCancelListener() {
				@Override
				public void onCancel(DialogInterface dialog) {
					Log.i("update", "stoptask");
					// stoptask=true;
					new AlertDialog.Builder(context)
							.setIcon(android.R.drawable.ic_dialog_alert)
							.setTitle("取消下载")
							.setMessage("是否取消下载安装包？")
							.setPositiveButton("是",
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog,
												int whichButton) {
											stoptask = true;
										}
									})
							.setNegativeButton("否",
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog,
												int whichButton) {
											stoptask = false;
											progressDialog.setCancelable(true);// 允许用户取消下载
											progressDialog.setTitle("下载安装包");
											progressDialog.show();
										}
									}).show();
				}
			});
			progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
			progressDialog.setCancelable(true);
			progressDialog.setTitle("下载安装包");
			progressDialog.setMax(PROGRESS_MAX);
			progressDialog.show();
		}
		progressDialog.setProgress(progress[0]);
	}

	@Override
	protected void onCancelled() {
		super.onCancelled();
		Log.i("update", "cancled");
	}

	@Override
	protected void onPostExecute(Uri result) {
		if (progressDialog != null) {
			progressDialog.dismiss();
		}

		if (result == null) {
			Toast.makeText(context, "下载失败，请点击更新按钮重新下载", Toast.LENGTH_SHORT)
					.show();
			return;
		}
		if (result == Uri.EMPTY) {
			Toast.makeText(context, "下载已取消", Toast.LENGTH_SHORT).show();
			return;
		}

		Toast.makeText(context, "下载完毕,开始安装...", Toast.LENGTH_SHORT).show();
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setDataAndType(result, "application/vnd.android.package-archive");
		context.startActivity(intent);

		// InitActivity.getInstance().close();
	}

	private Uri downloadApkFromURL(String url) {
		try {
			URLConnection connection = (new URL(url)).openConnection();
			if (connection != null) {
				int contentLength = connection.getContentLength();
				int len, length = 0;
				byte[] buf = new byte[1024];
				InputStream is = connection.getInputStream();
				File file = new File(context.getCacheDir(), apkName);
				Log.e("downloadkfapkdir", file.getAbsolutePath());
				if (!file.exists()) {
					file.createNewFile();
				}
				Runtime.getRuntime()
						.exec("chmod 777 " + file.getAbsolutePath());
				OutputStream os = new FileOutputStream(file);
				try {
					while ((len = is.read(buf, 0, buf.length)) > 0) {
						if (stoptask) {
							return Uri.EMPTY;
						}
						os.write(buf, 0, len);
						length += len;
						publishProgress((int) (PROGRESS_MAX * (float) length / contentLength));
					}
					os.flush();
				} finally {
					is.close();
					os.close();
				}
				return Uri.fromFile(file);
			}
		} catch (IOException ioe) {

			Log.e("downloading latest apk", ioe.getMessage() + "-->" + url);
		}
		return null;
	}

	/**
	 * 最新的版本信息
	 * @param apkInfoUrl
	 * @return
	 */
	public static String getLatestApkInfo(String apkInfoUrl) {
		try {
			Log.i("update", apkInfoUrl);
			URLConnection connection = (new URL(apkInfoUrl)).openConnection();
			InputStream is = connection.getInputStream();
			BufferedInputStream bis = new BufferedInputStream(is);
			ByteArrayBuffer baf = new ByteArrayBuffer(50);
			int current = 0;
			while ((current = bis.read()) != -1) {
				baf.append((byte) current);
			}
			byte[] receivedData = baf.toByteArray();
			String jsonStr = new String(receivedData, "GBK");
			return jsonStr;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}
}
