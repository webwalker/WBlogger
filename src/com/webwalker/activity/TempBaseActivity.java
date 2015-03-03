/**
 * 
 */
package com.webwalker.activity;

import java.util.List;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Toast;
import com.webwalker.utility.AbstractApplication;
import com.webwalker.utility.Consts;
import com.webwalker.utility.entity.DialogEntity;
import com.webwalker.utility.logger.AbstractLogger;
import com.webwalker.utility.logger.SysLogger;
import com.webwalker.utils.AppConstants;

/**
 * @author Administrator
 * 
 */
public abstract class TempBaseActivity extends Activity implements OnClickListener,
		OnDismissListener {

	public Class<?> backtoActivity;
	protected String LOG_TAG = "BaseActivity";
	private int currentDialogId;
	private String progressText;
	private DialogEntity dialogEntity = new DialogEntity(this);
	private AbstractLogger logger = SysLogger
			.getInstance(AppConstants.LOG_PREFIX);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// 不管用户如何旋转设备显示方向都不会随着改变
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);
		// 保持屏幕常亮，避免在欢迎界面下载数据时屏幕关闭
		//getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		// 初始化Logger
		logger.i(LOG_TAG,
				String.format("current activity is", this.getClass().getName()));

		super.onCreate(savedInstanceState);
	}

	@Override
	protected void onStart() {
		this.OnLifeEvent();
		super.onStart();
	}

	@Override
	protected void onResume() {
		this.OnLifeEvent();
		super.onResume();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	/**
	 * 对话框解除时调用
	 */
	@Override
	public void onDismiss(DialogInterface arg0) {
		// TODO Auto-generated method stub

	}

	/**
	 * 对话框初始化
	 */
	@Override
	protected Dialog onCreateDialog(int id) {
		Dialog dialog = new ProgressDialog(this);
		switch (id) {
		case 0: // dialog =
			break;
		case 1:// dialog =
			break;
		}

		if (dialog != null) {
			dialog.setOnDismissListener(this);
		}
		return dialog;
	}

	/**
	 * 显示之前动态更改对话框内容
	 */
	@Override
	protected void onPrepareDialog(int id, Dialog dialog) {
		currentDialogId = id;
		switch (id) {
		case 0:
			ProgressDialog progressDialog = (ProgressDialog) dialog;
			progressDialog.setMessage(dialogEntity.getStringMessage());
			break;
		case 1:
			break;
		}

		super.onPrepareDialog(id, dialog);
	}

	/**
	 * 授权：<uses-permission
	 * android:name="android.permission.GET_TASKS"></uses-permission>
	 */
	public boolean onCreateOptionsMenu(Menu menu) {
		// 获得当前活动的Activity
		ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
		List<RunningTaskInfo> runningTasks = manager.getRunningTasks(1);
		RunningTaskInfo cinfo = runningTasks.get(0);
		String className = cinfo.topActivity.getShortClassName();

		// 映射菜单项
		MenuInflater inflater = getMenuInflater();
		int menuResourceId = 0;
		/*
		 * switch (className) { case "1":// menuResourceId = R.menu.mainmenu;
		 * break; case "2":// menuResourceId = R.menu.mainmenu; break; }
		 */
		inflater.inflate(menuResourceId, menu);
		return super.onCreateOptionsMenu(menu);
	}

	/**
	 * if sub-class want to handle alert dialog, override it.
	 */
	public void onClick(DialogInterface dialog, int whichButton) {

	}

	@Override
	public boolean dispatchKeyEvent(KeyEvent event) {
		if (event.getAction() == KeyEvent.ACTION_DOWN
				&& event.getKeyCode() == KeyEvent.KEYCODE_BACK) {

		}
		return super.dispatchKeyEvent(event);
	}

	/**
	 * 更新UI，隐藏加载进度条
	 */
	protected void updateUi() {

	}

	private void OnLifeEvent() {
		Boolean isExit = (Boolean) getMyApplication().get(Consts.EXIT_APP);
		if (isExit != null && isExit)
			finish();
	}

	public AbstractApplication getMyApplication() {
		return (AbstractApplication) this.getApplication();
	}

	public void ExitApp() {
		getMyApplication().Exit();
		getMyApplication().Add(Consts.EXIT_APP, Boolean.valueOf(true));
		finish();
	}

	/**
	 * Show progress bar.
	 * 
	 * @param information
	 *            string need to be shown on progress bar
	 * @param dialog
	 *            {@code TYPE_PROGRESS_STYLE_UNCANCELABLE, TYPE_PROGRESS_STYLE_CANCELABLE}
	 */
	public void showProgressBar(String content, int dialog) {
		if (!(dialog > AppConstants.TYPE_PROGRESS_STYLE_START && dialog < AppConstants.TYPE_PROGRESS_STYLE_END)) {
			new IllegalArgumentException("no dialog for style:" + dialog
					+ " was used by showProgressBar");
		}
		if (content == null || content.trim().length() == 0) {
			new IllegalArgumentException(
					"no resource string for information was used by showProgressBar");
		}
		// 生效之前修改内容
		dialogEntity.setMessage(content);
		this.showDialog(dialog);
	}

	/**
	 * Show progress bar.
	 * 
	 * @param resId
	 *            string resource id need to be shown on progress bar
	 * @param dialog
	 *            {@code TYPE_PROGRESS_STYLE_UNCANCELABLE, TYPE_PROGRESS_STYLE_CANCELABLE}
	 */
	public void showProgressBar(int resId, int dialog) {
		progressText = getResources().getString(resId);
		showProgressBar(progressText, dialog);
	}

	/**
	 * Hide current showing progress bar. If there is no progress bar, do
	 * nothing.
	 */
	public void hideProgressBar() {
		// catch IllegalArgumentException if the progress dialog has been hidden
		// already.
		if (currentDialogId > AppConstants.TYPE_PROGRESS_STYLE_START
				&& currentDialogId < AppConstants.TYPE_PROGRESS_STYLE_END) {
			try {
				dismissDialog(currentDialogId);
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * show confirm dialog.
	 * 
	 * @param titleResourceId
	 *            title resource id.
	 * @param iconResourceId
	 *            icon resource id.
	 * @param messageResourceId
	 *            message resource id.
	 */
	public void showDialog(int titleResourceId, int iconResourceId,
			int messageResourceId, int dialogId) {
		String titleString = getResources().getString(titleResourceId);
		String messageString = getResources().getString(messageResourceId);
		Drawable iconDrawable = getResources().getDrawable(iconResourceId);
		showDialog(titleString, iconDrawable, messageString, dialogId);
	}

	/**
	 * show confirm dialog
	 * 
	 * @param titleString
	 *            title string
	 * @param iconDrawable
	 *            icon drawable
	 * @param messageString
	 *            message string
	 */
	public void showDialog(String titleString, Drawable iconDrawable,
			CharSequence messageString, int dialogId) {
		if (titleString != null && messageString != null) {
			dialogEntity.setTitle(titleString);
			dialogEntity.setIcon(iconDrawable);
			dialogEntity.setMessage(messageString);
			showDialog(dialogId);
		} else {
			new IllegalArgumentException(
					"titleString, iconDrawable or messageString is null for showConfirmDialog(String titleString, Drawable iconDrawable, String messageString)");
		}
	}



	/**
	 * 可以通过公共事件调用此方法完成，不同Activity上的跳转
	 */
	protected void NavAcctivity() {
		if (this.backtoActivity == null) {
			finish();
		} else {
			Intent intent = new Intent();
			intent.setClassName(this, backtoActivity.getName());
			startActivity(intent);
		}
	}

	/**
	 * 回调处理
	 */
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	}
}
