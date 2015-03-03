/**
 * 
 */
package com.webwalker.adpater;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.ListAdapter;
import android.widget.ListView;

/**
 * @author Administrator
 * 
 */
public final class ViewHolder {

	public class Keys {
		public static final String itemId = "itemId";
		public static final String itemAppId = "itemAppId";
		public static final String itemTitle = "itemTitle";
		public static final String itemText = "itemText";
		public static final String itemImage = "itemImage";
		public static final String itemStatus = "itemStatus";
		public static final String itemExpires = "itemExpires";
		public static final String itemCount = "itemCount";
		public static final String itemParentId = "itemParentId";
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
		// params.height = totalHeight + (51 * (listAdapter.getCount() - 1));
		((MarginLayoutParams) params).setMargins(10, 10, 10, 10);
		listView.setLayoutParams(params);
	}

	public static void setListViewHeightBasedOnFixed(ListView listView,
			int height) {
		ViewGroup.LayoutParams params = listView.getLayoutParams();
		params.height = listView.getCount() * height;
		((MarginLayoutParams) params).setMargins(10, 10, 10, 10);
		listView.setLayoutParams(params);
	}
}
