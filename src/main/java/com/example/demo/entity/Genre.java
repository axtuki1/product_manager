package com.example.demo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "genre")
public class Genre {
	@Id
	@Column(name = "genre_id")
	public Integer id;
	@Column(name = "genre_name")
	public String name;
	@Column(name = "genre_parent")
	public String parentId;
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
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	public static Genre getBlank() {
		Genre genre = new Genre();
		genre.setId(0);
		genre.setName("未指定");
		genre.setParentId(null);
		return genre;
	}
}
