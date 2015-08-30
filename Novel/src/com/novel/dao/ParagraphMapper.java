package com.novel.dao;

import java.util.List;

import org.springframework.stereotype.Component;

import com.novel.model.Paragraph;

@Component("paragraphMapper")
public interface ParagraphMapper {

	Integer deleteByPrimaryKey(Integer id) throws Exception;
    Integer insert(Paragraph record) throws Exception;
    Integer insertSelective(Paragraph record) throws Exception;
    Paragraph selectByPrimaryKey(Integer id) throws Exception;
    List<Paragraph> selectBySid(Integer sid) throws Exception;
    Integer updateByPrimaryKeySelective(Paragraph record) throws Exception;
    Integer updateByPrimaryKey(Paragraph record) throws Exception;
}