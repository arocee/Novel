package com.novel.dao;

import java.util.List;

import org.springframework.stereotype.Component;

import com.novel.model.Type;

@Component("typeMapper")
public interface TypeMapper {

	Integer deleteByPrimaryKey(Integer id) throws Exception;
	Integer insert(Type record) throws Exception;
	Integer insertSelective(Type record) throws Exception;
    Type selectByPrimaryKey(Integer id) throws Exception;
    List<Type> selectList() throws Exception;
    Integer updateByPrimaryKeySelective(Type record) throws Exception;
    Integer updateByPrimaryKey(Type record) throws Exception;
}