package com.novel.core.interceptor;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.novel.model.admin.Pv;
import com.novel.model.admin.Search;
import com.novel.util.Constants;

/**
 * 数据统计
 * @author Aroceee
 *
 */
public class DataInterceptor extends HandlerInterceptorAdapter  {

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		String url = request.getRequestURL().toString();
		String ip = getIpAddr(request);
		
		Pv pv = new Pv();
		pv.setIp(ip);
		pv.setTime(new Date());
		pv.setUrl(url);
		if(url.indexOf("/admin") != -1){
			pv.setType(1); // 后台
		} else {
			pv.setType(0); // 前台
		}
		
		Constants.pvQueque.add(pv);
		
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		String url = request.getRequestURL().toString();
		if(url.endsWith("/static/search")) {
			if(modelAndView.getModel().get("keyWords") == null 
					|| !((String)modelAndView.getModel().get("pageNow")).equals("0")) {
				return;
			}
			
			Search search = new Search();
			search.setKeyword((String) modelAndView.getModel().get("keyWords"));
			search.setResultcount((Integer)modelAndView.getModel().get("count"));
			search.setTime(new Date());
			
			Constants.searchQueque.add(search);
		}
	}

	// 获取用户ip
	private String getIpAddr(HttpServletRequest request) {  
	    String ip = request.getHeader("x-forwarded-for");  
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
	        ip = request.getHeader("Proxy-Client-IP");  
	    }  
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
	        ip = request.getHeader("WL-Proxy-Client-IP");  
	    }  
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
	        ip = request.getRemoteAddr();  
	    }  
	    return ip;  
	}  
	
}
