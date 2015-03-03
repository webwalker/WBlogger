/**
 * 
 */
package com.webwalker.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Spinner;

/**
 * @author Administrator
 * 
 */

public class MySpinner extends Spinner {
	// 定义一个选中位置默认选中-1
	private int currSelect = -1;

	public MySpinner(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	// 选中后currSelect设置为当前选中位置
	@Override
	public void setSelection(int position) {
		currSelect = position;
		super.setSelection(position);
	}

	@Override
	public void setSelection(int position, boolean animate) {
		currSelect = position;
		super.setSelection(position, animate);
	}

	// 把默认的返回的position改为自定义的postion
	@Override
	public int getSelectedItemPosition() {
		return currSelect;
	}
}