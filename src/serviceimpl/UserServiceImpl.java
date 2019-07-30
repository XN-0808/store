package serviceimpl;/*
@author Shawn
@creat 2019-07-16-18:06
*/

import dao.UserDao;
import daoimpl.UserDaoImpl;
import domain.User;
import service.UserService;

import java.sql.SQLException;

public class UserServiceImpl implements UserService {
	//注册
	@Override
	public void save(User user) throws SQLException {
		UserDao userDao = new UserDaoImpl();
		userDao.save(user);
	}
	//登陆
	@Override
	public User login(String username, String password) throws SQLException {
		UserDao userDao = new UserDaoImpl();
		return userDao.login(username,password);
	}
}
