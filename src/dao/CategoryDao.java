package dao;/*
@author Shawn
@creat 2019-07-17-17:06
*/

import domain.CateGory;

import java.sql.SQLException;
import java.util.List;

public interface CategoryDao {
	List<CateGory> findAll() throws SQLException;
}
