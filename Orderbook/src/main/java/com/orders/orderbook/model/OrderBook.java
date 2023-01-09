package com.orders.orderbook.model;

import java.util.List;

import com.orders.orderbook.entity.Order;

public class OrderBook {
	
	private List<Order> buyOrders;
	
	private List<Order> sellOrders;

	public List<Order> getBuyOrders() {
		return buyOrders;
	}

	public OrderBook setBuyOrders(List<Order> buyOrders) {
		this.buyOrders = buyOrders;
		return this;
	}

	public List<Order> getSellOrders() {
		return sellOrders;
	}

	public OrderBook setSellOrders(List<Order> sellOrders) {
		this.sellOrders = sellOrders;
		return this;
	}
	
	
	

}
