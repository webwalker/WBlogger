package com.webwalker.wblogger;

import android.app.Activity;
import android.app.Service;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.webwalker.wblogger.locations.Location;
import com.webwalker.wblogger.locations.Location.locationCallBack;

public class WeiboSignActivity extends Activity implements OnClickListener {

	EditText etSearch;
	ListView lvLocation;
	TextView tvLocation;

	private LocationClient mLocClient;
	private Location loc = new Location();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.weibo_sign);

		BindControls();
		getLocation();

		// listview支持缓存策略，更多加载
		// 搜索地点点击后，弹出搜索框
	}

	@Override
	public void onDestroy() {
		mLocClient.stop();
		loc.mTv = null;
		super.onDestroy();
	}

	void BindControls() {
		etSearch = (EditText) findViewById(R.id.etSearch);
		lvLocation = (ListView) findViewById(R.id.lvLocation);
		tvLocation = (TextView) findViewById(R.id.tvLocation);

		etSearch.setOnClickListener(this);
	}

	void getLocation() {
		loc.onInit(getApplicationContext());
		loc.setCallback(new myLocation());

		mLocClient = loc.mLocationClient;
		setLocationOption();
		mLocClient.start();
	}

	class myLocation implements locationCallBack {

		@Override
		public void Action(Object data) {
			BDLocation location = (BDLocation) data;
			if (location != null) {
				tvLocation.setText("你的位置：" + location.getAddrStr());
			}
		}
	}

	// 设置相关参数
	private void setLocationOption() {
		LocationClientOption option = new LocationClientOption();
		option.setOpenGps(true); // 打开gps
		option.setCoorType("bd09ll"); // 设置坐标类型
		option.setAddrType("all"); // 设置地址信息，仅设置为“all”时有地址信息，默认无地址信息
		option.setScanSpan(10000); // 设置定位模式，小于1秒则一次定位;大于等于1秒则定时定位
		mLocClient.setLocOption(option);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.weibo_sign, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		int id = item.getItemId();
		switch (id) {
		case R.id.menu_refresh:
			break;
		}

		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.etSearch:
			break;
		}
	}
}
