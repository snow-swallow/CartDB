package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import utils.ConnectionPool;

public class Good {

	public String id;
	public String shopId;
	public String name;
	public String logo;
	public int price;// cent
	public int sold;
	public int left;

	public static List<Good> fetchGoods(String shopId) {
		List<Good> goodList = new ArrayList<Good>();
		ConnectionPool pool = ConnectionPool.getInstance();
		Connection conn = pool.getConnection();
		PreparedStatement pstmt;
		try {
			System.out.println("shopId=" + shopId);
			pstmt = conn
					.prepareStatement("SELECT ID,NAME,LOGO,PRICE,SOLD,LEFT,SHOPID FROM SYSTEM.GOOD WHERE SHOPID=?");
			pstmt.setString(1, shopId);
			ResultSet rs = pstmt.executeQuery();
			System.out.println(null == rs);
			if (null != rs) {
				System.out.println(rs.getFetchSize());
				while (rs.next()) {
					Good good = new Good();
					good.id = rs.getString("ID");
					good.shopId = rs.getString("SHOPID");
					good.logo = rs.getString("LOGO");
					good.name = rs.getString("NAME");
					good.price = rs.getInt("PRICE");
					good.sold = rs.getInt("SOLD");
					good.left = rs.getInt("LEFT");

					goodList.add(good);
				}
				rs.close();
			}
			pstmt.close();
			pool.release(conn);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("goodList.size=" + goodList.size());
		return goodList;
	}

	public static boolean removeGood(String goodId) {
		ConnectionPool pool = ConnectionPool.getInstance();
		Connection conn = pool.getConnection();

		boolean result = false;
		try {
			Statement stmt = conn.createStatement();
			String sql = "DELETE FROM SYSTEM.GOOD WHERE ID=?";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, goodId);
			result = pstmt.execute();
			stmt.close();
			pool.release(conn);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	public static boolean createGood(String shopId, String name, String logo,
			int price, int left, int sold) {
		ConnectionPool pool = ConnectionPool.getInstance();
		Connection conn = pool.getConnection();

		boolean result = false;
		try {

			Statement stmt = conn.createStatement();
			String newIdSQL = "SELECT MAX(INT(ID))+1 AS ID FROM SYSTEM.GOOD";
			ResultSet rs = stmt.executeQuery(newIdSQL);
			while (rs.next()) {
				String newId = rs.getString("ID");
				System.out.println("newId=" + newId);
				String sql = "INSERT INTO SYSTEM.GOOD(ID, SHOPID, NAME, LOGO, PRICE, LEFT, SOLD) VALUES (?,?,?,?,?,?,?)";
				PreparedStatement pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, newId);
				pstmt.setString(2, shopId);
				pstmt.setString(3, name);
				pstmt.setString(4, logo);
				pstmt.setInt(5, price);
				pstmt.setInt(6, left);
				pstmt.setInt(7, sold);
				result = pstmt.execute();

				pstmt.close();
			}
			rs.close();
			stmt.close();
			pool.release(conn);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
}
