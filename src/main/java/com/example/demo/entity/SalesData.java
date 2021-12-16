package com.example.demo.entity;

import java.sql.Date;
import java.sql.Timestamp;

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
	public Integer billingAmount;
	@Column(name = "sale_payment_amount")
	public Integer paymentAmount;
	@Column(name = "sale_payment_method")
	public String paymentMethod;
	@Column(name = "sale_payment_timestamp")
	public Timestamp paymentTimestamp;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getBillingAmount() {
		return billingAmount;
	}
	public void setBillingAmount(Integer billingAmount) {
		this.billingAmount = billingAmount;
	}
	public Integer getPaymentAmount() {
		return paymentAmount;
	}
	public void setPaymentAmount(Integer paymentAmount) {
		this.paymentAmount = paymentAmount;
	}
	public String getPaymentMethod() {
		return paymentMethod;
	}
	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}
	public Timestamp getPaymentTimestamp() {
		return paymentTimestamp;
	}
	public void setPaymentTimestamp(Timestamp paymentTimestamp) {
		this.paymentTimestamp = paymentTimestamp;
	}
	public static SalesData genData(int billing, int payment, String method) {
		SalesData data = new SalesData();
		data.setBillingAmount(billing);
		data.setPaymentAmount(payment);
		data.setPaymentMethod(method);
		data.setPaymentTimestamp(new Timestamp(System.currentTimeMillis()));
		return data;
	}
}
