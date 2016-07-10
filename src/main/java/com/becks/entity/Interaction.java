package com.becks.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "interaction")
public class Interaction implements Serializable {
	private static final long serialVersionUID = 425651326847364061L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	private long id;
	@Column(name = "Ask")
	private String ask;
	@Column(name = "Answer")
	private String answer;
	@Column(name = "Source")
	private String source;
	@Column(name = "SourceUrl")
	private String sourceUrl;
	@Column(name = "PickTime")
	@Temporal(value = TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date pickTime;
	@Column(name = "TargetID")
	private long targetId;
	@Column(name = "IsRead")
	private long isRead;
	@Column(name = "CheckCode")
	private long checkCode;
	@Column(name = "Company")
	private String company;

	public Interaction() {
		super();
	}

	public Interaction(String ask, String answer) {
		super();
		this.ask = ask;
		this.answer = answer;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getAsk() {
		return ask;
	}

	public void setAsk(String ask) {
		this.ask = ask;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getSourceUrl() {
		return sourceUrl;
	}

	public void setSourceUrl(String sourceUrl) {
		this.sourceUrl = sourceUrl;
	}

	public Date getPickTime() {
		return pickTime;
	}

	public void setPickTime(Date pickTime) {
		this.pickTime = pickTime;
	}

	public long getTargetId() {
		return targetId;
	}

	public void setTargetId(long targetId) {
		this.targetId = targetId;
	}

	public long getIsRead() {
		return isRead;
	}

	public void setIsRead(long isRead) {
		this.isRead = isRead;
	}

	public long getCheckCode() {
		return checkCode;
	}

	public void setCheckCode(long checkCode) {
		this.checkCode = checkCode;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	@Override
	public String toString() {
		return "Interaction [id=" + id + ", ask=" + ask + ", answer=" + answer + ", source=" + source + ", sourceUrl="
				+ sourceUrl + ", pickTime=" + pickTime + ", targetId=" + targetId + ", isRead=" + isRead
				+ ", checkCode=" + checkCode + ", company=" + company + "]";
	}

}
