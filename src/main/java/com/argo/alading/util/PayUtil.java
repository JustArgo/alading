package com.argo.alading.util;

import java.io.InputStream;
import java.util.List;

import com.paypal.api.payments.Amount;
import com.paypal.api.payments.Capture;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Order;
import com.paypal.api.payments.Payment;
import com.paypal.api.payments.PaymentExecution;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.JSONFormatter;
import com.paypal.base.rest.PayPalRESTException;
import com.paypal.base.rest.PayPalResource;

public class PayUtil {

	private PayUtil(){
		InputStream is = PayUtil.class.getResourceAsStream("/sdk_config.properties");
		try{
			PayPalResource.initConfig(is);
		}catch(PayPalRESTException e){
			e.printStackTrace();
		}
	}
	
	public static String calc(Payment payment){
		
		String accessToken = "";
		APIContext apiContext = null;
		String approval_url = "";
		
		try{
			
			accessToken = GenerateAccessToken.getAccessToken();
			apiContext = new APIContext(accessToken);
			
			// 此处创建了 一个 付款对象 还需要买家 登录 paypal账号进行付款 操作 
			Payment createdPayment =  payment.create(apiContext);
			List<Links> links = createdPayment.getLinks();
			for(int i=0;i<links.size();i++){
				if(links.get(i).getRel().equalsIgnoreCase("approval_url")){
					approval_url = links.get(i).getHref();
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return approval_url;
	}
	
	public static String approve(Payment payment,PaymentExecution paymentExecution){
		
		String accessToken = "";
		APIContext apiContext = null;
		String state = "fail";
		
		try{
			
			accessToken = GenerateAccessToken.getAccessToken();
			apiContext = new APIContext(accessToken);
			
			Payment executedPayment = payment.execute(apiContext, paymentExecution);
			state = executedPayment.getState();
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return state;
	}
	
	public static void confirmGetProduct(String orderId){
		
		String accessToken = "";
		APIContext apiContext = null;
		
		try{
			
			accessToken = GenerateAccessToken.getAccessToken();
			apiContext = new APIContext(accessToken);
			
			Order order = Order.get(apiContext,orderId);
			
			//获得要转给卖家的欠款
			String total = order.getAmount().getTotal();
			
			//设置要捕获的统计对象
			Amount amount = new Amount();
			amount.setCurrency("USD");
			amount.setTotal(total);
			
			//新建捕获对象
			Capture capture = new Capture();
			capture.setAmount(amount);
			
			//开始捕获  这一步执行完之后 就相当于 官方将我们之前付的款 转给卖家
			Capture retCapture = order.capture(apiContext, capture);
			System.out.println(JSONFormatter.toJSON(retCapture));
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
		
		
	}
	
	
}
