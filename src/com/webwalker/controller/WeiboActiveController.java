/**
 * 
 */
package com.webwalker.controller;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import android.content.Context;
import android.database.Cursor;

import com.webwalker.adpater.ListViewItem;
import com.webwalker.adpater.ViewHolder.Keys;
import com.webwalker.controller.executor.ActionController;
import com.webwalker.controller.executor.Task;
import com.webwalker.data.MyDataHelper;
import com.webwalker.entity.UserAccountEntity;
import com.webwalker.listener.IActionListener;
import com.webwalker.listener.IClickListener;
import com.webwalker.listener.MyClickListener;
import com.webwalker.utility.MessagesUtil;
import com.webwalker.wblogger.R;

/**
 * @author Administrator
 * 
 */
public class WeiboActiveController extends BaseController {

	MyDataHelper helper = null;

	public WeiboActiveController(Context context) {
		super(context);
		helper = new MyDataHelper(context);
	}

	public WeiboActiveController(Context context, IActionListener listener) {
		super(context, listener);
		helper = new MyDataHelper(context);
	}

	public ArrayList<LinkedHashMap<String, String>> getAccount(String isParent) {

		ArrayList<LinkedHashMap<String, String>> myArrayList = new ArrayList<LinkedHashMap<String, String>>();

		try {
			Cursor cursor = helper.getAccountWithMapping();

			while (cursor.moveToNext()) {
				LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
				map.put(Keys.itemId,
						cursor.getString(cursor.getColumnIndex("id")));
				map.put(Keys.itemTitle,
						cursor.getString(cursor.getColumnIndex("nickname")));
				map.put(Keys.itemText,
						cursor.getString(cursor.getColumnIndex("createdate")));

				map.put(Keys.itemStatus, new AppController()
						.getStatusDesc(cursor.getString(cursor
								.getColumnIndex("status"))));
				map.put(Keys.itemCount,
						cursor.getString(cursor.getColumnIndex("num")));

				int appId = cursor.getInt(cursor.getColumnIndex("app_id"));
				map.put(Keys.itemAppId, "" + appId);
				map.put(Keys.itemImage,
						"" + new AppController().getAppIcon(appId));

				myArrayList.add(map);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			helper.close();
		}
		return myArrayList;
	}

	public boolean deleteUserAccount(final List<ListViewItem> list) {

		IClickListener clicklistener = new MyClickListener() {
			@Override
			public void OnClickOK() {
				try {
					helper.batchDeleteAccount(list);

					listener.OnAction();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			@Override
			public void OnClickCancel() {
			}
		};

		MessagesUtil m = new MessagesUtil(context);
		m.ShowDialog(R.string.dialog_title_confirm,
				R.string.dialog_message_delete_account, R.string.dialog_ok,
				R.string.dialog_cancel, clicklistener);
		return true;
	}

	// 更新父子关系
	public boolean updateIsParent(final int isParent, final int status,
			final int id) {
		MessagesUtil m = new MessagesUtil(context);
		m.ShowDialog(R.string.dialog_title_confirm,
				R.string.dialog_message_isparent, R.string.dialog_ok,
				R.string.dialog_cancel, new MyClickListener() {
					@Override
					public void OnClickOK() {
						try {

							AccountController ac = new AccountController(
									context);
							UserAccountEntity uae = ac.getSingleAccount(id);
							if (uae.expiresin == 0) {
								listener.OnAction(R.string.msg_cannot_be_sub);
								return;
							}

							helper.updateIsParent(isParent, status, id);

							listener.OnAction(R.string.msg_update_sub_succ);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}

					@Override
					public void OnClickCancel() {
					}
				});

		return true;
	}

	// 更新任务执行状态
	public boolean updateTaskStatus(final List<ListViewItem> list, int status) {
		try {
			// 可改为事务方式一步提交，待优化
			for (ListViewItem item : list) {
				helper.updateTaskStatus(1, status, item.getId());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	// 执行任务
	public boolean executeTask(List<ListViewItem> list, int status) {

		try {
			// 更新任务状态
			this.updateTaskStatus(list, status);

			ActionController controller = new ActionController(context);
			List<Task> taskList = new ArrayList<Task>();
			for (ListViewItem item : list) {
				Task task = new Task();
				task.setId(item.getId());
				task.setUid(String.valueOf(item.getExt(1)));
				taskList.add(task);
			}
			controller.startTask();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}

	// 停止任务
	public boolean stopTask(List<ListViewItem> list, int status) {

		try {
			// 更新任务状态
			this.updateTaskStatus(list, status);

			ActionController controller = new ActionController(context);
			List<Task> taskList = new ArrayList<Task>();

			for (ListViewItem item : list) {
				Task task = new Task();
				task.setId(item.getId());
				task.setUid(String.valueOf(item.getExt(1)));
				taskList.add(task);
			}
			controller.stopTask();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}

}
