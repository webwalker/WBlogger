package com.webwalker.wblogger;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ProgressBar;

import com.webwalker.utility.MessagesUtil;
import com.webwalker.utility.entity.DownCallbackEntity;
import com.webwalker.utility.entity.DownEntity;
import com.webwalker.utility.entity.UpdateEntity;
import com.webwalker.utility.example.CustomControlsActivity;
import com.webwalker.utility.example.DispatchActivity;
import com.webwalker.utility.network.DownCallback;
import com.webwalker.utility.network.HttpDownload;
import com.webwalker.version.AppUpdateActivity;
import com.webwalker.version.CheckVersionAsyncTask;
import com.webwalker.version.UpdateCallback;
import com.webwalker.version.CheckVersionAsyncTask.ActionType;
import com.webwalker.widget.MyProgressBar;

public class TestActivity extends Activity implements OnClickListener {
	HttpDownload down = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_test);

		final ProgressBar p = (ProgressBar) findViewById(R.id.downloadProgressBar);
		DownEntity entity = new DownEntity();
		entity.setUrl("http://download.ie.sogou.com/se/sogou_explorer_4.0u.exe");
		entity.setThreadCount(5);
		entity.setDownPath("/");
		entity.setFileName("a.apk");
		entity.setProgressBar(p);

		final ProgressDialog bar = new ProgressDialog(this);
		bar.setIndeterminate(true);
		bar.setCancelable(false);
		bar.setTitle("tttt");
		bar.setProgress(0);
		bar.setMax(100);
		bar.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		//bar.show();

		DownCallback callback = new DownCallback() {
			@Override
			public void callBack(DownCallbackEntity entity) {
				Log.v("sss",
						entity.getProgress() + "," + entity.getDownloadedSize());
				// p.setProgress(entity.getProgress());
				// bar.setMessage("已下载" + entity.getProgress() + "%");
				// bar.setProgress(entity.getProgress());
				// bar.incrementProgressBy(entity.getProgress());
				//
				// if (entity.getProgress() == 100) {
				// MessagesUtil.showToast(getBaseContext(),
				// entity.getMessage());
				// bar.cancel();
				// }
			}
		};

		// down = new HttpDownload(entity, callback);
		// down.Start();

		Button b = (Button) findViewById(R.id.downloadCancel);
		Button b1 = (Button) findViewById(R.id.downloadBt);
		b.setOnClickListener(this);
		b1.setOnClickListener(this);
		
		new CheckVersionAsyncTask(this, ActionType.Manual).execute("dialog");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_test, menu);
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View arg0) {
		int id = arg0.getId();
		if (id == R.id.downloadCancel) {
			down.Cancel();
		}
		if (id == R.id.downloadBt) {
			down.Start();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#dispatchKeyEvent(android.view.KeyEvent)
	 */
	@Override
	public boolean dispatchKeyEvent(KeyEvent event) {
		return super.dispatchKeyEvent(event);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#dispatchTouchEvent(android.view.MotionEvent)
	 */
	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		// TODO Auto-generated method stub

		MessagesUtil.showToast(this, "touch");

		return false;

		// return super.dispatchTouchEvent(ev);
	}

}
