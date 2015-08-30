package com.novel.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import com.novel.model.Section;

@Component("sectionMapper")
public interface SectionMapper {

	Integer deleteByPrimaryKey(Integer id) throws Exception;
	Integer insert(Section record) throws Exception;
	Integer insertSelective(Section record) throws Exception;
    Section selectByPrimaryKey(Integer id) throws Exception;
    Section selectSectionAndParagraphByPrimaryKey(@Param(value="id") Integer id) throws Exception;
    List<Section> selectByAid(Integer aid) throws Exception;
    Integer updateByPrimaryKeySelective(Section record) throws Exception;
    Integer updateByPrimaryKey(Section record) throws Exception;
}