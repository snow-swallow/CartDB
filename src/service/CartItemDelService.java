package service;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.CartItem;

public class CartItemDelService extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String itemId = req.getParameter("itemId");
		boolean result = CartItem.remove(itemId);

		resp.setStatus(200);
		resp.setContentType("application/json");
		PrintWriter out = resp.getWriter();
		out.println(result);
		out.flush();
		out.close();
	}

}
