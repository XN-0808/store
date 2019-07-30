package daoimpl;/*
@author Shawn
@creat 2019-07-21-23:27
*/

import dao.OrderDao;
import domain.OrderItem;
import domain.Orders;
import domain.Product;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.*;
import utils.JDBCUtils;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class OrderDaoImpl implements OrderDao {

	//1. 保存订单数据
	@Override
	public void save(Orders orders, Connection conn) throws SQLException {
		QueryRunner qr = new QueryRunner();
		String sql = "insert into orders values(?,?,?,?,?,?,?,?)";
		qr.update(conn,sql, orders.getOid(), orders.getOrder_time(), orders.getTotal(), orders.getState(), orders.getAddress(), orders.getName(), orders.getTelephone(), orders.getUser().getUid());

	}

	//2. 保存订单项数据
	@Override
	public void saveOrderItem(OrderItem orderItem, Connection conn) throws SQLException {
		QueryRunner qr = new QueryRunner();
		String sql = "insert into orderitem values(?,?,?,?,?)";
		qr.update(conn,sql,orderItem.getItemid(),orderItem.getCount(),orderItem.getSubtotal(),orderItem.getProduct().getPid(),orderItem.getOrders().getOid());
	}
	//查询登陆用户的订单
	@Override
	public List<Orders> findOrder(String uid, int pageNumber, int pageSize) throws Exception {
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		String sql = "select * from orders where uid=? limit ?,?";
		List<Orders> list = qr.query(sql, new BeanListHandler<Orders>(Orders.class),uid,pageNumber,pageSize);
		//查看每个订单下的订单项商品
		for (Orders orders : list) {

			//循环的该订单有订单项集合
			List<OrderItem> ordersList = orders.getList();

			String sql2 = "select * from orderitem o,product p where o.oid=? and o.pid=p.pid";
			//获取每个订单下的订单项数据OrderItem
				//MapHandler：将每天数据封装在map中，map的key就是字段名，map的value就是字段值
				//MapListHandler：将每天数据封装在map中，map的key就是字段名，map的value就是字段值
			 	//			      将封装好的map放在list中
				//这里每个订单有多个订单项，所以选择MapListHandler
			List<Map<String, Object>> mapList = qr.query(sql2, new MapListHandler(), orders.getOid());
			//将拿到手的map数据给orderitem
			for (Map<String, Object> map : mapList) {
				//有一个map就有一个OrderItem对象
				OrderItem orderItem = new OrderItem();
				BeanUtils.populate(orderItem,map);
				Product product = new Product();
				BeanUtils.populate(product,map);
				//把封装好的product 给orderItem
				orderItem.setProduct(product);
				//放到订单的订单项集合中
				ordersList.add(orderItem);
			}
		}
		return list;
	}

	//查询用户的订单总条数
	@Override
	public int count(String uid) throws SQLException {
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		String sql = "select count(*) from orders where uid=?";
		Long l = (Long) qr.query(sql, new ScalarHandler(), uid);
		return l.intValue();
	}

	//根据订单oid查询订单详情
	@Override
	public Orders findByOrder(String oid) throws Exception {
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		String sql = "select * from orders where oid=?";
		//订单数据，订单一个oid对应一个订单，使用BeanHandler
		Orders orders = qr.query(sql, new BeanHandler<Orders>(Orders.class), oid);
		//如果订单不为空
		if (orders != null){
			//订单项集合的数据
			List<OrderItem> orderItemsList = orders.getList();
			//获取该订单下所有订单项数据
			String sql2 = "select * from orderitem o,product p where o.oid=? and o.pid=p.pid";
			List<Map<String, Object>> mapList = qr.query(sql2, new MapListHandler(), orders.getOid());
			//map的数据给orderitem
			for (Map<String, Object> map : mapList) {
				OrderItem orderItem = new OrderItem();
				BeanUtils.populate(orderItem,map);
				Product product = new Product();
				BeanUtils.populate(product,map);
				orderItem.setProduct(product);
				//放到订单的订单项集合中
				orderItemsList.add(orderItem);
			}
		}
		return orders;
	}

	@Override
	public void update(Orders orders) throws SQLException {
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		String sql="update orders set state=?,address=?,name=?,telephone=? where oid=?";
		qr.update(sql, orders.getState(),orders.getAddress(),orders.getName(),orders.getTelephone(),orders.getOid());
	}
}




