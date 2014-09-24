package service;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Shop;

import com.google.gson.Gson;

public class ShopUpdateService extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String json = request.getParameter("newShop");
		Shop newShop = new Gson().fromJson(json, Shop.class);

		boolean result = Shop.updateShop(newShop.id, newShop.name,
				newShop.logo, newShop.description, newShop.isOpen);
		response.setStatus(200);
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();
		out.println(result);
		out.flush();
		out.close();
	}

}
