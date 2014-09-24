package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;

public class ConnectionPool {

	private ArrayList<Connection> pool;
	private String url;
	private String username;
	private String password;
	private String driverName;
	private int poolSize = 1;

	private static ConnectionPool instance = null;

	private ConnectionPool() {
		init();
	}

	private void init() {
		pool = new ArrayList<Connection>(poolSize);
		readConfig();
		addConnection();
	}

	public synchronized void release(Connection conn) {
		pool.add(conn);
	}

	public synchronized void closePool() {
		for (int i = 0; i < pool.size(); i++) {
			try {
				((Connection) pool.get(i)).close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			pool.remove(i);
		}
	}

	public static ConnectionPool getInstance() {
		if (null == instance) {
			instance = new ConnectionPool();
		}
		return instance;
	}

	public synchronized Connection getConnection() {
		if (pool.size() > 0) {
			Connection conn = pool.get(0);
			pool.remove(conn);
			return conn;
		} else {
			return null;
		}
	}

	private void addConnection() {
		Connection conn = null;
		for (int i = 0; i < poolSize; i++) {
			try {
				Class.forName(driverName);
				conn = DriverManager.getConnection("jdbc:db2://" + url,
						username, password);
				pool.add(conn);
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}
		}
	}

	private void readConfig() {
		try {
			String path = this.getClass().getClassLoader().getResource("/")
					.getPath()
					+ "\\dbpool.properties";
			FileInputStream is;
			is = new FileInputStream(path);
			Properties props = new Properties();
			props.load(is);

			this.driverName = props.getProperty("driverName");
			this.username = props.getProperty("username");
			this.password = props.getProperty("password");
			this.url = props.getProperty("url");
			this.poolSize = Integer.parseInt(props.getProperty("poolSize"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

