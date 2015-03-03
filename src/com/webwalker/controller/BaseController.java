/**
 * 
 */
package com.webwalker.controller;

import android.content.Context;

import com.webwalker.listener.IActionListener;

/**
 * @author Administrator
 * 
 */
public abstract class BaseController {
	Context context;
	IActionListener listener;

	public BaseController() {
	}

	public BaseController(Context context) {
		this.context = context;
	}

	public BaseController(Context context, IActionListener listener) {
		this.context = context;
		this.listener = listener;
	}
}
