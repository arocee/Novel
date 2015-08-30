package com.novel.core.listener;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.web.context.ServletContextAware;


/**
 * spring初始化执行方法
 * @author Aroceee
 *
 */
@Deprecated
public class InitDataListener implements InitializingBean, ServletContextAware{
	
	@Override
	public void afterPropertiesSet() throws Exception {
		//在这个方法里面写 初始化的数据也可以。
		
	}
	@Override
	public void setServletContext(ServletContext sc) {
		
	}
}