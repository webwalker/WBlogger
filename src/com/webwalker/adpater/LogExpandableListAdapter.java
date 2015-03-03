/**
 * 
 */
package com.webwalker.adpater;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.webwalker.entity.TaskLogEntity;
import com.webwalker.wblogger.ExecutorLogActivity;

/**
 * @author Administrator
 * 
 */
public class LogExpandableListAdapter extends BaseExpandableListAdapter {
	Context context;
	LayoutInflater inflater;
	View[] itemViews;
	View[] subViews;
	ArrayList<String> mappingList;

	List<String> group; // 组列表
	List<List<String>> child; // 子列表

	public LogExpandableListAdapter(Context context,
			ArrayList<TaskLogEntity> list) {

		this.context = context;
		this.itemViews = new View[list.size()];
		this.inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		buildViewList(list);

		// TaskLogEntity t = null;
		// for (int i = 0; i < itemViews.length; i++) {
		// t = list.get(i);
		// itemViews[i] = makeItemView(t);
		// }
	}

	public void buildViewList(ArrayList<TaskLogEntity> list) {
		group = new ArrayList<String>();
		child = new ArrayList<List<String>>();

		for (TaskLogEntity t : list) {
			group.add(t.nickname);

			List<String> childitem = new ArrayList<String>();
			childitem.add("请求时间：" + t.getCreatedate());
			childitem.add("请求次数：" + t.getRetrytimes());
			childitem.add("请求状态：" + t.getStatus());
			childitem.add("请求内容：" + t.getBody());
			childitem.add("监控规则：" + t.getMname());
			childitem.add("任务规则：" + t.getTaname());
			childitem.add("时间规则" + t.getTname());
			child.add(childitem);
		}
	}

	// -----------------Child----------------//
	@Override
	public Object getChild(int groupPosition, int childPosition) {
		return child.get(groupPosition).get(childPosition);
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		return child.get(groupPosition).size();
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		String string = child.get(groupPosition).get(childPosition);
		// return getGenericView(string);

		if (convertView == null) {
			convertView = getGenericView(string);

		}
		return convertView;
	}

	// ----------------Group----------------//
	@Override
	public Object getGroup(int groupPosition) {
		return group.get(groupPosition);
	}

	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	@Override
	public int getGroupCount() {
		return group.size();
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		String string = group.get(groupPosition);
		// return getGenericView(string);

		if (convertView == null) {
			convertView = getGenericView(string);

		}
		return convertView;
	}

	// 创建组/子视图
	public TextView getGenericView(String s) {
		// Layout parameters for the ExpandableListView
		AbsListView.LayoutParams lp = new AbsListView.LayoutParams(
				ViewGroup.LayoutParams.FILL_PARENT, 40);

		TextView text = new TextView(context);
		text.setLayoutParams(lp);
		// Center the text vertically
		text.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
		// Set the text starting position
		text.setPadding(36, 0, 0, 0);

		text.setText(s);
		return text;
	}

	@Override
	public boolean hasStableIds() {
		return false;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}

}
