package serviceimpl;/*
@author Shawn
@creat 2019-07-21-23:17
*/

import dao.OrderDao;
import daoimpl.OrderDaoImpl;
import domain.OrderItem;
import domain.Orders;
import domain.PageBean;
import service.OrderService;
import utils.JDBCUtils;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class OrderServiceImpl implements OrderService {
	@Override
	public void save(Orders orders) throws SQLException {
		Connection conn = null;
		try {
			OrderDao orderDao = new OrderDaoImpl();
			//开启事物
			conn = JDBCUtils.getConnection();
			conn.setAutoCommit(false);//让sql语句变成手动提交

			//1. 保存订单数据
			orderDao.save(orders,conn);
			//2. 保存订单项数据
			List<OrderItem> list = orders.getList();
			for (OrderItem orderItem : list) {
				orderDao.saveOrderItem(orderItem,conn);
			}
			//提交事务
			conn.commit();
		} catch (Exception e) {
			//回滚事务
			conn.rollback();
		}
	}

	//查询登陆用户的订单
	@Override
	public PageBean findOrder(String uid, int pageNumber, int pageSize) throws Exception {
		//直接带着参数调用dao
		OrderDao orderDao = new OrderDaoImpl();
		List<Orders> list = orderDao.findOrder(uid,(pageNumber-1)*pageSize,pageSize);

		//总条数
		int totalCount = orderDao.count(uid);
		//总页数
		int totalPage = 0;
		if(totalCount%pageSize == 0){
			totalPage = totalCount/pageSize;
		}else {
			totalPage = totalCount/pageSize+1;
		}
		//封装pageBean
		PageBean pb = new PageBean();
		pb.setPageNumber(pageNumber);
		pb.setPageSize(pageSize);
		pb.setList(list);
		pb.setTotalCount(totalCount);
		pb.setTotalPage(totalPage);
		return pb;
	}

	//查询订单详情，根据订单的编号oid
	@Override
	public Orders findByOrder(String oid) throws Exception {
		OrderDao orderDao = new OrderDaoImpl();
		return orderDao.findByOrder(oid);
	}

	@Override
	public void update(Orders orders) throws SQLException {
		OrderDao orderDao=new OrderDaoImpl();
		orderDao.update(orders);
	}
}
