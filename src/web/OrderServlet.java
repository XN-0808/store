package web;

import domain.*;
import service.OrderService;
import serviceimpl.OrderServiceImpl;
import utils.BaseServlet;
import utils.PaymentUtil;
import utils.UUIDUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/*
@author Shawn
@creat 2019-07-21-22:36
*/
@WebServlet(name = "OrderServlet",urlPatterns = {"/order"})
public class OrderServlet extends BaseServlet {

	//生成订单
	public String addOrder(HttpServletRequest request, HttpServletResponse response){
		try {
			//判断用户是否登陆，/jsp/head.jsp/被静态包含在/jsp/cart.jsp中，而user存在于head.jsp中的session域中
			//所以这里可以直接get得到session域中的内容
			HttpSession session = request.getSession();
			User user = (User) session.getAttribute("user");
			if (user == null){
				request.setAttribute("msg","下单前需要先登录...");
				return "/jsp/info.jsp";
			}
			//记录订单的数据到数据库   用对象来封装
			//订单的订单号
			//订单的下单时间
			//订单的状态
			//订单的总金额

			//订单的所属用户
			Orders orders = new Orders();
			//设置订单ID
			orders.setOid(UUIDUtils.getUUID());
			//读取当前时间
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			String date = dateFormat.format(new Date());
			orders.setOrder_time(date);
			//设置状态
			orders.setState(0);
			//订单的总金额在购物车中
			Cart cart = (Cart) session.getAttribute("cart");
			orders.setTotal(cart.getTotal());
			//设置用户
			orders.setUser(user);

			//获取订单中所有订单项的集合,添加订单项
			List<OrderItem> orderItemsList = orders.getList();

			//保存订单商品购物项
					//主键uuid
					//订单商品的数量
					//订单商品的小计
					//订单下的商品
					//订单商品属于哪个订单
			//调用service保存订单商品项(对象)到orderitem

			//把购物项转成订单项
			Map<String, CartItem> map = cart.getMap();
			for (String key : map.keySet()) {
				//循环一次创建一个订单项对象
				OrderItem orderItem = new OrderItem();
				//将购物项的数据转换给订单项
				orderItem.setItemid(UUIDUtils.getUUID());
				orderItem.setCount(map.get(key).getCount());
				orderItem.setSubtotal(map.get(key).getSubTotal());
				orderItem.setProduct(map.get(key).getProduct());
				orderItem.setOrders(orders);
				//添加订单项
				orderItemsList.add(orderItem);
			}

			//调用Service 传递对象数据 到数据库Orders保存
			OrderService orderService = new OrderServiceImpl();
			//保存订单
			orderService.save(orders);

			//清空购物车
			cart.clear();

			//保存订单项和订单项商品的数据到request域中
			request.setAttribute("orders",orders);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return "/jsp/order_info.jsp";
	}

	//查询登陆用户的订单
	public String findOrder(HttpServletRequest request, HttpServletResponse response){
		try {
			//获取当前页
			int pageNumber = Integer.parseInt(request.getParameter("pageNumber"));
			//设置每页显示的条数
			int pageSize = 3;
			//获取当前登陆的用户
			HttpSession session = request.getSession();
			User user = (User) session.getAttribute("user");
			//调用service传递参数
			OrderServiceImpl orderService = new OrderServiceImpl();
			PageBean pb = orderService.findOrder(user.getUid(), pageNumber, pageSize);
			//放到域对象中，带到页面展示
			request.setAttribute("pb",pb);

		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("msg","查询订单失败...");
			return "/jsp/info.jsp";
		}
		return "/jsp/order_list.jsp";
	}
	//查询指定订单的详情
	public String findByoid(HttpServletRequest request, HttpServletResponse response){

		try {
//		System.out.println("查询指定订单详情成功！");
			//点击获取订单的订单号
			String oid = request.getParameter("oid");
			//根据点击的订单号，查出来该订单以及该订单下面的订单项信息
			OrderService orderService = new OrderServiceImpl();
//		需要有订单的数据还有订单项的集合
			Orders orders = orderService.findByOrder(oid);
			//把封装好的orders放到request域中
			request.setAttribute("orders",orders);
		} catch (Exception e) {
			e.printStackTrace();
		}

		//返回到订单详情页面显示
		return "/jsp/order_info.jsp";
	}

	// 付款
	public String payOrders(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		try {

			// 获取订单id
			String oid = request.getParameter("oid");
			// 根据oid获取订单对象
			OrderService orderService=new OrderServiceImpl();
			Orders orders = orderService.findByOrder(oid);
			// 获取收货人
			String name = request.getParameter("name");
			// 获取地址
			String address = request.getParameter("address");
			// 获取电话
			String telephone = request.getParameter("telephone");
			// 封装orders剩下数据
			orders.setName(name);
			orders.setTelephone(telephone);
			orders.setAddress(address);

			// 调用service 传参  做一个通用的修改
			orderService.update(orders);

			// 付款 --第三方
			String p0_Cmd = "Buy";
			String p1_MerId = "10001126856";
			String p2_Order = orders.getOid(); // 订单号
			String p3_Amt = "0.01";//测试用1分钱，真正开发中用order.getTotal();
			String p4_Cur = "CNY";
			String p5_Pid = "";
			String p6_Pcat = "";
			String p7_Pdesc = "";
			String p8_Url = "http://localhost:8080"+request.getContextPath()+"/order?method=callBack";
			String p9_SAF = "0";
			String pa_MP = "";
			String pd_FrpId = request.getParameter("pd_FrpId");
			String pr_NeedResponse = "1";
			// 电子签名
			String hmac = PaymentUtil.buildHmac(p0_Cmd, p1_MerId, p2_Order, p3_Amt, p4_Cur, p5_Pid, p6_Pcat, p7_Pdesc, p8_Url, p9_SAF, pa_MP, pd_FrpId, pr_NeedResponse, "69cl522AV6q613Ii4W6u8K6XuW8vM1N6bFgyv769220IuYe9u37N4y7rI4Pl");


			StringBuffer buffer = new StringBuffer("https://www.yeepay.com/app-merchant-proxy/node?");
			buffer.append("p0_Cmd="+p0_Cmd);
			buffer.append("&p1_MerId="+p1_MerId);
			buffer.append("&p2_Order="+p2_Order);
			buffer.append("&p3_Amt="+p3_Amt);
			buffer.append("&p4_Cur="+p4_Cur);
			buffer.append("&p5_Pid="+p5_Pid);
			buffer.append("&p6_Pcat="+p6_Pcat);
			buffer.append("&p7_Pdesc="+p7_Pdesc);
			buffer.append("&p8_Url="+p8_Url);
			buffer.append("&p9_SAF="+p9_SAF);
			buffer.append("&pa_MP="+pa_MP);
			buffer.append("&pd_FrpId="+pd_FrpId);
			buffer.append("&pr_NeedResponse="+pr_NeedResponse);
			buffer.append("&hmac="+hmac);

			response.sendRedirect(buffer.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}


		return null;
	}

	// 支付成功会跳到项目的这个方法中
	public String callBack(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		try {
			// 获取支付的订单id
			String r6_Order = request.getParameter("r6_Order");
			// 修改状态为1 已付款
			OrderService orderService=new OrderServiceImpl();
			Orders order = orderService.findByOrder(r6_Order);
			order.setState(1);
			// 修改
			orderService.update(order);

			// 给页面显示支付成功或则失败
			request.setAttribute("msg","恭喜你,支付成功,你为"+r6_Order+"支付了"+request.getParameter("r3_Amt")+"钱");

		} catch (Exception e) {
			// TODO: handle exception
		}
		return "/jsp/info.jsp";
	}
}
