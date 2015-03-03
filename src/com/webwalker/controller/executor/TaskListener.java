/**
 * 
 */
package com.webwalker.controller.executor;

import java.util.List;


/**
 * @author Administrator
 * 
 */
public interface TaskListener {
	/**
	 * 根据传来的任务新建任务
	 */
	public void addTask(List<Task> task);

	/**
	 * 开始任务管理器管理的对应任务
	 * 
	 * @param taskController
	 */
	public void startTask();

	/**
	 * 暂停该任务管理器管理的任务
	 * 
	 * @param taskController
	 */
	public void stopTask();

	/**
	 * 删除任务
	 * 
	 * @param taskController
	 */
	public void deleteTask();

	/**
	 * 清空正在执行的任务
	 */
	public void clearTask();

	/**
	 * 任务结束（完成、失败）
	 */
	public void taskOver(Task task);
}
