package com.webwalker.wblogger;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

import com.webwalker.activity.BaseActivity;
import com.webwalker.adpater.AppSubListAdapter;
import com.webwalker.adpater.ListViewItem;
import com.webwalker.controller.AccountController;
import com.webwalker.controller.AppController;
import com.webwalker.controller.ListViewController;
import com.webwalker.entity.UserAccountEntity;
import com.webwalker.utils.TableConstants;

public class AppSubAccountActivity extends BaseActivity implements
		OnClickListener {

	private static RadioGroup radioderGroup;
	ListView listView;
	TextView emptyText;
	Button btnSelect, btnBind, btnUnBind;
	int selectedId;
	UserAccountEntity user;
	ArrayList<String> mappingList;
	AccountController ac = new AccountController(this);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.app_sub_account);

		BindControls();
		LoadingIntentData();
	}

	void BindControls() {
		listView = (ListView) this.findViewById(R.id.MyListView);
		btnSelect = (Button) this.findViewById(R.id.rbSelect);
		btnBind = (Button) this.findViewById(R.id.rbBind);
		btnUnBind = (Button) this.findViewById(R.id.rbUnBind);
		emptyText = (TextView) this.findViewById(R.id.emptyText);
		btnSelect.setOnClickListener(this);
		btnBind.setOnClickListener(this);
		btnUnBind.setOnClickListener(this);
	}

	void LoadingIntentData() {
		Intent intent = getIntent();
		selectedId = intent.getIntExtra(TableConstants.UserAccount.id, 0);

		AccountController controller = new AccountController(this);
		user = controller.getSingleAccount(selectedId);

		if (user != null) {
			mappingList = ac.getAccountMapping(user.id);
			getSubAccount("0", user.appid);

			setMyTitle("<< " + user.getNickname() + " >>");
		}
	}

	void getSubAccount(String isParent, int appId) {
		ArrayList<UserAccountEntity> list = ac.getAccount(isParent, appId);
		listView.setAdapter(new AppSubListAdapter(this, list, mappingList));

		emptyText.setVisibility(View.INVISIBLE);
		if (list.size() == 0) {
			emptyText.setVisibility(View.VISIBLE);
		}
	}

	@Override
	public void onClick(View v) {
		ListViewController controller = new ListViewController(
				getBaseContext(), listView);
		List<ListViewItem> list = null;

		int id = v.getId();
		switch (id) {
		case R.id.rbSelect:
			controller.CheckAll(R.id.ItemCheckbox);
			break;
		case R.id.rbBind:
			list = controller.GetCheckList(R.id.itemTitle, R.id.ItemCheckbox);
			if (list.size() == 0) {
				showToast(R.string.msg_no_item_select);
				break;
			}
			ac.BindAccount(user, list);

			getSubAccount("0", user.appid);
			showToast(R.string.msg_info_bind_succ);
			goHome(R.id.rbActive);
			break;
		case R.id.rbUnBind:
			list = controller.GetCheckList(R.id.itemTitle, R.id.ItemCheckbox);
			if (list.size() == 0) {
				showToast(R.string.msg_no_item_select);
				break;
			}
			ac.UnBindAccount(user.getId(), list);

			getSubAccount("0", user.appid);
			showToast(R.string.msg_info_unbind_succ);
			goHome(R.id.rbActive);
			break;
		}
	}
}
