package com.webwalker.activity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;

import com.webwalker.utility.MessagesUtil;
import com.webwalker.wblogger.GuideActivity;
import com.webwalker.wblogger.R;

public class SplashActivity extends Activity {
	SQLiteDatabase database;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_loading);

		this.Prepare();

		new Handler().postDelayed(new Runnable() {
			public void run() {
				Intent intent = new Intent(SplashActivity.this,
						GuideActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);

				SplashActivity.this.startActivity(intent);
				SplashActivity.this.finish();
			}
		}, 0);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (database != null) {
			database.close();
		}
	}

	public void Prepare() {
		// databases是 Android 程序默认的数据库存储目录
		String DB_PATH = "/data/data/com.webwalker.wblogger/databases/";
		String DB_NAME = "wblogger.db";

		if ((new File(DB_PATH + DB_NAME)).exists() == false) {
			File file = new File(DB_PATH);
			if (!file.exists()) {
				file.mkdir();
			}

			try {
				InputStream is = getBaseContext().getAssets().open(DB_NAME);
				OutputStream os = new FileOutputStream(DB_PATH + DB_NAME);
				byte[] buffer = new byte[1024];
				int length;
				while ((length = is.read(buffer)) > 0) {
					os.write(buffer, 0, length);
				}

				os.flush();
				os.close();
				is.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		// 测试数据库能否正常工作
		database = SQLiteDatabase.openOrCreateDatabase(DB_PATH + DB_NAME, null);
		Cursor cursor = database.rawQuery("select * from t_apptype", null);

		if (cursor.getCount() <= 0) {
			MessagesUtil.showToast(getBaseContext(), R.string.msg_err_init);
		}
		cursor.close();
	}
}
