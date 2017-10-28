package com.kaishengit.dao;

import java.util.List;

import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.kaishengit.entity.Notify;
import com.kaishengit.util.DbHelp;

public class NotifyDao {

	/**  
	 * 查询消息通知的总数
	 * @return
	 */
	public int count() {
		String sql = "select count(*) from t_notify ";
		return DbHelp.query(sql, new ScalarHandler<Long>()).intValue();
	}

	public List<Notify> findByPage(int start, int pageSize) {
		String sql = "select * from t_notify order by state asc, createtime desc limit ?,?";
		return DbHelp.query(sql, new BeanListHandler<>(Notify.class), start,pageSize);
	}

	public List<Notify> findAll() {
		String sql = "select * from t_notify order by state asc, createtime desc";
		return DbHelp.query(sql, new BeanListHandler<>(Notify.class));
	}

	public void add(Notify notify) {
		String sql = "insert into t_notify (content) values (?)";
		DbHelp.update(sql,notify.getContent());
	}

	public Notify findById(int id) {
		String sql = "select * from t_notify where id = ?";
		return DbHelp.query(sql, new BeanHandler<>(Notify.class),id);
	}

	public void update(Notify notify) {
		String sql = "update t_notify set readtime = ? , state = ? where id = ?";
		DbHelp.update(sql, notify.getReadTime(),notify.getState(),notify.getId());
	}
	

}
