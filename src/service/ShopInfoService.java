package service;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Shop;

import com.google.gson.Gson;

public class ShopInfoService extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String shopId = request.getParameter("shopId");
		Shop shop = Shop.findById(shopId);
		String body;
		if (null != shop) {
			body = new Gson().toJson(shop);
		} else {
			body = "-1";
		}
		response.setStatus(200);
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();
		out.println(body);
		out.flush();
		out.close();
	}

}
