package com.webwalker.api.sina;

import com.weibo.sdk.android.Oauth2AccessToken;
import com.weibo.sdk.android.WeiboParameters;
import com.weibo.sdk.android.api.WeiboAPI;
import com.weibo.sdk.android.net.RequestListener;

/**
 * 获取微博的扩展信息
 * 
 * @author Administrator
 * 
 */
public class MyAPI extends WeiboAPI {

	private static final String SERVER_URL_PRIX = API_SERVER + "/oauth2";

	public MyAPI(Oauth2AccessToken oauth2AccessToken) {
		super(oauth2AccessToken);
	}

	// 获取Token信息
	public void getTokenInfo(RequestListener listener) {
		WeiboParameters params = new WeiboParameters();
		request(SERVER_URL_PRIX + "/get_token_info.json", params,
				HTTPMETHOD_GET, listener);
	}
}
