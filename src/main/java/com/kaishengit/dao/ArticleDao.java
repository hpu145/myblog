package com.kaishengit.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.apache.commons.lang3.StringUtils;

import com.kaishengit.entity.Article;
import com.kaishengit.util.DbHelp;

public class ArticleDao {
	
	public int add(Article article) {
		String sql = "insert into t_article (title,content,scannum,replynum,nodeid,simplecontent,picture) values (?,?,?,?,?,?,?)";
		return DbHelp.insert(sql, article.getTitle(),article.getContent(),article.getScannum(),article.getReplynum(),article.getNodeid(),article.getSimpleContent(),article.getPicture());
	}
	
	public Article findById(int id) {
		String sql = "select * from t_article where id=?";
		return DbHelp.query(sql, new BeanHandler<>(Article.class), id);
	}
	
	public void update(Article article) {
		String sql = "update t_article set title = ? ,content = ?, scannum = ?, replynum = ?, lastreplytime = ?, nodeid = ? where id = ?";
		DbHelp.update(sql, article.getTitle(), article.getContent(), article.getScannum(), article.getReplynum(), article.getLastreplyTime(), article.getNodeid(), article.getId());
	
	}

	public int count(Map<String, String> params) {
		String sql = "select count(*) from t_article a ";
		List<String> arrays = new ArrayList<>();
		
		if (StringUtils.isNotEmpty(params.get("labelId"))) {
			sql += ", t_article_label al where a.id = al.aid and lid = ?";
			arrays.add(params.get("labelId"));
		} else if(StringUtils.isNotEmpty(params.get("nodeId"))) {
			sql += "where a.nodeid = ?";
			arrays.add(params.get("nodeId"));
		} else if(StringUtils.isNotEmpty(params.get("keys"))) {
			String keys = "%" + params.get("keys") + "%";
			sql += "where a.title like ?";
			arrays.add(keys);
		}
		//System.out.println(sql);
		return DbHelp.query(sql, new ScalarHandler<Long>(), arrays.toArray()).intValue();
	}

	
	public List<Article> findAllByParams(Map<String, String> params) {
		
		String sql = "select * from t_article a ";
		
		List<Object> arrays = new ArrayList<>();
		
		if (StringUtils.isNotEmpty(params.get("labelId"))) {
			sql += ", t_article_label al where a.id = al.aid and lid = ?";
			arrays.add(params.get("labelId"));
		} else if(StringUtils.isNotEmpty(params.get("nodeId"))) {
			sql += "where a.nodeid = ?";
			arrays.add(params.get("nodeId"));
		} else if(StringUtils.isNotEmpty(params.get("keys"))) {
			String keys = "%" + params.get("keys") + "%";
			sql += "where a.title like ?";
			arrays.add(keys);
		}
		
		sql += " limit ?,?";
		arrays.add(Integer.parseInt(params.get("start")));
		arrays.add(Integer.parseInt(params.get("pageSize")));
		return DbHelp.query(sql, new BeanListHandler<>(Article.class), arrays.toArray());
	}

	public List<Article> findScanSortList() {
		String sql = "select * from t_article order by scannum desc limit 0,5";
		return DbHelp.query(sql, new BeanListHandler<>(Article.class));
	}

	public void delById(int articleId) {
		String sql = "delete from t_article where id = ?";
		DbHelp.update(sql, articleId);
	}

	public List<Article> findAllByNodeId(int nodeId) {
		String sql = "select * from t_article where nodeid = ?";
		return DbHelp.query(sql, new BeanListHandler<>(Article.class), nodeId);
	}

	public Article findArticleById(int articleId) {
		String sql = "select * from t_article where id = ?";
		return DbHelp.query(sql, new BeanHandler<>(Article.class), articleId);
	}

	public void addReplyNum(Article article) {
		String sql = "update t_article set replynum = ? where id = ?";
		DbHelp.update(sql, article.getReplynum(),article.getId());
		
	}
	
	
	
	
}
