package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import utils.ConnectionPool;

public class Person {

	public String id;
	public String username;
	public String password;
	public String type;

	public static Person findPerson(String username, String password) {
		Person person = null;
		ConnectionPool pool = ConnectionPool.getInstance();
		Connection conn = pool.getConnection();
		PreparedStatement pstmt;
		try {
			pstmt = conn
					.prepareStatement("SELECT ID,USERNAME,PASSWORD,TYPE FROM SYSTEM.PERSON WHERE USERNAME=? AND PASSWORD=? FETCH FIRST 1 ROWS ONLY");
			pstmt.setString(1, username);
			pstmt.setString(2, password);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				// System.out.println(rs.getString("USERNAME"));
				person = new Person();
				person.id = rs.getString("ID");
				person.username = rs.getString("USERNAME");
				person.password = rs.getString("PASSWORD");
				person.type = rs.getString("TYPE");
			}
			rs.close();
			pstmt.close();
			pool.release(conn);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return person;
	}

}
