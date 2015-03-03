package com.webwalker.utility.network;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;
import android.util.Log;

public class JsonHttpClient extends BaseHttpClient {
	public static final String TAG = "JsonHttpClient";

	private ResponseHandler<JSONObject> jsonResponseHandler = new JsonResponseHandler();

	private static JsonHttpClient jsonHttpClient = null;

	public JsonHttpClient() {
		super();
	}

	public static JsonHttpClient INSTANCE() {
		if (jsonHttpClient == null) {
			try {
				jsonHttpClient = new JsonHttpClient();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return jsonHttpClient;
	}

	@SuppressWarnings(value = {})
	@Override
	public ResponseHandler<JSONObject> getResponseHandler() {
		return jsonResponseHandler;
	}

	private class JsonResponseHandler implements ResponseHandler<JSONObject> {
		public JSONObject handleResponse(HttpResponse response)
				throws ClientProtocolException, IOException {
			StatusLine statusLine = response.getStatusLine();
			if (statusLine != null) {
				int status = statusLine.getStatusCode();
				if (status == 401 || status == 801) {
					return getByResponseStatus(status);
				}
			}
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				String str = new String(EntityUtils.toByteArray(entity));
				Log.i(TAG, "response message: " + str);
				try {
					JSONObject jSONObject = new JSONObject(str);
					return jSONObject;
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
			return null;
		}
	};

	private JSONObject getByResponseStatus(int statusCode) {
		String message = "";
		switch (statusCode) {
		case 401:
			message = "长时间未操作，请重新登录！";
			break;
		case 801:
			message = "获取账号信息出错，请联系客服:4007208888！";
			break;
		}
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put(ResultStatusEnum.STATUS.getCode(),
					ResultStatusEnum.ERROR.getCode());
			JSONObject jsonResult = new JSONObject();
			jsonResult.put(ResultStatusEnum.RESULT_CODE.getCode(), statusCode);
			jsonResult.put(ResultStatusEnum.RESULT_MESSAGE.getCode(), message);
			jsonObject.put(ResultStatusEnum.RESULT.getCode(), jsonResult);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return jsonObject;
	}
}
