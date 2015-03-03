/**
 * 
 */
package com.webwalker.entity;

import com.webwalker.api.sina.WeiboAPI.COMMENTS_TYPE;
import com.weibo.sdk.android.net.RequestListener;

/**
 * @author Administrator
 * 
 */
@SuppressWarnings("serial")
public class RepostEntity extends RequestBody {
	private long id;
	private String status;
	private COMMENTS_TYPE is_comment;
	private RequestListener listener;

	/**
	 * @return the listener
	 */
	public RequestListener getListener() {
		return listener;
	}

	/**
	 * @param listener the listener to set
	 */
	public void setListener(RequestListener listener) {
		this.listener = listener;
	}

	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the is_comment
	 */
	public COMMENTS_TYPE getIs_comment() {
		return is_comment;
	}

	/**
	 * @param is_comment
	 *            the is_comment to set
	 */
	public void setIs_comment(COMMENTS_TYPE is_comment) {
		this.is_comment = is_comment;
	}

}
