/**
 * 
 */
package com.webwalker.rules;

/**
 * @author Administrator
 * 
 */
public class RulesMetaData {
	public enum Method {
		Post, RePost,
	}

	private int appId;
	private int taskId;
	private Method method;
	private String token;
	private String expiresin;
	private Object[] parameter;
	
}
