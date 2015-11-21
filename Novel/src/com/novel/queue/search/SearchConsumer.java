package com.novel.queue.search;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.novel.core.listener.SpringContextPathListener;
import com.novel.model.admin.Search;
import com.novel.service.NovelService;
import com.novel.service.admin.DataService;
import com.novel.util.Constants;
import com.novel.util.JedisCache;

public final class SearchConsumer {

private static Log log = LogFactory.getLog(NovelService.class);
	
	/**
	 * 数据最长缓存时间，当线程运行一定时间以后，强制写入一次
	 */
	private Integer maxWaitTime = Math.round(Constants.maxWaitTime / 2);  // 与PV错开
	
	private long count = -1;
	
	private DataService dataService = (DataService)SpringContextPathListener.getApplicationContext().getBean("dataService");
	
	public final void excute() {
		// 创建一个可重用固定线程数的线程池
		final ExecutorService executorService = Executors.newSingleThreadExecutor();
		executorService.execute(new Runnable(){
			@Override
			public void run() {
				while(true) {
					try {
						if(Constants.searchQueque.size() > 0) {
							Search search = Constants.searchQueque.remove(Constants.searchQueque.size() - 1);
							
							Map<String, String> map = new HashMap<String, String>();
							map.put("keyword", search.getKeyword());
							map.put("count", search.getResultcount() + "");
							map.put("time", search.getTime().getTime() + "");
							
							String id = "sc" + Math.random();
							
							JedisCache.putMap(id, map);
							count = JedisCache.putList("sc", id);
						}
						
						Thread.sleep(50);
					} catch(Exception e) {
						log.error("Search线程Single出错：<br/>" + e);
						continue;
					}
				}
			}
		});
		
		final ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
		scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
			@Override
			public void run() {
				try {
					if(count % Constants.MinDBTotal == 0 || maxWaitTime > Constants.maxWaitTime) {
						maxWaitTime = 0;
						count = -1;
						
						// 每100条便存一次
						List<String> list = JedisCache.getList("sc");
						JedisCache.del("sc");
						
						List<Search> searchList = new ArrayList<>();
						
						for (String key : list) {
							List<String> values = JedisCache.getList(key, "keyword", "count", "time");
							JedisCache.del(key);
							
							if(values.get(0) == null || values.get(1) == null || 
									values.get(2) == null) {
								continue;
							}
							
							Search search = new Search();
							search.setKeyword(values.get(0));
							search.setResultcount(Integer.parseInt(values.get(1)));
							search.setTime(new Date(Long.parseLong(values.get(2))));
							
							searchList.add(search);
						}
						
						if(searchList.size() > 0)
							dataService.batchInsertSearch(searchList);
					}
				} catch (Exception e) {
					log.error("Search线程Scheduled出错：<br/>" + e);
				}
				
				maxWaitTime ++;  // 每次加1
			}
		}, 
		Constants.scheduleTime,
		Constants.scheduleTime, 
		TimeUnit.SECONDS);
	}
}
