package com.argo.alading.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.argo.alading.domain.Order;
import com.argo.alading.domain.Product;
import com.argo.alading.mapper.ProductMapper;
import com.argo.alading.service.IProductService;
import com.argo.alading.util.PayUtil;
import com.mor.maven.dubboserver.domain.OrderDetail;
import com.paypal.api.payments.Amount;
import com.paypal.api.payments.Details;
import com.paypal.api.payments.Item;
import com.paypal.api.payments.ItemList;
import com.paypal.api.payments.Payer;
import com.paypal.api.payments.Payment;
import com.paypal.api.payments.PaymentExecution;
import com.paypal.api.payments.RedirectUrls;
import com.paypal.api.payments.Transaction;

@Service
public class ProductServiceImpl implements IProductService{

	@Autowired
	ProductMapper productMapper;
	
	@Override
	public Product get(Long id) {

		return productMapper.selectByPrimaryKey(id);
		
	}

	@Override
	public List<Product> selectAll() {

		return productMapper.selectAll();
		
	}

	@Override
	public String calc(Order order) {
		
		
		
		
		List<OrderDetail> orderDetails = order.getOrderDetails();
		//邮费
		Double shipping = new Double(0);
		Double subTotal = new Double(0);
		List<Item> items = new ArrayList<Item>();
		
		
		for(OrderDetail orderDetail:orderDetails){
			Long productId = orderDetail.getProductId();
			Product product = this.get(productId);
			shipping+=product.getPostage();
			//封装Item
			Item item = new Item();
			item.setName(product.getName()).setQuantity(orderDetail.getQuantity().toString()).setCurrency("USD").setPrice(String.valueOf(product.getPrice().intValue()));
			items.add(item);
			//计算小计
			subTotal+=product.getPrice()*orderDetail.getQuantity();
			
		}
		
		//Details
		Details details = new Details();
		details.setShipping(String.valueOf(shipping.intValue()));
		details.setSubtotal(String.valueOf(subTotal.intValue()));
		details.setTax("0");
		
		//Amount
		Amount amount = new Amount();
		amount.setCurrency("USD");
		amount.setTotal(String.valueOf(shipping.intValue()+subTotal.intValue()));
		amount.setDetails(details);
		
		//ItemList 可以设置送什么物品 送货地址 用哪家快递 收件人电话
		ItemList itemList = new ItemList();
		itemList.setItems(items);

		//Transaction 代表依次交易
		Transaction transaction  = new Transaction();
		transaction.setAmount(amount);
		transaction.setItemList(itemList);
		transaction.setNoteToPayee("goods will send to your home less than 3 day if the transport have no problem");
		
		//List<Transaction> Transaction的数组 由于贝宝支持一次向多个账户付款 所以有多个Transaction
		List<Transaction> transactions = new ArrayList<Transaction>();
		transactions.add(transaction);
		
		//RedirectUrls  重定向Url 用于支付成功后跳转 以及取消支付后的地址
		RedirectUrls urls = new RedirectUrls();
		urls.setCancelUrl("http://localhost:9091/alading/product/cancel");
		urls.setReturnUrl("http://localhost:9091/alading/product/approvePayment");
		
		//设置付款人
		Payer payer = new Payer();
		payer.setPaymentMethod("paypal");
		
		Payment payment = new Payment();
		payment.setPayer(payer);
		payment.setTransactions(transactions);
		payment.setIntent("order");
		payment.setRedirectUrls(urls);
		
		String approval = PayUtil.calc(payment);
		return approval;
	}

	@Override
	public String approvePayment(String paymentId, String PayerID, String token) {

		String state = "fail";
		if(StringUtils.hasLength(paymentId)){
			//新建一个Payment  把paymentId设置进去 
			Payment payment = new Payment();
			payment.setId(paymentId);
			
			//创建一个PaymentExecution对象  设置 paymentExecution的 payerID 
			PaymentExecution paymentExecution = new PaymentExecution();
			paymentExecution.setPayerId(PayerID);
			
			state =  PayUtil.approve(payment, paymentExecution);
		}
		return state;
		
	}

	@Override
	public void confirmGetProduct(String orderId) {

		PayUtil.confirmGetProduct(orderId);
		
	}

}
