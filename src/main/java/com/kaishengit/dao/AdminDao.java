package com.kaishengit.dao;

import org.apache.commons.dbutils.handlers.BeanHandler;

import com.kaishengit.entity.Admin;
import com.kaishengit.util.DbHelp;

public class AdminDao {

	public Admin findAdminByUsername(String username) {
		String sql = "select * from t_admin where name = ?";
		return DbHelp.query(sql, new BeanHandler<>(Admin.class), username);
		
	}
	
	
	
}
