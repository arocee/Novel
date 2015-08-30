package com.novel.dao;

import java.util.List;

import org.springframework.stereotype.Component;

import com.novel.model.Novel;
import com.novel.vo.NoverCountVo;

@Component("novelMapper")
public interface NovelMapper {

	List<Novel> selectAll() throws Exception;
	NoverCountVo selectCount() throws Exception;
}
