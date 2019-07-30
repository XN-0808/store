package domain;/*
@author Shawn
@creat 2019-07-17-22:17
*/

import java.util.List;

public class PageBean<T> {
	//当前页数
	private Integer pageNumber;
	//每页显示的条数
	private Integer pageSize;
	//总条数
	private Integer totalCount;
	//总页数
	private Integer totalPage;
	//每页显示的数据
	private List<T> list;

	public Integer getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(Integer pageNumber) {
		this.pageNumber = pageNumber;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}

	public Integer getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(Integer totalPage) {
		this.totalPage = totalPage;
	}

	public List<T> getList() {
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}
}
