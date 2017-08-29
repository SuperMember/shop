package com.example.lenovo.taoshop.bean.common;

import java.util.List;

public class Order extends TbOrder{

	private TbOrderItem orderItems;
	private TbOrderShipping orderShipping;
	public TbOrderItem getOrderItems() {
		return orderItems;
	}
	public void setOrderItems(TbOrderItem orderItems) {
		this.orderItems = orderItems;
	}
	public TbOrderShipping getOrderShipping() {
		return orderShipping;
	}
	public void setOrderShipping(TbOrderShipping orderShipping) {
		this.orderShipping = orderShipping;
	}

	@Override
	public String toString() {
		return "Order{" +
				"orderItems=" + orderItems +
				", orderShipping=" + orderShipping +
				'}';
	}
}