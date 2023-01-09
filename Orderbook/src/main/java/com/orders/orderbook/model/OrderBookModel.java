package com.orders.orderbook.model;

import java.math.BigDecimal;
import java.util.Objects;

public class OrderBookModel {

		private BigDecimal price;
					
		private Integer	volume;
		

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

		@Override
		public int hashCode() {
			return Objects.hash(price, volume);
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			OrderBookModel other = (OrderBookModel) obj;
			return Objects.equals(price, other.price) && Objects.equals(volume, other.volume);
		}

		@Override
		public String toString() {
		     final StringBuilder sb  = new StringBuilder();
		     sb.append("price=").append(price);
		     sb.append(",volume=").append(volume);
		     sb.append("}");
		     
		     return sb.toString();
		     
		     
		}
		
		
	
}
