package com.kaishengit.vo;

import java.util.List;

import com.kaishengit.entity.Article;
import com.kaishengit.entity.Node;
import com.kaishengit.entity.Reply;

public class ArticleDetailVO {// VO 向前端页面传输数据
	private Article article;
	private String nodeName;
	private List<Node> nodeList;
	private List<Reply> replyList;

	public Article getArticle() {
		return article;
	}

	public void setArticle(Article article) {
		this.article = article;
	}

	public String getNodeName() {
		return nodeName;
	}

	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}

	public List<Node> getNodeList() {
		return nodeList;
	}

	public void setNodeList(List<Node> nodeList) {
		this.nodeList = nodeList;
	}

	public List<Reply> getReplyList() {
		return replyList;
	}

	public void setReplyList(List<Reply> replyList) {
		this.replyList = replyList;
	}

}
