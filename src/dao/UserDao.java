package dao;/*
@author Shawn
@creat 2019-07-16-18:10
*/

import domain.User;

import java.sql.SQLException;

public interface UserDao {
	void save(User user) throws SQLException;

	User login(String username, String password) throws SQLException;
}
