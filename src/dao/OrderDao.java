package dao;/*
@author Shawn
@creat 2019-07-21-23:27
*/

import domain.OrderItem;
import domain.Orders;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface OrderDao {


	void save(Orders orders, Connection conn) throws SQLException;

	void saveOrderItem(OrderItem orderItem, Connection conn) throws SQLException;

	List<Orders> findOrder(String uid, int pageNumber, int pageSize) throws  Exception;

	int count(String uid) throws SQLException;

	Orders findByOrder(String oid) throws SQLException, Exception;

	void update(Orders orders) throws SQLException;
}
