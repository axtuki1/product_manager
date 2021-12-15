package com.example.demo.form;

import java.util.HashMap;

public class RegisterForm {
	private HashMap<String, Object> items; 
	private int total, payment;
	public HashMap<String, Object> getItems() {
		return items;
	}
	public void setItems(HashMap<String, Object> items) {
		this.items = items;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public int getPayment() {
		return payment;
	}
	public void setPayment(int payment) {
		this.payment = payment;
	}
}
