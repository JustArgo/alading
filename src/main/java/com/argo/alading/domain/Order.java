package com.argo.alading.domain;

import java.util.ArrayList;
import java.util.List;

import com.mor.maven.dubboserver.domain.OrderDetail;

public class Order {

	private List<OrderDetail> orderDetails = new ArrayList<OrderDetail>();  
	private Double totalAmount;
	
	public List<OrderDetail> getOrderDetails() {
		return orderDetails;
	}
	public void setOrderDetails(List<OrderDetail> orderDetails) {
		this.orderDetails = orderDetails;
	}
	public Double getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
	}
	
	
}
