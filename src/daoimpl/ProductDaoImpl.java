package daoimpl;/*
@author Shawn
@creat 2019-07-17-21:50
*/

import dao.ProductDao;
import domain.Product;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import utils.JDBCUtils;

import java.sql.SQLException;
import java.util.List;

public class ProductDaoImpl implements ProductDao {

	@Override
	public int findCount(String cid) throws SQLException {
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		String sql = "select count(*) from product where cid=?";
		//ScalarHandler针对的都是SQL中的聚合函数：min max count avg sum
		Long l = (Long) qr.query(sql, new ScalarHandler(), cid);
		return l.intValue();
	}

	@Override
	public List<Product> findList(String cid, int i, int pageSize) throws SQLException {
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		String sql = "select * from product where cid=? limit ?,? ";
		List<Product> list = qr.query(sql, new BeanListHandler<Product>(Product.class), cid, i, pageSize);
		return list;
	}

	//查询单个商品信息
	@Override
	public Product findBypid(String pid) throws SQLException {
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		String sql = "select * from product where pid=?";
		Product product = qr.query(sql,new BeanHandler<Product>(Product.class),pid);
		return product;
	}

	@Override
	public List<Product> findHot() throws SQLException {
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		String sql = "select * from product where is_hot=1 and pflag=0 limit 0,9";
		List<Product> list = qr.query(sql, new BeanListHandler<Product>(Product.class));
		return list;
	}

	@Override
	public List<Product> findNew() throws SQLException {
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		String sql = "SELECT * FROM product WHERE pflag=0 ORDER BY pdate DESC LIMIT 0,9";
		List<Product> list = qr.query(sql, new BeanListHandler<Product>(Product.class));
		return list;
	}
}
