package com.novel.model.admin;

import java.util.Date;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.novel.core.validate.UserDel;
import com.novel.core.validate.UserLogin;
import com.novel.core.validate.UserPass;
import com.novel.core.validate.UserReg;
import com.novel.core.validate.UserRule;

@SuppressWarnings("serial")
public class User implements java.io.Serializable {

	private Integer id;
	private String imgurl;
	private String username;
	private String password;
	private Integer rule;
	private Date regtime;
	
	public User(){
		
	}

	public User(Integer id, String imgurl, String username, String password, Integer rule, Date regtime) {
		this.id = id;
		this.imgurl = imgurl;
		this.username = username;
		this.password = password;
		this.rule = rule;
		this.regtime = regtime;
	}

	@NotNull(message="{user.id.require.error}", groups={UserPass.class, UserRule.class, UserDel.class})
	@Min(value=1,message="{user.id.type.error}", groups={UserPass.class, UserRule.class, UserDel.class})
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getImgurl() {
		return imgurl;
	}

	public void setImgurl(String imgurl) {
		this.imgurl = imgurl;
	}

	@NotNull(message="{user.username.require.error}", groups={UserLogin.class, UserReg.class})
	@Size(min=5,max=10,message="{user.username.length.error}", groups={UserLogin.class, UserReg.class})
	@Pattern(regexp="^\\w+$",message="{user.username.reg.error}", groups={UserLogin.class, UserReg.class})
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@NotNull(message="{user.password.require.error}", groups={UserLogin.class})
	@Size(min=5,max=10,message="{user.password.length.error}", groups={UserLogin.class})
	@Pattern(regexp="^\\w+$",message="{user.password.reg.error}", groups={UserLogin.class})
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@NotNull(message="{user.rule.require.error}", groups={UserReg.class, UserRule.class})
	@Min(value=1,message="{user.rule.type.error}", groups={UserReg.class, UserRule.class})
	@Max(value=4,message="{user.rule.type.error}", groups={UserReg.class, UserRule.class})
	public Integer getRule() {
		return rule;
	}

	public void setRule(Integer rule) {
		this.rule = rule;
	}

	public Date getRegtime() {
		return regtime;
	}

	public void setRegtime(Date regtime) {
		this.regtime = regtime;
	}
	
}
