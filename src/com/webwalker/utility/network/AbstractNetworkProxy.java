/**
 * 
 */
package com.webwalker.utility.network;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.http.auth.AuthenticationException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.conn.ConnectTimeoutException;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

/**
 * @author mabangya.albert
 * 
 */
public abstract class AbstractNetworkProxy {
	protected static final int TYPE_REQUEST_POST = 0;
	protected static final int TYPE_REQUEST_GET = 1;
	protected RequestProxyCallBack requestProxyCallBack;
	private boolean isRequesting;
	private RequestTask requestTask;

	// 使用缓存机制,需要传入上下文
	protected Context context;

	/**
	 * Send post request.
	 * 
	 * @param requestProxyCallBack
	 * @throws IOException
	 * @throws CertificateException
	 * @throws KeyStoreException
	 * @throws NoSuchAlgorithmException
	 * @throws UnrecoverableKeyException
	 * @throws KeyManagementException
	 */
	protected final void sendPostRequest(
			RequestProxyCallBack requestProxyCallBack) {
		if (isRequesting) {
			cancelRequest();
		}
		isRequesting = true;
		this.requestProxyCallBack = requestProxyCallBack;
		Bundle paramsObjects = this.getParameters();
		requestTask = new RequestTask(TYPE_REQUEST_POST);
		Log.i(AbstractNetworkProxy.class.getCanonicalName(), "Send Post Data:"
				+ paramsObjects.toString());
		requestTask.execute(paramsObjects);
	}

	/**
	 * Send get request.
	 * 
	 * @param requestProxyCallBack
	 */
	public final void sendGetRequest(RequestProxyCallBack requestProxyCallBack) {
		if (isRequesting) {
			cancelRequest();
		}
		isRequesting = true;
		this.requestProxyCallBack = requestProxyCallBack;
		Bundle paramsObjects = this.getParameters();
		requestTask = new RequestTask(TYPE_REQUEST_GET);
		Log.i(AbstractNetworkProxy.class.getCanonicalName(), "Send Get Data:"
				+ paramsObjects);
		requestTask.execute(paramsObjects);
	}

	/**
	 * Cancel network request. And disable the callback.
	 */
	public void cancelRequest() {
		// disable callback firstly.
		requestProxyCallBack = null;
		if (requestTask != null && !requestTask.isCancelled()) {
			requestTask.cancel(true);
		}
		requestTask = null;

	}

	/**
	 * Get network parameters
	 * 
	 * @return
	 */
	abstract protected Bundle getParameters();

	/**
	 * Get Url
	 * 
	 * @return
	 */
	abstract protected String getUrl();

	class RequestTask extends AsyncTask<Bundle, Void, JSONObject> {
		JsonHttpClient jsonHttpClient;
		int requestType;

		public RequestTask(int type) {
			requestType = type;
			jsonHttpClient = new JsonHttpClient();
		}

		@Override
		protected JSONObject doInBackground(Bundle... bundles) {
			jsonHttpClient = new JsonHttpClient();
			JSONObject response = null;
			try {
				// if (context != null)
				// response = CacheUtil.getCacheData(context, requestType,
				// getUrl(), bundles[0]);

				if (requestType == TYPE_REQUEST_POST) {
					response = jsonHttpClient.sendPostMessage(getUrl(),
							bundles[0]);
				} else if (requestType == TYPE_REQUEST_GET) {
					response = jsonHttpClient.sendGetMessage(getUrl(),
							bundles[0]);
				}

			} catch (ConnectTimeoutException e) {
				response = new JSONObject();
				try {
					response.put(NetworkConstants.KEY_RESPONSE_STATUS,
							NetworkConstants.CONTENT_TIME_OUT);
					response.put(NetworkConstants.KEY_RESPONSE_RESULT, "");
				} catch (JSONException e1) {
					e1.printStackTrace();
				}
			} catch (ClientProtocolException e) {
				if (ExceptionUtils.indexOfThrowable(e,
						AuthenticationException.class) > 0) {
					response = new JSONObject();

					// TODO manrong will do the refactor via use json to notify
					// client.
					try {
						response.put(NetworkConstants.KEY_RESPONSE_STATUS,
								NetworkConstants.VALUE_LOGIN_TIMEOUT);
						response.put(NetworkConstants.KEY_RESPONSE_RESULT, "");
					} catch (JSONException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				} else if (e.getMessage() != null) {
					Log.i("ClientProtocolException", e.getMessage());
				}
				// e.printStackTrace();
			} catch (IOException e) {
				if (e.getMessage() != null) {
					Log.i("IOExceptionException", e.getMessage());
				}
				e.printStackTrace();
			}
			if (response != null) {
				Log.i("AbstractRequestProxy", response.toString());

			} else {
				Log.i("AbstractRequestProxy", "Service not avaliable.");
			}
			return response;
		}

		@Override
		protected void onPostExecute(JSONObject result) {
			requestProxyCallBack.networkFinished(result,
					AbstractNetworkProxy.this);
		}

	}

	/**
	 * Callback interface when network is finished.
	 * 
	 * @author mabangya.albert
	 * 
	 */
	public interface RequestProxyCallBack {
		/**
		 * Callback interface for network finished.
		 * 
		 * @param jsonObject
		 *            , if jsonObject is null, network is unavailable.
		 * @param proxy
		 *            , current proxy, for user to check which proxy come back.
		 */
		public void networkFinished(JSONObject jsonObject,
				AbstractNetworkProxy proxy);
	}
}
