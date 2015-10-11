package com.novel.model.admin;

import java.util.Date;

@SuppressWarnings("serial")
public class Search implements java.io.Serializable {

	private Integer id;
	private String keyword;
	private Integer resultcount;
	private Date time;
	private String date; // 格式化日期
	private Integer times; // 搜索次数
	
	public Search(){
		
	}

	public Search(Integer id, String keyword, Integer resultcount, String date, Date time) {
		this.id = id;
		this.keyword = keyword;
		this.resultcount = resultcount;
		this.time = time;
		this.date = date;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public Integer getResultcount() {
		return resultcount;
	}

	public void setResultcount(Integer resultcount) {
		this.resultcount = resultcount;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public Integer getTimes() {
		return times;
	}

	public void setTimes(Integer times) {
		this.times = times;
	}
}
