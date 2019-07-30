package service;/*
@author Shawn
@creat 2019-07-17-21:47
*/

import domain.PageBean;
import domain.Product;

import java.sql.SQLException;
import java.util.List;

public interface ProductService {
	PageBean findProduct(String cid, int pageNMumber, int pageSize) throws SQLException;

	Product findBypid(String pid) throws SQLException;

	List<Product> findHot() throws SQLException;

	List<Product> findNew() throws SQLException;
}
