package com.kaishengit.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.apache.commons.lang3.StringUtils;

import com.kaishengit.entity.Reply;
import com.kaishengit.util.DbHelp;

public class ReplyDao {

	public List<Reply> findAllByArticleId(int articleId) {
		String sql = "select * from t_reply where articleId = ? order by createtime";
		return DbHelp.query(sql, new BeanListHandler<>(Reply.class), articleId);
	}

	public void add(Reply reply) {
		String sql = "insert into t_reply (content, userip, articleid,pid) values (?,?,?,?)";
		DbHelp.update(sql, reply.getContent(), reply.getUserip(), reply.getArticleId(),reply.getPid());
	}

	public void delByArticleId(int articleId) {
		String sql = "delete from t_reply where articleid = ?";
		DbHelp.update(sql, articleId);
	}

	public int countByParam(String keys) {
		String sql = "select count(*) from t_reply ";//注意空格
		List<String> arrays = new ArrayList<>();
		if(StringUtils.isNotEmpty(keys)) {
			keys = "%" + keys + "%";
			sql += "where content like ?";
			arrays.add(keys);
		}
		return DbHelp.query(sql, new ScalarHandler<Long>(), arrays.toArray()).intValue();
		
	}

	public List<Reply> findListByParam(String keys, int start, int pageSize) {
		//String sql = "select * from t_reply ";//注意空格
		String sql = "select a.title,r.id,r.content,r.userip,r.createtime from t_article a, t_reply r where a.id = r.articleid ";
		List<Object> arrays = new ArrayList<>();
		if(StringUtils.isNotEmpty(keys)) {
			keys = "%" + keys + "%"; //模糊查询
			sql += "and a.title like ? ";
			arrays.add(keys);
		}
		sql += "limit ?,?";
		arrays.add(start);
		arrays.add(pageSize);
		return DbHelp.query(sql, new BeanListHandler<>(Reply.class), arrays.toArray());
	}

	public void delReplyById(int replyId) {
		String sql = "delete from t_reply where id = ?";
		DbHelp.update(sql, replyId);
	}

	public Reply findReplyById(String id) {
		String sql = "select * from t_reply where id=?";
		return DbHelp.query(sql, new BeanHandler<>(Reply.class),id);
	}

}
