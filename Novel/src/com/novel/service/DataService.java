package com.novel.service;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.novel.dao.NovelMapper;
import com.novel.dao.PvMapper;
import com.novel.dao.SearchMapper;
import com.novel.model.Pv;
import com.novel.model.Search;
import com.novel.vo.NovelCountVo;
import com.novel.vo.PvCountVo;
import com.novel.vo.PvDataVo;

@Service("dataService")
public class DataService {
	
	@Resource(name="pvMapper")
	private PvMapper pvMapper;
	
	@Resource(name="searchMapper")
	private SearchMapper searchMapper;
	
	@Resource(name="novelMapper")
	private NovelMapper novelMapper;
	
	@Transactional
	public Integer batchUpdatePv(List<Pv> pvList) throws Exception {
		return pvMapper.batchUpdate(pvList);
	}
	
	@Transactional
	public Integer batchInsertPv(List<Pv> pvList) throws Exception {
		return pvMapper.batchInsert(pvList);
	}
	
	@Transactional
	public Integer batchInsertSearch(List<Search> searchList) throws Exception {
		return searchMapper.batchInsert(searchList);
	}
	
	@Transactional
	public NovelCountVo getNovelCount() throws Exception  {
		return novelMapper.selectCount();
	}
	
	@Transactional
	public PvCountVo getPvCount() throws Exception  {
		return pvMapper.selectCount();
	}
	
	@Transactional
	public List<PvDataVo> getPvByLastSevenDay(Date startTime, Date endTime) throws Exception {
		return pvMapper.selectTimesByDay(startTime, endTime);
	}
	
	@Transactional
	public List<Search> getSearchByLastSevenDay(Date startTime, Date endTime) throws Exception {
		return searchMapper.selectTimesByDay(startTime, endTime);
	}

	@Transactional
	public List<Search> getSearchAllDay() throws Exception {
		return searchMapper.selectTimesAll();
	}
}
