package com.example.demo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "items")
public class Item {
	@Id
	@Column(name = "item_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer id;
	@Column(name = "item_name")
	public String name;
	@Column(name = "item_price")
	public Integer price;
	@Column(name = "item_amount")
	public Integer amount;
	@Column(name = "itemGenre")
	public Integer genre;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
	public Integer getGenre() {
		return genre;
	}
	public void setGenre(Integer genre) {
		this.genre = genre;
	}
	public static Item genItem(Integer id, String name, Integer price, Integer amount, Integer genre) {
		Item item = new Item();
		item.setId(id);
		item.setName(name);
		item.setPrice(price);
		item.setAmount(amount);
		item.setGenre(genre);
		return item;
	}
}
