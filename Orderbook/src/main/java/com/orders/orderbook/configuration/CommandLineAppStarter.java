package com.orders.orderbook.configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.orders.orderbook.controller.OrderBookContainer;
import com.orders.orderbook.controller.OrderBookController;
import com.orders.orderbook.entity.Order;
import com.orders.orderbook.model.OrderBook;
import com.orders.orderbook.model.OrderBookModel;

@Component
public class CommandLineAppStarter implements CommandLineRunner{
	
	@Autowired
	OrderBookController bookController;
	
	@Autowired
	OrderBookContainer bookContainer;
	

	@Override
	public void run(String... args) throws Exception {
		try {
			Thread.sleep(6000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	    ResponseEntity<OrderBook> orderBookResponse=	bookController.getOrderBook();
	    List<Order> orderBookBuys =  orderBookResponse.getBody().getBuyOrders();
	    List<Order> orderBookSells =  orderBookResponse.getBody().getSellOrders();
	    
	    List<Order> allOrders = Stream.concat(orderBookBuys.stream(), orderBookSells.stream()).toList();
	    
	    for (Order orders : allOrders) {
			bookContainer.process(orders.getId(), orders);
		}
	   
	    
	    System.out.println(orderBookBuys.toString());
	    System.out.println(orderBookSells.toString());
	    
	    bookContainer.finishProcessing();
	    
	 // Print results
	    bookContainer.printContent(System.out);
		
	}
	
	

}
