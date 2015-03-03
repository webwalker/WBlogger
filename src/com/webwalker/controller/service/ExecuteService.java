/**
 * 
 */
package com.webwalker.controller.service;

import java.util.List;

import android.content.Intent;
import android.os.IBinder;

import com.webwalker.api.APIFactory;
import com.webwalker.api.ApiContainer;
import com.webwalker.controller.BizTaskController;
import com.webwalker.controller.TaskLogController;
import com.webwalker.controller.executor.Abstractor;
import com.webwalker.controller.executor.AbstractorRealization;
import com.webwalker.controller.executor.TaskController;
import com.webwalker.entity.SchedueExecutorEntity;
import com.webwalker.utils.AppConstants;

/**
 * 执行服务，负责从计划表获取数据并直接执行，用户可从界面删除，对于已在内存中的计划不接受中断操作
 * 
 * @author Administrator
 * 
 */
public class ExecuteService extends TaskController {

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

		// 首次启动时启动主线程
		if (startId == 1) {
			MainRunnable er = new MainRunnable();
			Thread t = new Thread(er);
			t.setPriority(Thread.NORM_PRIORITY);
			t.start();
		}
		return super.onStartCommand(intent, flags, startId);
	}

	class MainRunnable implements Runnable {
		@Override
		public void run() {
			while (true) {
				// if (hasInterupted)
				// return;
				try {
					// 获取任务列表
					BizTaskController controller = new BizTaskController(
							getBaseContext());
					List<SchedueExecutorEntity> list = controller
							.getExecutorTask(50);

					// 执行子任务
					for (SchedueExecutorEntity s : list) {
						pool.execute(new TaskRunnable(s));
					}

					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	class TaskRunnable implements Runnable {
		SchedueExecutorEntity s;

		public TaskRunnable(SchedueExecutorEntity schedue) {
			this.s = schedue;
		}

		@Override
		public void run() {
			Abstractor ab = new AbstractorRealization();
			try {
				ApiContainer container = APIFactory.getInstance()
						.getExecutorContainer(getBaseContext(), s);
				container.startAction();

				s.setStatus(AppConstants.TaskStatus.STATE_COMPLETED); // 任务完成

			} catch (Exception e) {
				e.printStackTrace();
				s.setStatus(AppConstants.TaskStatus.STATE_FAILED); // 任务失败
			}

			// 记录执行日志
			ab.setImplementor(new TaskLogController<SchedueExecutorEntity>(
					ExecuteService.this, s));
			ab.Operation();
		}
	}
}
