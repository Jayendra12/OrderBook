package com.orders.orderbook;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;

import com.orders.orderbook.controller.OrderBookContainer;
import com.orders.orderbook.controller.OrderBookController;
import com.orders.orderbook.controller.OrderBookExecutions;
import com.orders.orderbook.model.OrderBook;

@SpringBootApplication
public class OrderBookApplication {
	
	public static void main(String[] args) {
		
		SpringApplication.run(OrderBookApplication.class, args);
		
	}

	

}
