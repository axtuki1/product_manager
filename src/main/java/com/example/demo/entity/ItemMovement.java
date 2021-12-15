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
	@Column(name = "item_taget_id")
	public String targetId;
	@Column(name = "item_move_amount")
	public Integer moveAmount;
	@Column(name = "item_move_sale_code")
	public Integer saleCode;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getTargetId() {
		return targetId;
	}
	public void setTargetId(String targetId) {
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
	
}
