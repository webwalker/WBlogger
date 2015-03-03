package com.webwalker.wblogger;

import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.webwalker.activity.BaseActivity;
import com.webwalker.api.APIFactory;
import com.webwalker.api.ApiContainer;
import com.webwalker.api.ApiListener.ListenerCallback;
import com.webwalker.api.sina.Weibo;
import com.webwalker.controller.AccountController;
import com.webwalker.data.MyDataHelper;
import com.webwalker.entity.ApiCommonEntity;
import com.webwalker.entity.UserAccountEntity;
import com.webwalker.rules.RulesDefine.TaskRule;
import com.webwalker.utils.AppConstants;
import com.webwalker.utils.TableConstants;
import com.weibo.sdk.android.Oauth2AccessToken;
import com.weibo.sdk.android.WeiboAuthListener;
import com.weibo.sdk.android.WeiboDialogError;
import com.weibo.sdk.android.WeiboException;

public class SinaAuthActivity extends BaseActivity {
	private Weibo mWeibo;
	public static Oauth2AccessToken accessToken;
	public static final String TAG = "SinaAuthActivity";
	private boolean isupdate = false;
	ProgressBar pbLoading;
	Intent intent = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sina_auth);
		setMyTitle(R.string.title_activity_sina_auth);

		intent = getIntent();
		isupdate = intent.getBooleanExtra(AppConstants.OAuth.isupdate, false);

		mWeibo = Weibo.getInstance(AppConstants.OAuth.CONSUMER_KEY,
				AppConstants.OAuth.REDIRECT_URL);
		mWeibo.authorize(SinaAuthActivity.this, new AuthDialogListener());
	}

	class AuthDialogListener implements WeiboAuthListener {

		@Override
		public void onComplete(Bundle values) {
			String token = values.getString(AppConstants.OAuth.access_token);
			String expires_in = values.getString(AppConstants.OAuth.expires_in);
			String uid = values.getString(AppConstants.OAuth.uid);
			SinaAuthActivity.accessToken = new Oauth2AccessToken(token,
					expires_in);
			if (SinaAuthActivity.accessToken.isSessionValid()) {
				Toast.makeText(SinaAuthActivity.this,
						getString(R.string.auth_get_userinfo),
						Toast.LENGTH_SHORT).show();

				// 获取用户信息
				ApiCommonEntity a = new ApiCommonEntity();
				a.setAppid(AppConstants.OAuth.sina);
				a.setTaskRule(TaskRule.GUIID);
				a.setUid(uid);
				a.setToken(accessToken);
				a.setListener(new getUserInfoListener());

				ApiContainer container = APIFactory.getInstance()
						.getCommonContainer(getBaseContext(), a);
				container.startAction();
			}
		}

		@Override
		public void onError(WeiboDialogError e) {
			Toast.makeText(getApplicationContext(),
					"Auth error : " + e.getMessage(), Toast.LENGTH_LONG).show();
		}

		@Override
		public void onCancel() {
			Toast.makeText(getApplicationContext(), "Auth cancel",
					Toast.LENGTH_LONG).show();
		}

		@Override
		public void onWeiboException(WeiboException e) {
			Toast.makeText(getApplicationContext(),
					"Auth exception : " + e.getMessage(), Toast.LENGTH_LONG)
					.show();
		}

	}

	class getUserInfoListener implements ListenerCallback {

		@Override
		public void callBack(Object data) {
			try {
				JSONObject json = new JSONObject(String.valueOf(data));

				MyDataHelper db = new MyDataHelper(SinaAuthActivity.this);
				UserAccountEntity u = new UserAccountEntity();
				u.setAppid(AppConstants.OAuth.sina);
				u.setExpiresin(SinaAuthActivity.accessToken.getExpiresTime());
				u.setNickname(json.optString("screen_name"));
				u.setStatus(AppConstants.validStatus);
				u.setUid(json.optString("id"));
				u.setUsertoken(accessToken.getToken());
				u.setIsparent(intent.getIntExtra(
						TableConstants.UserAccount.isparent, 0));

				// 验证本地账号是否存在
				AccountController controller = new AccountController(
						SinaAuthActivity.this);
				UserAccountEntity user = controller.getSingleAccountByName(
						u.getAppid(), u.getNickname());
				if (user != null) {
					
					u.setId(user.getId());
					db.updateAccount(u);
					
					Log.v(TAG, "update Account over!");
					sendMessage(R.string.msg_update_account_succ);
				} else {
					db.insertAccount(u);
					Log.v(TAG, "insert Account over!");
					sendMessage(R.string.msg_add_account_succ);
				}

				goHome(R.id.rbActive);
			} catch (JSONException e) {
				e.printStackTrace();
				sendMessage(R.string.msg_add_oauth_exception, e.getMessage());
			}
		}

		@Override
		public void onError(Object data) {
			Log.v(TAG, data.toString());
			sendMessage(R.string.msg_add_oauth_exception, data.toString());
		}

	}
}
