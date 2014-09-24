package service;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Good;

import com.google.gson.Gson;

public class GoodService extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws IOException, ServletException {
		String shopId = req.getParameter("shopId");

		List<Good> goods = Good.fetchGoods(shopId);

		String body;
		if (!goods.isEmpty()) {
			body = new Gson().toJson(goods);
		} else {
			body = "-1";
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
