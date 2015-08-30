package com.novel.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import com.novel.model.Search;

@Component("searchMapper")
public interface SearchMapper {

	Integer selectAllTimes() throws Exception;
	Search selectTimesByDay(Date time) throws Exception;
	Integer batchInsert(@Param("list")List<Search> search) throws Exception;
	Integer batchUpdate(List<Search> search) throws Exception;
}
