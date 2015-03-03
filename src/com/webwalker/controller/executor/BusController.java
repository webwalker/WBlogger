/**
 * 
 */
package com.webwalker.controller.executor;

import java.util.List;

/**
 * 总线控制器，职责：负责所有控制器任务处理、频率调度，BusController -> TaskController -> Task
 * 
 * @author Administrator
 * 
 */
public class BusController implements TaskListener {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.webwalker.controller.executor.TaskListener#addTask(com.webwalker.
	 * controller.executor.Task)
	 */
	@Override
	public void addTask(List<Task> task) {
		// TODO Auto-generated method stub
		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.webwalker.controller.executor.TaskListener#startTask()
	 */
	@Override
	public void startTask() {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.webwalker.controller.executor.TaskListener#stopTask()
	 */
	@Override
	public void stopTask() {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.webwalker.controller.executor.TaskListener#deleteTask()
	 */
	@Override
	public void deleteTask() {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.webwalker.controller.executor.TaskListener#clearTask()
	 */
	@Override
	public void clearTask() {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.webwalker.controller.executor.TaskListener#taskOver(com.webwalker
	 * .controller.executor.Task)
	 */
	@Override
	public void taskOver(Task task) {
		// TODO Auto-generated method stub

	}

}
