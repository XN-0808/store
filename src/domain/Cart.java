package domain;/*
@author Shawn
@creat 2019-07-21-14:32
*/

import java.util.LinkedHashMap;
import java.util.Map;

//购物车
public class Cart {

	//购物项的集合,选择map是因为map删除商品更快，根据商品的key直接就可以删除商品
	//不能使用hashMap，因为购物车需要是有序的，因此选择LinkedHashMap
	Map<String, CartItem> map = new LinkedHashMap<>();

	//总金额
	private double total;

	public Map<String, CartItem> getMap() {
		return map;
	}

	public void setMap(Map<String, CartItem> map) {
		this.map = map;
	}

	public double getTotal() {
		return total;
	}

//	public void setTotal(double total) {
//		this.total = total;
//	}
	//设置金额三种情况: 清空 添加 删除
	public void clear() {
		map.clear();
		total = 0;
	}

	public void remove(String key){
		//去购物车集合中根据key删除对应的购物项
		CartItem item = map.remove(key);
		//计算总金额
		total = total - item.getSubTotal();
	}

	public void add(CartItem cartItem){
		//判断添加进来的购物项是否存在于集合之中
		CartItem item = map.get(cartItem.getProduct().getPid());

		if (item == null){
			//如果没有，直接添加到购物项之中，总金额=总金额新添加进来的商品小计
			map.put(cartItem.getProduct().getPid(),cartItem);
			total = total + cartItem.getSubTotal();
		}else{
			//如果有，将存在于集合之中的购物项数量进行累加，总金额=总金额新添加进来的商品小计
			item.setCount(item.getCount()+cartItem.getCount());
			total = total + cartItem.getSubTotal();
		}

	}
}
