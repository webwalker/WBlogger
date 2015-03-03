package com.webwalker.wblogger;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.GridView;
import android.widget.ImageView;

import com.webwalker.activity.BaseActivity;
import com.webwalker.entity.AppInfo;
import com.webwalker.listener.IClickListener;
import com.webwalker.mediator.FunctionsAdapter;
import com.webwalker.mediator.GridViewFunction;
import com.webwalker.mediator.NormalAppFunction;
import com.webwalker.utility.MessagesUtil;
import com.webwalker.utility.PackageUtil;
import com.webwalker.utils.AppConstants;
import com.webwalker.utils.MyContext;

public class AppListActivity extends BaseActivity {
	private ViewPager viewPager;
	private ArrayList<View> pageViews;
	private ImageView imageView;
	private ImageView[] imageViews;
	// 包裹滑动图片LinearLayout
	private ViewGroup main;
	// 包裹小圆点的LinearLayout
	private ViewGroup group;
	// 当前页码
	private int pageNo;
	// 页记录数
	private int pageSize = 16;
	private int totalCount = 0;
	private int totalPages = 0;
	private List<AppInfo> appList = null;
	private GridView gvActive;
	private FunctionsAdapter faActive;
	private LayoutInflater inflater;
	static AppInfo currentApp = null;
	boolean isRefresh = false;

	/** Called when the activity is first created. */
	@SuppressWarnings("unchecked")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		inflater = getLayoutInflater();
		main = (ViewGroup) inflater.inflate(R.layout.tab_pa_main, null);

