package com.kaishengit.entity;

import java.sql.Timestamp;
import java.util.List;

public class Article {
	private int id;
	private String title;
	private String content;
	private Timestamp createTime;
	private int scannum;
	private int replynum;
	private Timestamp lastreplyTime;
	private int nodeid;

	private String simpleContent;
	private String picture;

	private List<Label> labelList;
	private Node node;

	public List<Label> getLabelList() {
		return labelList;
	}

	public void setLabelList(List<Label> labelList) {
		this.labelList = labelList;
	}

	public Node getNode() {
		return node;
	}

	public void setNode(Node node) {
		this.node = node;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
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

	public int getScannum() {
		return scannum;
	}

	public void setScannum(int scannum) {
		this.scannum = scannum;
	}

	public int getReplynum() {
		return replynum;
	}

	public void setReplynum(int replynum) {
		this.replynum = replynum;
	}

	public Timestamp getLastreplyTime() {
		return lastreplyTime;
	}

	public void setLastreplyTime(Timestamp lastreplyTime) {
		this.lastreplyTime = lastreplyTime;
	}

	public int getNodeid() {
		return nodeid;
	}

	public void setNodeid(int nodeid) {
		this.nodeid = nodeid;
	}

	public String getSimpleContent() {
		return simpleContent;
	}

	public void setSimpleContent(String simpleContent) {
		this.simpleContent = simpleContent;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

}
