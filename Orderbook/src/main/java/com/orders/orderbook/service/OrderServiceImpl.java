package com.orders.orderbook.service;

import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.orders.orderbook.entity.Order;
import com.orders.orderbook.model.OrderBook;
import com.orders.orderbook.model.OrderBookModel;
import com.orders.orderbook.model.OrderModel;
import com.orders.orderbook.repository.OrderRepository;

import static com.orders.orderbook.common.Side.BUY;
import static com.orders.orderbook.common.Side.SELL;

@Service
public class OrderServiceImpl implements OrderService{
	
	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	
	
	

	@Override
	public OrderBook getOrderBook() {
	
	  List<Order> orderCollection  = orderRepository.findAllByOrderBySideAscReceivedTimeDesc();
	  
	  List<Order> buyOrders = getBuyOrdersFromOrderBook(orderCollection).stream().collect(Collectors.toList());
		
	  List<Order> sellOrders = getSellOrdersFromOrderBook(orderCollection).stream().collect(Collectors.toList());
	    
		return new OrderBook().setBuyOrders(buyOrders).setSellOrders(sellOrders);
	}

	@Override
	public OrderModel storeOrderBook(OrderModel orderModel) {
		Order order  = convertToEntity(orderModel);
		order.setReceivedTime(new Date());
        Order orderSaved = orderRepository.save(order);
        
		return orderSaved!=null?
				convertToOrderModel(orderSaved).setIsOperationSuccessful(Boolean.TRUE):
					orderModel.setIsOperationSuccessful(Boolean.FALSE)
					.setMessage(String.format("Place order operation failed for clientOrderId:%s", orderModel.getClientOrderId()));
	}

	@Override
	public OrderModel cancelOrderBook(OrderModel orderModel) {
		Long id = orderRepository.deleteByClientOrderId(orderModel.getClientOrderId());
		return id!=null?orderModel.setIsOperationSuccessful(Boolean.TRUE):
			      orderModel.setIsOperationSuccessful(Boolean.FALSE)
			      .setMessage(String.format("Cancel order operation for clientOrderId:%s", orderModel.getClientOrderId()));
		
	}
	
	public Order convertToEntity(OrderModel orderModel)
	{
		 return modelMapper.map(orderModel, Order.class).setId(null);
	}
	
	
	private List<Order> getBuyOrdersFromOrderBook(List<Order> orderList)
	{
	  return orderList.stream()
		                 .filter(obOrder->obOrder.getSide().equalsIgnoreCase(BUY.getCode()))
		                 .sorted(Comparator.comparing(Order::getPrice).reversed().thenComparing(Order::getReceivedTime))
		                 .collect(Collectors.toList());
		                		
	}
	
	
	private List<Order> getSellOrdersFromOrderBook(List<Order> orderList)
	{
		return orderList.stream().filter(obOrder->obOrder.getSide().equalsIgnoreCase(SELL.getCode()))
		                  .sorted(Comparator.comparing(Order::getPrice).reversed().thenComparing(Order::getReceivedTime))
		                  .collect(Collectors.toList());
	}
	
	
	/*
	 * private OrderBookModel convertToOrderBookModel(Order order) { return
	 * modelMapper.map(order, OrderBookModel.class); }
	 */
	  
	  private OrderModel convertToOrderModel(Order order) 
	  {
		  return modelMapper.map(order, OrderModel.class); 
	  }
	 

}
