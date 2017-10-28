package com.kaishengit.dao;

import java.util.ArrayList;
import java.util.List;

import com.kaishengit.util.DbHelp;

public class ArticleLabelDao {
	
	public void add(int articleId,int labelId) {
		String sql = "insert into t_article_label (aid,lid) values (articleId,labelId)";
		DbHelp.update(sql, articleId,labelId);
	}
	
	/**
	 * 对添加的文章标签进行分类
	 * @param articleId
	 * @param idList
	 */
	public void patchAdd(int articleId,List<Integer> idList) {
		String sql = "insert into t_article_label (aid,lid) values";
		List<Integer> params = new ArrayList<>();
		for(Integer id : idList) {
			sql += "(?,?),";
			params.add(articleId);
			params.add(id);
			
		}
		sql = sql.substring(0, sql.length()-1);
		DbHelp.update(sql, params.toArray());
		
	}

	public void delByAid(int aid) {
		String sql = "delete from t_article_label where aid = ?";
		DbHelp.update(sql, aid);
	}
	
	
}
