package com.novel.dao;

import java.util.List;

import org.springframework.stereotype.Component;

import com.novel.model.Rule;

@Component("ruleMapper")
public interface RuleMapper {

	List<Rule> selectAll() throws Exception;
	Rule selectByPrimaryKey(Integer id) throws Exception;
}
