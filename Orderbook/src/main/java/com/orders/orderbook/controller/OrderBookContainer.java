package com.orders.orderbook.controller;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Component;

import com.google.common.base.Preconditions;
import com.orders.orderbook.entity.Order;
import com.orders.orderbook.model.OrderBook;
import com.orders.orderbook.model.OrderBookModel;
import com.orders.orderbook.model.OrderModel;

@Component
public class OrderBookContainer {
	List<OrderBookExecutions> books = new ArrayList<>();
	private Map<Long, BlockingQueue<Order>> queues = new HashMap<>();
	private ExecutorService executor = Executors.newFixedThreadPool(4);
	
	
	public void process(Long bookId,Order order)
	{
		if(!queues.containsKey(bookId))
		{
			BlockingQueue<Order> queue = new LinkedBlockingQueue<>();
			queues.put(bookId, queue);
			
			OrderBookExecutions book = new OrderBookExecutions(bookId, queue);
			books.add(book);
			executor.execute(book);
			
			
			  try { 
				  queues.get(bookId).put(order); 
			  } catch (InterruptedException e) 
			  { 
				  throw new RuntimeException(e); 
			  }
			 
			
			
		}
	}
	
	/**
	 * Notify underlying order book queues of end of processing.
	 * @throws InterruptedException
	 */
	public void finishProcessing() throws InterruptedException {
		for (BlockingQueue<Order> queue : queues.values()) {
			queue.put(new Order());
		}
		executor.shutdown();
		executor.awaitTermination(5, TimeUnit.SECONDS);
	}
	
	
	/**
	 * Print books and their content to given stream.
	 * @param out
	 */
	public void printContent(PrintStream out) {
		Preconditions.checkArgument(out != null, "OutputStream cannot be null");
		
		for (OrderBookExecutions book : books) {
			out.println("book: " + book.toString());
			book.printContent(out);
			out.println();
			out.flush();
		}
	}
	

}
