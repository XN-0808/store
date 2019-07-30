package daoimpl;/*
@author Shawn
@creat 2019-07-17-17:07
*/

import dao.CategoryDao;
import domain.CateGory;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import utils.JDBCUtils;

import java.sql.SQLException;
import java.util.List;

public class CategoryDaoImpl implements CategoryDao {
	@Override
	public List<CateGory> findAll() throws SQLException {
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		String sql = "select * from category";
		List<CateGory> list = qr.query(sql,new BeanListHandler<CateGory>(CateGory.class));
		return list;
	}
}
