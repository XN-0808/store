package domain;/*
@author Shawn
@creat 2019-07-21-14:31
*/

public class CartItem {
	//商品对象
	private Product product;
	//商品数量
	private Integer count;
	//商品小计
	private double subTotal;

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	//内部自定义好价格
	public double getSubTotal() {
		//商品单价*数量
		return product.getShop_price()*count;
	}

	//小计金额不允许自定义
//	public void setSubTotal(int subTotal) {
//		this.subTotal = subTotal;
//	}
}
