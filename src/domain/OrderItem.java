package domain;/*
@author Shawn
@creat 2019-07-22-10:44
*/

public class OrderItem {

	private String itemid; //主键
	private Integer count; // 数量
	private double subtotal;//小计
	// 外键 ---2个对象
	private Product product;  // 订单项下的商品
	private Orders orders;  // 订单项所属的订单


	public String getItemid() {
		return itemid;
	}
	public void setItemid(String itemid) {
		this.itemid = itemid;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	public double getSubtotal() {
		return subtotal;
	}
	public void setSubtotal(double subtotal) {
		this.subtotal = subtotal;
	}
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public Orders getOrders() {
		return orders;
	}
	public void setOrders(Orders orders) {
		this.orders = orders;
	}
}
