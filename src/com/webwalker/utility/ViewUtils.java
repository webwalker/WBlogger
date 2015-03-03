package com.webwalker.utility;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.SimpleAdapter.ViewBinder;
import android.widget.SpinnerAdapter;

public class ViewUtils {
	private ViewUtils() {
	}

	public static void setVisibility(View view, int visibility) {
		view.setVisibility(visibility);
		if (view instanceof ViewGroup) {
			ViewGroup vg = (ViewGroup) view;
			for (int i = 0; i < vg.getChildCount(); i++) {
				setVisibility(vg.getChildAt(i), visibility);
			}
		}
	}

	public static void setListViewHeightBasedOnChildren(ListView listView) {
		ListAdapter listAdapter = listView.getAdapter();
		if (listAdapter == null) {
			return;
		}
		int totalHeight = 0;
		for (int i = 0; i < listAdapter.getCount(); i++) {
			View listItem = listAdapter.getView(i, null, listView);
			listItem.measure(0, 0);
			totalHeight += listItem.getMeasuredHeight();
		}
		ViewGroup.LayoutParams params = listView.getLayoutParams();
		params.height = totalHeight
				+ (listView.getDividerHeight() * (listAdapter.getCount() - 1));
		listView.setLayoutParams(params);
	}

	public static final ViewBinder checkboxViewBinder = new ViewBinder() {

		@Override
		public boolean setViewValue(View view, Object data,
				String textRepresentation) {
			if (view instanceof CheckedTextView) {
				CheckedTextView ctv = (CheckedTextView) view;
				ctv.setText(textRepresentation);
				return true;
			} else {
				return false;
			}
		}
	};

	public static SpinnerAdapter createSimpleSpinnerAdapter(Context belongTo,
			List<Map<String, ?>> list, String labelKey) {
		if (list == null) {
			list = new ArrayList<Map<String, ?>>();
		}
		SimpleAdapter sa = new SimpleAdapter(belongTo, list,
				android.R.layout.simple_spinner_item,
				new String[] { labelKey }, new int[] { android.R.id.text1 });
		sa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		sa.setViewBinder(ViewUtils.checkboxViewBinder);
		return sa;
	}

	/**
	 * 当当前字符达到多少时,自动跳转到下一个View
	 * 
	 * @param next
	 * @param length
	 * @return
	 */
	public static TextWatcher getTextWatcher(final View next, final int length) {
		TextWatcher watcher = new TextWatcher() {
			@Override
			public void afterTextChanged(Editable s) {
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				if (s != null && s.toString().length() == length) {
					next.setFocusable(true);
					next.setFocusableInTouchMode(true);
					next.requestFocus();
				}
			}
		};
		return watcher;
	}
}
