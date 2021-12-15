package com.example.demo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "sales_data")
public class SalesData {
	@Id
	@Column(name = "sale_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer id;
	@Column(name = "sale_billing_amount")
	public String billingAmount;
	@Column(name = "sale_payment_amount")
	public Integer paymentAmount;
	@Column(name = "sale_payment_merthod")
	public Integer paymentMethod;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getBillingAmount() {
		return billingAmount;
	}
	public void setBillingAmount(String billingAmount) {
		this.billingAmount = billingAmount;
	}
	public Integer getPaymentAmount() {
		return paymentAmount;
	}
	public void setPaymentAmount(Integer paymentAmount) {
		this.paymentAmount = paymentAmount;
	}
	public Integer getPaymentMethod() {
		return paymentMethod;
	}
	public void setPaymentMethod(Integer paymentMethod) {
		this.paymentMethod = paymentMethod;
	}
	
}
