package web;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/*
@author Shawn
@creat 2019-07-15-21:46
*/
@WebServlet(name = "ServletDemo1",urlPatterns = {"/sd1"})
public class ServletDemo1 extends HttpServlet {
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request,response);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//首先通过servlet请求转发访问注册页面
		request.getRequestDispatcher("/WEB-INF/jsp/register.jsp").forward(request,response);
	}
}
