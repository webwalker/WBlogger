package com.webwalker.wblogger;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ExpandableListView;

import com.webwalker.activity.BaseActivity;
import com.webwalker.adpater.ListViewItem;
import com.webwalker.adpater.LogExpandableListAdapter;
import com.webwalker.controller.BizTaskController;
import com.webwalker.controller.ListViewController;
import com.webwalker.entity.TaskLogEntity;
import com.webwalker.listener.MyRequestListener;

public class ExecutorLogActivity extends BaseActivity {

	BizTaskController tc = new BizTaskController(this);
	ExpandableListView listView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.schedue_plan);
		setMyTitle(R.string.title_activity_executor_log);

		listView = (ExpandableListView) this
				.findViewById(R.id.MyExpandableListView);
		InitListView();
	}

	void InitListView() {

		ArrayList<TaskLogEntity> list = tc.getExecutorLog();
		listView.setAdapter(new LogExpandableListAdapter(this, list));

		if (list.size() == 0) {
			showToast(R.string.msg_no_log);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.executor_log, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		ListViewController controller = new ListViewController(
				getBaseContext(), listView);

		switch (item.getItemId()) {
		case R.id.menu_select:
			controller.CheckAll(R.id.ItemCheckbox);
			break;
		case R.id.menu_delete:
			List<ListViewItem> list = controller.GetCheckList(R.id.itemId,
					R.id.ItemCheckbox);
			if (list.size() == 0) {
				showToast(R.string.msg_no_item_select);
				break;
			}

			tc = new BizTaskController(ExecutorLogActivity.this,
					new MyRequestListener() {
						@Override
						public void OnAction() {
							InitListView();
						}
					});
			tc.deleteScheduePlan(list);

			break;
		default:
			break;
		}
		return true;
	}
}
