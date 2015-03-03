package com.webwalker.data;

import java.util.List;

import android.content.Context;
import android.database.Cursor;

import com.webwalker.adpater.ListViewItem;
import com.webwalker.entity.AppLogEntity;
import com.webwalker.entity.SchedueExecutorEntity;
import com.webwalker.entity.SchedueMonitorEntity;
import com.webwalker.entity.TaskLogEntity;
import com.webwalker.entity.UserAccountEntity;
import com.webwalker.utility.Encrypter;
import com.webwalker.utility.data.AbstractDBHelper;
import com.webwalker.utils.AppConstants;
import com.webwalker.wblogger.R;

/**
 * 事务、光标需要在外部进行操作，兼容两种模式， 读数据、事务处理需要采用外部控制的方式调用MyDataHelper Cursor外部控制
 * 
 * @author Administrator
 * 
 */
public class MyDataHelper extends AbstractDBHelper {

	public MyDataHelper(Context context) {
		super(context);
	}

	@Override
	protected String getTag() {
		return "MyDataHelper";
	}

	@Override
	protected String getDbName() {
		return "wblogger.db";
	}

	@Override
	protected int getDatabaseVersion() {
		return 1;
	}

	@Override
	protected String[] createDBTables() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String[] dropDBTables() {
		// TODO Auto-generated method stub
		return null;
	}

	// 系统用户登录
	public boolean getSysUser(String userId, String password) {
		Cursor cursor = Query(R.string.select_user, new String[] { userId,
				password });

		return cursor.getCount() > 0 ? true : false;
	}

	// 插入系统用户
	public boolean insertSysUser(String userId, String password) {
		Encrypter des = new Encrypter(Encrypter.Algorithm.MD5);
		String pass = des.MD5Encrypt(AppConstants.EncryptKEY, password);

		return super.execAutoClose(R.string.insert_user, new String[] { userId,
				pass });
	}

	// 获取账户、子账户(isparent = 1 and status = 1)
	public Cursor getAccountWithMapping() {
		return Query(R.string.select_account_with_mapping, null);
	}

	public Cursor getAccount(String isParent, int appId) {
		return Query(R.string.select_app_account,
				new String[] { String.valueOf(appId), isParent });
	}

	/**
	 * 获取跨App的所有子账号，方便提供给签到、同步功能使用， 且父账号不需要授权，所以签到、同步不应该包含父类账号
	 * 
	 * @param isParent
	 * @param appId
	 * @return
	 */
	public Cursor getAllSubAccount(String isParent) {
		return Query(R.string.select_all_sub_account, new String[] { isParent });
	}

	// 获得单个账号信息
	public Cursor getSingleAccount(int id) {
		return Query(R.string.select_single_account,
				new String[] { String.valueOf(id) });
	}

	// 获得单个账号信息
	public Cursor getSingleAccountByName(int appId, String nickname) {
		return Query(R.string.select_single_account_byname, new String[] {
				String.valueOf(appId), nickname });
	}

