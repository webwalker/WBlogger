/**
 *
 */
package com.webwalker.utility.network;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.webwalker.utility.BeanUtil;
import com.webwalker.utility.network.AbstractNetworkProxy.RequestProxyCallBack;

/**
 * RequestNetworkProxy is a simple networking proxy for simple use. Any complicated use, please extend {@code AbstractNetworkProxy}}
 * @author mabangya.albert
 */
public class RequestNetworkProxy extends AbstractNetworkProxy implements RequestProxyCallBack {
    protected Handler    handler;
    protected String     url;
    protected BaseResult baseResult;
    protected Bundle     bundle;
    protected int        flag;

    /**
     * Request post message
     * @param url  url of service
     * @param requestParam reqeust parameters
     * @param baseResult as a input/out parameters.
     * @param handler handle network callback.
     */
    public void requestPostMessage(String url, Bundle requestParam, BaseResult baseResult, Handler handler) {
        if (url != null) {
            this.handler = handler;
            this.url = url;
            this.baseResult = baseResult;
            this.bundle = requestParam;
            this.sendPostRequest(this);
        }
    }

    /**
     * 发送url请求，使用缓存机制
     * zzf 20120322
     * @param context
     * @param url
     * @param requestParam
     * @param baseResult
     * @param handler
     */
    public void requestPostMessage(Context context, String url, Bundle requestParam, BaseResult baseResult,
                                   Handler handler) {
        if (url != null) {
            this.handler = handler;
            this.url = url;
            this.baseResult = baseResult;
            this.bundle = requestParam;
            //使用缓存机制
            this.context = context;
            this.sendPostRequest(this);
        }
    }

    /**
     * Request post message
     * @param url  url of service
     * @param requestParam reqeust parameters
     * @param baseResult as a input/out parameters.
     * @param handler handle network callback.
     * @param flag flag to handle different proxy
     */
    public void requestPostMessage(String url, int flag, Bundle requestParam, BaseResult baseResult, Handler handler) {
        if (url != null) {
            this.handler = handler;
            this.url = url;
            this.baseResult = baseResult;
            this.bundle = requestParam;
            this.flag = flag;
            this.sendPostRequest(this);
        }
    }

    /**
     * Request get message
     * @param url  url of service
     * @param requestParam reqeust parameters
     * @param baseResult as a input/out parameters.
     * @param handler handle network callback.
     */
    public void requestGetMessage(String url, Bundle requestParam, BaseResult baseResult, Handler handler) {
        if (url != null) {
            this.handler = handler;
            this.url = url;
            this.baseResult = baseResult;
            this.bundle = requestParam;
            this.sendGetRequest(this);
        }
    }

    /**
     * Request get message
     * @param url  url of service
     * @param requestParam reqeust parameters
     * @param baseResult as a input/out parameters.
     * @param handler handle network callback.
     * @param flag lag to handle different proxy
     */
    public void requestGetMessage(String url, int flag, Bundle requestParam, BaseResult baseResult, Handler handler) {
        if (url != null) {
            this.handler = handler;
            this.url = url;
            this.baseResult = baseResult;
            this.bundle = requestParam;
            this.flag = flag;
            this.sendGetRequest(this);
        }
    }

    @Override
    protected Bundle getParameters() {
        return bundle;
    }

    @Override
    protected String getUrl() {
        return this.url;
    }

    /**
     * It is run on ui thread.
     * (non-Javadoc)
     * @see com.sdp.spm.network.AbstractNetworkProxy.RequestProxyCallBack#networkFinished(org.json.JSONObject, com.sdp.spm.network.AbstractNetworkProxy)
     */
    @Override
    public void networkFinished(JSONObject jsonObject, AbstractNetworkProxy proxy) {
        if (handler == null) {
            return;
        }
        Message message = null;
        if (jsonObject != null) {
            try {
                String errCode = jsonObject.getString(NetworkConstants.KEY_RESPONSE_STATUS);
                String errMsg = jsonObject.getString(NetworkConstants.KEY_RESPONSE_RESULT);
                if (NetworkConstants.VALUE_OK.equals(errCode)) {
                    message = handler.obtainMessage(NetworkConstants.STATUS_NETWORK_OK, null);
                    Bundle bundle = new Bundle();
                    if (baseResult != null) {
                        initBeans(jsonObject, baseResult);
                        bundle.putSerializable(NetworkConstants.KEY_VO, baseResult);
                    }
                    bundle.putString(NetworkConstants.KEY_JSON_RESULT, jsonObject.toString());
                    bundle.putInt(NetworkConstants.KEY_REQUEST_FLAG, flag);
                    message.setData(bundle);
                } else if (NetworkConstants.VALUE_LOGIN_TIMEOUT.equals(errCode)) {
                    message = handler.obtainMessage(NetworkConstants.STATUS_NETWORK_TOKEN_TIME_OUT);
                } else if (NetworkConstants.CONTENT_TIME_OUT.equals(errCode)) {
                    message = handler.obtainMessage(NetworkConstants.STATUS_NETWORK_TIMEOUT);
                } else {
                    message = handler.obtainMessage(NetworkConstants.STATUS_NETWORK_FAIL, errMsg);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            message = handler.obtainMessage(NetworkConstants.STATUS_NETWORK_UNAVAILABLE, null);

        }
        handler.sendMessage(message);

    }

    /**
     * 获取返回result中的Json对象,并转换为Java对象
     * @param src
     * @param target
     * @throws JSONException
     */
    private void initBeans(JSONObject src, Object target) throws JSONException {
        //TODO:服务端返回的result有些不是对象（如boolean值），这边先不统一处理
        JSONObject jsonObject = null;
        try {
            jsonObject = src.getJSONObject(NetworkConstants.KEY_RESPONSE_RESULT);
        } catch (Exception e) {
            Log.e("RequestNetworkProxy.initBeans", e.getMessage());
            return;
        }
        BeanUtil.initBeans(jsonObject, baseResult);
    }

}
