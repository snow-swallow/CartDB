package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import utils.ConnectionPool;

public class Shop {
	public String id;
	public String ownerId;
	public String name;
	public String logo;
	public String description;
	public boolean isOpen;

	public static Shop findById(String shopId) {
		ConnectionPool pool = ConnectionPool.getInstance();
		Connection conn = pool.getConnection();
		Shop shop = null;
		try {
			System.out.println("shopId=" + shopId);
			PreparedStatement pstmt = conn
					.prepareStatement("SELECT * FROM SYSTEM.SHOP WHERE ID=? FETCH FIRST 1 ROWS ONLY");
			pstmt.setString(1, shopId);
			ResultSet rs = pstmt.executeQuery();
			if (null != rs) {
				while (rs.next()) {
					shop = new Shop();
					shop.id = rs.getString("ID");
					shop.name = rs.getString("NAME");
					shop.description = rs.getString("DESCRIPTION");
					shop.isOpen = rs.getString("ISOPEN").equalsIgnoreCase(
							"TRUE");
					shop.logo = rs.getString("LOGO");
					shop.ownerId = rs.getString("OWNERID");
				}
				rs.close();
			}
			pstmt.close();
			pool.release(conn);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return shop;
	}

	public static Shop findBySellerId(String sellerId) {
		ConnectionPool pool = ConnectionPool.getInstance();
		Connection conn = pool.getConnection();
		Shop shop = null;
		try {
			System.out.println("sellerId=" + sellerId);
			PreparedStatement pstmt = conn
					.prepareStatement("SELECT * FROM SYSTEM.SHOP WHERE OWNERID=? FETCH FIRST 1 ROWS ONLY");
			pstmt.setString(1, sellerId);
			ResultSet rs = pstmt.executeQuery();
			if (null != rs) {
				while (rs.next()) {
					shop = new Shop();
					shop.id = rs.getString("ID");
					shop.name = rs.getString("NAME");
					shop.description = rs.getString("DESCRIPTION");
					shop.isOpen = rs.getString("ISOPEN").equalsIgnoreCase(
							"TRUE");
					shop.logo = rs.getString("LOGO");
					shop.ownerId = rs.getString("OWNERID");
				}
				rs.close();
			}
			pstmt.close();
			pool.release(conn);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return shop;
	}

	public static List<Shop> fetchShops() {
		ConnectionPool pool = ConnectionPool.getInstance();
		Connection conn = pool.getConnection();

		List<Shop> shops = new ArrayList<Shop>();
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt
					.executeQuery("SELECT * FROM SYSTEM.SHOP WHERE ISOPEN='TRUE'");
			if (null != rs) {
				while (rs.next()) {
					Shop shop = new Shop();
					shop.id = rs.getString("ID");
					shop.name = rs.getString("NAME");
					shop.description = rs.getString("DESCRIPTION");
					shop.isOpen = rs.getString("ISOPEN").equalsIgnoreCase(
							"TRUE");
					shop.logo = rs.getString("LOGO");
					shop.ownerId = rs.getString("OWNERID");

					shops.add(shop);
				}
				rs.close();
			}
			stmt.close();
			pool.release(conn);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return shops;
	}

	public static boolean updateShop(String id, String name, String logo,
			String description, boolean isOpen) {
		ConnectionPool pool = ConnectionPool.getInstance();
		Connection conn = pool.getConnection();

		boolean result = false;
		try {
			Statement stmt = conn.createStatement();
			String sql = "UPDATE SYSTEM.SHOP SET NAME=?, LOGO=?, DESCRIPTION=?, ISOPEN=? WHERE ID=?";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, name);
			pstmt.setString(2, logo);
			pstmt.setString(3, description);
			pstmt.setString(4, isOpen ? "TRUE" : "FALSE");
			pstmt.setString(5, id);
			result = pstmt.execute();
			stmt.close();
			pool.release(conn);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
}
