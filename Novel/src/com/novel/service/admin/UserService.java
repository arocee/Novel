package com.novel.service.admin;

import java.io.InputStream;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.novel.dao.admin.RuleMapper;
import com.novel.dao.admin.UserMapper;
import com.novel.model.admin.Rule;
import com.novel.model.admin.User;
import com.novel.util.DwindlePic;
import com.novel.util.MD5;
import com.novel.util.PropertiesUtil;
import com.novel.vo.UserRuleVo;

@Service("userService")
public class UserService {

	@Resource(name="userMapper")
	private UserMapper userMapper;
	
	@Resource(name="ruleMapper")
	private RuleMapper ruleMapper;
	
	@Transactional(readOnly=true)
	public User queryUser(User user) throws Exception {
		User u = userMapper.selectByUsername(user.getUsername());
		if(u == null) {
			return null;
		}
		String password = MD5.md5(user.getPassword());
		
		if(password.equals(u.getPassword())) {
			return u;
		}
		return null;
	}
	
	@Transactional(readOnly=true)
	public User getUserById(Integer id) throws Exception {
		return userMapper.selectByPrimaryKey(id);
	}
	
	@Transactional
	public Integer addUser(User user) throws Exception {
		return userMapper.insert(user);
	}
	
	@Transactional
	public Integer deleteUserByPrimaryKey(Integer id) throws Exception {
		return userMapper.deleteByPrimaryKey(id);
	}
	
	@Transactional
	public Integer updateUser(User user, Integer id) throws Exception {
		user.setId(id);
		return userMapper.updateByPrimaryKeySelective(user);
	}
	
	@Transactional(readOnly=true)
	public List<Rule> queryRuleAll() throws Exception {
		return ruleMapper.selectAll();
	}
	
	@Transactional(readOnly=true)
	public Rule queryRuleById(Integer id) throws Exception {
		return ruleMapper.selectByPrimaryKey(id);
	}
	
	@Transactional(readOnly=true)
	public List<UserRuleVo> queryUserByRule() throws Exception {
		return userMapper.selectByRule();
	}
	
	public User uploadPhoto(InputStream in, float left, float top, float areaWidth, float areaHeight, float width, float height, String extensions, User user) throws Exception {
		// 对图片进行缩放
		DwindlePic dwindlePic = new DwindlePic();
		
		String imgurl = dwindlePic.handle(in, left, top, areaWidth, areaHeight, width, height, extensions, user.getUsername());
		
		User u = new User();
		
		u.setId(user.getId());
		u.setImgurl(imgurl);
		
		try {
			userMapper.updateByPrimaryKeySelective(u);
			// 删除旧的图片
			if(!user.getImgurl().equals(PropertiesUtil.getPropertyValue("default.tou"))){
				dwindlePic.deleteFile(user.getImgurl()); 
			}
		} catch (Exception e) {
			dwindlePic.deleteFile(imgurl); // 删除失败的图片
			throw e;
		}
		
		user.setImgurl(imgurl);
		
		return user;
	}
}
