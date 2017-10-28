package com.kaishengit.util;

import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.kaishengit.exception.DataAccessException;

public class DbHelp {
	
	/**
	 * 执行insert updata delete操作
	 * @param sql
	 * @param params
	 * @throws DataAccessException
	 */
	public static void update(String sql,Object...params) throws DataAccessException {
		QueryRunner runner = new QueryRunner(ConnectionManager.getDataSource());
		
		try {
			runner.update(sql,params);
		} catch (SQLException e) {
			throw new DataAccessException("执行" + sql + "异常");
		}
		
	}
	
	/**
	 * 执行insert操作并返回新增数据的主键id
	 * @param sql
	 * @param params
	 * @return
	 * @throws DataAccessException
	 */
	public static int insert(String sql,Object...params) throws DataAccessException{
		QueryRunner runner = new QueryRunner(ConnectionManager.getDataSource());
		try {
			//处理结果集，将Long类型转换为int
			return runner.insert(sql, new ScalarHandler<Long>(),params).intValue();
		} catch (SQLException e) {
			throw new DataAccessException("执行" + sql + "异常");
		}
	}
	
	
	
	/**
	 * 查询
	 * @param sql
	 * @param rsh
	 * @param params
	 * @return
	 * @throws DataAccessException
	 */
	public static <T> T query(String sql, ResultSetHandler<T> rsh,Object... params) 
			throws DataAccessException{
			QueryRunner runner = new QueryRunner(ConnectionManager.getDataSource());
			
			try {
				T t = runner.query(sql, rsh, params);
				return t;
			} catch (SQLException e) {
				//System.out.println(e.getMessage());
				throw new DataAccessException("执行" + sql + "异常");
			}
		}
	
}
