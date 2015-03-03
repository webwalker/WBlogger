/**
 * 
 */
package com.webwalker.controller.executor;

import android.annotation.SuppressLint;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.webwalker.rules.RulesDefine.MonitorRule;
import com.webwalker.rules.RulesDefine.TaskRule;

/**
 * 待执行的任务
 * 
 * @author Administrator
 * 
 */
public class Task {

	@SuppressLint("SimpleDateFormat")
	public boolean isNeedUpdate() {
		if (monitordate.equals(""))
			return true;
		else {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-DD-mm");
			try {
				Date lastUpdate = format.parse(monitordate);
				Date now = new Date();
				long timespan = now.getTime() - lastUpdate.getTime();
				long days = timespan / (1000 * 60 * 60 * 24);

				// 当天不需要进行再次更新
				if (days == 0)
					return false;

			} catch (ParseException e) {
				e.printStackTrace();
			}
		}

		return true;
	}

	private String id;
	private int uaid;
	private String uid;
	private String nickname;
	private int monitoruleid;
	private MonitorRule monitorRule;
	private int taskruleid;
	private int timeruleid;
	private long postsinceid;
	private long repostsinceid;
	private int commid;
	/**
	 * 任务的执行状态，取值范围（1、2、3、4、5），分别对应（新建、下载、完成、暂停、失败）五个状态
	 */
	private int status;
	private String createdate;
	private String monitordate;
	private int appid;
	private String token;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getUaid() {
		return uaid;
	}

	public void setUaid(int uaid) {
		this.uaid = uaid;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public int getMonitoruleid() {
		return monitoruleid;
	}

	public void setMonitoruleid(int monitoruleid) {
		this.monitoruleid = monitoruleid;
	}

	public MonitorRule getMonitorRule() {
		MonitorRule monitorRule = MonitorRule.getByCode(monitoruleid);
		return monitorRule;
	}

	public int getTaskruleid() {
		return taskruleid;
	}

	public void setTaskruleid(int taskruleid) {
		this.taskruleid = taskruleid;
	}

	public int getTimeruleid() {
		return timeruleid;
	}

	public void setTimeruleid(int timeruleid) {
		this.timeruleid = timeruleid;
	}

	public long getPostsinceid() {
		return postsinceid;
	}

	public void setPostsinceid(long postsinceid) {
		this.postsinceid = postsinceid;
	}

	public long getRepostsinceid() {
		return repostsinceid;
	}

	public void setRepostsinceid(long repostsinceid) {
		this.repostsinceid = repostsinceid;
	}

	public int getCommid() {
		return commid;
	}

	public void setCommid(int commid) {
		this.commid = commid;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getCreatedate() {
		return createdate;
	}

	public void setCreatedate(String createdate) {
		this.createdate = createdate;
	}

	public int getAppid() {
		return appid;
	}

	public void setAppid(int appid) {
		this.appid = appid;
	}

	public String getMonitordate() {
		return monitordate;
	}

	public void setMonitordate(String monitordate) {
		this.monitordate = monitordate;
	}
}
