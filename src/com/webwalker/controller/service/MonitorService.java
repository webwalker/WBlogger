/**
 * 
 */
package com.webwalker.controller.service;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

import org.json.JSONObject;

import android.content.Intent;
import android.os.IBinder;

import com.webwalker.api.APIFactory;
import com.webwalker.api.ApiContainer;
import com.webwalker.api.sina.WeiboAPI.COMMENTS_TYPE;
import com.webwalker.controller.BizTaskController;
import com.webwalker.controller.executor.Task;
import com.webwalker.controller.executor.TaskController;
import com.webwalker.entity.ApiCommonEntity;
import com.webwalker.entity.RepostEntity;
import com.webwalker.entity.SchedueExecutorEntity;
import com.webwalker.rules.RulesDefine.TaskRule;
import com.webwalker.rules.TimeParser;
import com.webwalker.utility.BeanUtil;
import com.webwalker.utility.MessagesUtil;
import com.webwalker.utils.AppConstants;
import com.webwalker.utils.MyContext;
import com.webwalker.wblogger.R;

/**
 * 解析监控规则，监控微博动态
 * 
 * @author Administrator
 * 
 */
public class MonitorService extends TaskController {
	BizTaskController bizController = new BizTaskController();
	TreeMap<String, Task> taskMap = null;
	int maxCommentId = 1;

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {

		// 首次启动时调用
		if (startId == 1) {
			MainRunnable er = new MainRunnable();
			Thread t = new Thread(er);
			t.setPriority(Thread.MAX_PRIORITY);
			t.start();
		}

		return super.onStartCommand(intent, flags, startId);
	}

	class MainRunnable implements Runnable {
		@SuppressWarnings("unchecked")
		@Override
		public void run() {

			InitializeMonitor();

			while (true) {
				// if (hasInterupted)
				// return;
				try {
					Iterator iter = taskMap.entrySet().iterator();
					while (iter.hasNext()) {
						Map.Entry entry = (Map.Entry) iter.next();
						Task t = (Task) entry.getValue();

						// 根据monitor rule处理不同职责(暂不解析规则表达式)
						switch (t.getMonitorRule()) {
						case MAPSI:
							// 获取最新的微博ID列表
							ApiCommonEntity c = new ApiCommonEntity();
							c.setTaskRule(TaskRule.UPWID);
							c.setAppid(t.getAppid());
							c.setUaid(t.getUaid());
							c.setUid(t.getUid());
							c.setPostsinceid(t.getPostsinceid());
							c.setCount(100);
							ApiContainer container = APIFactory.getInstance()
									.getCommonContainer(getBaseContext(), c);
							container.startAction();

							// 根据最新的微博ID生成执行计划
							List<RepostEntity> list = c.getExt();
							for (RepostEntity re : list) {
								try {
									SchedueExecutorEntity s = new SchedueExecutorEntity();
									s.setAppid(t.getAppid());
									s.setUaid(t.getUaid());
									s.setUid(t.getUid());
									s.setTaskruleid(t.getTaskruleid());
									// request body
									re.setIs_comment(COMMENTS_TYPE.BOTH);
									int commentId = t.getCommid();
									switch (commentId) {
									// 随机
									case -1:
										commentId = new Random(maxCommentId)
												.nextInt();
										break;
									// 禁止
									case -2:
										re.setIs_comment(COMMENTS_TYPE.NONE);
										break;
									}
									if (commentId > 0) {
										String comments = bizController
												.getComments(commentId);
										re.setStatus(comments);
									}
									JSONObject json = new JSONObject();
									BeanUtil.bean2Json(re, json);

									// parse request time
									TimeParser<Date> timer = new TimeParser<Date>();
									Date jobDate = timer.Parse(t
											.getTimeruleid());

									s.setRequestbody(json.toString());
									s.setRequesttime(jobDate);
									bizController.insertSchedueTask(s);
								} catch (Exception e) {
									e.printStackTrace();
									// 记录出错日志
								}
							}

						default:
							break;
						}
					}
					
					// 时间规则、任务规则、评论规则
					// 由执行服务生成待执行的微博任务

					Thread.sleep(60000);
				} catch (InterruptedException e) {
					e.printStackTrace();

				}
			}
		}
	}

	// 初始化监控器
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void InitializeMonitor() {

		taskMap = (TreeMap<String, Task>) MyContext
				.get(AppConstants.Keys.MonitorKeys);
		if (taskMap == null && taskMap.size() == 0) {
			MessagesUtil.sendNotify(getBaseContext(),
					getString(R.string.msg_schedue_tip), "初始化监听异常！");
			return;
		}

		// 初始化最新的微博ID
		Iterator iter = taskMap.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry entry = (Map.Entry) iter.next();
			Task t = (Task) entry.getValue();

			// 根据monitor rule处理不同职责
			switch (t.getMonitorRule()) {
			case MAPSI:
				if (t.isNeedUpdate()) {

					ApiCommonEntity c = new ApiCommonEntity();
					c.setTaskRule(TaskRule.UPWID);
					c.setAppid(t.getAppid());
					c.setUaid(t.getUaid());
					c.setUid(t.getUid());
					c.setCount(1);

					ApiContainer container = APIFactory.getInstance()
							.getCommonContainer(getBaseContext(), c);
					container.startAction();

					t.setPostsinceid(c.getPostsinceid());
				}
			default:
				break;
			}
			break;
		}

		maxCommentId = bizController.getMaxCommentId();
	}
}
