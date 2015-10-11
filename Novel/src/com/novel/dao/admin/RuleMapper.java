package com.novel.dao.admin;

import java.util.List;

import org.springframework.stereotype.Component;

import com.novel.model.admin.Rule;

@Component("ruleMapper")
public interface RuleMapper {

	List<Rule> selectAll() throws Exception;
	Rule selectByPrimaryKey(Integer id) throws Exception;
}
