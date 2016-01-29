package com.argo.alading.domain;

import java.io.Serializable;

public class Product implements Serializable{

	private Long id;
	private String name;
	private Double price;
	private Double postage;
	private Integer repertory;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public Double getPostage() {
		return postage;
	}
	public void setPostage(Double postage) {
		this.postage = postage;
	}
	public Integer getRepertory() {
		return repertory;
	}
	public void setRepertory(Integer repertory) {
		this.repertory = repertory;
	}
	
	
	
}
