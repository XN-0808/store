package web;

import service.CategoryService;
import serviceimpl.CategoryServletImpl;
import utils.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

/*
@author Shawn
@creat 2019-07-17-16:57
*/
@WebServlet(name = "CategoryServlet",urlPatterns = {"/category"})
public class CategoryServlet extends BaseServlet {
	//都是自己的方法
	public String findCategory(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//解决浏览器乱码
		response.setCharacterEncoding("utf-8");
		try {
			//调用Service
			CategoryService categoryService = new CategoryServletImpl();
			String json = categoryService.findAll();
//			System.out.println(json);
			//把数据给ajax
			response.getWriter().print(json);
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("msg","查询数据失败...");
			return "/jsp/info.jsp";
		}
		return null;
	}
}
