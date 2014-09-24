package utils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnPoolTest {
	public static void main(String[] args) throws SQLException {
		String sql = "SELECT * FROM SYSTEM.PERSON";
		long start = System.currentTimeMillis();
		ConnectionPool pool = ConnectionPool.getInstance();
		Connection conn = pool.getConnection();
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(sql);
		while (rs.next()) {
			System.out.println(rs.getString("USERNAME"));
		}
		rs.close();
		stmt.close();
		pool.release(conn);
		System.out.println("cost: " + (System.currentTimeMillis() - start));
	}
}
