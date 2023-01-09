package com.orders.orderbook.service;

import com.orders.orderbook.model.OrderBook;
import com.orders.orderbook.model.OrderModel;

public interface OrderService {
	
	
	OrderBook getOrderBook();
	
	OrderModel storeOrderBook(OrderModel orderModel);
	
    OrderModel cancelOrderBook(OrderModel orderModel);
	
	
	
	
	

}
