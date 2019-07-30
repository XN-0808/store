package dao;/*
@author Shawn
@creat 2019-07-17-21:50
*/

import domain.Product;

import java.sql.SQLException;
import java.util.List;

public interface ProductDao {
	List<Product> findList(String cid, int i, int pageSize) throws SQLException;

	int findCount(String cid) throws SQLException;

	Product findBypid(String pid) throws SQLException;

	List<Product> findHot() throws SQLException;

	List<Product> findNew() throws SQLException;
}
