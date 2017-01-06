package cn.edu.cuit.jade;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

public class DBUtils {

	public static final String JDBC_DRIVER = "jdbc.driver";   //$NON-NLS-1$
	public static final String JDBC_URL = "jdbc.url";   //$NON-NLS-1$
	public static final String JDBC_USER = "jdbc.user";   //$NON-NLS-1$
	public static final String JDBC_PASS = "jdbc.pass";   //$NON-NLS-1$
	
	private static DataSource dataSource;
	private Connection connection;
	
	public Connection getConnection() {
		try {
			connection = DBUtils.dataSource.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return connection;
	}
}
