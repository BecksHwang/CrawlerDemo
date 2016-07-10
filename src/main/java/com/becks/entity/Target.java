package com.becks.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "target")
public class Target implements Serializable {
	private static final long serialVersionUID = 1703892862527086785L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	private Long id;
	@Column(name = "MissionID")
	private Long missionId;
	@Column(name = "Type")
	private String type;
	@Column(name = "Name")
	private String name;
	@Column(name = "Url")
	private String url;
	@Column(name = "First")
	private String first;
	@Column(name = "Last")
	private String last;
	@Column(name = "Step")
	private Integer step;
	@Column(name = "Sequence")
	private Integer sequence;
	@Column(name = "StartTag")
	private String startTag;
	@Column(name = "EndTag")
	private String endTag;
	@Column(name = "MD5")
	private String md5;
	@Column(name = "UseKeywordsFilter")
	private Boolean useKeywordsFilter;
	@Column(name = "ColumnID")
	private Long column;
	@Column(name = "Monitored")
	private Boolean monitored;

	public Target() {
	}

	public Target(Long id, Long missionId, String type, String name, String url, String first, String last,
			Integer step, Integer sequence, String startTag, String endTag, String md5, Boolean useKeywordsFilter,
			Long column, Boolean monitored) {
		super();
		this.id = id;
		this.missionId = missionId;
		this.type = type;
		this.name = name;
		this.url = url;
		this.first = first;
		this.last = last;
		this.step = step;
		this.sequence = sequence;
		this.startTag = startTag;
		this.endTag = endTag;
		this.md5 = md5;
		this.useKeywordsFilter = useKeywordsFilter;
		this.column = column;
		this.monitored = monitored;
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getFirst() {
		return first;
	}

	public void setFirst(String first) {
		this.first = first;
	}

	public String getLast() {
		return last;
	}

	public void setLast(String last) {
		this.last = last;
	}

	public Integer getStep() {
		return step;
	}

	public void setStep(Integer step) {
		this.step = step;
	}

	public Integer getSequence() {
		return sequence;
	}

	public void setSequence(Integer sequence) {
		this.sequence = sequence;
	}

	public String getStartTag() {
		return startTag;
	}

	public void setStartTag(String startTag) {
		this.startTag = startTag;
	}

	public String getEndTag() {
		return endTag;
	}

	public void setEndTag(String endTag) {
		this.endTag = endTag;
	}

	public String getMd5() {
		return md5;
	}

	public void setMd5(String md5) {
		this.md5 = md5;
	}

	public Boolean getUseKeywordsFilter() {
		return useKeywordsFilter;
	}

	public void setUseKeywordsFilter(Boolean useKeywordsFilter) {
		this.useKeywordsFilter = useKeywordsFilter;
	}

	public Long getColumn() {
		return column;
	}

	public void setColumn(Long column) {
		this.column = column;
	}

	public Boolean getMonitored() {
		return monitored;
	}

	public void setMonitored(Boolean monitored) {
		this.monitored = monitored;
	}

	@Override
	public String toString() {
		return "Target [id=" + id + ", missionId=" + missionId + ", type=" + type + ", name=" + name + ", url=" + url
				+ ", first=" + first + ", last=" + last + ", step=" + step + ", sequence=" + sequence + ", startTag="
				+ startTag + ", endTag=" + endTag + ", md5=" + md5 + ", useKeywordsFilter=" + useKeywordsFilter
				+ ", column=" + column + ", monitored=" + monitored + "]";
	}

}
