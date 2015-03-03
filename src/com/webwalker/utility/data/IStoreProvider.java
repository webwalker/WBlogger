/**
 * 
 */
package com.webwalker.utility.data;

/**
 * 处理几类数据存储的基类
 * 
 * @author Administrator
 * 
 */
public interface IStoreProvider {

	String Get(String key);

	void Set(String key, String value);

	void Remove(String key);

	void Clear();
}
