package web;

import domain.Cart;
import domain.CartItem;
import domain.Product;
import service.CategoryService;
import service.ProductService;
import serviceimpl.CategoryServletImpl;
import serviceimpl.ProductServiceImpl;
import utils.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/*
@author Shawn
@creat 2019-07-21-15:37
*/
@WebServlet(name = "CartServlet",urlPatterns = {"/cart"})
public class CartServlet extends BaseServlet {
	//都是自己的方法

	//获取购物车
	private Cart getCart(HttpServletRequest request) {
		//获取session
		HttpSession session = request.getSession();
		Cart cart = (Cart) session.getAttribute("cart");
		if (cart == null){
			//创建cart
			cart = new Cart();
			//放在session中
			session.setAttribute("cart",cart);
		}
		return cart;
	}

	public String addCart(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		try {
			//获取点击商品的id
			String pid = request.getParameter("pid");

			//获取到product对象
			ProductService productService = new ProductServiceImpl();
			Product product = productService.findBypid(pid);

			//获取用户购买的数量
			Integer count =Integer.parseInt(request.getParameter("count")) ;
//			System.out.println("pid:"+pid+" "+"count:"+count);

			//封装好购物项:商品对象和数量
			CartItem item = new CartItem();
			item.setProduct(product);
			item.setCount(count);

			//把购物项添加到购物车里面
//			Cart cart = new Cart(); 这里不能这么用，因为每个用户只有一个购物车
			Cart cart = getCart(request);
			cart.add(item);
			//放在域对象中
			request.setAttribute("cart",cart);

		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("msg","添加商品失败...");
			return "/jsp/info.jsp";
		}
		return "/jsp/cart.jsp";
	}

	public void remove(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//获取id
		String pid = request.getParameter("pid");
		//获取购物车
		Cart cart = getCart(request);
		//调用购物车删除的方法
		cart.remove(pid);
		//重定向
		response.sendRedirect(request.getContextPath()+"/jsp/cart.jsp");
	}

	public void removeAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//获取购物车
		Cart cart = getCart(request);
		//调用购物车删除的方法
		cart.clear();
		//重定向
		response.sendRedirect(request.getContextPath()+"/jsp/cart.jsp");

	}

}
