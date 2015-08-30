package com.novel.model;

import java.util.Date;

@SuppressWarnings("serial")
public class Pv implements java.io.Serializable {

	private Integer id;
	private String url;
	private Integer type;
	private Date time;
	private String ip;
	private Integer times;
	
	public Pv(){
		
	}

	public Pv(Integer id, String url, Integer type, Date time, String ip) {
		this.id = id;
		this.url = url;
		this.type = type;
		this.time = time;
		this.ip = ip;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public Integer getTimes() {
		return times;
	}

	public void setTimes(Integer times) {
		this.times = times;
	}
	
}
