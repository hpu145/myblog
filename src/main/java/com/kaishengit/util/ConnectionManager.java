package com.kaishengit.util;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;

import com.kaishengit.exception.DataAccessException;

public class ConnectionManager {

	private static String DRIVER;
	private static String URL;
	private static String USERNAME;
	private static String PASSWORD;
	private static BasicDataSource datasource = new BasicDataSource();

	static {
		DRIVER = Config.get("jdbc.driver");
		URL = Config.get("jdbc.url");
		USERNAME = Config.get("jdbc.username");
		PASSWORD = Config.get("jdbc.password");

		datasource.setDriverClassName(DRIVER);
		datasource.setUrl(URL);
		datasource.setUsername(USERNAME);
		datasource.setPassword(PASSWORD);

		datasource.setInitialSize(5);
		datasource.setMaxIdle(20);
		datasource.setMinIdle(5);
		datasource.setMaxWaitMillis(3000);
	}

	/**
	 * 获取数据库连接池
	 * 
	 * @return
	 */
	public static DataSource getDataSource() {
		return datasource;
	}

	public static Connection getConnection() {
		Connection conn = null;
		try {
			Class.forName(DRIVER);
			conn = datasource.getConnection();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			throw new DataAccessException("数据库连接异常");
		}
		return conn;
	}

}
