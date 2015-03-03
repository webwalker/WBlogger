package com.webwalker.wblogger;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import android.content.Intent;
import android.os.Bundle;
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
import com.webwalker.controller.AccountController;
import com.webwalker.controller.BizTaskController;
import com.webwalker.entity.SchedueMonitorEntity;
import com.webwalker.entity.UserAccountEntity;
import com.webwalker.utility.ViewUtils;
import com.webwalker.utils.AppConstants;
import com.webwalker.utils.TableConstants;

public class SettingCommentActivity extends BaseActivity {

	BizTaskController controller = new BizTaskController(this);
	UserAccountEntity user;
	Spinner cSpinner;
	EditText etComment;
	CheckBox cbRandom, cbDisable;
	int commentId;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setting_comment);
		setMyTitle(R.string.title_activity_setting_comment);

		BindControls();
	}

	void BindControls() {
		cSpinner = (Spinner) findViewById(R.id.commentSpinner);
		etComment = (EditText) findViewById(R.id.etComment);
		cbRandom = (CheckBox) findViewById(R.id.cbRandom);
		cbDisable = (CheckBox) findViewById(R.id.cbRandomDisable);
		Button btnComment = (Button) findViewById(R.id.btnComment);

		List<Map<String, ?>> list = controller.getComments();
		Map<String, Object> map = new TreeMap<String, Object>();
		map.put(AppConstants.MapValue, "-1");
		map.put(AppConstants.MapKey, getString(R.string.item_custom_text));
		list.add(map);
		SpinnerAdapter cAdapter = ViewUtils.createSimpleSpinnerAdapter(this,
				list, AppConstants.MapKey);
		cSpinner.setAdapter(cAdapter);
		cSpinner.setOnItemSelectedListener(new ItemSelectedListener());

		btnComment.setOnClickListener(new SubmitClickListener());
	}

	class ItemSelectedListener implements OnItemSelectedListener {

		@Override
		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			@SuppressWarnings("unchecked")
			Map<String, Object> map = (Map<String, Object>) arg0
					.getItemAtPosition(arg2);
			commentId = Integer.valueOf(map.get("value").toString());
			
			etComment.setVisibility(View.INVISIBLE);
			if (commentId == -1) {
				etComment.setVisibility(View.VISIBLE);
			}
		}

		@Override
		public void onNothingSelected(AdapterView<?> arg0) {
		}
	}

	class SubmitClickListener implements OnClickListener {
		@Override
		public void onClick(View v) {
			try {
				Intent intent = getIntent();
				int uaid = intent.getIntExtra(TableConstants.UserAccount.id, 0);
				user = new AccountController(SettingCommentActivity.this)
						.getSingleAccount(uaid);

				// 新增自定义评论
				if (etComment.getVisibility() == View.VISIBLE) {
					String comment = etComment.getText().toString();
					if (comment.equals("")) {
						showToast(R.string.msg_comment_null);
						return;
					}
					controller.insertComment(etComment.getText().toString());
					commentId = controller.getMaxCommentId();
				}

				if (cbRandom.isChecked()) { // 随机
					commentId = -1;
				}

				if (cbDisable.isChecked()) { // 禁用, 优先级高
					commentId = -2;
				}

				boolean haset = controller.hasSetRule(uaid);
				if (haset) {
					// 更新评论映射
					controller.updateSchedueComment(uaid, commentId);
				} else {
					// 新增评论映射
					SchedueMonitorEntity s = new SchedueMonitorEntity();
					s.setUaid(user.id);
					s.setUid(user.getNickname());
					s.setStatus(1);
					s.setCommentId(commentId);
					controller.addSchedueRule(s);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			// TODO 通知服务更新

			showToast(R.string.msg_success);
			goHome(R.id.rbActive);
		}
	}
}
