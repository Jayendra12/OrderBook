package com.orders.orderbook.common;

public enum Side {
	
	BUY("Buy"),SELL("Sell");
	
	
	private String code;

	 Side(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}
	
	 
	 
	
	

}
