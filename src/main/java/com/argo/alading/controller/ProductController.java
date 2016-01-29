package com.argo.alading.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.argo.alading.domain.Order;
import com.argo.alading.domain.Product;
import com.argo.alading.service.IProductService;
import com.argo.alading.util.FinanceUtil;
import com.mor.maven.dubboserver.service.IOrderDetailService;

@Controller
@RequestMapping("/product")
public class ProductController {

	@Autowired
	private IProductService productService;
	
	
	/**
	 * 点击立即购买 需要 改变数量
	 * @return 返回指定的页面
	 */
	@RequestMapping("/buy")
	private String buy(){
		return "products";
	}
	
	@RequestMapping("/view/{id}")
	private String view(@PathVariable("id")Long id,Model model){
		
		Product p = productService.get(id);
		model.addAttribute("product", p);
		return "productDetails";
	}

	@RequestMapping("/buyNow/{id}")
	private String buy(@PathVariable("id")Long id,Model model){
		
		Product p = productService.get(id);
		model.addAttribute("product", p);
		return "buyNow";
		
	}
	
	@RequestMapping("/calc")
	private void calc(Order order,Model model,HttpServletResponse response){
		
		/**
		 * 将订单中的明细插入数据库
		 */
		for(int i=0;i<order.getOrderDetails().size();i++){
			//orderDetailService.save(order.getOrderDetails().get(i));
		}
		
		
		System.out.println("come here");
		System.out.println(order.getOrderDetails().get(0).getProductId());
		String approval_url = productService.calc(order);
		try {
			response.sendRedirect(approval_url);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping("/approvePayment")
	private String approve(String paymentId,String PayerID,String token,Model model){
		
		System.out.println("begin to approve");
		String payState = productService.approvePayment(paymentId,PayerID,token);
		
		System.out.println(payState);
		if(payState.equals("completed")){
			return "paySuccess";
		}else{
			return "payFail";
		}
		
	}
	
	@RequestMapping("/confirmGetProduct")
	private String confirmGetProduct(String orderId){
		productService.confirmGetProduct(orderId);
		return "confirmSuccess";
	}
	
	@RequestMapping("/exchange")
	@ResponseBody
	private String exchange(Double amount,String currency){
		
		return FinanceUtil.transfer("USD", currency, amount).toString();
		
	}
	
}
