package filter;

import domain.User;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

//权限过滤器：没有登陆无法访问订单和订单详情
@WebFilter(filterName = "QxFilter",urlPatterns = {"/jsp/order_info.jsp" ,"/jsp/order_list.jsp"})
public class QxFilter implements Filter
{
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// 获取session
		HttpServletRequest request=(HttpServletRequest)req;
		HttpSession session = request.getSession();
		
		// 从session中获取用户
		User user =(User)session.getAttribute("user");
		// 判断用户是否存在
		if(user==null)
		{
			request.setAttribute("msg", "亲,你没有权限访问该资源,需要先登录");
			request.getRequestDispatcher("/jsp/info.jsp").forward(request, response);
			return;
		}
		// 存在 --放行
		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

}
