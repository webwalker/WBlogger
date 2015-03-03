package com.webwalker.wblogger;

import android.os.Bundle;
import android.view.Menu;

import com.webwalker.activity.BaseActivity;

public class WeiboSyncActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.weibo_sync);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.weibo_sync, menu);
		return true;
	}

}
