package com.webwalker.wblogger;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnCreateContextMenuListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.TextView;

import com.webwalker.activity.BaseActivity;
import com.webwalker.adpater.BindListAdapter;
import com.webwalker.adpater.ListViewItem;
import com.webwalker.adpater.ViewHolder;
import com.webwalker.controller.ListViewController;
import com.webwalker.controller.WeiboActiveController;
import com.webwalker.listener.MyRequestListener;
import com.webwalker.utils.AppConstants;
import com.webwalker.utils.TableConstants;
import com.webwalker.widget.MyListView;
import com.webwalker.widget.MyListView.OnRefreshListener;

public class WeiboActiveActivity extends BaseActivity {

	int selectedId;
	MyListView listView;
	ListViewController listController;
	WeiboActiveController wac = new WeiboActiveController(
			WeiboActiveActivity.this);

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.weibo_active);

		listView = (MyListView) this.findViewById(R.id.MyListView);
		listView.setOnCreateContextMenuListener(new ContextMenuListener());
		listView.setOnItemLongClickListener(new ItemLongClickListener());
		listController = new ListViewController(getBaseContext(), listView);

		listView.setonRefreshListener(new OnRefreshListener() {
			public void onRefresh() {
				new AsyncTask<Void, Void, Void>() {
					protected Void doInBackground(Void... params) {
						try {
							Thread.sleep(1000);
						} catch (Exception e) {
							e.printStackTrace();
						}
						onProgressUpdate();
						return null;
					}

					@Override
					protected void onPostExecute(Void result) {
						// adapter.notifyDataSetChanged();
						listView.onRefreshComplete();
					}

					@Override
					protected void onProgressUpdate(Void... values) {
						// InitListView();
					}

				}.execute();
			}
		});
	}

	@Override
	protected void onResume() {
		super.onResume();
		InitListView();
	}

	void InitListView() {

		ArrayList<LinkedHashMap<String, String>> list = wac.getAccount("1");
		listView.setAdapter(new BindListAdapter(this, list));
		ViewHolder.setListViewHeightBasedOnFixed(listView, 81);

		if (list.size() == 0) {
			showToast(R.string.msg_no_user);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.weibo_active, menu);
		return true;
	}

	private List<ListViewItem> getSelectedItem() {
		List<ListViewItem> list = listController.GetCheckList(R.id.itemId,
				R.id.itemAppId, R.id.itemTitle, R.id.ItemCheckbox);
		if (list.size() == 0) {
			showToast(R.string.msg_no_item_select);
			return null;
		}
		return list;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		Intent intent = new Intent();

		switch (item.getItemId()) {
		case R.id.menu_select:
			listController.CheckAll(R.id.ItemCheckbox);
			break;
		case R.id.menu_add:
			intent.setClass(this, AddBindActivity.class);
			startActivity(intent);
			break;
		case R.id.menu_delete:

			List<ListViewItem> list = getSelectedItem();
			if (list != null) {
				wac = new WeiboActiveController(WeiboActiveActivity.this,
						new MyRequestListener() {
							@Override
							public void OnAction() {
								InitListView();
							}
						});
				wac.deleteUserAccount(list);
			}

			break;
		case R.id.menu_run:
			list = getSelectedItem();
			if (list != null) {
				wac.executeTask(list, AppConstants.TaskStatus.STATE_RUNNING);
			}

			break;
		case R.id.menu_stop:
			list = getSelectedItem();
			if (list != null) {
				wac.stopTask(list, AppConstants.TaskStatus.STATE_RUNNING);
			}
			break;
		case R.id.menu_sub_all:
			intent.setClass(this, SubAccountActivity.class);
			startActivity(intent);
			break;
		case R.id.menu_schedue:
			intent.setClass(this, ScheduePlanActivity.class);
			startActivity(intent);
			break;
		case R.id.menu_log:
			intent.setClass(this, ExecutorLogActivity.class);
			startActivity(intent);
			break;
		default:
			break;
		}
		return true;
	}

	class ContextMenuListener implements OnCreateContextMenuListener {
		@Override
		public void onCreateContextMenu(ContextMenu menu, View v,
				ContextMenuInfo menuInfo) {
			getMenuInflater().inflate(R.menu.bind_list_item, menu);
		}
	}

	class ItemLongClickListener implements OnItemLongClickListener {
		@Override
		public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
				int arg2, long arg3) {
			TextView txtId = (TextView) arg1.findViewById(R.id.itemId);
			selectedId = Integer.valueOf(txtId.getText().toString());

			// 返回true则忽略ContextMenuListener
			return false;
		}
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		Intent intent = new Intent();
		intent.putExtra(TableConstants.UserAccount.id, selectedId);

		switch (item.getItemId()) {
		case R.id.menu_set_sub:
			wac = new WeiboActiveController(WeiboActiveActivity.this,
					new MyRequestListener() {
						@Override
						public void OnAction() {
							InitListView();
						}

						@Override
						public void OnAction(Object data) {
							if (data != null) {
								showToast(Integer.valueOf(data.toString()));
							}
							InitListView();
						}
					});
			wac.updateIsParent(0, 1, selectedId);
			break;
		case R.id.menu_bind_sub:
			intent.setClass(this, AppSubAccountActivity.class);
			startActivity(intent);
			break;
		// TODO 已更新，不需要立即生效，下次执行遍历时生效
		case R.id.menu_rule:
			intent.setClass(this, SettingRuleActivity.class);
			startActivity(intent);
			break;
		case R.id.menu_comment:
			intent.setClass(this, SettingCommentActivity.class);
			startActivity(intent);
			break;
		default:
			break;
		}

		return super.onContextItemSelected(item);
	}
}
