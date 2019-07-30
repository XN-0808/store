package web;

import domain.User;
import org.apache.commons.beanutils.BeanUtils;
import service.UserService;
import serviceimpl.UserServiceImpl;
import utils.BaseServlet;
import utils.MailUtils;
import utils.UUIDUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.UUID;

/*
@author Shawn
@creat 2019-07-15-22:00
*/
@WebServlet(name = "UserServlet",urlPatterns = {"/user"})
public class UserServlet extends BaseServlet {

//	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		doGet(request,response);
//	}

//	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		/*//获取参数
//		String method = request.getParameter("method");
//		//根据不同的method，选择不同的方法
//		if ("registerUI".equals(method)){
//			//调到注册页面的方法
//			registerUI(request,response);
//		}else if ("loginUI".equals(method)){
//			//跳到登陆页面的方法
//			loginUI(request,response);
//		}else if ("login".equals(method)){
//			//登录的方法
//		}*/
//		//以上的方法解决了使用一个servlet对应一个模块的问题，但是方法不太好else if 太过频繁
//		//能不能用更好的方法代替？回答：可以,使用——————>反射
//		try {
//
//			Class clazz = this.getClass();
//			//传什么，获取什么方法
//			//通过字节码对象获取到指定的方法
//			//参数一：传过来的方法的名字
//			//参数二：方法的参数类型
//			Method method = clazz.getMethod(request.getParameter("method"), HttpServletRequest.class, HttpServletResponse.class);
//			//让获取到的方法执行(注意下面自己的方法必须是public 类型的，若为private则使用反射会报错)
//			String value = (String)method.invoke(clazz.getDeclaredConstructor().newInstance(), request, response);
//			//做统一的请求转发
//			if (value != null){
//				request.getRequestDispatcher(value).forward(request,response);
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//		// 以上问题完美的优化解决了一个模块下的功能都在一个servlet中
//		// 问题: 但是只有该模块解决  别的模块还得再频繁的书写遍 能不能所有的模块都不用在写了 共享一个
//		// 能  就是把以上代码抽出来定义成一个工具类  供所有模块使用  -----BaseServlet
//
//	}

	//自己创建的方法

	//1. 跳转到注册页面的方法
	public String registerUI(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		request.getRequestDispatcher("/WEB-INF/jsp/register.jsp").forward(request,response);
		return "/jsp/register.jsp";
	}

	//2.跳转到登录页面的方法
	public String loginUI(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		request.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(request,response);
		return "/jsp/login.jsp";
	}

	//3.用户注册功能实现
	public String register(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		try {

			//获取页面上所有的数据
			Map<String, String[]> map = request.getParameterMap();
			//将map的数据给到Javabean
			User user = new User();
			BeanUtils.populate(user,map);

			//当前user还缺少三个东西（uid 激活码 激活状态）
			user.setUid(UUIDUtils.getUUID());
			user.setCode(UUIDUtils.getUUID()+UUIDUtils.getUUID());
			user.setState(0);

			//调用service传递user对象
			UserService userService = new UserServiceImpl();
			userService.save(user);
			System.out.println("-----------------注册成功！------------------");

//			// 给注册的用户发邮件,暂时无法实现，因为电脑配置 易邮 和 foxmail 不成功。
//			String email=user.getEmail();
//			String emailMsg="这是一封激活邮件,请<a href=http://localhost:8080/store/user?method=active&code="+user.getCode()+">点击激活"+user.getCode()+"</a>";
//			MailUtils.sendMail(email, emailMsg);
//			// 给成功提示
//			request.setAttribute("msg", "亲,注册成功啦,赶快去激活吧..");

		} catch (Exception e) {
			e.printStackTrace();//会记录到日志
			//跳到一个页面，提示失败
			request.setAttribute("msg","注册失败，请稍后重试！");
			return "/jsp/info.jsp";
		}
		return null;
	}

	//4.点击激活功能
	public String active(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		//先查数据库有无该用户
		//没有 --提示激活时间过期
		//有，激活
//		System.out.println("激活成功！");
		return null;
	}

	//5.用户登陆功能
	public String login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		try {
			//获取用户名和密码
			String username = request.getParameter("username");
			String password = request.getParameter("password");
			//根据用户名和密码查询数据库
			UserService userService = new UserServiceImpl();
			User user = userService.login(username,password);
			//如果为空，去login.jsp页面提示不存在
			if (user == null){
				request.setAttribute("msg","用户名或者密码错误！");
				return "/jsp/login.jsp";
			}
			//如果不为空，判断用户是否激活
			if (user.getState() != 1){
				//没激活，继续提示
				request.setAttribute("msg","亲，你还没有激活，请到邮箱中激活再登陆！");
				return "/jsp/info.jsp";
			}

			//已经激活，放到session中到首页显示用户信息
			HttpSession session = request.getSession();
			session.setAttribute("user",user);
			//重定向,需要加项目名（这一步中：若项目放在了WEB-INF文件夹中会出现错误）
			//需要注意，重定向能做的请求转发都能做，但是request中存放数据之后，只能用重定向，否则刷新网页会报错
			response.sendRedirect(request.getContextPath()+"/jsp/index.jsp");

		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("msg","登录失败！");
			return "/jsp/info.jsp";
		}
		return null;
	}

	//6.退出功能
	public String quit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		//获取session
		HttpSession session = request.getSession();
		//删除session的user的key
		session.removeAttribute("user");
		//重新走首页
		response.sendRedirect(request.getContextPath()+"/jsp/index.jsp");
		return null;
	}
}
