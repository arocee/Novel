package com.novel.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.novel.model.Pv;

public class Constants {

	public static final List<Pv> pvQueque = Collections.synchronizedList(new ArrayList<Pv>());  // PV消息队列
	public static final int Maxtotal = Integer.parseInt(PropertiesUtil.getPropertyValue("queue.maxTotal")); // PV最大队列数  
}
