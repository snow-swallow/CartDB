package service;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Good;

import com.google.gson.Gson;

public class GoodCreateService extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String json = request.getParameter("newGood");
		System.out.println("CREATE GOOD json=" + json);
		Good newGood = new Gson().fromJson(json, Good.class);

		boolean result = Good.createGood(newGood.shopId, newGood.name,
				newGood.logo, newGood.price, newGood.left, newGood.sold);

		response.setStatus(200);
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();
		out.println(result);
		out.flush();
		out.close();
	}

}
