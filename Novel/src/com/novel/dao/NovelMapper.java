package com.novel.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import com.novel.model.Novel;
import com.novel.model.admin.Search;

@Component("novelMapper")
public interface NovelMapper {

	List<Novel> selectAll() throws Exception;
	List<Search> selectKeyword(@Param("key")String key) throws Exception;
}
