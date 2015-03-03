/**
 * 
 */
package com.webwalker.controller.executor;

import java.util.List;
import java.util.TreeMap;

import android.content.Context;
import android.content.Intent;

import com.webwalker.controller.BizTaskController;
import com.webwalker.utility.MessagesUtil;
import com.webwalker.utils.AppConstants;
import com.webwalker.utils.MyContext;
import com.webwalker.wblogger.R;

/**
 * 行为控制
 * 
 * @author Administrator
 * 
 */
public class ActionController extends AbstractSchedueController {

	public ActionController(Context context) {
		super(context);
	}

	@Override
	public void addTask(List<Task> task) {
		taskRunnable er = new taskRunnable(task);
		Thread t = new Thread(er);
		t.start();
	}

	class taskRunnable implements Runnable {

		List<Task> tasks;

		public taskRunnable(List<Task> tasks) {
			this.tasks = tasks;
		}

		@SuppressWarnings("unchecked")
		@Override
		public void run() {
			TreeMap<String, Task> taskMap = (TreeMap<String, Task>) MyContext
					.get(AppConstants.Keys.MonitorKeys);

			// 停止原有监控
			taskMap.clear();

			// 获取最新的任务相关信息
			for (Task item : tasks) {
				BizTaskController controller = new BizTaskController(context);
				Task task = controller.getSchedueMonitor(item.getId());
				if (task == null) {
					MessagesUtil
							.sendNotify(
									context,
									context.getString(R.string.msg_schedue_tip),
									item.getUid()
											+ context
													.getString(R.string.msg_monitor_not_exist));
					return;
				}
				taskMap.put(item.getId(), task);
			}
		}
	}

	@Override
	public void startTask() {

		if (!hasRunning) {
			Intent intent = new Intent(context, ms.getClass());
			context.startService(intent);

			// intent = new Intent(context, ExecuteService.class);
			// context.startService(intent);
			// intent = new Intent(context, GeneratorService.class);
			// context.startService(intent);
			// intent = new Intent(context, RemindService.class);
			// context.startService(intent);
		}
	}

	@Override
	public void stopTask() {
	}

	public void deleteTask() {
	}

	@Override
	public void clearTask() {
	}

	@Override
	public void taskOver(Task task) {
	}
}
