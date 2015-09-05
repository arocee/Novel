package com.novel.dao;

import java.util.List;

import org.springframework.stereotype.Component;

import com.novel.model.Novel;
import com.novel.vo.NovelCountVo;

@Component("novelMapper")
public interface NovelMapper {

	List<Novel> selectAll() throws Exception;
	NovelCountVo selectCount() throws Exception;
}
