package service;/*
@author Shawn
@creat 2019-07-16-18:08
*/

import domain.User;

import java.sql.SQLException;

public interface UserService {
	void save(User user) throws SQLException;

	User login(String username, String password) throws SQLException;
}
