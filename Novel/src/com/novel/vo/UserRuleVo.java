package com.novel.vo;

import java.util.List;

import com.novel.model.admin.User;

@SuppressWarnings("serial")
public class UserRuleVo implements java.io.Serializable {

	private Integer id;
	private String description;
	private Integer addUser;
	private Integer manage;
	private List<User> users;
	
	public UserRuleVo() {
		
	}

	public UserRuleVo(Integer id, String description, Integer addUser,
			Integer manage, List<User> users) {
		this.id = id;
		this.description = description;
		this.addUser = addUser;
		this.manage = manage;
		this.users = users;
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

	public Integer getAddUser() {
		return addUser;
	}

	public void setAddUser(Integer addUser) {
		this.addUser = addUser;
	}

	public Integer getManage() {
		return manage;
	}

	public void setManage(Integer manage) {
		this.manage = manage;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}
}
