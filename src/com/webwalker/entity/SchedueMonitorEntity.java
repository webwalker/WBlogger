/**
 * 
 */
package com.webwalker.entity;

import java.util.Date;

/**
 * @author Administrator
 * 
 */
public class SchedueMonitorEntity {
	private int id;
	private String uid;
	private int uaid;
	private int monitoruleid;
	private int taskruleid;
	private int timeruleid;
	private long postsinceid;
	private int repostsinceid;
	private int commentid;
	private int status;
	private Date createdate;

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

	/**
	 * @return the postsinceid
	 */
	public long getPostsinceid() {
		return postsinceid;
	}

	/**
	 * @param since_id
	 *            the postsinceid to set
	 */
	public void setPostsinceid(long since_id) {
		this.postsinceid = since_id;
	}

	/**
	 * @return the repostsinceid
	 */
	public int getRepostsinceid() {
		return repostsinceid;
	}

	/**
	 * @param repostsinceid
	 *            the repostsinceid to set
	 */
	public void setRepostsinceid(int repostsinceid) {
		this.repostsinceid = repostsinceid;
	}

	/**
	 * @return the comment
	 */
	public int getCommentId() {
		return commentid;
	}

	/**
	 * @param comment
	 *            the comment to set
	 */
	public void setCommentId(int commentid) {
		this.commentid = commentid;
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
	 * @return the createdate
	 */
	public Date getCreatedate() {
		return createdate;
	}

	/**
	 * @param createdate
	 *            the createdate to set
	 */
	public void setCreatedate(Date createdate) {
		this.createdate = createdate;
	}
}
