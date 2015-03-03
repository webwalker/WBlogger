/**
 * 
 */
package com.webwalker.entity;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 规则解析后生成的可以用以微博操作的直接请求信息
 * 
 * @author Administrator
 * 
 */
@SuppressWarnings("serial")
public class RequestBody implements Serializable {
	public int id;
	public String uaid;
	public Object ext;

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.SHORT_PREFIX_STYLE);
	}
}
