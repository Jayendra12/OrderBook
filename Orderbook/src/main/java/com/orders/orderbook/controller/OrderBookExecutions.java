package com.orders.orderbook.controller;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.orders.orderbook.entity.Order;
import com.orders.orderbook.model.OrderBookModel;


public class OrderBookExecutions implements Runnable {
	Long id;
	
	List<Order> buys;
	
	List<Order> sells;
	
	Map<Integer, Order> orderCache = new HashMap<>();
	
	BlockingQueue<Order> queue;
	
	public OrderBookExecutions(Long id,BlockingQueue<Order> queue) {
		this.queue = queue;
		this.id = id;
		
		buys = new LinkedList<>();
		sells = new LinkedList<>();
	}

	@Override
	public void run() {
		try {
			 Order order =  queue.take();
			 
			 if(order == null)
				 return ;
			 if(order.getSide().equals("BUY"))
			 {
				 buy(order);
			 }else if(order.getSide().equals("SELL"))
			 {
				 sell(order);
			 }
			 
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}
	
	private void buy(Order order)
	{
		Preconditions.checkArgument(order != null, "Order cannot be null");
		Preconditions.checkArgument(order.getSide().equals("BUY"), "Operation must be of type BUY");
		
		processBuy(order);
		
		if(order.getVolume()>0)
		{
			orderCache.put(order.getClientOrderId(), order);
			
			buys.add(order);
			
			Collections.sort(buys,new Comparator<Order>() {
					 
			           @Override
			        public int compare(Order first, Order second) {
			        	
			        	   if(first.getPrice().equals(second.getPrice()))
			        	   {
			        		   return first.getReceivedTime().compareTo(second.getReceivedTime());
			        	   }else {
			        		   return -1*first.getPrice().compareTo(second.getPrice());
			        	   }
			        	   
			        	
			        }
			});
			
		}
		
		
	}
	
	private void sell(Order order) {
		Preconditions.checkArgument(order != null, "Order cannot be null");
		Preconditions.checkArgument(order.getSide().equals("SELL"), "Operation must be of type SELL");
		
		processSell(order);
		
		if (order.getVolume() > 0) {
			orderCache.put(order.getClientOrderId(), order);
			
			sells.add(order);
			
			Collections.sort(sells, new Comparator<Order>() {
				@Override
				public int compare(Order first, Order second) {
					if (first.getPrice().equals(second.getPrice())) {
						return first.getReceivedTime().compareTo(second.getReceivedTime());
					} else {
						return first.getPrice().compareTo(second.getPrice());
					}
				}
			});
		}
	}
	
	private void processSell(Order sell) {
		for (Order buy : buys) {
			if(buy.getPrice().compareTo(buy.getPrice())>0 || buy.getVolume()==0)
			{
				break;
			}
			
			Integer contractVolume = Math.min(sell.getVolume(), buy.getVolume());
			buy.decreaseVolume(contractVolume);
			sell.decreaseVolume(contractVolume);
			
		}
		buys = clearEmptyOrders(buys);
	}

	/**
	 * tries to match given sell operation with all present buy operation
	 * Get rid of empty buy operations
	 * @param orderModel
	 */
	private void processBuy(Order buy) {
		for(Order sell:sells)
		{
			if(sell.getPrice().compareTo(buy.getPrice())>0 || buy.getVolume()==0)
			{
				break;
			}
			
			Integer contractVolume = Math.min(buy.getVolume(), sell.getVolume());
			buy.decreaseVolume(contractVolume);
			sell.decreaseVolume(contractVolume);
			
			
		}
		sells = clearEmptyOrders(sells);
	}
	
	
	List<Order> clearEmptyOrders(List<Order> orders) {
		int index = 0;
		
		for (Order o : orders) {
			if (o.getVolume() > 0) {
				break;
			}
			
			index++;
			this.orderCache.remove(o.getClientOrderId());
		}
		
		int lastIndex = Math.max(0, orders.size());
	    
		
		
		return new ArrayList<>(orders.subList(Math.min(index, lastIndex), lastIndex)); 
	}

	/** Print a nicely formatted contents to the given stream. */
	public void printContent(PrintStream out) {
		int lineWidth = 40;
		int columnWidth = (lineWidth+1) / 2;
		
		out.println(Strings.padStart("Buy -", columnWidth, ' ') + Strings.padEnd("- Sell", columnWidth, ' '));
		out.println(Strings.repeat("=", lineWidth));
		
		int higherNumberOfOrders = Math.max(buys.size(), sells.size());
		for (int i = 0; i < higherNumberOfOrders; i++) {
			out.println(
					Strings.padStart(getFormattedOrder(buys, i) + " -", columnWidth, ' ') + 
					Strings.padEnd("- " + getFormattedOrder(sells, i), columnWidth, ' '));
		}
	}
	
	/** 
	 * Returns String representation of order from the list.
	 * @param orders List of orders
	 * @param index Index of order to be formatted. 
	 * 		If a list does not contain order in the given position empty String is returned 
	 */
	private String getFormattedOrder(List<Order> orders, int index) {
		String formattedOrder = "";
		if (index >= 0 && index < orders.size()) {
			Order order =  orders.get(index);
			formattedOrder = String.format("%d@%.2f", order.getVolume(), order.getPrice());
		}
		return formattedOrder; 
	}

	@Override
	public String toString() {
		return "OrderBookExecutions [id=" + id + ", buys=" + buys + ", sells=" + sells + ", orderCache=" + orderCache
				+ ", queue=" + queue + "]";
	}
	
	
	
}
