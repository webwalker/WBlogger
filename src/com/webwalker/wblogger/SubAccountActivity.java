package com.webwalker.wblogger;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.webwalker.activity.BaseActivity;
import com.webwalker.adpater.ListViewItem;
import com.webwalker.adpater.SubAccountListAdapter;
import com.webwalker.controller.AccountController;
import com.webwalker.controller.ListViewController;
import com.webwalker.entity.UserAccountEntity;
import com.webwalker.listener.MyRequestListener;

public class SubAccountActivity extends BaseActivity {

	AccountController ac = new AccountController(this);
	ListView listView;
	TextView emptyText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sub_account);

		listView = (ListView) this.findViewById(R.id.MyListView);
		emptyText = (TextView) this.findViewById(R.id.emptyText);

		InitListView();
	}

	void InitListView() {

		setMyTitle(R.string.title_activity_sub_account);

		ArrayList<UserAccountEntity> list = ac.getAllSubAccount(0);
		listView.setAdapter(new SubAccountListAdapter(this, list));

		emptyText.setVisibility(View.INVISIBLE);
		if (list.size() == 0) {
			showToast(R.string.msg_no_sub);
			emptyText.setVisibility(View.VISIBLE);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.sub_account, menu);
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
		case R.id.menu_add:
			intent.setClass(this, AddBindActivity.class);
			startActivity(intent);
			break;
		case R.id.menu_delete:
			List<ListViewItem> list = controller.GetCheckList(R.id.itemId,
					R.id.itemAppId, R.id.itemTitle, R.id.ItemCheckbox);
			if (list.size() == 0) {
				showToast(R.string.msg_no_item_select);
				break;
			}

			ac = new AccountController(SubAccountActivity.this,
					new MyRequestListener() {
						@Override
						public void OnAction() {
							InitListView();
						}
					});
			ac.deleteSubAccount(list);

			break;
		default:
			break;
		}
		return true;
	}
}
