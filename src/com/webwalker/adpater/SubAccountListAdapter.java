/**
 * 
 */
package com.webwalker.adpater;

import java.util.ArrayList;
import java.util.Date;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.webwalker.entity.UserAccountEntity;
import com.webwalker.utility.StringUtil;
import com.webwalker.wblogger.R;

/**
 * @author Administrator
 * 
 */
public class SubAccountListAdapter extends BaseAdapter {
	Context context;
	LayoutInflater inflater;
	View[] itemViews;
	ArrayList<String> mappingList;

	public SubAccountListAdapter(Context context,
			ArrayList<UserAccountEntity> list) {

		this.context = context;
		this.itemViews = new View[list.size()];
		this.inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		UserAccountEntity user = null;
		for (int i = 0; i < itemViews.length; i++) {
			user = list.get(i);
			itemViews[i] = makeItemView(user);
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

	private View makeItemView(UserAccountEntity user) {
		View itemView = inflater.inflate(R.layout.sub_item_list, null);

		TextView id = (TextView) itemView.findViewById(R.id.itemId);
		id.setText(String.valueOf(user.getId()));
		TextView appId = (TextView) itemView.findViewById(R.id.itemAppId);
		appId.setText(user.getAppid() + "");
		TextView title = (TextView) itemView.findViewById(R.id.itemTitle);
		title.setText(user.getNickname());
		TextView text = (TextView) itemView.findViewById(R.id.itemText);
		text.setText(user.getCreateDate());
		ImageView image = (ImageView) itemView.findViewById(R.id.itemImage);
		image.setImageResource(user.getImgId());

		if (user.getExpiresin() != 0) {
			// String date = new
			// java.text.SimpleDateFormat("yyyy/MM/dd hh:mm:ss")
			// .format(user.getExpiresin());

			String expireText = "";
			Date now = new Date();
			long timeSpan = user.getExpiresin() - now.getTime();
			if (timeSpan <= 0) {
				expireText = "已过期";
			} else {
				String date = StringUtil.formatMillisecond(timeSpan);
				expireText = "将于[" + date + "]后过期";
			}
			TextView expires = (TextView) itemView
					.findViewById(R.id.itemExpires);
			expires.setText(expireText);
		}

		return itemView;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null)
			return itemViews[position];
		return convertView;
	}
}
