package service;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.CartItem;

import com.google.gson.Gson;

public class CartItemService extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {

		String ownerId = req.getParameter("ownerId");
		System.out.println("ownerId=" + ownerId);

		List<CartItem> items = CartItem.fetchByOwnerId(ownerId);

		String body;
		if (!items.isEmpty()) {
			body = new Gson().toJson(items);
		} else {
			body = "-1";
		}
		System.out.println("body=" + body);

		resp.setStatus(200);
		resp.setContentType("application/json");
		PrintWriter out = resp.getWriter();
		out.println(body);
		out.flush();
		out.close();
	}

}
