package com.novel.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.novel.model.admin.Pv;
import com.novel.model.admin.Search;

/**
 * 各种常量
 * @author Aroceee
 *
 */
public class Constants {

	public static final List<Pv> pvQueque = Collections.synchronizedList(new ArrayList<Pv>());  // PV消息队列
	public static final List<Search> searchQueque = Collections.synchronizedList(new ArrayList<Search>());  // search消息队列
	
	public static final Integer MinPvtotal = Integer.parseInt(PropertiesUtil.getPropertyValue("queue.minPvTotal")); // PV最小队列数（存入redis缓存）  
	public static final Integer MinDBTotal = Integer.parseInt(PropertiesUtil.getPropertyValue("queue.minDBTotal")); // 写入数据库的最小值
	public static final Integer maxWaitTime = Integer.parseInt(PropertiesUtil.getPropertyValue("queue.maxWaitTime")); // 数据最长缓存时间
	public static final Integer scheduleTime = Integer.parseInt(PropertiesUtil.getPropertyValue("queue.scheduleTime")); // 线程zhixing	
	
	public static final String defaultTou = PropertiesUtil.getPropertyValue("default.tou"); // 默认头像
	public static final String defaultPass = PropertiesUtil.getPropertyValue("default.pass"); // 默认密码
	
	public static final Integer adminRule = Integer.parseInt(PropertiesUtil.getPropertyValue("rule.admin")); // 管理员权限
	public static final Integer guestRule = Integer.parseInt(PropertiesUtil.getPropertyValue("rule.guest")); // 游客
	public static final Integer adduserRule = Integer.parseInt(PropertiesUtil.getPropertyValue("rule.adduser")); // 用户管理权限
	public static final Integer manageRule = Integer.parseInt(PropertiesUtil.getPropertyValue("rule.manage")); // 内容维护权限
}
