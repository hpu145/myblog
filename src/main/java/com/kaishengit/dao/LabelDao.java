package com.kaishengit.dao;

import java.util.List;

import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.kaishengit.entity.Label;
import com.kaishengit.util.DbHelp;

public class LabelDao {
	public Label findLabelByName(String name) {
		String sql = "select * from t_label where name =?";
		return DbHelp.query(sql, new BeanHandler<>(Label.class), name);
	}
	
	public int add(Label label) {
		String sql = "insert into t_label (name) values (?)";
		return DbHelp.insert(sql, label.getName());
	}

	public List<Label> findLabelByArticleId(int id) {
		String sql = "select l.id , l.name from t_label l , t_article_label al where l.id = al.lid and aid = ?";
		return DbHelp.query(sql, new BeanListHandler<>(Label.class), id);
		
	}

	
	
}
