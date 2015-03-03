/**
 * 
 */
package com.webwalker.utility;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.content.Context;

/**
 * @author Administrator
 * 
 */
public class HttpUtil {
	Context context;

	public HttpUtil(Context context) {
		this.context = context;
	}

	/**
	 * 获取指定URL内容
	 * 
	 * @param url
	 * @return
	 */
	public static String getContent(String url) {
		HttpClient client = new DefaultHttpClient();
		HttpGet get = new HttpGet(url);
		HttpResponse response = null;
		BufferedReader bis = null;
		StringBuilder sb = new StringBuilder();

		try {
			response = client.execute(get);
			if (response.getStatusLine().getStatusCode() != 200) {
				throw new IOException("StatusCode!=200");
			}
			HttpEntity entity = response.getEntity();
			bis = new BufferedReader(new InputStreamReader(entity.getContent(),
					"gb2312"));
			String s = null;
			if (bis != null) {
				s = bis.readLine();
				while (s != null) {
					sb.append("\n" + s);
					s = bis.readLine();
				}
				bis.close();
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();

		} catch (IOException e) {
			e.printStackTrace();
		}

		return sb.toString();
	}
}
