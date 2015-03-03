/**
 * 
 */
package com.webwalker.mediator;

import java.util.List;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.webwalker.wblogger.R;

/**
 * @author Administrator
 * 
 */
public class FunctionsAdapter extends BaseAdapter {

	private Context context = null; // 上下文
	private List<GridViewFunction> list = null; // 数据源

	private RelativeLayout layout = null;
	private TextView appTitle = null;
	private ImageView appIcon = null;

	// 适配器构造函数
	public FunctionsAdapter(Context c, List<GridViewFunction> list) {
		this.context = c;// c是上下文，在UI编程中，一般这个参数都是必要的
		this.list = list;// list中是一个Function数组，存放了所有要显示的Function
	}

	// 下面三个是实现抽象函数，可以无视
	public int getCount() {
		return list.size();
	}

	public Object getItem(int position) {
		return list.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	// 根据参数，个性化自己的View
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.gv_menu, null);// 通过上下文获取一开始定义的menu_item布局，以这个布局文件为样式造出一个自定义View

			// layout = (RelativeLayout) convertView
			// .findViewById(R.id.appItemLayout);
			appTitle = (TextView) convertView.findViewById(R.id.main_grid_view);
			appIcon = (ImageView) convertView.findViewById(R.id.app_icon);

			GridViewFunction func = list.get(position);

			Drawable img = func.app.appIcon;
			img.setBounds(0, 0, img.getIntrinsicWidth(),
					img.getIntrinsicHeight());
			appIcon.setImageDrawable(img);
			appTitle.setText(func.app.appName);// 对功能按钮的名字进行赋值

			convertView.setOnClickListener(func.getOnClickListener());
			convertView.setOnCreateContextMenuListener(func
					.getOnContextMenuClickListener());
		}
		return convertView;// 最终返回一个View，这一个View就是一整个功能按钮，而且是个性化的功能按钮
	}

}
