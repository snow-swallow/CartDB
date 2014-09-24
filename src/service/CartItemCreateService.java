package service;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.CartItem;

import com.google.gson.Gson;

public class CartItemCreateService extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String json = req.getParameter("newItem");
		System.out.println("CREATE CARTITEM json=" + json);
		CartItem newItem = new Gson().fromJson(json, CartItem.class);

		String result = CartItem.create(newItem.goodId, newItem.ownerId,
				newItem.amount);

		resp.setStatus(200);
		resp.setContentType("application/json");
		PrintWriter out = resp.getWriter();
		out.println(result);
		out.flush();
		out.close();
	}
}
