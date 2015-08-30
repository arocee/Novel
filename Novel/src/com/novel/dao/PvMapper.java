package com.novel.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import com.novel.model.Pv;
import com.novel.vo.PvCountVo;

@Component("pvMapper")
public interface PvMapper {

	Integer selectAllTimes(Integer type) throws Exception;
	Pv selectTimesByDay(@Param("type") Integer type, @Param("time") Date time) throws Exception;
	PvCountVo selectCount() throws Exception;
	Integer batchInsert(@Param("list")List<Pv> pv) throws Exception;
	Integer batchUpdate(List<Pv> pv) throws Exception;
	Integer insert(Pv pv) throws Exception;
}
