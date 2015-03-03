/**
 * 
 */
package com.webwalker.rules;

/**
 * 规则解析，考虑到移动APP系统资源&性能，不采用 反射机制
 * 
 * @author Administrator
 * 
 */
public class RulesParser<T> implements IParser<T> {

	private Object data = null;

	public RulesParser(Object data) {
		this.data = data;
	}

	private RulesMetaData meta;

	@Override
	public T Parse(int id) {
		return null;
	}

	public Object Invoke() {
		return null;
	}

	private Object Sina() {
		return null;
	}

	/**
	 * @param metadata
	 *            the metadata to set
	 */
	public void setMetadata(RulesMetaData metadata) {
		this.meta = metadata;
	}
}
