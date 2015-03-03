/**
 * 
 */
package com.webwalker.api;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 动态代理、委托
 * 
 * @author Administrator
 * 
 */
public class MyInvocationHandler implements InvocationHandler {

	// public static List<Class> list;

	private Object obj = null;

	public Object bind(Object obj) {
		this.obj = obj;
		return Proxy.newProxyInstance(obj.getClass().getClassLoader(), obj
				.getClass().getInterfaces(), this);
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		Object temp = method.invoke(this.obj, args);
		return temp;
	}

	public Object invoke(Class<?> classType, String method, Class[] classes,
			Object... data) {

		try {
			Object invokeOperation = classType.newInstance();
			Method addMethod = classType.getMethod(method, classes);
			return addMethod.invoke(invokeOperation, data);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
