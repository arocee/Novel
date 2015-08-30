package com.novel.core.interceptor;


import java.io.File;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.novel.util.HtmlPath;



public class FreeMarkerInterceptor extends HandlerInterceptorAdapter {
	
	@Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		
		if(handler.getClass().isAssignableFrom(HandlerMethod.class)){
			AuthPassport authPassport = ((HandlerMethod) handler).getMethodAnnotation(AuthPassport.class);
			
			//没有声明需要权限,或者声明不验证权限
            if(authPassport == null || authPassport.validate() == false) {
            	return true;
            } else {
            	//站点根目录的绝对路径
        		String basePath = request.getSession().getServletContext().getRealPath("/");
        		String requestHTML = HtmlPath.getRequestHTML(request);
        		//静态页面绝对路径
        		String htmlPath = basePath + requestHTML;
        		File htmlFile = new File(htmlPath);
        		
        		if(!htmlFile.exists()){
                	return true;
                }
        		
        		Date date = new Date();
        		
        		long lastModified = htmlFile.lastModified();
        		long nowTime = date.getTime();
        		
        		long modifiedTime = (nowTime - lastModified) / 1000;  
        		
        		// 每小时更新一次
        		if(modifiedTime < 60 * 60) {
        			request.getRequestDispatcher(requestHTML).forward(request, response);
        			return false;
        		}
        		
            	return true;
            }
		}
		return true;
	}
}
