package com.novel.dao.admin;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import com.novel.model.admin.Search;
import com.novel.vo.NovelCountVo;

@Component("searchMapper")
public interface SearchMapper {
	Integer selectAllTimes() throws Exception;
	List<Search> selectTimesAll() throws Exception;
	List<Search> selectTimesByDay(@Param("starttime")Date startTime, @Param("endtime")Date endTime) throws Exception;
	NovelCountVo selectCount() throws Exception;
	Integer batchInsert(@Param("list")List<Search> search) throws Exception;
	Integer batchUpdate(List<Search> search) throws Exception;
}
