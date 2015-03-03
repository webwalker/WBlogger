/**
 * 
 */
package com.webwalker.adpater;

import java.text.MessageFormat;
import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.webwalker.entity.SchedueExecutorEntity;
import com.webwalker.entity.UserAccountEntity;
import com.webwalker.utility.StringUtil;
import com.webwalker.wblogger.R;

/**
 * @author Administrator
 * 
 */
public class SchedueListAdapter extends BaseAdapter {
	Context context;
	LayoutInflater inflater;
	View[] itemViews;
	ArrayList<String> mappingList;

	public SchedueListAdapter(Context context,
			ArrayList<SchedueExecutorEntity> list) {

		this.context = context;
		this.itemViews = new View[list.size()];
		this.inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		SchedueExecutorEntity s = null;
		for (int i = 0; i < itemViews.length; i++) {
			s = list.get(i);
			itemViews[i] = makeItemView(s);
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

	private String getStatusDesc(int status) {
		switch (status) {
		case 0:
			return "未执行";
		case 1:
			return "执行中";
		case 2:
			return "执行失败";
		case 3:
			return "执行成功";
		}

		return "未知";
	}

	private View makeItemView(SchedueExecutorEntity s) {
		View itemView = inflater.inflate(R.layout.sub_item_list, null);

		TextView id = (TextView) itemView.findViewById(R.id.itemId);
		id.setText(s.getId());
		TextView appId = (TextView) itemView.findViewById(R.id.itemAppId);
		appId.setText(s.getAppid() + "");
		TextView title = (TextView) itemView.findViewById(R.id.itemTitle);
		title.setText(s.getTaskdesc());
		TextView text = (TextView) itemView.findViewById(R.id.itemText);
		
		String desc = MessageFormat.format("{0}-{1}-{2}", s.getUid(),
				s.getRequesttime(), s.getStatus());
		text.setText(desc);
		ImageView image = (ImageView) itemView.findViewById(R.id.itemImage);
		image.setImageResource(s.getImgId());

		return itemView;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null)
			return itemViews[position];
		return convertView;
	}
}
