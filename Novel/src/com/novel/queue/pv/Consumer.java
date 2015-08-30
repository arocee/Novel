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
import com.novel.util.JedisCache;
import com.novel.util.Constants;

public class Consumer implements Runnable {
	
	private List<Pv> pvQueque = new ArrayList<>();
	
	private static final Log log = LogFactory.getLog(Consumer.class);
	
	private DataService dataService = (DataService)SpringContextPathListener.getApplicationContext().getBean("dataService");
	
	@Override
	public synchronized void run() {
		while(true) {
			try{              
				if(Constants.pvQueque.size() != 0){
					this.pvQueque.add(Constants.pvQueque.remove(Constants.pvQueque.size() - 1));
				}
				
				if(this.pvQueque.size() >= Constants.Maxtotal) {
					for (int i = 0; i < Constants.Maxtotal; i ++) {
						if(this.pvQueque.size() == 0){
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
						long count = JedisCache.putList("pv", id);
						
						if(count % 100 == 0){
							// 每100条便存一次
							List<String> list = JedisCache.getList("pv");
							JedisCache.del("pv");
							
							List<Pv> pvList = new ArrayList<>();
							
							for (String key : list) {
								List<String> values = JedisCache.getList(key, "url", "type", "time", "ip");
								JedisCache.del(key);
								
								if(values.get(0) == null || values.get(1) == null || 
										values.get(2) == null || values.get(3) == null) {
									continue;
								}
								
								pv = new Pv();
								pv.setUrl(values.get(0));
								pv.setType(Integer.parseInt(values.get(1)));
								pv.setTime(new Date(Long.parseLong(values.get(2))));
								pv.setIp(values.get(3));
								
								pvList.add(pv);
							}
							
							try {
								dataService.batchInsertPv(pvList);
							} catch (Exception e) {
								log.error("存储pv失败！<br/>" + e);
							}
						}
					}
				}
				Thread.sleep(10);
			}catch(InterruptedException e){              
				e.printStackTrace();          
			}catch(IllegalMonitorStateException e){              
				e.printStackTrace();          
			}
		}
	}
}
