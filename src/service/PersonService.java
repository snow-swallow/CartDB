package service;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

public class PersonService extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		System.err.println("--------");
		System.out.println("contentType= " + request.getContentType());
		System.out.println("param1=" + request.getParameter("param1"));
		response.setStatus(200);
		response.setContentType("application/json");
		Map<String, String> params = new HashMap<String, String>();
		params.put("result", "succ");
		PrintWriter out = response.getWriter();
		out.println(new Gson().toJson(params));
		out.flush();
		out.close();
	}
}