		LoadingData();
	}

	void LoadingData() {

		if (isRefresh) {
			if (group != null)
				group.removeAllViews();
			MyContext.Remove(AppConstants.Keys.AppList);
			isRefresh = false;
		}

		showProgressBar(R.string.msg_app_loading,
				AppConstants.TYPE_PROGRESS_DEFAULT);

		// 获取APP列表
		new AppAsyncLoader().execute();
		setContentView(main);
	}

	class AppAsyncLoader extends AsyncTask<Integer, Integer, Void> {

		@Override
		protected void onPreExecute() {
			group = (ViewGroup) main.findViewById(R.id.myViewGroup);
			viewPager = (ViewPager) main.findViewById(R.id.myPages);
			pageViews = new ArrayList<View>();
		}

		@SuppressWarnings("unchecked")
		@Override
		protected Void doInBackground(Integer... params) {
			appList = (List<AppInfo>) MyContext.get(AppConstants.Keys.AppList);
			if (appList == null) {
				appList = new PackageUtil(AppListActivity.this)
						.getInstallPackage();
				MyContext.Add(AppConstants.Keys.AppList, appList);
			}

			totalCount = appList.size();
			totalPages = totalCount % pageSize == 0 ? totalCount / pageSize
					: totalCount / pageSize + 1;
			for (int i = 0; i < totalPages; i++) {
				pageViews.add(inflater.inflate(R.layout.app_list, null));
			}

			imageViews = new ImageView[totalPages];

			for (int i = 0; i < totalPages; i++) {
				imageView = new ImageView(AppListActivity.this);
				imageView.setLayoutParams(new LayoutParams(30, 20));
				imageView.setPadding(20, 0, 20, 0);
				imageViews[i] = imageView;
				if (pageNo == i) {
					imageViews[pageNo]
							.setBackgroundResource(R.drawable.dot_now);
				} else {
					imageViews[i].setBackgroundResource(R.drawable.dot);
				}
				publishProgress(i);
			}
			return null;
		}

		@Override
		protected void onProgressUpdate(Integer... values) {
			group.addView(imageViews[values[0]]);
		}

		@Override
		protected void onPostExecute(Void result) {
			viewPager.setAdapter(new GuidePageAdapter());
			viewPager.setOnPageChangeListener(new GuidePageChangeListener());
			hideProgressBar();
		}
	}

	public void onResume() {
		viewPager.setCurrentItem(pageNo);
		super.onResume();
	}

	// 指引页面数据适配器
	class GuidePageAdapter extends PagerAdapter {

		@Override
		public int getCount() {
			return pageViews.size();
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

		@Override
		public int getItemPosition(Object object) {
			return super.getItemPosition(object);
		}

		@Override
		public void destroyItem(View arg0, int arg1, Object arg2) {
			((ViewPager) arg0).removeView(pageViews.get(arg1));
		}

		@Override
		public Object instantiateItem(View arg0, int arg1) {
			showGridView(arg1);
			((ViewPager) arg0).addView(pageViews.get(arg1));
			return pageViews.get(arg1);
		}
	}

	// 指引页面更改事件监听器
	class GuidePageChangeListener implements OnPageChangeListener {
		@Override
		public void onPageScrollStateChanged(int arg0) {
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
		}

		@Override
		public void onPageSelected(int arg0) {
			pageNo = arg0;
			for (int i = 0; i < imageViews.length; i++) {
				imageViews[arg0].setBackgroundResource(R.drawable.dot_now);
				if (arg0 != i) {
					imageViews[i].setBackgroundResource(R.drawable.dot);
				}
			}
			showGridView(arg0);
		}
	}

	/**
	 * 获取用户安装的应用列表
	 */
	private List<GridViewFunction> getActiveList(int pageIndex) {
		List<GridViewFunction> menuList = new ArrayList<GridViewFunction>();

		int beginIndex = pageIndex * pageSize;
		int endIndex = (pageIndex + 1) >= totalPages ? totalCount
				: ((pageIndex + 1) * pageSize);
		for (int i = beginIndex; i < endIndex; i++) {
			menuList.add(new NormalAppFunction(this, appList.get(i)));
		}
		return menuList;
	}

	private void showGridView(int index) {
		gvActive = (GridView) pageViews.get(index).findViewById(R.id.gvMenu);
		/** 设置激活的应用 */
		faActive = new FunctionsAdapter(AppListActivity.this,
				getActiveList(index));
		gvActive.setAdapter(faActive);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.app_list, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_recycle_all:
			new MessagesUtil(this).ShowDialog(R.string.dialog_title_confirm,
					R.string.is_continue, R.string.dialog_ok,
					R.string.dialog_cancel, new IClickListener() {

						@Override
						public void OnClickOK() {
							new PackageUtil(AppListActivity.this)
									.clearCacheSchedule();
							showToast(R.string.msg_op_succ);
						}

						@Override
						public void OnClickCancel() {
						}
					});
			break;
		case R.id.menu_kill_all:
			new MessagesUtil(this).ShowDialog(R.string.dialog_title_confirm,
					R.string.is_continue, R.string.dialog_ok,
					R.string.dialog_cancel, new IClickListener() {

						@Override
						public void OnClickOK() {
							new PackageUtil(AppListActivity.this)
									.killProcessSchedule();

							showToast(R.string.msg_op_succ);
						}

						@Override
						public void OnClickCancel() {
						}
					});
			break;
		default:
			break;
		}
		return true;
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {

		AppInfo app = (AppInfo) MyContext.get(AppConstants.Keys.CurrentApp);
		if (app == null)
			return true;

		Intent intent = new Intent();
		switch (item.getItemId()) {
		case R.id.menu_app_uninstall:
			intent.setAction("android.intent.action.DELETE");
			intent.addCategory("android.intent.category.DEFAULT");
			intent.setData(Uri.parse("package:" + app.packageName));
			startActivityForResult(intent, 0);
			break;
		case R.id.menu_app_detail:
			String line = "\r\n";
			StringBuilder sb = new StringBuilder();
			sb.append("应用名称：" + app.appName).append(line);
			sb.append("应用版本：" + app.versionName).append(line);
			sb.append(
					"缓存大小：" + PackageUtil.getFormatSize(app.sizeInfo.cachesize))
					.append(line);
			sb.append(
					"数据大小：" + PackageUtil.getFormatSize(app.sizeInfo.datasize))
					.append(line);
			sb.append(
					"应用大小：" + PackageUtil.getFormatSize(app.sizeInfo.codesize))
					.append(line);
			sb.append(
					"总的大小：" + PackageUtil.getFormatSize(app.sizeInfo.totalsize))
					.append(line);
			sb.append("安装日期：" + app.updateDate);

			MessagesUtil m = new MessagesUtil(this);
			m.ShowDialog(app.appName + "信息", sb.toString());
			break;
		case R.id.menu_app_delete_data:
			if (!PackageUtil.isRooted()) {
				showToast(R.string.msg_err_root);
				return true;
			}
			showToast(R.string.msg_err_clear);
			break;
		case R.id.menu_app_delete_all:
			if (!PackageUtil.isRooted()) {
				showToast(R.string.msg_err_root);
				return true;
			}
			showToast(R.string.msg_err_clear);
			break;
		case R.id.menu_app_run_end:

			boolean done = PackageUtil.killBackgroundProcess(this,
					app.packageName);
			if (done) {
				showToast(app.appName + "已结束!");
			} else {
				showToast(app.appName + "未运行!");
			}
			break;
		default:
			break;
		}
		return super.onContextItemSelected(item);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		isRefresh = true;
		LoadingData();
	}
}
