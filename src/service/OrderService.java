package service;/*
@author Shawn
@creat 2019-07-21-23:17
*/

import domain.Orders;
import domain.PageBean;

import java.sql.SQLException;
import java.util.List;

public interface OrderService {
	void save(Orders orders) throws SQLException;

	PageBean findOrder(String uid, int pageNumber, int pageSize) throws Exception;

	Orders findByOrder(String oid) throws Exception;

	void update(Orders orders) throws SQLException;
}
