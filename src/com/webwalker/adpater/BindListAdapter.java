/**
 * 
 */
package com.webwalker.adpater;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.webwalker.adpater.ViewHolder.Keys;
import com.webwalker.wblogger.R;

/**
 * @author Administrator
 * 
 */
public class BindListAdapter extends BaseAdapter {
	Context context;
	LayoutInflater inflater;
	View[] itemViews;

	public BindListAdapter(Context context,
			ArrayList<LinkedHashMap<String, String>> list) {

		this.context = context;
		this.itemViews = new View[list.size()];
		this.inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		LinkedHashMap<String, String> map = null;
		for (int i = 0; i < itemViews.length; i++) {
			map = list.get(i);
			itemViews[i] = makeItemView(map.get(Keys.itemId),
					map.get(Keys.itemAppId), map.get(Keys.itemTitle),
					map.get(Keys.itemText), map.get(Keys.itemStatus),
					map.get(Keys.itemCount),
					Integer.valueOf(map.get(Keys.itemImage)));
		}
	}

	public int getCount() {
		return itemViews.length;
	}

	public View getItem(int position) {
		return itemViews[position];
	}

	public long getItemId(int position) {
		return position;
	}

	private View makeItemView(String strId, String strAppId, String strTitle,
			String strText, String strStatus, String strCount, int resId) {
		View itemView = inflater.inflate(R.layout.parent_item_list, null);

		TextView id = (TextView) itemView.findViewById(R.id.itemId);
		id.setText(strId);
		TextView appId = (TextView) itemView.findViewById(R.id.itemAppId);
		appId.setText(strAppId);
		TextView title = (TextView) itemView.findViewById(R.id.itemTitle);
		title.setText(strTitle);
		TextView text = (TextView) itemView.findViewById(R.id.itemText);
		text.setText(strText);
		TextView status = (TextView) itemView.findViewById(R.id.itemStatus);
		status.setText(strStatus);
		TextView count = (TextView) itemView.findViewById(R.id.itemSubCount);
		count.setText(strCount);
		ImageView image = (ImageView) itemView.findViewById(R.id.itemImage);
		image.setImageResource(resId);

		return itemView;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null)
			return itemViews[position];
		return convertView;
	}
}
