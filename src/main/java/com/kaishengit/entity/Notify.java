package com.kaishengit.entity;

import java.sql.Timestamp;

public class Notify {

	public static final int NOTIFY_UNREAD = 0;// 消息未读
	public static final int NOTIFY_READ = 1; // 消息已读
	
	private int id;
	private String content;
	private Timestamp createTime;
	private int state;
	private Timestamp readTime;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public Timestamp getReadTime() {
		return readTime;
	}

	public void setReadTime(Timestamp readTime) {
		this.readTime = readTime;
	}

}
