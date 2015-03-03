/**
 * 
 */
package com.webwalker.utils;

import java.util.Hashtable;

/**
 * @author Administrator
 * 
 */
public class MyContext {
	private static Hashtable<String, Object> contextInfo;

	static {
		contextInfo = new Hashtable<String, Object>();
	}

	public static Object get(String key) {
		if (key != null) {
			return contextInfo.get(key);
		} else {
			new IllegalArgumentException(
					"null pointer value for key via get(String key)");
		}
		return null;
	}

	public synchronized static void Add(String key, Object value) {
		if (key != null && value != null) {
			contextInfo.put(key, value);
		} else {
			new IllegalArgumentException(
					"null pointer value for key or value via Add(String key, Object value)");
		}
	}

	public synchronized static Object Remove(String key) {
		return contextInfo.remove(key);
	}

}
