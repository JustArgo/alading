package com.argo.alading.util;

import java.io.InputStream;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

public class FinanceUtil {

	private FinanceUtil(){}
	
	/**
	 * 将A类型货币的x金额 转成B类型货币的y金额
	 * @return
	 */
	public static Double transfer(String sourceCurrency,String targetCurrency,Double sourceAmount){
		Double targetAmount = new Double(0);
		
		DefaultHttpClient client = new DefaultHttpClient();
		HttpGet get = new HttpGet("http://finance.yahoo.com/webservice/v1/symbols/allcurrencies/quote");
		
		try{
			
			HttpResponse resp = client.execute(get);
			
			HttpEntity entity = resp.getEntity();
			InputStream is = entity.getContent();
			
			SAXReader reader = new SAXReader();
			Document doc = reader.read(is);
			Element root = doc.getRootElement();
			List<Node> nodes = root.selectNodes("resources/resource/field[@name='name']");
			Double exchangeRate = new Double(0);
			
			for(int i=0;i<nodes.size();i++){
				if(nodes.get(i).getStringValue().equals(sourceCurrency+"/"+targetCurrency)){
					String rate = nodes.get(i).getParent().selectSingleNode("field[@name='price']").getStringValue();
					exchangeRate = new Double(rate);
					break;
				}
			}
			targetAmount = exchangeRate*sourceAmount;
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
		return targetAmount;
	}
	
	public static void main(String[] args) {
		
		System.out.println(FinanceUtil.transfer("USD", "EUR", 1D));
		
	}
	
}
