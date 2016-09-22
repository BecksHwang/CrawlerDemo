package com.becks.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "stockinfo")
public class StockInfo implements Serializable {
	private static final long serialVersionUID = 589451326547560825L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	private Long id;
	@Column(name = "Name")
	private String name;
	@Column(name = "AnotherName")
	private String anotherName;
	@Column(name = "StockCode")
	private String stockCode;
	@Column(name = "MD5")
	private String md5;
	@Column(name = "MD52")
	private String md52;

	public StockInfo() {
		super();
	}

	public StockInfo(Long id, String name, String anotherName, String stockCode, String md5, String md52) {
		super();
		this.id = id;
		this.name = name;
		this.anotherName = anotherName;
		this.stockCode = stockCode;
		this.md5 = md5;
		this.md52 = md52;
	}

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

	public String getAnotherName() {
		return anotherName;
	}

	public void setAnotherName(String anotherName) {
		this.anotherName = anotherName;
	}

	public String getStockCode() {
		return stockCode;
	}

	public void setStockCode(String stockCode) {
		this.stockCode = stockCode;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getMd5() {
		return md5;
	}

	public void setMd5(String md5) {
		this.md5 = md5;
	}

	public String getMd52() {
		return md52;
	}

	public void setMd52(String md52) {
		this.md52 = md52;
	}

	@Override
	public String toString() {
		return "StockInfo [id=" + id + ", name=" + name + ", anotherName=" + anotherName + ", stockCode=" + stockCode
				+ ", md5=" + md5 + "]";
	}

}