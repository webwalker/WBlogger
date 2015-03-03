/**
 * 
 */
package com.webwalker.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.webwalker.adpater.ListViewItem;
import com.webwalker.controller.executor.Task;
import com.webwalker.data.MyDataHelper;
import com.webwalker.entity.SchedueExecutorEntity;
import com.webwalker.entity.SchedueMonitorEntity;
import com.webwalker.entity.TaskLogEntity;
import com.webwalker.listener.IActionListener;
import com.webwalker.listener.IClickListener;
import com.webwalker.listener.MyClickListener;
import com.webwalker.utility.MessagesUtil;
import com.webwalker.utils.AppConstants;
import com.webwalker.wblogger.R;

/**
 * @author Administrator
 * 
 */
public class BizTaskController extends BaseController {

	public enum RuleType {
		Monitor, Time, Task
	}

	MyDataHelper helper = null;

	public BizTaskController() {
	}

	public BizTaskController(Context context) {
		super(context);
		helper = new MyDataHelper(context);
	}

	public BizTaskController(Context context, IActionListener listener) {
		super(context, listener);
		helper = new MyDataHelper(context);
	}

	// 是否已经设置过规则
	public boolean hasSetRule(int uaid) {
		try {
			return helper.hasSchedueMonitor(uaid);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	// 获取账号监控信息
	public Task getSchedueMonitor(String uaid) {
		Task s = null;

		try {
			Cursor cursor = helper.getSchedueMonitor(uaid);

			if (cursor.moveToNext()) {
				s = new Task();

				s.setId(cursor.getString(cursor.getColumnIndex("id")));
				s.setUaid(cursor.getInt(cursor.getColumnIndex("ua_id")));
				s.setUid(cursor.getString(cursor.getColumnIndex("uid")));
				s.setNickname(cursor.getString(cursor
						.getColumnIndex("nickname")));
				s.setMonitoruleid(cursor.getInt(cursor
						.getColumnIndex("monitor_rule_id")));
				s.setTaskruleid(cursor.getInt(cursor
						.getColumnIndex("task_rule_id")));
				s.setTimeruleid(cursor.getInt(cursor
						.getColumnIndex("time_rule_id")));
				s.setPostsinceid(cursor.getInt(cursor
						.getColumnIndex("post_since_id")));
				s.setCommid(cursor.getInt(cursor.getColumnIndex("comm_id")));

				s.setAppid(cursor.getInt(cursor.getColumnIndex("app_id")));
				s.setToken(cursor.getString(cursor.getColumnIndex("user_token")));

				s.setStatus(cursor.getInt(cursor.getColumnIndex("status")));

				s.setMonitordate(cursor.getString(cursor
						.getColumnIndex("monitordate")));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			helper.close();
		}
		return s;
	}

	// 新增评论
	public boolean insertComment(String commdesc) {
		try {
			return helper.insertComment(commdesc);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	// 最大的评论ID
	public int getMaxCommentId() {
		try {
			return helper.getMaxCommentId();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 5;
	}

	// 更新监控规则
	public boolean updateSchedueMonitorRule(SchedueMonitorEntity s) {
		try {
			return helper.updateSchedueMonitorRule(s);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	// 更新最新的微博ID
	public boolean updatePostSinceId(long sinceId, int id) {
		try {
			return helper.updatePostSinceId(sinceId, id);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	// 更新监控评论
	public boolean updateSchedueComment(int uaid, int commid) {
		try {
			return helper.updateSchedueComment(uaid, commid);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	// 获取业务规则
	public List<Map<String, ?>> getRules(RuleType type) {
		List<Map<String, ?>> list = new ArrayList<Map<String, ?>>();

		MyDataHelper helper = new MyDataHelper(context);

		try {
			Cursor cursor = null;
			switch (type) {
			case Monitor:
				cursor = helper.getMonitorRules();
				break;
			case Time:
				cursor = helper.getTimeRules();
				break;
			case Task:
				cursor = helper.getTaskRules();
				break;
			}

			while (cursor.moveToNext()) {
				Map<String, Object> m = new TreeMap<String, Object>();
				m.put(AppConstants.MapValue,
						cursor.getString(cursor.getColumnIndex("rule_id")));
				m.put(AppConstants.MapKey,
						cursor.getString(cursor.getColumnIndex("rule_name")));
				list.add(m);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			helper.close();
		}

		return list;
	}

	// 关联业务规则
	public boolean addSchedueRule(SchedueMonitorEntity s) {
		try {
			return helper.insertSchedueMonitor(s);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return true;
	}

	// 获得评论内容
	public List<Map<String, ?>> getComments() {
		List<Map<String, ?>> list = new ArrayList<Map<String, ?>>();

		MyDataHelper helper = new MyDataHelper(context);

		try {
			Cursor cursor = helper.getComments();

			while (cursor.moveToNext()) {
				Map<String, Object> m = new TreeMap<String, Object>();
				m.put("value",
						cursor.getString(cursor.getColumnIndex("comm_id")));
				m.put(AppConstants.MapKey,
						cursor.getString(cursor.getColumnIndex("comm_desc")));
				list.add(m);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			helper.close();
		}

		return list;
	}

	// 获得单条评论内容
	public String getComments(int commmentId) {
		MyDataHelper helper = new MyDataHelper(context);

		try {
			Cursor cursor = helper.getComments(commmentId);

			if (cursor.moveToNext()) {
				return cursor.getString(cursor.getColumnIndex("comm_desc"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			helper.close();
		}
		return "";
	}

	// 新增执行计划
	public boolean insertSchedueTask(SchedueExecutorEntity s) {
		try {
			return helper.insertSchedueTask(s);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}

	// 获取所有执行计划
	public ArrayList<SchedueExecutorEntity> getAllScheduePlan() {
		ArrayList<SchedueExecutorEntity> list = new ArrayList<SchedueExecutorEntity>();

		try {
			Cursor cursor = helper.getAllSchedueExecutor();

			while (cursor.moveToNext()) {
				SchedueExecutorEntity s = new SchedueExecutorEntity();

				s.setId(cursor.getInt(cursor.getColumnIndex("id")));
				s.setUaid(cursor.getInt(cursor.getColumnIndex("ua_id")));
				s.setUid(cursor.getString(cursor.getColumnIndex("uid")));
				s.setTaskruleid(cursor.getInt(cursor
						.getColumnIndex("task_rule_id")));
				s.setTaskdesc(cursor.getString(cursor
						.getColumnIndex("rule_name")));
				s.setRequestbody(cursor.getString(cursor
						.getColumnIndex("request_body")));
				s.setRequesttime(new Date(Long.parseLong(cursor
						.getString(cursor.getColumnIndex("request_time")))));
				s.setLockflag(cursor.getInt(cursor.getColumnIndex("lock_flag")));
				s.setStatus(cursor.getInt(cursor.getColumnIndex("status")));
				s.setCreatedate(cursor.getString(cursor
						.getColumnIndex("createdate")));

				int appId = cursor.getInt(cursor.getColumnIndex("app_id"));
				s.setAppid(appId);
				s.setImgId(new AppController().getAppIcon(appId));

				list.add(s);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			helper.close();
		}
		return list;
	}

	// 删除选中的执行计划
	public boolean deleteScheduePlan(final List<ListViewItem> list) {

		IClickListener clicklistener = new MyClickListener() {
			@Override
			public void OnClickOK() {
				try {
					helper.batchDeleteSchedueExecutor(list);

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
		m.ShowDialog(R.string.dialog_title_confirm, R.string.msg_delete_sub,
				R.string.dialog_ok, R.string.dialog_cancel, clicklistener);
		return true;
	}

	// 获取最新的前50条执行日志
	public ArrayList<TaskLogEntity> getExecutorLog() {
		ArrayList<TaskLogEntity> list = new ArrayList<TaskLogEntity>();

		try {
			Cursor cursor = helper.getExecutorLog();

			while (cursor.moveToNext()) {
				TaskLogEntity t = new TaskLogEntity();

				t.setId(cursor.getInt(cursor.getColumnIndex("id")));
				t.setMonitoruleid(cursor.getInt(cursor
						.getColumnIndex("monitor_rule_id")));
				t.setTaskruleid(cursor.getInt(cursor
						.getColumnIndex("task_rule_id")));
				t.setTimeruleid(cursor.getInt(cursor
						.getColumnIndex("time_rule_id")));
				t.setMname(cursor.getString(cursor.getColumnIndex("mname")));
				t.setTaname(cursor.getString(cursor.getColumnIndex("taname")));
				t.setTname(cursor.getString(cursor.getColumnIndex("tname")));
				t.setUid(cursor.getString(cursor.getColumnIndex("uid")));

				t.setNickname(cursor.getString(cursor
						.getColumnIndex("nickname")));
				t.setBody(cursor.getString(cursor.getColumnIndex("body")));
				t.setStatus(cursor.getInt(cursor.getColumnIndex("status")));
				t.setRetrytimes(cursor.getInt(cursor
						.getColumnIndex("retry_times")));
				t.setCreatedate(cursor.getString(cursor
						.getColumnIndex("createdate")));

				int appId = cursor.getInt(cursor.getColumnIndex("app_id"));
				t.setAppid(appId);
				t.setImgId(new AppController().getAppIcon(appId));

				list.add(t);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			helper.close();
		}
		return list;
	}

	// 获得执行计划
	public synchronized ArrayList<SchedueExecutorEntity> getExecutorTask(
			int topNum) {
		ArrayList<SchedueExecutorEntity> list = new ArrayList<SchedueExecutorEntity>();

		SQLiteDatabase db = helper.getDatabase();
		try {
			db.beginTransaction();

			Cursor cursor = helper.getUnLockSchedueExecutor(topNum);

			while (cursor.moveToNext()) {
				SchedueExecutorEntity t = new SchedueExecutorEntity();

				t.setId(cursor.getInt(cursor.getColumnIndex("id")));
				t.setUaid(cursor.getColumnIndex("ua_id"));
				t.setAppid(cursor.getInt(cursor.getColumnIndex("app_id")));
				t.setUid(cursor.getString(cursor.getColumnIndex("uid")));

				t.setMonitoruleid(cursor.getInt(cursor
						.getColumnIndex("monitor_rule_id")));
				t.setTaskruleid(cursor.getInt(cursor
						.getColumnIndex("task_rule_id")));
				t.setTimeruleid(cursor.getInt(cursor
						.getColumnIndex("time_rule_id")));
				t.setRequestbody(cursor.getString(cursor
						.getColumnIndex("request_body")));
				t.setRequesttime(new Date(Long.parseLong(cursor
						.getString(cursor.getColumnIndex("request_time")))));
				t.setStatus(cursor.getInt(cursor.getColumnIndex("status")));
				t.setLockflag(cursor.getInt(cursor.getColumnIndex("lock_flag")));
				t.setCreatedate(cursor.getString(cursor
						.getColumnIndex("createdate")));

				list.add(t);
			}

			// 更新锁定
			for (SchedueExecutorEntity s : list) {
				helper.updateSchedueExecutor(1,
						AppConstants.TaskStatus.STATE_RUNNING, s.getId());
			}

			db.setTransactionSuccessful();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (db != null)
				db.close();
		}
		return list;
	}

	// 更新计划执行状态（仅为失败时才更新记录，成功则删除并记录到日志表）
	public boolean updateSchedueExecuteStatus(int lockStatus, int status,
			List<String> list) {
		return helper.batchUpdateSchedueExecutor(lockStatus, status, list);
	}
}
