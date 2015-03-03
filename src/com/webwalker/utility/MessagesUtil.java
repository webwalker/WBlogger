/**
 * 
 */
package com.webwalker.utility;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.webwalker.listener.IClickListener;
import com.webwalker.utils.AppConstants;
import com.webwalker.wblogger.R;

/**
 * 通常消息样式
 * 
 * @author Administrator
 * 
 */
public class MessagesUtil extends Activity {

	static Context _Context = null;

	public MessagesUtil(Context context) {
		_Context = context;
	}

	public static void showToast(Context context, int msgId) {
		showToast(context, context.getString(msgId));
	}

	public static void showToast(Context context, String msg) {
		Toast toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
		toast.setGravity(Gravity.CENTER_HORIZONTAL, 0, 0);
		toast.show();
	}

	protected static void showToast(String msg) {
		Toast.makeText(_Context, msg, Toast.LENGTH_SHORT).show();
	}

	/**
	 * 退出应用程序
	 */
	public void ShowExitDialog() {
		AlertDialog.Builder alertbBuilder = new AlertDialog.Builder(this);
		alertbBuilder
				.setTitle(R.string.exit)
				.setMessage(R.string.confirm_exit)
				.setPositiveButton(R.string.dialog_ok,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								finish();
								int nPid = android.os.Process.myPid();
								android.os.Process.killProcess(nPid);
							}
						})
				.setNegativeButton(R.string.dialog_cancel,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								dialog.cancel();
							}
						}).create();
		alertbBuilder.show();
	}

	/**
	 * 显示关于对话框
	 */
	public void ShowAbout(int layId, int resId) {
		LayoutInflater inflater = getLayoutInflater();
		View layout = inflater.inflate(layId, (ViewGroup) findViewById(resId));

		// 显示对话框
		new AlertDialog.Builder(this).setTitle(R.string.menu_about)
				.setView(layout).setPositiveButton(R.string.dialog_ok, null)
				.show();
	}

	public void ShowDialog(int title, int message, int ok, int cancel,
			final IClickListener listener) {
		ShowDialog(_Context.getResources().getString(title), _Context
				.getResources().getString(message), ok, cancel, listener);
	}

	public void ShowDialog(String title, String message) {
		ShowDialog(title, message, R.string.dialog_ok, R.string.dialog_cancel,
				null);
	}

	public void ShowDialog(int title, String message, int ok, int cancel,
			final IClickListener listener) {
		ShowDialog(_Context.getResources().getString(title), message,
				R.string.dialog_ok, R.string.dialog_cancel, listener);
	}

	public void ShowDialog(String title, String message, int ok, int cancel,
			final IClickListener listener) {
		AlertDialog.Builder builder = new Builder(_Context);
		builder.setTitle(title);
		builder.setMessage(message);
		if (listener != null) {
			builder.setPositiveButton(ok, new OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					listener.OnClickOK();
				}
			});
			builder.setNegativeButton(cancel, new OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					listener.OnClickCancel();
				}
			});
		}
		Dialog noticeDialog = builder.create();
		noticeDialog.show();
	}

	public void showNoticeDialog(String title, String message, String ok) {
		AlertDialog.Builder builder = new Builder(_Context);
		builder.setTitle(title);
		builder.setMessage(message);
		builder.setPositiveButton(ok, new OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		Dialog noticeDialog = builder.create();
		noticeDialog.show();
	}

	public static void showNotify(Context context, int resourceId,
			String title, String body) {
		Notification notification = new Notification(resourceId,
				context.getString(R.string.sys_msg_title),
				System.currentTimeMillis());

		// notification.defaults |= Notification.DEFAULT_SOUND;
		// notification.sound = Uri
		// .parse("file:///sdcard/notification/ringer.mp3");

		notification.defaults |= Notification.DEFAULT_VIBRATE;
		long[] vibrate = { 0, 100, 200, 300 };
		notification.vibrate = vibrate;

		notification.defaults |= Notification.DEFAULT_LIGHTS;
		notification.ledARGB = 0xff00ff00;
		notification.ledOnMS = 300;
		notification.ledOffMS = 1000;
		notification.flags |= Notification.FLAG_SHOW_LIGHTS;

		notification.flags |= notification.FLAG_AUTO_CANCEL;
		NotificationManager notificationManager = (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);

		PendingIntent pendingIntent = PendingIntent.getActivity(context, 0,
				new Intent(), 0);
		notification.setLatestEventInfo(context, title, body, pendingIntent);
		notificationManager.notify(0, notification);
	}

	public static void sendNotify(Context context, String title, String body) {
		Intent i = new Intent();
		i.setAction(AppConstants.ReceiverFilter);
		i.putExtra(AppConstants.Keys.NotifyTitle, title);
		i.putExtra(AppConstants.Keys.NotifyBody, body);
		context.sendBroadcast(i);
	}

	//
	// /**
	// * 显示没有版本更新
	// */
	// private void showNotNewVersionDialog() {
	// ToolUtils.Log("showNotNewVersionDialog");
	// String verName = config.VerName;
	// StringBuffer sb = new StringBuffer();
	// sb.append("当前版本:");
	// sb.append(verName);
	// sb.append(",\n已是最新版,无需更新!");
	// Dialog dialog = new AlertDialog.Builder(_context).setTitle("软件更新")
	// .setMessage(sb.toString())// 设置内容
	// .setPositiveButton("确定",// 设置确定按钮
	// new DialogInterface.OnClickListener() {
	// public void onClick(DialogInterface dialog,
	// int which) {
	// finish();
	// }
	// }).create();
	// // 显示对话框
	// dialog.show();
	// }
	//
	// /**
	// * 显示下载确认对话框
	// */
	// private void showDownloadDialog() {
	// ToolUtils.Log("showDownloadDialog");
	// AlertDialog.Builder builder = new Builder(_context);
	// builder.setTitle("软件版本更新");
	//
	// final LayoutInflater inflater = LayoutInflater.from(_context);
	// View v = inflater.inflate(R.layout.progress, null);
	// _progress = (ProgressBar) v.findViewById(R.id.progress);
	//
	// builder.setView(v);
	// builder.setNegativeButton("取消", new OnClickListener() {
	// public void onClick(DialogInterface dialog, int which) {
	// dialog.dismiss();
	// interceptFlag = true;
	// }
	// });
	// downloadDialog = builder.create();
	// downloadDialog.show();
	//
	// // 下载APK
	// downloadApk();
	// }

}
