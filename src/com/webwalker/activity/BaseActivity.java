/**
 * 
 */
package com.webwalker.activity;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.TextView;
import android.widget.Toast;

import com.webwalker.utils.AppConstants;
import com.webwalker.utils.MyContext;
import com.webwalker.utils.AppConstants.MessageWhat;
import com.webwalker.wblogger.MainActivity;
import com.webwalker.wblogger.MyApplication;
import com.webwalker.wblogger.R;
import com.webwalker.widget.MyProgressBar;

/**
 * @author Administrator
 * 
 */
public abstract class BaseActivity extends Activity {
	private int currentDialogId;
	private String progressText;
	protected String TAG = "Wblogger";
	private Handler uiHandler = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// 不管用户如何旋转设备显示方向都不会随着改变
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);

		// 隐藏标题
		// requestWindowFeature(Window.FEATURE_NO_TITLE);

		uiHandler = getUIHandler();

		super.onCreate(savedInstanceState);
	}

	protected Handler getUIHandler() {
		if (uiHandler == null) {
			uiHandler = new Handler() {
				@Override
				public void handleMessage(Message msg) {
					switch (msg.what) {
					case MessageWhat.Toast:
						Toast.makeText(getBaseContext(),
								msg.getData().getString(AppConstants.Keys.MSG),
								Toast.LENGTH_LONG).show();
						break;
					}
				}
			};
		}

		return uiHandler;
	}

	@Override
	protected void onStart() {
		super.onStart();
		commonProcess();
	}

	@Override
	protected void onResume() {
		super.onResume();
		commonProcess();
	}

	@Override
	public void onConfigurationChanged(Configuration arg0) {
		super.onConfigurationChanged(arg0);
		if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
			// land
		} else if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
			// port
		}
	}

	@Override
	protected Dialog onCreateDialog(int id) {
		Dialog dialog = null;

		switch (id) {
		case AppConstants.TYPE_PROGRESS_STYLE_UNCANCELABLE: {
			MyProgressBar progressDialog = new MyProgressBar(this);
			progressDialog.setIndeterminate(true);
			progressDialog.setCancelable(false);
			dialog = progressDialog;
			break;
		}
		case AppConstants.TYPE_PROGRESS_DEFAULT: {
			ProgressDialog progressDialog = new ProgressDialog(this);
			progressDialog.setIndeterminate(true);
			progressDialog.setCancelable(false);
			dialog = progressDialog;
			break;
		}
		}
		if (dialog != null) {
			dialog.setOnDismissListener(new dialogDismissListener());
		}
		return dialog;
	}

	@Override
	protected void onPrepareDialog(int id, Dialog dialog) {
		currentDialogId = id;
		switch (id) {
		case AppConstants.TYPE_PROGRESS_STYLE_UNCANCELABLE: {
			ProgressDialog progressDialog = (ProgressDialog) dialog;
			progressDialog.setMessage(progressText);
			break;
		}
		case AppConstants.TYPE_PROGRESS_DEFAULT: {
			ProgressDialog progressDialog = (ProgressDialog) dialog;
			progressDialog.setMessage(progressText);
			break;
		}
		}
		// super.onPrepareDialog(id, dialog);
	}

	protected class dialogDismissListener implements OnDismissListener {

		@Override
		public void onDismiss(DialogInterface dialog) {

		}
	}

	public void showProgressBar(int resId, int style) {
		progressText = getResources().getString(resId);
		this.showDialog(style);
	}

	protected void hideProgressBar() {
		dismissDialog(currentDialogId);
	}

	public MyApplication getMyApplication() {
		return (MyApplication) this.getApplication();
	}

	private void commonProcess() {
		Boolean isExit = (Boolean) MyContext.get(AppConstants.Keys.ExitApp);
		if (isExit != null && isExit) {
			finish();
			System.exit(0);
		}
	}

	protected void setMyTitle(int resId) {
		TextView tv = (TextView) findViewById(R.id.tvTitle);
		tv.setText(resId);
	}

	protected void setMyTitle(String title) {
		TextView tv = (TextView) findViewById(R.id.tvTitle);
		tv.setText(title);
	}
	
	protected void showToast(String msg) {
		Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
	}

	protected void showToast(String msg, int type) {
		Toast.makeText(this, msg, type).show();
	}

	protected void showToast(int resId) {
		Toast.makeText(getBaseContext(), getString(resId), Toast.LENGTH_SHORT)
				.show();
	}

	protected void sendMessage(int resId) {
		sendMessage(getString(resId));
	}

	protected void sendMessage(int resId, String data) {
		sendMessage(getString(resId) + "\r\n" + data);
	}

	protected void sendMessage(String data) {
		Message msg = new Message();
		msg.what = AppConstants.MessageWhat.Toast;
		Bundle bundle = new Bundle();
		bundle.putString(AppConstants.Keys.MSG, data);
		msg.setData(bundle);
		getUIHandler().sendMessage(msg);
	}

	protected void goHome(int checkedId) {
		MyContext.Add(AppConstants.ReferIntent, checkedId);
		Intent intent = new Intent(getBaseContext(), MainActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(intent);
		this.finish();
	}
}
