package com.novel.dao;

import org.springframework.stereotype.Component;

import com.novel.model.User;

@Component("userMapper")
public interface UserMapper {

	User selectByPrimaryKey(Integer id) throws Exception;
	User selectByUsername(String username) throws Exception;
	Integer deleteByPrimaryKey(Integer id) throws Exception;
	Integer insert(User user) throws Exception;
	Integer updateByPrimaryKeySelective(User user) throws Exception;
}
