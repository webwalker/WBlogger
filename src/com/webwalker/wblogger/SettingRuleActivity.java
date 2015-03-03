package com.webwalker.wblogger;

import java.util.Map;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import com.webwalker.activity.BaseActivity;
import com.webwalker.controller.AccountController;
import com.webwalker.controller.BizTaskController;
import com.webwalker.controller.BizTaskController.RuleType;
import com.webwalker.entity.SchedueMonitorEntity;
import com.webwalker.entity.UserAccountEntity;
import com.webwalker.utility.ViewUtils;
import com.webwalker.utils.AppConstants;
import com.webwalker.utils.TableConstants;

public class SettingRuleActivity extends BaseActivity {

	UserAccountEntity user;
	Spinner mSpinner, timeSpinner, tSpinner;
	int mRuleId, timeRuleId, tRuleId;
	BizTaskController controller = new BizTaskController(this);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setting_rule);
		setMyTitle(R.string.title_activity_setting_rule);

		BindControls();
	}

	void BindControls() {
		mSpinner = (Spinner) findViewById(R.id.mSpinner);
		timeSpinner = (Spinner) findViewById(R.id.timeSpinner);
		tSpinner = (Spinner) findViewById(R.id.tSpinner);
		Button btnSubmit = (Button) findViewById(R.id.btnRule);

		SpinnerAdapter mAdapter = ViewUtils.createSimpleSpinnerAdapter(this,
				controller.getRules(RuleType.Monitor), AppConstants.MapKey);
		SpinnerAdapter timeAdapter = ViewUtils.createSimpleSpinnerAdapter(this,
				controller.getRules(RuleType.Time), AppConstants.MapKey);
		SpinnerAdapter tAdapter = ViewUtils.createSimpleSpinnerAdapter(this,
				controller.getRules(RuleType.Task), AppConstants.MapKey);
		mSpinner.setAdapter(mAdapter);
		mSpinner.setOnItemSelectedListener(new ItemSelectedListener());
		tSpinner.setAdapter(tAdapter);
		tSpinner.setOnItemSelectedListener(new ItemSelectedListener());
		timeSpinner.setAdapter(timeAdapter);
		timeSpinner.setOnItemSelectedListener(new ItemSelectedListener());

		btnSubmit.setOnClickListener(new SubmitClickListener());
	}

	class SubmitClickListener implements OnClickListener {
		@Override
		public void onClick(View v) {
			Intent intent = getIntent();
			int uaid = intent.getIntExtra(TableConstants.UserAccount.id, 0);

			AccountController controller = new AccountController(
					SettingRuleActivity.this);
			user = controller.getSingleAccount(uaid);

			// 保存监控规则
			saveMonitorRule();
		}
	}

	// 保存监控规则
	private void saveMonitorRule() {

		SchedueMonitorEntity s = new SchedueMonitorEntity();
		s.setUaid(user.id);
		s.setUid(user.nickname);
		s.setMonitoruleid(mRuleId);
		s.setTimeruleid(timeRuleId);
		s.setTaskruleid(tRuleId);
		s.setStatus(1);

		boolean haset = controller.hasSetRule(user.id);
		if (haset) {
			controller.updateSchedueMonitorRule(s);
		} else {

			controller.addSchedueRule(s);
		}

		// TODO 通知服务更新，不需要，等到下次遍历规则时自动生效
		showToast(R.string.msg_success);
		goHome(R.id.rbActive);
	}

	class ItemSelectedListener implements OnItemSelectedListener {

		@Override
		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			@SuppressWarnings("unchecked")
			Map<String, Object> map = (Map<String, Object>) arg0
					.getItemAtPosition(arg2);

			switch (arg0.getId()) {
			case R.id.mSpinner:
				mRuleId = Integer.valueOf(map.get("value").toString());
				break;
			case R.id.timeSpinner:
				timeRuleId = Integer.valueOf(map.get("value").toString());
				break;
			case R.id.tSpinner:
				tRuleId = Integer.valueOf(map.get("value").toString());
				break;
			}
		}

		@Override
		public void onNothingSelected(AdapterView<?> arg0) {
		}
	}
}
