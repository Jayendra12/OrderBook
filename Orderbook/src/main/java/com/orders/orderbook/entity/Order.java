package com.orders.orderbook.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "OB_ORDER")
public class Order {
	
	@Id
	@GeneratedValue
	@Column(name = "id",nullable = false)
	private Long id;
	
	
	@Column(name = "client_order_id",nullable = false)
	private Integer clientOrderId;
	
	@Column(name = "side",nullable = false)
	private String side;
	
	@Column(name = "price",nullable = false)
	private BigDecimal price;
	
	@Column(name = "volume",nullable = false)
	private Integer volume;
	
	@Column(name = "received_time",nullable = false)
	private Date receivedTime;
	
	
	

	@Override
	public int hashCode() {
		return Objects.hash(clientOrderId, id, price, receivedTime, side, volume);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Order other = (Order) obj;
		return Objects.equals(clientOrderId, other.clientOrderId) && Objects.equals(id, other.id)
				&& Objects.equals(price, other.price) && Objects.equals(receivedTime, other.receivedTime)
				&& Objects.equals(side, other.side) && Objects.equals(volume, other.volume);
	}

	public Long getId() {
		return id;
	}

	public Order setId(Long id) {
		this.id = id;
		return this;
	}

	public Integer getClientOrderId() {
		return clientOrderId;
	}

	public void setClientOrderId(Integer clientOrderId) {
		this.clientOrderId = clientOrderId;
	}

	public String getSide() {
		return side;
	}

	public void setSide(String side) {
		this.side = side;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public Integer getVolume() {
		return volume;
	}

	public void setVolume(Integer volume) {
		this.volume = volume;
	}

	public Date getReceivedTime() {
		return receivedTime;
	}

	public void setReceivedTime(Date receivedTime) {
		this.receivedTime = receivedTime;
	}
	
	public void decreaseVolume(Integer delta) {
		volume = getVolume() - delta;
	}

}
