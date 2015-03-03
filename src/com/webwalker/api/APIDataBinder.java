/**
 * 
 */
package com.webwalker.api;

/**
 * @author Administrator
 * 
 */
public class APIDataBinder {

	private int appId;
	private int taskId;

	public APIDataBinder(int appId, int taskId) {
		this.appId = appId;
		this.taskId = taskId;
	}

	private Object data;

	/**
	 * @return the data
	 */
	public Object getData() {
		return data;
	}

	/**
	 * @param data
	 *            the data to set
	 */
	public void setData(Object data) {
		this.data = data;
	}
}
