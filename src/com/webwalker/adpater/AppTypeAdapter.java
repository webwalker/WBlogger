/**
 * 
 */
package com.webwalker.adpater;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

/**
 * @author Administrator
 * 
 */
public class AppTypeAdapter<T> extends ArrayAdapter<T> {
	private LayoutInflater mInflater;
	private ViewHolder holder;
	private View rootView = null;
	private SpinnerHandler handler;
	private List<T> list;
	private Spinner spinner;

	public AppTypeAdapter(Context context, int textViewResourceId, Spinner sp,
			List<T> objects) {
		super(context, textViewResourceId, objects);

		this.spinner = sp;
		this.list = objects;
		this.handler = new SpinnerHandler();
		this.mInflater = (LayoutInflater) ((Activity) context)
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		// 在下拉列表中不要显示“请选择:”这一项，所以要将总数减1
		return list.size() - 1;
	}

	@Override
	public View getDropDownView(int position, View convertView, ViewGroup parent) {

		position++;
		return super.getDropDownView(position, convertView, parent);
	}

	// 获取控件view
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		int nPos = 0;

		int nIndex = spinner.getSelectedItemPosition();
		// 如果没有选中，返回“请选择：”，否则返回选中项
		if (nIndex == -1) {
			nPos = 0;
		} else {
			nPos = position + 1;
		}

		return super.getView(nPos, convertView, parent);
	}

	private class ViewHolder {
		public int index;
		public TextView txt;
		// public RadioButton radio;
	}

	private class SpinnerHandler extends Handler {

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				if (null != rootView) {
					rootView.setVisibility(View.GONE);
				}
				break;
			default:
				break;
			}
		}
	}
}