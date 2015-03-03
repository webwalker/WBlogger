package com.webwalker.wblogger;

import android.app.Activity;
import android.app.ActivityGroup;
import android.app.AlertDialog;
import android.app.LocalActivityManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.webwalker.controller.service.RemindReceiver;
import com.webwalker.utility.PackageUtil;
import com.webwalker.utils.AppConstants;
import com.webwalker.utils.MyContext;

public class MainActivity extends ActivityGroup implements
		OnCheckedChangeListener, OnClickListener {

	private RelativeLayout parentHead;
	private LinearLayout container = null;
	private static RadioGroup radioderGroup;
	TextView tvTitle;
	Button btnTitle;
	RemindReceiver receiver;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// Intent a = new Intent(this, WBloggerPreferenceActivity.class);
		// startActivity(a);
		parentHead = (RelativeLayout) findViewById(R.id.comHead);
		tvTitle = (TextView) parentHead.findViewById(R.id.tvTitle);
		container = (LinearLayout) findViewById(R.id.containerBody);
		radioderGroup = (RadioGroup) findViewById(R.id.commonMainRadio);
		radioderGroup.setOnCheckedChangeListener(this);

		int checkId = R.id.rbActive;
		Object intent = MyContext.get(AppConstants.ReferIntent);
		if (intent != null) {
			checkId = (Integer) intent;
		}
		onCheckedChanged(radioderGroup, checkId);
		// changeItem("", intent);
		// MyContext.Remove(AppConstants.ReferIntent);

		receiver = new RemindReceiver();
		this.registerReceiver(receiver, new IntentFilter(
				AppConstants.ReceiverFilter));

		PackageUtil.getNotifyManager(this).cancelAll();

		/*
		 * for (int j = 0; j < 3; j++) { Intent i = new
		 * Intent(MainActivity.this, MonitorService.class); startService(i); i =
		 * new Intent(MainActivity.this, RemindService.class); startService(i);
		 * try { Thread.sleep(2000); } catch (InterruptedException e) { // TODO
		 * Auto-generated catch block e.printStackTrace(); } }
		 */
	}

	@Override
	public void onCheckedChanged(RadioGroup arg0, int checkedId) {
		((RadioButton) findViewById(checkedId)).setChecked(true);
		switch (checkedId) {
		case R.id.rbHome:
			tvTitle.setText(R.string.title_activity_app_list);
			changeItem("ONE", AppListActivity.class);
			break;
		case R.id.rbActive:
			tvTitle.setText(R.string.title_activity_weibo_active);
			changeItem("TWO", WeiboActiveActivity.class);
			break;
		case R.id.rbSync:
			tvTitle.setText(R.string.title_activity_weibo_sync);
			changeItem("THREE", WeiboSyncActivity.class);
			break;
		case R.id.rbSign:
			tvTitle.setText(R.string.title_activity_weibo_sign);
			changeItem("FOUR", WeiboSignActivity.class);
			break;
		case R.id.rbMore:
			tvTitle.setText(R.string.title_activity_more);
			changeItem("FIVE", MoreActivity.class);
			break;
		}
	}

	private void changeItem(String itemTitle, Class<?> activity) {
		container.removeAllViews();
		container.addView(getLocalActivityManager().startActivity(
				itemTitle,
				new Intent(this, activity)
						.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
				.getDecorView());
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		// 如果返回false，此方法就把用户点击menu的动作给消费了
		// onCreateOptionsMenu方法将不会被调用
		menu.clear();
		LocalActivityManager lam = this.getLocalActivityManager();
		Activity activity = lam.getCurrentActivity();
		return activity.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		LocalActivityManager lam = this.getLocalActivityManager();
		Activity activity = lam.getCurrentActivity();
		return activity.onOptionsItemSelected(item);
	}

	@Override
	public boolean dispatchKeyEvent(KeyEvent event) {
		if (event.getAction() == KeyEvent.ACTION_DOWN
				&& event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
			AlertDialog.Builder alertbBuilder = new AlertDialog.Builder(this);
			alertbBuilder
					.setTitle(R.string.exit)
					.setMessage(R.string.confirm_exit)
					.setPositiveButton(R.string.dialog_ok,
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int which) {
									MyContext.Add(AppConstants.Keys.ExitApp,
											"1");
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

		return super.dispatchKeyEvent(event);
	}

	@Override
	protected void onDestroy() {
		// RemindReceiver r = (RemindReceiver)
		// MyContext
		// .get("Receiver");
		unregisterReceiver(receiver);
		PackageUtil.getNotifyManager(MainActivity.this).cancelAll();
		getLocalActivityManager().getCurrentActivity().finish();

		super.onDestroy();
	}

	@Override
	public void onClick(View arg0) {

		switch (arg0.getId()) {
		case R.id.btnTitle:

			break;
		}
	}
}
