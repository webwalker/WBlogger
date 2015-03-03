package com.webwalker.wblogger;

import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import com.webwalker.activity.BaseActivity;
import com.webwalker.api.APIFactory;
import com.webwalker.api.ApiContainer;
import com.webwalker.api.ApiListener.ListenerCallback;
import com.webwalker.controller.AccountController;
import com.webwalker.controller.AppController;
import com.webwalker.data.MyDataHelper;
import com.webwalker.entity.ApiCommonEntity;
import com.webwalker.entity.UserAccountEntity;
import com.webwalker.rules.RulesDefine.TaskRule;
import com.webwalker.utility.MessagesUtil;
import com.webwalker.utility.ViewUtils;
import com.webwalker.utils.AppConstants;
import com.webwalker.utils.TableConstants;

public class AddBindActivity extends BaseActivity {
	private String TAG = "AddBindActivity";
	private Intent intent = null;
	CheckBox cbParent, cbAuth;
	Button btnAdd;
	Spinner spinner;
	EditText textNickName;
	ContentValues values;
	String nickName = "";
	int appId = 0;
	boolean isParent = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_bind);
		setMyTitle(R.string.title_activity_add_bind);

		BindControls();
	}

	void BindControls() {
		spinner = (Spinner) findViewById(R.id.spinner1);
		cbParent = (CheckBox) findViewById(R.id.cbParent);
		cbAuth = (CheckBox) findViewById(R.id.cbAuth);
		btnAdd = (Button) findViewById(R.id.button1);
		btnAdd.setOnClickListener(new AddListener());

		List<Map<String, ?>> list = new AppController(this).getAppList();
		SpinnerAdapter sa = ViewUtils.createSimpleSpinnerAdapter(this, list,
				AppConstants.MapKey);
		spinner.setAdapter(sa);
		spinner.setOnItemSelectedListener(new ItemSelectedListener());

		// 跳转过来时更新授权信息
		intent = getIntent();
		appId = intent.getIntExtra(AppConstants.OAuth.appid, 0);
		if (appId != 0) {
			appAuth(appId);
		}
	}

	void appAuth(int appid) {
		switch (appid) {
		case AppConstants.OAuth.sina:
			intent.setClass(getBaseContext(), SinaAuthActivity.class);
			break;
		}

		startActivity(intent);
	}

	class AddListener implements OnClickListener {
		@Override
		public void onClick(View v) {
			isParent = cbParent.isChecked();
			boolean isAuth = cbAuth.isChecked();
			if (isParent == false && isAuth == false) {
				showToast(R.string.msg_must_select_one);
				return;
			}

			intent.putExtra(TableConstants.UserAccount.app_id, appId);
			intent.putExtra(TableConstants.UserAccount.isparent,
					isParent == true ? 1 : 0);
			if (isAuth)
				appAuth(appId);
			else {
				// 添加普通账号
				final View view = LayoutInflater.from(AddBindActivity.this)
						.inflate(R.layout.normal_account, null);
				final EditText textNickName = (EditText) view
						.findViewById(R.id.textNickName);

				AlertDialog.Builder builder = new AlertDialog.Builder(
						AddBindActivity.this);
				builder.setTitle(getString(R.string.dialog_account_title))
						.setView(view)
						.setMessage(R.string.nor_nickname)
						.setNegativeButton(getString(R.string.dialog_cancel),
								null);
				builder.setPositiveButton(getString(R.string.dialog_ok),
						new DialogInterface.OnClickListener() {

							public void onClick(DialogInterface dialog,
									int which) {
								nickName = textNickName.getText().toString();
								if (nickName.equals("")) {
									showToast(R.string.msg_input_empty);
									return;
								}

								// 验证本地账号是否存在
								AccountController controller = new AccountController(
										AddBindActivity.this);
								if (controller.getSingleAccountByName(appId,
										nickName) != null) {
									showToast(R.string.msg_account_exists);
									return;
								}

								// 等待提示
								showProgressBar(R.string.msg_processing,
										AppConstants.TYPE_PROGRESS_DEFAULT);

								// 判断微博账号是否存在
								ApiCommonEntity a = new ApiCommonEntity();
								a.setAppid(appId);
								a.setTaskRule(TaskRule.GUINICKNAME);
								a.setUid(nickName);
								a.setListener(new getUserInfoListener());

								ApiContainer container = APIFactory
										.getInstance().getCommonContainer(
												getBaseContext(), a);
								container.startAction();
							}
						});
				builder.show();
			}
		}
	}

	class getUserInfoListener implements ListenerCallback {

		@Override
		public void callBack(Object data) {
			try {
				JSONObject json = new JSONObject(String.valueOf(data));

				MyDataHelper db = new MyDataHelper(getBaseContext());
				UserAccountEntity u = new UserAccountEntity();
				u.setAppid(appId);
				u.setNickname(nickName);
				u.setUid(json.optString("id"));
				u.setStatus(1);
				u.setIsparent(isParent == true ? 1 : 0);
				db.insertAccount(u);

				// 成功
				sendMessage(R.string.msg_add_account_succ);
				goHome(R.id.rbActive);
			} catch (JSONException e) {
				e.printStackTrace();
				sendMessage(R.string.msg_add_account_exception, e.getMessage());
			} finally {
				hideProgressBar();
				finish();
			}
		}

		@Override
		public void onError(Object data) {
			hideProgressBar();
			// sendMessage(R.string.msg_add_account_exception);
			sendMessage(R.string.msg_add_account_exception, data.toString());
		}
	}

	class ItemSelectedListener implements OnItemSelectedListener {

		@Override
		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			Map<String, Object> map = (Map<String, Object>) arg0
					.getItemAtPosition(arg2);
			appId = Integer.valueOf(map.get("value").toString());
		}

		@Override
		public void onNothingSelected(AdapterView<?> arg0) {
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		Intent intent = new Intent();
		switch (requestCode) {
		case AppConstants.RequestCode.WeiboActive:
			intent.setClass(AddBindActivity.this, WeiboActiveActivity.class);
			break;
		}

		startActivity(intent);
		finish();
	}
}
