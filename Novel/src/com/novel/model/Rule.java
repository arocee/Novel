package com.novel.model;

@SuppressWarnings("serial")
public class Rule implements java.io.Serializable {

	private Integer id;
	private String description;
	private Integer adduser;
	private Integer manage;
	
	public Rule() {
		
	}
	
	public Rule(Integer id, String description, Integer adduser, Integer manage) {
		this.id = id;
		this.description = description;
		this.adduser = adduser;
		this.manage = manage;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getAdduser() {
		return adduser;
	}

	public void setAdduser(Integer adduser) {
		this.adduser = adduser;
	}

	public Integer getManage() {
		return manage;
	}

	public void setManage(Integer manage) {
		this.manage = manage;
	}
	
	
}
