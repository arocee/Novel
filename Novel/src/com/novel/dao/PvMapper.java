package com.novel.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import com.novel.model.Pv;
import com.novel.vo.PvCountVo;
import com.novel.vo.PvDataVo;

@Component("pvMapper")
public interface PvMapper {
	Integer selectAllTimes(Integer type) throws Exception;
	List<PvDataVo> selectTimesByDay(@Param("starttime")Date startTime, @Param("endtime")Date endTime) throws Exception;
	PvCountVo selectCount() throws Exception;
	Integer batchInsert(@Param("list") List<Pv> pv) throws Exception;
	Integer batchUpdate(List<Pv> pv) throws Exception;
	Integer insert(Pv pv) throws Exception;
}
