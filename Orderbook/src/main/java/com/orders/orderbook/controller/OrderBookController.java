package com.orders.orderbook.controller;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


import com.orders.orderbook.model.OrderBook;
import com.orders.orderbook.model.OrderModel;
import com.orders.orderbook.service.OrderService;

@RestController
@RequestMapping("/market")
public class OrderBookController {
	
	@Autowired
	OrderService orderService;
	
	/**
	 * Generates OrderBook from existing Orders extracted from orderStore
	 * @return
	 */
	@RequestMapping(path = "/orderBook",method = RequestMethod.GET)
	public ResponseEntity<OrderBook> getOrderBook()
	{
		OrderBook orderBook = orderService.getOrderBook();
		
		return orderBook!=null?ResponseEntity.ok(orderBook):
			ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}
	
	
	/**
	 * place order in order table
	 * @param orderModel
	 * @return
	 */
	@RequestMapping(value = "/placeOrder",method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<OrderModel> placeOrder(@Valid @RequestBody(required = true) OrderModel orderModel)
	{
		 OrderModel orderModel_reponse =  orderService.storeOrderBook(orderModel);
		 
		 HttpStatus httpStatus = orderModel_reponse!=null?HttpStatus.OK:HttpStatus.INTERNAL_SERVER_ERROR;
		 
		 return orderModel_reponse!=null && orderModel_reponse.getIsOperationSuccessful()?
				 ResponseEntity.ok(orderModel_reponse):
				 ResponseEntity.status(httpStatus).build();	 		 
	}
	
	@RequestMapping(value = "/cancelOrder/{clientOrderId}",method = RequestMethod.POST)
	@ResponseBody
	@Validated
	public ResponseEntity<OrderModel> cancelOrder(@NotNull @NotBlank @Pattern(regexp = "\\d") @PathVariable String clientOrderId)
	{
		OrderModel orderModel_response =  orderService.cancelOrderBook(new OrderModel().setClientOrderId(Integer.valueOf(clientOrderId)));
		
		return orderModel_response!=null && orderModel_response.getIsOperationSuccessful()?
				ResponseEntity.ok(orderModel_response):
				ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}
	

}
