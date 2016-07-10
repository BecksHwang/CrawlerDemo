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

import com.becks.util.StringUtil;

@Entity
@Table(name = "news")
public class News implements Serializable {
	private static final long serialVersionUID = 589451326547563067L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	private Long id;
	@Column(name = "MissionID")
	private Long missionId;
	@Column(name = "CategoryID")
	private Long categoryId;
	@Column(name = "TargetID")
	private Long targetId;
	@Column(name = "JobID")
	private Long jobId;
	@Column(name = "Title")
	private String title;
	@Column(name = "Url")
	private String url;
	@Column(name = "Author")
	private String author;
	@Column(name = "Source")
	private String source;
	@Column(name = "SourceUrl")
	private String sourceUrl;
	@Column(name = "Brief")
	private String brief;
	@Column(name = "Content")
	private String content;
	@Column(name = "Keywords")
	private String keywords;
	@Column(name = "ReleaseTime")
	@Temporal(value = TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date releaseTime;
	@Column(name = "PickTime")
	@Temporal(value = TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date pickTime;
	@Column(name = "Status")
	private String status;
	@Column(name = "MonitorType")
	private String monitorType;
	@Column(name = "PureTitle")
	private String pureTitle;
	@Column(name = "Editor")
	private String editor;
	@Column(name = "ColumnID")
	private Long column;
	@Column(name = "CheckCode")
	private Long checkCode;
	@Column(name = "IsRead")
	private Long isRead;

	public News() {
		super();
	}

	public News(Long missionId, Long targetId, String source, String keywords, Long checkCode) {
		super();
		this.missionId = missionId;
		this.targetId = targetId;
		this.source = source;
		this.keywords = keywords;
		this.checkCode = checkCode;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getMissionId() {
		return missionId;
	}

	public void setMissionId(Long missionId) {
		this.missionId = missionId;
	}

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public Long getTargetId() {
		return targetId;
	}

	public void setTargetId(Long targetId) {
		this.targetId = targetId;
	}

	public Long getJobId() {
		return jobId;
	}

	public void setJobId(Long jobId) {
		this.jobId = jobId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUrl() {
		if (this.url == null || this.url.indexOf("%") != -1) {
			return this.url;
		}
		return StringUtil.decodeURL(this.url);
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getBrief() {
		return brief;
	}

	public void setBrief(String brief) {
		this.brief = brief;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public Date getReleaseTime() {
		return releaseTime;
	}

	public void setReleaseTime(Date releaseTime) {
		this.releaseTime = releaseTime;
	}

	public Date getPickTime() {
		return pickTime;
	}

	public void setPickTime(Date date) {
		this.pickTime = date;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMonitorType() {
		return monitorType;
	}

	public void setMonitorType(String monitorType) {
		this.monitorType = monitorType;
	}

	public String getPureTitle() {
		return pureTitle;
	}

	public void setPureTitle(String pureTitle) {
		this.pureTitle = pureTitle;
	}

	public String getEditor() {
		return editor;
	}

	public void setEditor(String editor) {
		this.editor = editor;
	}

	public Long getColumn() {
		return column;
	}

	public void setColumn(Long column) {
		this.column = column;
	}

	public Long getCheckCode() {
		return checkCode;
	}

	public void setCheckCode(Long checkCode) {
		this.checkCode = checkCode;
	}

	public Long getIsRead() {
		return isRead;
	}

	public void setIsRead(Long isRead) {
		this.isRead = isRead;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getSourceUrl() {
		return sourceUrl;
	}

	public void setSourceUrl(String sourceUrl) {
		this.sourceUrl = sourceUrl;
	}

	@Override
	public String toString() {
		return "News [id=" + id + ", missionId=" + missionId + ", categoryId=" + categoryId + ", targetId=" + targetId
				+ ", jobId=" + jobId + ", title=" + title + ", url=" + url + ", author=" + author + ", source=" + source
				+ ", sourceUrl=" + sourceUrl + ", brief=" + brief + ", content=" + content + ", keywords=" + keywords
				+ ", releaseTime=" + releaseTime + ", pickTime=" + pickTime + ", status=" + status + ", monitorType="
				+ monitorType + ", pureTitle=" + pureTitle + ", editor=" + editor + ", column=" + column
				+ ", checkCode=" + checkCode + ", isRead=" + isRead + "]";
	}

}