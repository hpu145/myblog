package com.kaishengit.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.apache.commons.lang3.StringUtils;

import com.kaishengit.entity.Node;
import com.kaishengit.util.DbHelp;

public class NodeDao {

	public List<Node> findAll() {
		String sql = "select * from t_node";
		return DbHelp.query(sql, new BeanListHandler<>(Node.class));

	}

	public Node findById(int nodeid) {
		String sql = "select * from t_node where id = ?";
		return DbHelp.query(sql, new BeanHandler<>(Node.class), nodeid);
	}

	public int countByParam(String keys) {
		String sql = "select count(*) from t_node ";//注意空格
		List<String> arrays = new ArrayList<>();
		if(StringUtils.isNotEmpty(keys)) {
			keys = "%" + keys + "%";
			sql += "where name like ?";
			arrays.add(keys);
		}
		return DbHelp.query(sql, new ScalarHandler<Long>(), arrays.toArray()).intValue();
	}

	public List<Node> findListByParam(String keys, int start, int pageSize) {
		String sql = "select * from t_node ";//注意空格
		List<Object> arrays = new ArrayList<>();
		if(StringUtils.isNotEmpty(keys)) {
			keys = "%" + keys + "%"; //模糊查询
			sql += "where name like ? ";
			arrays.add(keys);
		}
		sql += "limit ?,?";
		arrays.add(start);
		arrays.add(pageSize);
		return DbHelp.query(sql, new BeanListHandler<>(Node.class), arrays.toArray());
	}

	public Node findByName(String nodeName) {
		String sql = "select * from t_node where name = ?";
		return DbHelp.query(sql, new BeanHandler<>(Node.class), nodeName);
	}

	public void add(Node node) {
		String sql = "insert into t_node (name) values (?)";
		DbHelp.update(sql, node.getName());
	}

	public void del(int nodeId) {
		String sql = "delete from t_node where id = ?";
		DbHelp.update(sql, nodeId);
	}

	public void update(Node node) {
		String sql = "update t_node set name = ? where id = ?";
		DbHelp.update(sql, node.getName(), node.getId());
	}
	
	
}
