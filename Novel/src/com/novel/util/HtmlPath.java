package com.novel.util;

import javax.servlet.http.HttpServletRequest;

/**
 * 获取静态化的路径
 * @author Aroceee
 *
 */
public class HtmlPath {

	/**
	 * 计算要生成的静态文件相对路径
	 * 因为大家在调试的时候一般在Tomcat的webapps下面新建站点目录的，
	 * 但在实际应用时直接布署到ROOT目录里面,这里要保证路径的一致性。
	 * @param request HttpServletRequest
	 * @return /目录/*.htm
	 */
	public static String getRequestHTML(HttpServletRequest request){
		//web应用名称,部署在ROOT目录时为空
		String contextPath = request.getContextPath();
		//web应用/目录/文件.do
		String requestURI = request.getRequestURI();
		//basePath里面已经有了web应用名称，所以直接把它replace掉，以免重复
		requestURI = requestURI.replaceFirst(contextPath, "");
		//将.do改为.htm,稍后将请求转发到此htm文件
		//requestURI = requestURI.substring(0, requestURI.indexOf(".")) + ".html";
		requestURI = requestURI + ".html";
		
		return "/WEB-INF/html" + requestURI;
	}
}
