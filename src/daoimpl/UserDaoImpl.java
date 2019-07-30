package daoimpl;/*
@author Shawn
@creat 2019-07-16-18:10
*/

import dao.UserDao;
import domain.User;
import javafx.beans.binding.ObjectBinding;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import utils.JDBCUtils;

import java.sql.SQLException;

public class UserDaoImpl  implements UserDao {
	//注册
	@Override
	public void save(User user) throws SQLException {
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		String sql = "insert into user values(?,?,?,?,?,?,?,?,?)";
		Object[] obj={user.getUid(),user.getUsername(),user.getPassword(),user.getName(),user.getEmail(),user.getBirthday(),user.getSex(),user.getState(),user.getCode()};
		qr.update(sql,obj);
	}
	//登陆
	@Override
	public User login(String username, String password) throws SQLException {
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		String sql = "select * from user where username=? and password=?";
		return qr.query(sql,new BeanHandler<User>(User.class),username,password);
	}
}
