package com.orders.orderbook.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.orders.orderbook.entity.Order;


/**
 * 
 * find and delete operation api
 * @author JayendraKa
 *
 */

public interface OrderRepository extends JpaRepository<Order, Long>, OrderCustomRepository {

	
	public List<Order> findAllByOrderBySideAscReceivedTimeDesc();
	
	
	/**
	 * find order by client order as key
	 * @param clientOrderId
	 * @return
	 */
	public Order findOneByClientOrderId(Integer clientOrderId);
	
	
	/**
	 * delete by clientOrderId 
	 * @param clientOrderId
	 * @return
	 */
	@Transactional
    Long deleteByClientOrderId(Integer clientOrderId);
	
	
}
