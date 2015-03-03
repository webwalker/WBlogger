package com.webwalker.wblogger;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.webwalker.activity.BaseActivity;

public class LoginActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		setMyTitle(R.string.login);

		Button btnLogin = (Button) findViewById(R.id.btnLogin);
		btnLogin.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				EditText etPass = (EditText) findViewById(R.id.etLogin);
				String pass = etPass.getText().toString();
				if (pass.equals("")
						|| !WBloggerPreferenceActivity.login(
								LoginActivity.this, pass)) {
					showToast(R.string.msg_input_login);
					return;
				}

				showToast(R.string.msg_login_succ);

				Intent intent = new Intent(LoginActivity.this,
						MainActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
				startActivity(intent);
				finish();

				overridePendingTransition(R.anim.scale_rotate,
						R.anim.my_alpha_action);
			}
		});
	}
}
