package com.becks.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "stocklabel")
public class StockLabel implements Serializable {
	private static final long serialVersionUID = 589451326547560712L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	private Long id;
	@Column(name = "LableName")
	private String lableName;
	@Column(name = "LableType")
	private String lableType;
	@Column(name = "LableTypeId")
	private Long lableTypeId;
	@Column(name = "Category")
	private String category;
	@Column(name = "CategoryId")
	private Long categoryId;

	public StockLabel() {
		super();
	}

	public StockLabel(Long id, String lableName, String lableType, Long lableTypeId, String category, Long categoryId) {
		super();
		this.id = id;
		this.lableName = lableName;
		this.lableType = lableType;
		this.lableTypeId = lableTypeId;
		this.category = category;
		this.categoryId = categoryId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLableName() {
		return lableName;
	}

	public void setLableName(String lableName) {
		this.lableName = lableName;
	}

	public String getLableType() {
		return lableType;
	}

	public void setLableType(String lableType) {
		this.lableType = lableType;
	}

	public Long getLableTypeId() {
		return lableTypeId;
	}

	public void setLableTypeId(Long lableTypeId) {
		this.lableTypeId = lableTypeId;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "StockLabel [id=" + id + ", lableName=" + lableName + ", lableType=" + lableType + ", lableTypeId="
				+ lableTypeId + ", category=" + category + ", categoryId=" + categoryId + "]";
	}

}