package com.novel.dao;

import java.util.List;

import org.springframework.stereotype.Component;

import com.novel.model.Article;

@Component("articleMapper")
public interface ArticleMapper {

	Integer deleteByPrimaryKey(Integer id) throws Exception;
	Integer insert(Article record) throws Exception;
	Integer insertSelective(Article record) throws Exception;
    Article selectByPrimaryKey(Integer id) throws Exception;
    Article selectArticleAndSectionByPrimaryKey(Integer id) throws Exception;
    List<Article> selectList() throws Exception;
    List<Article> selectByTid(Integer tid) throws Exception;
    Integer updateByPrimaryKeySelective(Article record) throws Exception;
    Integer updateByPrimaryKey(Article record) throws Exception;
}