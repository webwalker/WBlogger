package com.webwalker.wblogger;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.webwalker.activity.BaseActivity;
import com.webwalker.adpater.ListViewItem;
import com.webwalker.adpater.SchedueListAdapter;
import com.webwalker.controller.BizTaskController;
import com.webwalker.controller.ListViewController;
import com.webwalker.entity.SchedueExecutorEntity;
import com.webwalker.listener.MyRequestListener;

public class ScheduePlanActivity extends BaseActivity {

	BizTaskController tc = new BizTaskController(this);
	ListView listView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.schedue_plan);
		setMyTitle(R.string.title_activity_schedue_plan);

		listView = (ListView) this.findViewById(R.id.MyListView);
		InitListView();
	}

	void InitListView() {

		ArrayList<SchedueExecutorEntity> list = tc.getAllScheduePlan();
		listView.setAdapter(new SchedueListAdapter(this, list));

		if (list.size() == 0) {
			showToast(R.string.msg_no_plan);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.schedue_plan, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		Intent intent = new Intent();
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

			tc = new BizTaskController(ScheduePlanActivity.this,
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
