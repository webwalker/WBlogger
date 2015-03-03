package com.webwalker.wblogger;

import com.webwalker.adpater.BindListAdapter;
import com.webwalker.adpater.ViewHolder;
import com.webwalker.listener.IActionListener;
import com.webwalker.listener.MyRequestListener;

import android.os.Bundle;
import android.app.Activity;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnCreateContextMenuListener;
import android.widget.ListView;

public class BindListActivity extends Activity {
	ListView listView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.bind_list);
	}

	@Override
	protected void onStart() {
		super.onStart();
		InitListView();
	}

	void InitListView() {
//		listView.setAdapter(new BindListAdapter(this, wac.getAccount("1")));
//		ViewHolder.setListViewHeightBasedOnFixed(listView, 81);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.weibo_active, menu);
		return true;
	}

	class ContextMenuListener implements OnCreateContextMenuListener {
		@Override
		public void onCreateContextMenu(ContextMenu menu, View v,
				ContextMenuInfo menuInfo) {
			getMenuInflater().inflate(R.menu.bind_list_item, menu);
		}
	}

	IActionListener listener = new MyRequestListener() {
		@Override
		public void OnAction() {
			InitListView();
		}
	};
}
