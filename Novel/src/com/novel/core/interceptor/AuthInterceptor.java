package com.novel.core.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.novel.model.admin.User;


public class AuthInterceptor extends HandlerInterceptorAdapter {

	@Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		
		if(handler.getClass().isAssignableFrom(HandlerMethod.class)){
			AuthPassport authPassport = ((HandlerMethod) handler).getMethodAnnotation(AuthPassport.class);
			String url = request.getRequestURL().toString();
			
			//没有声明需要权限,或者声明不验证权限
            if(authPassport == null || authPassport.validate() == false) {
            	return true;
            } else if(url.indexOf("/member") != -1 || url.indexOf("/login") != -1){
            	return true;
            }else {
            	// 获取信息
        		User user = (User) request.getSession().getAttribute("user"); // 当前登录用户
        		
        		if(user == null) {  // 未登录
        			// 是否是ajax请求
        			if(url.indexOf("/easy") != -1){
        				// ajax
        				response.setHeader("sessionstatus", "timeout");
        			} else {
        				response.sendRedirect("/Novel/admin/member");
        			}
        			return false;
        		} else {
        			return true;
        		}
            }
		}
		return true;
	}
}
