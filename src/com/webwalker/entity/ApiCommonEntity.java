package com.webwalker.entity;

import java.util.List;

import com.webwalker.api.ApiListener.ListenerCallback;
import com.webwalker.rules.RulesDefine.TaskRule;

public class ApiCommonEntity {
	private int appid;
	private int taskruleid;
	private long postsinceid;
	private int uaid;
	private String uid;
	private TaskRule taskRule = null;
	private String nickname;
	private Object token;
	private String comments;
	private ListenerCallback listener;
	private int count;
	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	private List<RepostEntity> ext;

	public TaskRule getTaskRule() {
		if (taskRule == null)
			taskRule = TaskRule.getByCode(taskruleid);
		return taskRule;
	}

	public void setTaskRule(TaskRule taskRule) {
		this.taskRule = taskRule;
	}

	public List<RepostEntity> getExt() {
		return ext;
	}

	public void setExt(List<RepostEntity> ext) {
		this.ext = ext;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public ListenerCallback getListener() {
		return listener;
	}

	public void setListener(ListenerCallback listener) {
		this.listener = listener;
	}

	public Object getToken() {
		return token;
	}

	public void setToken(Object token) {
		this.token = token;
	}

	public int getAppid() {
		return appid;
	}

	public void setAppid(int appid) {
		this.appid = appid;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public int getUaid() {
		return uaid;
	}

	public void setUaid(int uaid) {
		this.uaid = uaid;
	}

	public long getPostsinceid() {
		return postsinceid;
	}

	public void setPostsinceid(long postsinceid) {
		this.postsinceid = postsinceid;
	}

	public int getTaskruleid() {
		return taskruleid;
	}

	public void setTaskruleid(int taskruleid) {
		this.taskruleid = taskruleid;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}
}
