package com.novel.dao.admin;

import java.util.List;

import org.springframework.stereotype.Component;

import com.novel.model.admin.User;
import com.novel.vo.UserRuleVo;

@Component("userMapper")
public interface UserMapper {

	User selectByPrimaryKey(Integer id) throws Exception;
	User selectByUsername(String username) throws Exception;
	List<UserRuleVo> selectByRule() throws Exception;
	Integer deleteByPrimaryKey(Integer id) throws Exception;
	Integer insert(User user) throws Exception;
	Integer updateByPrimaryKeySelective(User user) throws Exception;
}
