package com.novel.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.novel.model.Pv;
import com.novel.model.Search;

public class Constants {

	public static final List<Pv> pvQueque = Collections.synchronizedList(new ArrayList<Pv>());  // PV消息队列
	public static final List<Search> searchQueque = Collections.synchronizedList(new ArrayList<Search>());  // search消息队列
	
	public static final Integer MinPvtotal = Integer.parseInt(PropertiesUtil.getPropertyValue("queue.minPvTotal")); // PV最小队列数（存入redis缓存）  
	public static final Integer MinDBTotal = Integer.parseInt(PropertiesUtil.getPropertyValue("queue.minDBTotal")); // 写入数据库的最小值
	public static final Integer maxWaitTime = Integer.parseInt(PropertiesUtil.getPropertyValue("queue.maxWaitTime")); // 数据最长缓存时间
	public static final Integer maxWaitTimeCache = Integer.parseInt(PropertiesUtil.getPropertyValue("queue.maxWaitTimeCache")); // 数据最长缓存时间缓存
}
