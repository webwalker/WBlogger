/**
 * 
 */
package com.webwalker.entity;

import java.util.Date;

import com.webwalker.rules.RulesDefine.TaskRule;

/**
 * @author Administrator
 * 
 */
public class SchedueExecutorEntity {
	private int id;
	private int uaid;
	private int status;
	private int lockflag;
	private String createdate;
	private String uid;
	private String nickName;
	private int appid;
	private int monitoruleid;
	private int taskruleid;
	private int timeruleid;
	private long postsinceid;
	private String taskdesc;
	private String requestbody;
	private Date requesttime;
	private int imgId;

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the uaid
	 */
	public int getUaid() {
		return uaid;
	}

	/**
	 * @param uaid
	 *            the uaid to set
	 */
	public void setUaid(int uaid) {
		this.uaid = uaid;
	}

	/**
	 * @return the appid
	 */
	public int getAppid() {
		return appid;
	}

	/**
	 * @param appid
	 *            the appid to set
	 */
	public void setAppid(int appid) {
		this.appid = appid;
	}

	/**
	 * @return the uid
	 */
	public String getUid() {
		return uid;
	}

	/**
	 * @param uid
	 *            the uid to set
	 */
	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	/**
	 * @return the monitoruleid
	 */
	public int getMonitoruleid() {
		return monitoruleid;
	}

	/**
	 * @param monitoruleid
	 *            the monitoruleid to set
	 */
	public void setMonitoruleid(int monitoruleid) {
		this.monitoruleid = monitoruleid;
	}

	/**
	 * @return the taskruleid
	 */
	public int getTaskruleid() {
		return taskruleid;
	}

	public TaskRule getTaskRule() {
		TaskRule taskRule = TaskRule.getByCode(taskruleid);
		return taskRule;
	}

	/**
	 * @param taskruleid
	 *            the taskruleid to set
	 */
	public void setTaskruleid(int taskruleid) {
		this.taskruleid = taskruleid;
	}

	/**
	 * @return the timeruleid
	 */
	public int getTimeruleid() {
		return timeruleid;
	}

	/**
	 * @param timeruleid
	 *            the timeruleid to set
	 */
	public void setTimeruleid(int timeruleid) {
		this.timeruleid = timeruleid;
	}

	public long getPostsinceid() {
		return postsinceid;
	}

	public void setPostsinceid(long postsinceid) {
		this.postsinceid = postsinceid;
	}

	/**
	 * @return the taskdesc
	 */
	public String getTaskdesc() {
		return taskdesc;
	}

	/**
	 * @param taskdesc
	 *            the taskdesc to set
	 */
	public void setTaskdesc(String taskdesc) {
		this.taskdesc = taskdesc;
	}

	/**
	 * @return the requestbody
	 */
	public String getRequestbody() {
		return requestbody;
	}

	/**
	 * @param requestbody
	 *            the requestbody to set
	 */
	public void setRequestbody(String requestbody) {
		this.requestbody = requestbody;
	}

	/**
	 * @return the requesttime
	 */
	public Date getRequesttime() {
		return requesttime;
	}

	/**
	 * @param requesttime
	 *            the requesttime to set
	 */
	public void setRequesttime(Date requesttime) {
		this.requesttime = requesttime;
	}

	/**
	 * @return the status
	 */
	public int getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(int status) {
		this.status = status;
	}

	/**
	 * @return the lockflag
	 */
	public int getLockflag() {
		return lockflag;
	}

	/**
	 * @param lockflag
	 *            the lockflag to set
	 */
	public void setLockflag(int lockflag) {
		this.lockflag = lockflag;
	}

	/**
	 * @return the createdate
	 */
	public String getCreatedate() {
		return createdate;
	}

	/**
	 * @param createdate
	 *            the createdate to set
	 */
	public void setCreatedate(String createdate) {
		this.createdate = createdate;
	}

	/**
	 * @return the imgId
	 */
	public int getImgId() {
		return imgId;
	}

	/**
	 * @param imgId
	 *            the imgId to set
	 */
	public void setImgId(int imgId) {
		this.imgId = imgId;
	}
}
