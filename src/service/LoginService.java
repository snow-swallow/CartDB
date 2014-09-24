package service;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Person;
import model.Result;

import com.google.gson.Gson;

public class LoginService extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");

		System.out.println("username=" + username);
		System.out.println("password=" + password);

		Result res = new Result();
		Person p = Person.findPerson(username, password);
		if (null == p) {
			res.succ = false;
			res.body = "";
		} else {
			res.succ = true;
			res.body = new Gson().toJson(p);
		}

		response.setStatus(200);
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();
		out.println(new Gson().toJson(res));
		out.flush();
		out.close();
	}

}
