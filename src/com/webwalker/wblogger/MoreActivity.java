package com.webwalker.wblogger;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.webwalker.activity.BaseActivity;
import com.webwalker.camera.CaptureActivity;
import com.webwalker.listener.IClickListener;
import com.webwalker.listener.MyClickListener;
import com.webwalker.utility.MessagesUtil;
import com.webwalker.utility.PackageUtil;
import com.webwalker.utility.PathUtil;
import com.webwalker.version.AppUpdateAsyncThread;
import com.webwalker.version.CheckVersionAsyncTask;
import com.webwalker.version.CheckVersionAsyncTask.ActionType;

public class MoreActivity extends BaseActivity implements OnClickListener {

	private TextView down, moresetting, about, feedback, support, update;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.more);

		down = (TextView) findViewById(R.id.fastDownload);
		moresetting = (TextView) findViewById(R.id.moresetting);
		support = (TextView) findViewById(R.id.officeWeiboContent);
		feedback = (TextView) findViewById(R.id.feedBackContent);
		update = (TextView) findViewById(R.id.updateContent);
		about = (TextView) findViewById(R.id.moreAboutContent);
		down.setOnClickListener(this);
		support.setOnClickListener(this);
		feedback.setOnClickListener(this);
		update.setOnClickListener(this);
		about.setOnClickListener(this);
		moresetting.setOnClickListener(this);

		// View header = LayoutInflater.from(this)
		// .inflate(R.layout.com_head, null);

		// Button btnTitle = (Button) findViewById(R.id.btnTitle);
		// btnTitle.setText("设置");
		// btnTitle.setVisibility(View.VISIBLE);
		// btnTitle.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		Intent intent = new Intent();
		switch (v.getId()) {
		// case R.id.btnTitle:
		// intent.setClass(MoreActivity.this, WBloggerPreferenceActivity.class);
		// startActivity(intent);
		// break;
		case R.id.moresetting:
			Intent i = new Intent(MoreActivity.this,
					WBloggerPreferenceActivity.class);
			startActivity(i);
			break;
		case R.id.fastDownload:
			// 打开扫描界面扫描条形码或二维码
			intent.setClass(MoreActivity.this, CaptureActivity.class);
			startActivityForResult(intent, 0);
			break;
		case R.id.officeWeiboContent:
			Intent it = new Intent(Intent.ACTION_VIEW,
					Uri.parse("http://www.mcan.cn"));

			// UC浏览器
			try {
				PackageInfo pi = getPackageManager().getPackageInfo(
						"com.UCMobile", 0);
				if (pi != null) {
					PackageUtil.startAppByPackageName(this, "com.UCMobile", it);
					return;
				}
			} catch (NameNotFoundException e) {
				e.printStackTrace();
			}

			it.setClassName("com.android.browser",
					"com.android.browser.BrowserActivity");
			startActivity(it);
			break;
		case R.id.feedBackContent:

			Intent data = new Intent(Intent.ACTION_SENDTO);
			data.setData(Uri.parse("mailto:14876534@qq.com"));
			data.putExtra(Intent.EXTRA_EMAIL, new String[] {
					"Jason-Xu@msn.com", "14876534@qq.com" });
			// data.putExtra(Intent.EXTRA_CC, new String[] { "" });
			// data.putExtra(Intent.EXTRA_BCC, new String[] { "" });
			data.putExtra(Intent.EXTRA_SUBJECT,
					getString(R.string.extra_app_feedback));
			data.putExtra(Intent.EXTRA_TEXT, "");
			// startActivity(data);
			startActivity(Intent.createChooser(data,
					getString(R.string.extra_select_smtp)));

			break;
		case R.id.updateContent:
			new CheckVersionAsyncTask(this, ActionType.Manual)
					.execute("dialog");
			break;
		case R.id.moreAboutContent:
			new AlertDialog.Builder(this).setTitle(R.string.menu_about)
					.setMessage(R.string.about)
					.setPositiveButton(R.string.dialog_ok, null).show();
			break;
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		// 处理扫描结果（在界面上显示）
		if (resultCode == RESULT_OK) {
			Bundle bundle = data.getExtras();
			final String scanResult = bundle.getString("result");
			final String fileName = PathUtil.getFileName(scanResult);

			IClickListener clicklistener = new MyClickListener() {
				@Override
				public void OnClickOK() {
					try {
						boolean isInstall = fileName.endsWith(".apk") ? true
								: false;
						new AppUpdateAsyncThread(MoreActivity.this, scanResult,
								fileName, isInstall).startUpdate();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}

				@Override
				public void OnClickCancel() {
				}
			};

			MessagesUtil m = new MessagesUtil(this);
			m.ShowDialog(R.string.dialog_title_download, "即将下载" + scanResult
					+ ", 是否继续?\r\n下载路径：/mnt/sdcard/" + fileName,
					R.string.dialog_ok, R.string.dialog_cancel, clicklistener);
		}
	}
}
