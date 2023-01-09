package com.orders.orderbook.model;

import java.util.Date;
import java.util.Objects;

import org.springframework.lang.NonNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.orders.orderbook.entity.Order;

public class OrderModel {
	
	@NonNull
	private Integer clientOrderId;
	
	@NonNull
	private Integer price;
	
	@NonNull
	private Integer volume;
	
	@NonNull
	private String side;
	
	@JsonIgnore
	private Date receivedTime;
	
	@JsonIgnore
	private Boolean isOperationSuccessful;
	
	@JsonIgnore
	private String message;
	
	
	

	public Integer getClientOrderId() {
		return clientOrderId;
	}

	public OrderModel setClientOrderId(Integer clientOrderId) {
		this.clientOrderId = clientOrderId;
		return this;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public Integer getVolume() {
		return volume;
	}

	public void setVolume(Integer volume) {
		this.volume = volume;
	}

	public String getSide() {
		return side;
	}

	public void setSide(String side) {
		this.side = side;
	}

	public Date getReceivedTime() {
		return receivedTime;
	}

	public void setReceivedTime(Date receivedTime) {
		this.receivedTime = receivedTime;
	}

	public Boolean getIsOperationSuccessful() {
		return isOperationSuccessful;
	}

	public OrderModel setIsOperationSuccessful(Boolean isOperationSuccessful) {
		this.isOperationSuccessful = isOperationSuccessful;
		return this;
	}

	public String getMessage() {
		return message;
	}

	public OrderModel setMessage(String message) {
		this.message = message;
		return this;
	}

	@Override
	public int hashCode() {
		return Objects.hash(clientOrderId, isOperationSuccessful, message, price, receivedTime, side, volume);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OrderModel other = (OrderModel) obj;
		return Objects.equals(clientOrderId, other.clientOrderId)
				&& Objects.equals(isOperationSuccessful, other.isOperationSuccessful)
				&& Objects.equals(message, other.message) && Objects.equals(price, other.price)
				&& Objects.equals(receivedTime, other.receivedTime) && Objects.equals(side, other.side)
				&& Objects.equals(volume, other.volume);
	}
	
	
	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		
		sb.append("clientOrderId").append(clientOrderId);
		sb.append(",side").append(side).append('\'');
		sb.append(",price").append(price);
		sb.append(",receivedTime").append(receivedTime);
		sb.append(",volume").append(volume);
		sb.append(",isOperationSuccessful").append(isOperationSuccessful);
		sb.append(",message").append(false).append('\'');
		sb.append("}");
		
		return sb.toString();
	}

}
