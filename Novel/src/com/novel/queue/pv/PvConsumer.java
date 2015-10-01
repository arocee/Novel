package com.novel.queue.pv;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.novel.core.listener.SpringContextPathListener;
import com.novel.model.Pv;
import com.novel.service.DataService;
import com.novel.service.NovelService;
import com.novel.util.JedisCache;
import com.novel.util.Constants;

public class PvConsumer implements Runnable {
	
	private List<Pv> pvQueque = new ArrayList<>();
	
	private static Log log = LogFactory.getLog(NovelService.class);
	
	/**
	 * 数据最长缓存时间，当线程运行一定时间以后，强制写入一次
	 */
	private Integer maxWaitTime = 0;
	private Integer maxWaitTimeCache = 0;
	
	private long count = -1;
	
	private DataService dataService = (DataService)SpringContextPathListener.getApplicationContext().getBean("dataService");
	
	@Override
	public synchronized void run() {
		while(true) {
			try {
				if(Constants.pvQueque.size() != 0) {
					this.pvQueque.add(Constants.pvQueque.remove(Constants.pvQueque.size() - 1));
				}
				if(this.pvQueque.size() >= Constants.MinPvtotal || maxWaitTime > Constants.maxWaitTime) {
					for (int i = 0; i < Constants.MinPvtotal; i ++) {
						if(this.pvQueque.size() == 0){
							maxWaitTime = 0;
							break;
						}
						
						Pv pv = this.pvQueque.remove(this.pvQueque.size() - 1);
						
						Map<String, String> map = new HashMap<String, String>();
						map.put("url", pv.getUrl());
						map.put("type", pv.getType() + "");
						map.put("time", pv.getTime().getTime() + "");
						map.put("ip", pv.getIp());
						
						String id = "pv" + Math.random();
						
						JedisCache.putMap(id, map);
						count = JedisCache.putList("pv", id);
						
						if(count % Constants.MinDBTotal == 0 || maxWaitTime > Constants.maxWaitTime) {
							maxWaitTime = 0;
							count = -1;
							
							// 每100条便存一次
							List<String> list = JedisCache.getList("pv");
							JedisCache.del("pv");
							
							List<Pv> pvList = new ArrayList<>();
							
							for (String key : list) {
								List<String> values = JedisCache.getList(key, "url", "type", "time", "ip");
								JedisCache.del(key);
								
								pv = new Pv();
								pv.setUrl(values.get(0));
								pv.setType(Integer.parseInt(values.get(1)));
								pv.setTime(new Date(Long.parseLong(values.get(2))));
								pv.setIp(values.get(3));
								
								pvList.add(pv);
							}
							
							if(pvList.size() > 0)
								dataService.batchInsertPv(pvList);
						}
					}
					maxWaitTime = 0;
				}
				
				Thread.sleep(5);
			} catch (Exception e) {
				log.error("PV线程出错：<br/>" + e);
				continue;
			}
			
			maxWaitTimeCache ++;
			if(maxWaitTimeCache > Constants.maxWaitTimeCache) {
				maxWaitTimeCache = 0;
				maxWaitTime ++;
			}
		}
	}
}
