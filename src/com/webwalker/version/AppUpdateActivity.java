package com.webwalker.version;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.webwalker.utility.StringUtil;
import com.webwalker.utility.entity.UpdateEntity;
import com.webwalker.wblogger.R;

public class AppUpdateActivity extends Activity {
	LinearLayout lotVersionInfo;
	TextView txtVersion;
	UpdateEntity entity = null;
	boolean isforce;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog_version_upgrade);

		entity = (UpdateEntity) getIntent().getSerializableExtra("update_data");

		lotVersionInfo = (LinearLayout) findViewById(R.id.appupdateinfoout);
		String[] versioninfo = StringUtil.split(entity.versionInfo, "-||-");
		if (versioninfo != null) {
			for (int i = 0; i < versioninfo.length; i++) {
				View v = LayoutInflater.from(this).inflate(
						R.layout.dialog_version_upgrade_text, null);
				((TextView) v.findViewById(R.id.tvIndex)).setText(String
						.valueOf(i + 1) + ".");
				((TextView) v.findViewById(R.id.tvText))
						.setText(versioninfo[i]);
				lotVersionInfo.addView(v);
			}
		}
		txtVersion = (TextView) findViewById(R.id.dialogTitle);
		txtVersion.setText("发现新版本：" + entity.versionName);
		isforce = entity.isMustUpdate;
		if (isforce) {
			findViewById(R.id.appupdatebtncancle).setVisibility(8);
			Button startbutton = (Button) findViewById(R.id.appupdatebtnstart);
			MarginLayoutParams lp = (MarginLayoutParams) startbutton
					.getLayoutParams();
			lp.rightMargin = 0;
			startbutton.setLayoutParams(lp);
		}
	}

	// 启动升级
	public void startupdate(View view) {
		new AppUpdateAsyncTask(this, UpdateController.NEW_PACKAGE_SAVE_NAME)
				.execute(entity.releaseUrl);
	}

	// 取消升级
	public void cancleupdate(View view) {
		finish();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (isforce) {
				quit();
			}
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public void onBackPressed() {
		if (isforce) {
			quit();
		}
	}

	@Override
	public boolean dispatchKeyEvent(KeyEvent event) {
		if (event.getAction() == KeyEvent.ACTION_DOWN
				&& event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
			if (isforce) {
				quit();
			}
		}
		return super.dispatchKeyEvent(event);
	}

	@Override
	protected void onStart() {
		super.onStart();
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	void quit() {
		finish();
	}
}
