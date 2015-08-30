package com.novel.core.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.novel.queue.pv.Consumer;

/**
 * spring获取webapplicationcontext,applicationcontext
 * @author Aroceee
 *
 */
public class SpringContextPathListener implements ServletContextListener {
	
	private static WebApplicationContext springContext;
	
	public static Consumer consumer;
	
	public SpringContextPathListener() {
		super();
	}

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		springContext = WebApplicationContextUtils.getWebApplicationContext(sce.getServletContext());
		
		this.init();
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		
	}
	
	public static ApplicationContext getApplicationContext() {
        return springContext;
    }
	
	private void init() {
		consumer = new Consumer();
		new Thread(consumer).start();
	}

}
