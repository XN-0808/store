package domain;/*
@author Shawn
@creat 2019-07-21-23:01
*/

import java.util.ArrayList;
import java.util.List;

public class Orders {
	private String oid;//主键
	private String order_time;//下单时间
	private Double total;//总金额
	private Integer state;//订单状态  0:未付款 1：未发货 2：未评价 3：已完成
	//这三项暂时可以不获取
	private String address;//收货地址
	private String name;//收货人姓名
	private String telephone;//收货人电话
	//所属用户——外键
	private User user;		//订单属于哪个用户

	//订单项的数据
	List<OrderItem> list = new ArrayList<>();

	public List<OrderItem> getList() {
		return list;
	}

	public void setList(List<OrderItem> list) {
		this.list = list;
	}




	public String getOid() {
		return oid;
	}

	public void setOid(String oid) {
		this.oid = oid;
	}

	public String getOrder_time() {
		return order_time;
	}

	public void setOrder_time(String order_time) {
		this.order_time = order_time;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
