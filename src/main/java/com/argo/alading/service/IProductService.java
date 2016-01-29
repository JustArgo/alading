package com.argo.alading.service;

import java.util.List;

import com.argo.alading.domain.Order;
import com.argo.alading.domain.Product;

public interface IProductService {

	
	Product get(Long id);
	List<Product> selectAll();
	String calc(Order order);
	String approvePayment(String paymentId,String PayerID,String token);
	void confirmGetProduct(String orderId);
}
