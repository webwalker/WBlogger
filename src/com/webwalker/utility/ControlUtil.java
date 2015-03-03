/**
 * 
 */
package com.webwalker.utility;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

/**
 * @author Administrator
 * 
 */
public class ControlUtil extends Activity {

	private static ControlUtil _Instance = null;

	public synchronized static ControlUtil getInstance() {
		if (_Instance == null) {
			_Instance = new ControlUtil();
		}
		return _Instance;
	}

	public void ResetEditText(ViewGroup view) {
		// 全局遍历 需要为每个控件设定ID
		for (int i = 0; i < view.getChildCount(); i++) {
			View v1 = view.getChildAt(i);
			if (v1 instanceof EditText) {
				EditText e = (EditText) findViewById(v1.getId());
				e.setText("");
			}
		}
	}
}
