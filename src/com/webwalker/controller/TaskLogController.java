/**
 * 
 */
package com.webwalker.controller;

import android.content.Context;

import com.webwalker.controller.executor.Implementor;
import com.webwalker.data.MyDataHelper;
import com.webwalker.entity.SchedueExecutorEntity;
import com.webwalker.entity.TaskLogEntity;

/**
 * 保存任务执行日志
 * 
 * @author Administrator
 * 
 */
public class TaskLogController<T> extends Implementor {

	Context context;
	SchedueExecutorEntity s = null;

	public TaskLogController(Context context, T data) {
		this.context = context;
		this.s = (SchedueExecutorEntity) data;
	}

	@Override
	public void Action() {

		TaskLogEntity log = new TaskLogEntity();
		log.setAppid(s.getAppid());
		log.setMonitoruleid(s.getMonitoruleid());
		log.setTaskruleid(s.getMonitoruleid());
		log.setTimeruleid(s.getMonitoruleid());
		log.setUid(s.getUid());
		log.setNickname(s.getUid());
		log.setBody(s.getRequestbody());
		log.setStatus(s.getStatus());
		log.setRetrytimes(1);

		MyDataHelper helper = new MyDataHelper(context);
		helper.insertTaskLog(log);
	}
}