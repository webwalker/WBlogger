/**
 * 
 */
package com.webwalker.api;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.webwalker.api.ApiListener.ListenerCallback;
import com.webwalker.api.ApiListener.sinaListener;
import com.webwalker.api.sina.StatusesAPI;
import com.webwalker.controller.AccountController;
import com.webwalker.controller.BizTaskController;
import com.webwalker.entity.RepostEntity;
import com.webwalker.entity.RequestBody;
import com.webwalker.entity.SchedueExecutorEntity;
import com.webwalker.entity.UserAccountEntity;
import com.webwalker.utils.AppConstants;
import com.weibo.sdk.android.Oauth2AccessToken;

/**
 * @author Administrator
 * 
 */
public class SinaExecutor extends ExecutorCreator {

	Oauth2AccessToken token = null;

	public SinaExecutor(Context context, SchedueExecutorEntity s) {
		super(context, s);

		// 获取最新的token认证信息
		AccountController controller = new AccountController(context);
		UserAccountEntity user = controller.getSingleAccount(s.getUaid());
		if (!user.getUsertoken().equals("")) {
			token = new Oauth2AccessToken(user.getUsertoken(),
					String.valueOf(user.getExpiresin()));
		}
	}

	@Override
	public RequestBody getRequestBody() {
		if (requestBody != null)
			return requestBody;

		switch (executor.getTaskRule()) {
		case FW:
			requestBody = new RepostEntity();
		default:
			break;
		}
		return requestBody;
	}

	@Override
	public void startAction() {
		StatusesAPI statusApi = new StatusesAPI(token);
		switch (executor.getTaskRule()) {
		case FW:
			RepostEntity r = (RepostEntity) getRequest(executor
					.getRequestbody());
			statusApi.repost(r.getId(), r.getStatus(), r.getIs_comment(),
					new nullListener());// r.getListener();

			break;
		default:
			break;
		}
	}

	@Override
	public Object startActionResult() {
		// TODO Auto-generated method stub
		return null;
	}
}
