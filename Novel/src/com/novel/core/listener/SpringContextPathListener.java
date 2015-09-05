package com.novel.core.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.novel.queue.pv.PvConsumer;
import com.novel.queue.search.SearchConsumer;

/**
 * spring获取webapplicationcontext,applicationcontext
 * @author Aroceee
 *
 */
public class SpringContextPathListener implements ServletContextListener {
	
	private static WebApplicationContext springContext;
	
	private static PvConsumer pvConsumer;
	private static SearchConsumer searchConsumer;
	
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
		pvConsumer = new PvConsumer();
		searchConsumer = new SearchConsumer();
		new Thread(pvConsumer).start();
		new Thread(searchConsumer).start();
	}
}
