package serviceimpl;/*
@author Shawn
@creat 2019-07-17-21:47
*/

import dao.ProductDao;
import daoimpl.ProductDaoImpl;
import domain.PageBean;
import domain.Product;
import service.ProductService;

import java.sql.SQLException;
import java.util.List;

public class ProductServiceImpl implements ProductService {
	@Override
	public PageBean findProduct(String cid, int pageNumber, int pageSize) throws SQLException {
		//创建DAO
		ProductDao productDao = new ProductDaoImpl();
		//获取总条数
		int totalCount = productDao.findCount(cid);
		//获取总页数
		int totalPages = 0;
		if (totalCount%pageSize == 0){
			totalPages = totalCount/pageSize;
		}else {
			totalPages = totalCount/pageSize + 1;
		}
		//获取每页显示的数据
		List<Product> list = productDao.findList(cid,(pageNumber-1)*pageSize,pageSize);
		//创建PageBean,完成封装
		PageBean pb = new PageBean();
		pb.setPageNumber(pageNumber);//当前页数
		pb.setPageSize(pageSize);//每页条数
		pb.setTotalCount(totalCount);//总条数
		pb.setTotalPage(totalPages);//总页数
		pb.setList(list);//每页显示的数据
		return pb;
	}

	@Override
	public Product findBypid(String pid) throws SQLException {
		//调用Dao方法传递参数
		ProductDao productDao = new ProductDaoImpl();
		Product product = productDao.findBypid(pid);
		return product;
	}
	//查热门
	@Override
	public List<Product> findHot() throws SQLException {
		ProductDao productDao = new ProductDaoImpl();
		return productDao.findHot();
	}
	//查最新
	@Override
	public List<Product> findNew() throws SQLException {
		ProductDao productDao = new ProductDaoImpl();
		return productDao.findNew();
	}
}
