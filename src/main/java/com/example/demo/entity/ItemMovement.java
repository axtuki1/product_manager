package com.example.demo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "item_movement")
public class ItemMovement {
	@Id
	@Column(name = "item_move_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer id;
	@Column(name = "item_target_id")
	public Integer targetId;
	@Column(name = "item_move_amount")
	public Integer moveAmount;
	@Column(name = "item_move_sale_code")
	public Integer saleCode;
	@Column(name = "item_price")
	public Integer price;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getTargetId() {
		return targetId;
	}
	public void setTargetId(Integer targetId) {
		this.targetId = targetId;
	}
	public Integer getMoveAmount() {
		return moveAmount;
	}
	public void setMoveAmount(Integer moveAmount) {
		this.moveAmount = moveAmount;
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
	public static ItemMovement genData(int sale_code, int moveId,int moveAmount, int price) {
		ItemMovement im = new ItemMovement();
		im.setMoveAmount(moveAmount);
		im.setTargetId(moveId==0?null:moveId);
		im.setSaleCode(sale_code);
		im.setPrice(price);
		return im;
	}
}
