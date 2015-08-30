package com.novel.service;


import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.novel.dao.PvMapper;
import com.novel.model.Pv;

@Service("dataService")
public class DataService {
	
	@Resource(name="pvMapper")
	private PvMapper pvMapper;
	
	@Transactional
	public Integer batchUpdatePv(List<Pv> pv) throws Exception {
		return pvMapper.batchUpdate(pv);
	}
	
	@Transactional
	public Integer batchInsertPv(List<Pv> pv) throws Exception {
		return pvMapper.batchInsert(pv);
	}
}
