package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import utils.ConnectionPool;

public class CartItem {

	public String id;
	public String ownerId;
	public String goodId;
	public int amount;
	public Good good;

	public static List<CartItem> fetchByOwnerId(String ownerId) {
		List<CartItem> itemList = new ArrayList<CartItem>();
		ConnectionPool pool = ConnectionPool.getInstance();
		Connection conn = pool.getConnection();
		PreparedStatement pstmt;
		try {
			pstmt = conn
					.prepareStatement("SELECT g.ID AS GID,g.NAME,g.LOGO,g.PRICE,g.SOLD,g.LEFT,g.SHOPID,c.ID AS CID,c.OWNERID,c.GOODID,c.AMOUNT "
							+ "FROM SYSTEM.GOOD g, SYSTEM.CARTITEM c WHERE c.GOODID=g.ID AND c.OWNERID=?");
			pstmt.setString(1, ownerId);
			ResultSet rs = pstmt.executeQuery();
			System.out.println(null == rs);
			if (null != rs) {
				System.out.println(rs.getFetchSize());
				while (rs.next()) {
					Good good = new Good();
					good.id = rs.getString("GID");
					good.shopId = rs.getString("SHOPID");
					good.logo = rs.getString("LOGO");
					good.name = rs.getString("NAME");
					good.price = rs.getInt("PRICE");
					good.sold = rs.getInt("SOLD");
					good.left = rs.getInt("LEFT");

					CartItem item = new CartItem();
					item.good = good;
					item.id = rs.getString("CID");
					item.ownerId = rs.getString("OWNERID");
					item.amount = rs.getInt("AMOUNT");

					itemList.add(item);
				}
				rs.close();
			}
			pstmt.close();
			pool.release(conn);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("itemList.size=" + itemList.size());
		return itemList;
	}

	public static boolean remove(String itemId) {
		ConnectionPool pool = ConnectionPool.getInstance();
		Connection conn = pool.getConnection();

		boolean result = false;
		try {
			Statement stmt = conn.createStatement();
			String sql = "DELETE FROM SYSTEM.CARTITEM WHERE ID=?";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, itemId);
			result = pstmt.execute();
			stmt.close();
			pool.release(conn);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	public static String create(String goodId, String ownerId, int amount) {
		ConnectionPool pool = ConnectionPool.getInstance();
		Connection conn = pool.getConnection();
		String id = "-1";
		try {

			Statement stmt = conn.createStatement();
			String newIdSQL = "SELECT MAX(INT(ID))+1 AS ID FROM SYSTEM.CARTITEM";
			ResultSet rs = stmt.executeQuery(newIdSQL);
			while (rs.next()) {
				String newId = rs.getString("ID");
				System.out.println("newId=" + newId);
				String sql = "INSERT INTO SYSTEM.CARTITEM(ID, GOODID, OWNERID, AMOUNT) VALUES (?,?,?,?)";
				PreparedStatement pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, newId);
				pstmt.setString(2, goodId);
				pstmt.setString(3, ownerId);
				pstmt.setInt(4, amount);
				int result = pstmt.executeUpdate();
				if (result > 0) {
					id = newId;
				}
				pstmt.close();
			}
			rs.close();
			stmt.close();
			pool.release(conn);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return id;
	}
}
