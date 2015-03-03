/**
 * 
 */
package com.webwalker.controller.executor;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import android.content.Context;

import com.webwalker.controller.service.MonitorService;
import com.webwalker.utils.AppConstants;
import com.webwalker.utils.MyContext;

/**
 * 微博活跃控制器（中介）
 * 
 * @author Administrator
 * 
 */
public abstract class AbstractSchedueController implements IController,
		TaskListener, NotifyListener {

	protected boolean hasRunning = false;
	protected BusController busController;
	protected Context context;
	protected TreeMap<String, Task> taskMap = null;
	protected MonitorService ms = null;

	/**
	 * 总控制器需要管理的全部任务
	 */
	Set<TaskController> taskControllerSet = new HashSet<TaskController>();

	public AbstractSchedueController() {
		createInstance();
	}

	public AbstractSchedueController(Context context) {
		this.context = context;
		createInstance();
	}

	private void createInstance() {
		synchronized (this) {
			if (taskMap == null) {
				taskMap = new TreeMap<String, Task>();
				MyContext.Add(AppConstants.Keys.MonitorKeys, taskMap);
			}
			if (busController == null) {
				this.busController = new BusController();
			}

			if (ms == null) {
				ms = new MonitorService();
			}
		}
	}
}
