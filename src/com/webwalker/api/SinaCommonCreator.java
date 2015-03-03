/**
 * 
 */
package com.webwalker.api;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.webwalker.api.ApiListener.ListenerCallback;
import com.webwalker.api.ApiListener.sinaListener;
import com.webwalker.api.sina.StatusesAPI;
import com.webwalker.api.sina.UsersAPI;
import com.webwalker.api.sina.WeiboAPI;
import com.webwalker.controller.AccountController;
import com.webwalker.entity.ApiCommonEntity;
import com.webwalker.entity.RepostEntity;
import com.webwalker.entity.UserAccountEntity;
import com.webwalker.utils.AppConstants;
import com.weibo.sdk.android.Oauth2AccessToken;

/**
 * @author Administrator
 * 
 */
public class SinaCommonCreator extends WeiboCreator<ApiCommonEntity> {

	Oauth2AccessToken token = null;

	public SinaCommonCreator(Context context, ApiCommonEntity s) {
		super(context, s);

		token = new Oauth2AccessToken("2.00EjzHJC0WS2wM57b21d031foSuE2D", "0");

		if (s.getToken() == null) {
			// 获取最新的token认证信息
			AccountController controller = new AccountController(context);
			UserAccountEntity user = controller.getSingleAccount(s.getUaid());
			if (user != null && !user.getUsertoken().equals("")) {
				token = new Oauth2AccessToken(user.getUsertoken(),
						String.valueOf(user.getExpiresin()));
			}
		} else {
			token = (Oauth2AccessToken) s.getToken();
		}
	}

	@Override
	public void startAction() {
		StatusesAPI statusApi = new StatusesAPI(token);
		switch (executor.getTaskRule()) {
		case UPWID: // 获取最新发布的微博ID
			ApiListener al = new ApiListener(new getLastestWeiboListener());
			sinaListener listener = al.new sinaListener();
			statusApi.userTimelineIds(AppConstants.OAuth.CONSUMER_KEY,
					executor.getUid(), executor.getPostsinceid(), 0,
					executor.getCount(), 1, false, WeiboAPI.FEATURE.ALL,
					listener);
			break;
		case GUIID: // 根据ID获取用户信息
			al = new ApiListener(executor.getListener());
			listener = al.new sinaListener();
			UsersAPI api = new UsersAPI(token);
			api.show(Long.valueOf(executor.getUid()), listener);
			break;
		case GUINICKNAME: // 根据昵称获取用户信息
			al = new ApiListener(executor.getListener());
			listener = al.new sinaListener();
			api = new UsersAPI(token);
			api.show(executor.getUid(), listener);
			break;
		default:
			break;
		}
	}

	@Override
	public Object startActionResult() {
		return null;
	}

	class getLastestWeiboListener implements ListenerCallback {

		@Override
		public void callBack(Object data) {
			try {
				JSONObject json = new JSONObject(String.valueOf(data));
				JSONArray ids = json.getJSONArray("statuses");

				if (executor.getCount() == 1) {
					executor.setPostsinceid(ids.getLong(0));
					return;
				}

				// ID列表
				List<RepostEntity> list = new ArrayList<RepostEntity>();
				for (int i = 0; i < ids.length(); i++) {
					RepostEntity re = new RepostEntity();
					re.setId(ids.getLong(i));
					list.add(re);
				}
				executor.setExt(list);

			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

		@Override
		public void onError(Object data) {

		}
	}
}
