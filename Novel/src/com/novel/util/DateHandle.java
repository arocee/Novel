package com.novel.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 时间操作
 * @author Aroceee
 *
 */
public class DateHandle {
	
	/**
	 * 获取格式化时间字符串
	 * @param date
	 * @param format
	 * @return
	 * @throws ParseException
	 */
	public static String getFormatedDate(Date date, String format) throws ParseException {
		if(format == null) {
			format = "yyyy-MM-dd HH:mm:ss";
		}
		DateFormat sf = new SimpleDateFormat(format);
		
		return sf.format(date);
	}

	/**
	 * 获取之前或之后的某一天
	 * @param off
	 * @return
	 * @throws ParseException
	 */
	public static Date getTheDate(int off) throws ParseException {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -off);
		Date date = cal.getTime();
		DateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		String dateStr = sf.format(date).split("\\s")[0] + " 00:00:00";
		
		return sf.parse(dateStr);
	}
	
	/**
	 * 获取今天零点零时零分
	 * @return
	 * @throws ParseException
	 */
	public static Date getTodayZoreTime() throws ParseException {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, 0);
		Date date = cal.getTime();
		DateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		String dateStr = sf.format(date).split("\\s")[0] + " 00:00:00";
		
		return sf.parse(dateStr);
	}
	
	/**
	 * 获取一个时间范围
	 * @param startTime
	 * @param endTime
	 * @return
	 * @throws ParseException
	 */
	public static List<Date> getBetweenDay(Date startTime, Date endTime) throws ParseException {
		List<Date> list = new ArrayList<>();
		int off = daysBetween(startTime, endTime);
		
		list.add(startTime);
		
		for(int i = 1; i < off; i ++) {
			list.add(getSpecifiedDayAfter(list.get(list.size() - 1)));
		}
		
		return list;
	}
	
	/**
	 * 格式化一个时间数组
	 * @param dateList
	 * @return
	 * @throws ParseException
	 */
	public static List<String> getFomatedDays(List<Date> dateList) throws ParseException {
		DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		List<String> fomatedDateList = new ArrayList<>();
		
		for (Date date : dateList) {
			fomatedDateList.add(sdf.format(date));
		}
		
		return fomatedDateList;
	}
	
	/** 
	 * 计算两个日期之间相差的天数 
	* @param smdate 较小的时间 
	* @param bdate 较大的时间 
	* @return 相差天数 
	* @throws ParseException 
	 */ 
	public static int daysBetween(Date smdate,Date bdate) throws ParseException { 
		 DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); 
		 smdate = sdf.parse(sdf.format(smdate)); 
		 bdate = sdf.parse(sdf.format(bdate)); 
		 Calendar cal = Calendar.getInstance(); 
		 cal.setTime(smdate); 
		 long time1 = cal.getTimeInMillis(); 
		 cal.setTime(bdate); 
		 long time2 = cal.getTimeInMillis(); 
		 long between_days = (time2-time1)/(1000*3600*24); 
	
		 return Integer.parseInt(String.valueOf(between_days)); 
	 }
	 
	 /** 
	 *字符串的日期格式的计算 
	 */ 
	 public static int daysBetween(String smdate,String bdate) throws ParseException{ 
		  DateFormat sdf=new SimpleDateFormat("yyyy-MM-dd"); 
		  Calendar cal = Calendar.getInstance(); 
		  cal.setTime(sdf.parse(smdate)); 
		  long time1 = cal.getTimeInMillis(); 
		  cal.setTime(sdf.parse(bdate)); 
		  long time2 = cal.getTimeInMillis(); 
		  long between_days=(time2-time1)/(1000*3600*24); 
	
		  return Integer.parseInt(String.valueOf(between_days)); 
	  } 
	 
	 /**
     * 获得指定日期的前一天
     * 
     * @param specifiedDay
     * @return
     * @throws Exception
     */
    public static Date getSpecifiedDayBefore(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int day = c.get(Calendar.DATE);
        c.set(Calendar.DATE, day - 1);

        
        return c.getTime();
    }

    /**
     * 获得指定日期的后一天
     * 
     * @param specifiedDay
     * @return
     */
    public static Date getSpecifiedDayAfter(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int day = c.get(Calendar.DATE);
        c.set(Calendar.DATE, day + 1);

        return c.getTime();
    }
    
    /**
     * 获取一天的范围
     * @param date
     * @return
     * @throws ParseException
     */
    public static List<Date> getDateRange(String date) throws ParseException {
    	List<Date> dateRange = new ArrayList<>();
    	
    	DateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	Date startTime = sdf.parse(date + " 00:00:00");
    	dateRange.add(startTime);
    	
    	Date endTime = getSpecifiedDayAfter(startTime);
    	dateRange.add(endTime);
    	
    	return dateRange;
    }
}
