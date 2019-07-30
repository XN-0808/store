package serviceimpl;/*
@author Shawn
@creat 2019-07-17-17:04
*/

import dao.CategoryDao;
import daoimpl.CategoryDaoImpl;
import domain.CateGory;
import net.sf.json.JSONArray;
import service.CategoryService;

import java.sql.SQLException;
import java.util.List;

public class CategoryServletImpl implements CategoryService {
	@Override
	public String findAll() throws SQLException {
		CategoryDao categoryDao = new CategoryDaoImpl();
		List<CateGory> list = categoryDao.findAll();
		//把JAVA的list转成json的数据
				//转的是list和数组----JSONArray
				//转的是map和对象--JSONObject
		JSONArray json = JSONArray.fromObject(list);
		return json.toString();
	}
}
