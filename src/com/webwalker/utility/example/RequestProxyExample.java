package com.webwalker.utility.example;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.webwalker.utility.network.BaseResult;
import com.webwalker.utility.network.NetworkConstants;
import com.webwalker.utility.network.RequestNetworkProxy;
import com.webwalker.utility.network.ResultStatusEnum;

public class RequestProxyExample {
	private Handler networkhandler;
	Context context;

	public RequestProxyExample(Context context) {
		context = context;
	}

	public void Post() {
		RequestNetworkProxy proxy = new RequestNetworkProxy();
		Bundle requestParam = new Bundle();
		requestParam.putString("a", "1");
		proxy.requestPostMessage("http://xxxxx", 1, requestParam, null,
				getDefaultNetworkHandler());

		BaseResult baseResult = null;
		proxy.requestPostMessage("http://xxxxx", 1, requestParam, baseResult,
				getDefaultNetworkHandler());
	}

	public Handler getDefaultNetworkHandler() {
		if (networkhandler == null)
			networkhandler = new Handler() {
				public void handleMessage(Message msg) {
					// 如果当前activity已销毁，不处理网络响应
					// if (isFinishing()) {
					// return;
					// }

					switch (msg.what) {
					case NetworkConstants.STATUS_NETWORK_OK:
						BaseResult baseResult = null;
						if (msg.getData().containsKey(NetworkConstants.KEY_VO)) {
							baseResult = (BaseResult) msg.getData()
									.getSerializable(NetworkConstants.KEY_VO);
						}
						int flag = msg.getData().getInt(
								NetworkConstants.KEY_REQUEST_FLAG);
						//调用子类重写的此方法，完成更新
						updateUi(
								baseResult,
								flag,
								msg.getData().getString(
										NetworkConstants.KEY_JSON_RESULT));
						break;
					case NetworkConstants.STATUS_NETWORK_TOKEN_TIME_OUT:
						// loginTokenIdTimeOut();
						break;
					case NetworkConstants.STATUS_NETWORK_UNAVAILABLE:
						// hideProgressBar();
						// networkUnavailable();
						break;
					case NetworkConstants.STATUS_NETWORK_FAIL:
						businessError((String) msg.obj);
						break;
					case NetworkConstants.STATUS_NETWORK_TIMEOUT:
						// hideProgressBar();
						// networkTimeout();
						break;
					}
				}
			};
		return networkhandler;
	}

	protected void updateUi(BaseResult baseVO, int flag, String jsonString) {
		updateUi(baseVO, jsonString);
	}

	/**
	 * if you need to update you ui, override it.
	 * 
	 * @param baseVO
	 */
	protected void updateUi(BaseResult baseVO, String jsonString) {

	}

	protected void businessError(String jsonString) {
		String resultMsg = "";
		// 有些服务接口返回错误信息result的值不是对象，是一个字符串～～～
		try {
			JSONObject jsonObject = new JSONObject(jsonString);
			if (jsonObject.has(ResultStatusEnum.RESULT_CODE.getCode())) {
				String errCode = jsonObject
						.getString(ResultStatusEnum.RESULT_CODE.getCode());
				if ("401".equals(errCode)) {
					new AlertDialog.Builder(context)
							.setIcon(android.R.drawable.ic_dialog_alert)
							.setTitle("确认")
							.setPositiveButton("确认",
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog,
												int whichButton) {

										}
									}).show();
					return;
				}
			}
			if (jsonObject.has(ResultStatusEnum.RESULT_MESSAGE_NEW.getCode())) {
				resultMsg = jsonObject
						.getString(ResultStatusEnum.RESULT_MESSAGE_NEW
								.getCode());
			}
			if (jsonObject.has(ResultStatusEnum.RESULT_MESSAGE.getCode())) {
				resultMsg = jsonObject
						.getString(ResultStatusEnum.RESULT_MESSAGE.getCode());
			}
		} catch (JSONException e) {
			Log.e("BaseSpmActivity.businessError", e.getMessage());
			resultMsg = jsonString;
		}
		// this.showAlertDialog("温馨提示", null, Html.fromHtml(resultMsg));
	}
}
