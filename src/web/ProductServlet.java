package web;

import domain.PageBean;
import domain.Product;
import service.ProductService;
import serviceimpl.ProductServiceImpl;
import utils.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/*
@author Shawn
@creat 2019-07-17-21:32
*/
@WebServlet(name = "ProductServlet",urlPatterns = {"/product"})
public class ProductServlet extends BaseServlet {
	//查询某个分类下的所有商品
	public String findProduct(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		try {
			//获取点击的分类ID
			String cid = request.getParameter("cid");
			//获取当前页数
			int pageNumber = Integer.parseInt(request.getParameter("pageNumber"));
			//设置每页显示的条数
			int pageSize = 12;
			//调用service传递参数
			ProductService productService = new ProductServiceImpl();
			PageBean pb = productService.findProduct(cid,pageNumber,pageSize);
			//把cid带回到product_list.jsp中
			request.setAttribute("cid",cid);
			//放到request中的product_list.jsp展示数据
			request.setAttribute("pb",pb);
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("msg","查询失败...");
			return "/jsp/info.jsp";
		}
		return "/jsp/product_list.jsp";
	}
	//查看商品详情
	public String findBypid(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		Product product = null;
		try {
			//获取到参数
			String pid = request.getParameter("pid");
//			System.out.println("pid为："+pid);
			//调用Service传递参数
			ProductService productService = new ProductServiceImpl();
			Product singleProduct = productService.findBypid(pid);
//			System.out.println("查看取到的商品信息："+singleProduct.getPname()+"-"+singleProduct.getPdesc());
			//封装对象，请求转发到	"/jsp/product_info.jsp"
			request.setAttribute("singleProduct",singleProduct);
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("msg","查询商品详情失败....T_T");
			return "/jsp/info.jsp";
		}
		return "/jsp/product_info.jsp";
	}
	//查热门和最新商品
	public String findList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{

		try {
			//调用service
			ProductService productService = new ProductServiceImpl();
			//查热门
			List<Product> hotlist = productService.findHot();
			//查最新
			List<Product> newlist = productService.findNew();
			request.setAttribute("hotlist",hotlist);
			request.setAttribute("newlist",newlist);
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("msg","查询热门商品失败....T_T....");
			return "/jsp/info.jsp";
		}
		return "/jsp/index.jsp";
	}
}