	// 新增账户
	public boolean insertAccount(UserAccountEntity u) {
		String token = "";
		if (u.usertoken != null && !u.usertoken.equals("")) {

			try {
				token = Encrypter.encryptDES(u.usertoken);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return super.execAutoClose(R.string.insert_user_account, new Object[] {
				u.appid, u.nickname, u.uid, token, u.expiresin, u.isparent,
				u.status });
	}

	// 更新账户主信息
	public boolean updateAccount(UserAccountEntity u) {
		String token = "";
		try {
			if (u.usertoken != null && !u.usertoken.equals("")) {
				token = Encrypter.encryptDES(u.usertoken);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return super.execAutoClose(R.string.update_user_account, new Object[] {
				u.nickname, token, u.expiresin, u.id });
	}

	// 删除账号及关联处理
	public boolean batchDeleteAccount(List<ListViewItem> list) {

		try {
			this.open();
			db.beginTransaction();

			String id = "0";
			for (ListViewItem item : list) {
				id = item.getId();
				// 映射关系
				super.execSql(R.string.delete_all_account_mapping,
						new Object[] { id });
				// 执行计划
				super.execSql(R.string.delete_schedue_executor_parent,
						new Object[] { id });
				// 监控规则
				super.execSql(R.string.delete_schedue_monitor_parent,
						new Object[] { id });

				// 账号
				super.execSql(R.string.delete_user_account, new Object[] { id });
			}

			db.setTransactionSuccessful();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (db != null) {
				db.endTransaction();
				db.close();
			}
		}
		return true;
	}

	// 删除指定的子账号
	public boolean batchDeleteSubAccount(List<ListViewItem> list) {

		this.open();
		try {
			db.beginTransaction();

			for (ListViewItem item : list) {
				// 映射关系, 根据昵称批量删除
				super.execSql(R.string.delete_account_mapping_via_name,
						new Object[] { item.getExt(0), item.getExt(1) });

				// 账号
				super.execSql(R.string.delete_user_account,
						new Object[] { item.getId() });
			}

			db.setTransactionSuccessful();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (db != null) {
				db.endTransaction();
				db.close();
			}
		}
		return true;
	}

	// 删除用户账号
	public boolean deleteUserAccount(String id) {
		return super.execAutoClose(R.string.delete_user_account,
				new Object[] { id });
	}

	// 更新父子关系
	public boolean updateIsParent(int isParent, int status, int id) {

		this.open();
		try {
			db.beginTransaction();

			// 映射关系
			super.execSql(R.string.delete_all_account_mapping,
					new Object[] { id });
			// 执行计划
			super.execSql(R.string.delete_schedue_executor_parent,
					new Object[] { id });
			// 监控规则
			super.execSql(R.string.delete_schedue_monitor_parent,
					new Object[] { id });
			// 更新账号关系
			super.execSql(R.string.update_account_isparent, new Object[] {
					isParent, status, id });

			db.setTransactionSuccessful();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (db != null) {
				db.endTransaction();
				db.close();
			}
		}
		return true;
	}

	// 更新任务状态
	public boolean updateTaskStatus(int isParent, int status, String id) {

		try {
			// 更新账号关系
			super.execAutoClose(R.string.update_account_isparent, new Object[] {
					isParent, status, id });
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	// 新增父子账户映射
	public boolean insertAccountMapping(UserAccountEntity u) {
		return super.execAutoClose(R.string.insert_account_mapping,
				new Object[] { u.appid, u.uaid, u.nickname, u.subuid, 1 });
	}

	// 更新账户映射状态
	public boolean updateAccountMapping(UserAccountEntity u,
			List<ListViewItem> list) {

		this.open();
		try {
			db.beginTransaction();

			// 删除原有绑定信息
			super.execSql(R.string.delete_all_account_mapping,
					new Object[] { u.getId() });

			for (ListViewItem item : list) {
				// 增加新的绑定关系
				super.execSql(R.string.insert_account_mapping, new Object[] {
						u.appid, u.id, u.nickname, item.getId(), 1 });
			}

			db.setTransactionSuccessful();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (db != null) {
				db.endTransaction();
				db.close();
			}
		}
		return true;

	}

	// 查询指定账号下的所有绑定关系
	public Cursor getAccountMapping(int uaId) {
		return Query(R.string.select_account_mapping,
				new String[] { String.valueOf(uaId) });
	}

	// 删除账号映射
	public boolean deleteAccountMapping(int uaid, String sub_uid) {
		return super.execAutoClose(R.string.delete_account_mapping,
				new Object[] { String.valueOf(uaid), sub_uid });
	}

	// 获取所有子账户列表
	public Cursor getChildAccountList(String uid, String appid) {
		return Query(R.string.select_mapping_childlist, new String[] { uid,
				appid });
	}

	// 获取AppType
	public Cursor getAppTypes() {
		return Query(R.string.select_apptype, new String[] { "1" });
	}

	// 插入APP日志
	public boolean insertAppLog(AppLogEntity l) {
		return super.execAutoClose(R.string.insert_applog, new Object[] { l.id,
				l.title, l.body });
	}

	// 插入任务执行日志
	public boolean insertTaskLog(TaskLogEntity l) {
		return super.execAutoClose(R.string.insert_tasklog, new Object[] {
				l.appid, l.monitoruleid, l.taskruleid, l.timeruleid, l.uid,
				l.nickname, l.body, l.getStatus(), l.retrytimes });
	}

	// 获取执行日志
	public Cursor getExecutorLog() {
		return Query(R.string.select_task_log, null);
	}

	// 获取监控规则
	public Cursor getMonitorRules() {
		return Query(R.string.select_monitor_rule, new String[] { "1" });
	}

	// 获取任务规则（做什么事情）
	public Cursor getTaskRules() {
		return Query(R.string.select_task_rule, new String[] { "1" });
	}

	// 获取时间规则（什么时间做）
	public Cursor getTimeRules() {
		return Query(R.string.select_time_rule, new String[] { "1" });
	}

	// 新增执行计划
	public boolean insertSchedueTask(SchedueExecutorEntity s) {
		return super.execAutoClose(
				R.string.insert_schedue_executor,
				new Object[] { s.getAppid(), s.getUid(), s.getTaskruleid(),
						s.getRequestbody(), s.getRequesttime(), s.getStatus(),
						s.getLockflag() });
	}

	// 获取所有的执行计划
	public Cursor getAllSchedueExecutor() {
		return Query(R.string.select_se_all, null);
	}

	// 获得Lock、UnLock状态的执行计划
	public Cursor getUnLockSchedueExecutor(int topNum) {
		return Query(R.string.select_schedue_executor, new String[] { "0",
				String.valueOf(topNum) });
	}

	// 更新执行计划
	public boolean updateSchedueExecutor(int lockflag, int status, int id) {
		return super.execAutoClose(R.string.update_schedue_executor,
				new Object[] { lockflag, status, id });
	}

	// 批量更新执行计划状态
	public boolean batchUpdateSchedueExecutor(int lockflag, int status,
			List<String> ids) {

		this.open();
		try {
			db.beginTransaction();

			for (String s : ids) {
				super.execSql(R.string.update_schedue_executor, new Object[] {
						lockflag, status, s });
			}

			db.setTransactionSuccessful();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (db != null) {
				db.endTransaction();
				db.close();
			}
		}
		return true;
	}

	// 删除执行计划
	public boolean deleteSchedueExecutor(int id) {
		return super.execAutoClose(R.string.delete_schedue_executor,
				new Object[] { id });
	}

	// 批量删除执行计划
	public boolean batchDeleteSchedueExecutor(List<ListViewItem> list) {

		this.open();
		try {
			db.beginTransaction();

			for (ListViewItem item : list) {
				super.execSql(R.string.delete_user_account,
						new Object[] { item.getId() });
			}

			db.setTransactionSuccessful();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (db != null) {
				db.endTransaction();
				db.close();
			}
		}
		return true;
	}

	// 删除执行计划
	public boolean deleteSchedueExecutorByParent(int uaid) {
		return super.execAutoClose(R.string.delete_schedue_executor_parent,
				new Object[] { uaid });
	}

	// 新增计划监控
	public boolean insertSchedueMonitor(SchedueMonitorEntity s) {
		return super.execAutoClose(
				R.string.insert_schedue_monitor,
				new Object[] { s.getUaid(), s.getUid(), s.getMonitoruleid(),
						s.getTaskruleid(), s.getTimeruleid(),
						s.getPostsinceid(), s.getCommentId(), s.getStatus() });
	}

	// 是否已存在此监控计划
	public boolean hasSchedueMonitor(int uaid) {

		// this.open();
		Cursor cursor = null;
		try {
			cursor = Query(R.string.select_schedue_monitor,
					new String[] { String.valueOf(uaid) });

			if (cursor.moveToNext()) {
				return true;
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (cursor != null) {
				cursor.close();
			}
			this.close();
		}
		return false;
	}

	// 获取监控计划
	public Cursor getSchedueMonitor(String uaid) {
		try {
			Cursor cursor = Query(R.string.select_schedue_monitor_full,
					new String[] { uaid });
			return cursor;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	// 删除监控计划
	public boolean deleteSchedueMonitor(String uaid) {
		return super.execAutoClose(R.string.delete_schedue_monitor_parent,
				new Object[] { uaid });
	}

	// 更新监控评论ID
	public boolean updateSchedueComment(int uaid, int commid) {
		return super.execAutoClose(R.string.update_monitor_comment,
				new Object[] { commid, uaid });
	}

	// 更新监控规则
	public boolean updateSchedueMonitorRule(SchedueMonitorEntity s) {
		return super.execAutoClose(
				R.string.update_sm_rule,
				new Object[] { s.getMonitoruleid(), s.getTaskruleid(),
						s.getTimeruleid(), s.getPostsinceid(), s.getUaid() });
	}

	public boolean updatePostSinceId(long sinceId, int id) {
		return super.execAutoClose(R.string.update_sm_extinfo, new Object[] {
				sinceId, id });
	}

	// 获得系统评论
	public Cursor getComments() {
		return Query(R.string.select_comments, new String[] { "1" });
	}

	// 获得单条评论
	public Cursor getComments(int commentId) {
		return Query(R.string.select_single_comment,
				new String[] { String.valueOf(commentId), "1" });
	}

	// 新增评论
	public boolean insertComment(String commdesc) {
		return super.execAutoClose(R.string.insert_comments, new Object[] {
				commdesc, 1 });
	}

	// 获取最大的评论ID
	public int getMaxCommentId() {

		this.open();
		try {
			Cursor cursor = Query(R.string.select_comments_maxid, null);

			if (cursor.moveToNext()) {
				return cursor.getInt(cursor.getColumnIndex("id"));
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (db != null) {
				db.close();
			}
		}
		return 10;
	}
}
