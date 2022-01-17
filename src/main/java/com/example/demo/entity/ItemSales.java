package com.example.demo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "item_sales")
public class ItemSales {
	@Id
	@Column(name = "item_sales_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer id;
	@Column(name = "item_id")
	public Integer itemId;
	@Column(name = "sales_code")
	public Integer saleCode;
	@Column(name = "item_amount")
	public Integer amount;
	@Column(name = "item_price")
	public Integer price;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getItemId() {
		return itemId;
	}
	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}
	public Integer getSaleCode() {
		return saleCode;
	}
	public void setSaleCode(Integer saleCode) {
		this.saleCode = saleCode;
	}
	public Integer getPrice() {
		return price;
	}
	public void setPrice(Integer price) {
		this.price = price;
	}
	public Integer getAmount() {
		return amount;
	}
	public void setAmount(Integer amount) {
		this.amount = amount;
	}
	public static ItemSales gen(int salesCode, int itemId, int amount, int price) {
		ItemSales out = new ItemSales();
		out.setItemId(itemId);
		out.setPrice(price);
		out.setSaleCode(salesCode);
		out.setAmount(amount);
		return out;
	}
}
