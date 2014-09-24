package service;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Shop;

import com.google.gson.Gson;

public class ShopService extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws IOException, ServletException {

		List<Shop> shops = Shop.fetchShops();

		String body = "-1";
		if (!shops.isEmpty()) {
			body = new Gson().toJson(shops);
		}
		System.out.println("body=" + body);

		res.setStatus(200);
		res.setContentType("application/json");
		PrintWriter out = res.getWriter();
		out.println(body);
		out.flush();
		out.close();
	}

}
