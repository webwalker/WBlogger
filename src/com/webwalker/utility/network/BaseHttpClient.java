/**
 * 
 */
package com.webwalker.utility.network;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.params.HttpClientParams;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.conn.ssl.X509HostnameVerifier;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.util.EntityUtils;

import android.os.Bundle;
import android.util.Log;

/**
 * Remove singleton, it will corrupt when it is multiple network request.
 * 
 * @author Tiger Yang
 * @modifier Albert Ma
 * 
 */
public class BaseHttpClient {
	private static final String TAG = "SimpleHttpClient";
	protected HttpClient client;

	private ResponseHandler<String> responseHandler = new StringResponseHandler();

	public BaseHttpClient() {
		this(null);
	}

	public BaseHttpClient(String paramString) {
		Log.i(TAG, "Init HttpClient...");
		try {
			BasicHttpParams httpParams = new BasicHttpParams();
			HttpConnectionParams.setStaleCheckingEnabled(httpParams, true);
			HttpConnectionParams.setConnectionTimeout(httpParams, 25000);
			HttpConnectionParams.setSoTimeout(httpParams, 15000);
			HttpConnectionParams.setSocketBufferSize(httpParams, 16384);
			HttpClientParams.setRedirecting(httpParams, false);
			HttpClientParams.setAuthenticating(httpParams, false);
			if (paramString != null)
				HttpProtocolParams.setUserAgent(httpParams, paramString);

			SchemeRegistry schemeRegistry = new SchemeRegistry();
			PlainSocketFactory socketFactory = PlainSocketFactory
					.getSocketFactory();

			Scheme httpScheme = new Scheme("http", socketFactory, 80);
			schemeRegistry.register(httpScheme);

			KeyStore trustStore = KeyStore.getInstance(KeyStore
					.getDefaultType());
			trustStore.load(null, null);

			// SSLSocketFactory sslSocketFactory =
			// SSLSocketFactory.getSocketFactory();
			SSLSocketFactory sslSocketFactory = new MySSLSocketFactory(
					trustStore);

			X509HostnameVerifier x509HostnameVerifier = SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER;

			sslSocketFactory.setHostnameVerifier(x509HostnameVerifier);
			HttpsURLConnection.setDefaultHostnameVerifier(x509HostnameVerifier);

			Scheme httpsScheme = new Scheme("https", sslSocketFactory, 443);
			schemeRegistry.register(httpsScheme);

			ClientConnectionManager connManager = new ThreadSafeClientConnManager(
					httpParams, schemeRegistry);

			client = new DefaultHttpClient(connManager, httpParams);
		} catch (Exception e) {
			Log.i(TAG, "Init HttpClient...");
		}
	}

	@SuppressWarnings("unchecked")
	public <T> T execute(HttpUriRequest request,
			ResponseHandler<T> responseHandler) throws ClientProtocolException,
			IOException {
		Log.i(TAG, "HttpClient execute request...");
		if (responseHandler == null)
			return (T) client.execute(request);
		return client.execute(request, responseHandler);
	}

	@SuppressWarnings("unchecked")
	public <T> T sendGetMessage(String uri) throws ClientProtocolException,
			IOException {
		Log.i(TAG, "sendGetMessage, uri: " + uri);

		HttpGet httpGet = new HttpGet(uri);
		return (T) execute(httpGet, getResponseHandler());
	}

	@SuppressWarnings("unchecked")
	public <T> T sendGetMessage(String uri, Bundle paramsMap)
			throws ClientProtocolException, IOException {
		Log.i(TAG, "sendGetMessage, uri: " + uri + " paramsMap: " + paramsMap);

		StringBuilder sb = new StringBuilder(uri);
		if (paramsMap != null && paramsMap.size() != 0) {
			if (!uri.contains("?")) {
				sb.append("?");
			} else {
				sb.append("&");
			}
			for (String key : paramsMap.keySet()) {
				sb.append(key + "=" + paramsMap.get(key) + "&");
			}
		}
		String url = sb.substring(0, sb.length() - 1);
		HttpGet httpGet = new HttpGet(url);
		HttpParams params = new BasicHttpParams();
		httpGet.setParams(params);
		return (T) execute(httpGet, getResponseHandler());
	}

	@SuppressWarnings("unchecked")
	public <T> T sendPostMessage(String uri, Bundle params)
			throws ClientProtocolException, IOException {
		Log.i(TAG, "sendPostMessage, uri: " + uri + " params: " + params);

		HttpPost httpPost = new HttpPost(uri);
		if (params != null && !params.isEmpty()) {
			UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(
					getParamsList(params), "utf-8");
			httpPost.setEntity(formEntity);
		}
		return (T) execute(httpPost, getResponseHandler());
	}

	@SuppressWarnings("unchecked")
	public <T> T sendPostMessage(String uri, String params)
			throws ClientProtocolException, IOException {
		Log.i(TAG, "sendPostMessage, uri: " + uri + " paramsMap: " + params);

		HttpPost httpPost = new HttpPost(uri);
		if (params != null) {
			UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(
					getParamsList(params));
			httpPost.setEntity(formEntity);
		}
		return (T) execute(httpPost, getResponseHandler());
	}

	private List<NameValuePair> getParamsList(String params) {
		String[] paramArray = params.split("&");

		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		for (String param : paramArray) {
			String[] keyValue = param.split("=");

			nameValuePairs
					.add(new BasicNameValuePair(keyValue[0], keyValue[1]));
		}

		return nameValuePairs;
	}

	private static List<NameValuePair> getParamsList(Bundle bundle) {
		if (bundle == null || bundle.size() == 0) {
			return null;
		}
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		for (String key : bundle.keySet()) {
			params.add(new BasicNameValuePair(key, bundle.getString(key)));
		}
		return params;
	}

	@SuppressWarnings("rawtypes")
	public ResponseHandler getResponseHandler() {
		return responseHandler;
	}

	public void shutdown() {
		if (client != null) {
			client.getConnectionManager().shutdown();
		}
	}

	class StringResponseHandler implements ResponseHandler<String> {
		public String handleResponse(HttpResponse response)
				throws ClientProtocolException, IOException {
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				return new String(EntityUtils.toByteArray(entity));
			}
			return null;
		}
	}
}
