package com.test;

import static org.junit.Assert.assertEquals;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import com.novel.controller.NovelController;
import com.novel.dao.ArticleMapper;
import com.novel.model.Article;
import com.novel.util.MD5;

@RunWith(SpringJUnit4ClassRunner.class)  
@WebAppConfiguration  
@ContextConfiguration(locations = { "classpath:applicationContext.xml", "file:C:/Program Files/apache-tomcat-8.0.14/webapps/Novel/WEB-INF/spring-servlet.xml"})  
//当然 你可以声明一个事务管理 每个单元测试都进行事务回滚 无论成功与否  
@TransactionConfiguration(defaultRollback = true)  
//记得要在XML文件中声明事务哦~~~我是采用注解的方式  
@Transactional  

public class NovelTests {
	private RequestMappingHandlerAdapter handlerAdapter = new RequestMappingHandlerAdapter();
	
	@Autowired
	private NovelController controller;
	
	@Resource
	private ArticleMapper articleMapper;
	
	private final MockHttpServletRequest request = new MockHttpServletRequest();      
	private final MockHttpServletResponse response = new MockHttpServletResponse();
	
	@Test
	public void testGetPatchList_1() throws Exception {          
		//Dao的某个方法   
		List<Article> articles = articleMapper.selectList();
		assertEquals(1, articles.size());
	}
	
	@Test
	public void testMain4User() throws Exception {
		request.setRequestURI("/index");          
		request.setMethod(HttpMethod.POST.name());          
		//HttpSession session = request.getSession();          
		//设置 认证信息         
		//session.setAttribute(CommonConstants.SESSION_USER_TYPE, 1);          
		//session.setAttribute(CommonConstants.SESSION_USER_ID, 0);          
		//session.setAttribute(CommonConstants.SESSION_USER_ACC, "aa1");            
		ModelAndView mav = handlerAdapter.handle(request, response, controller);          
		assertEquals("index", mav.getViewName());
	}
	
	@Test
	public void testMd5() throws Exception {
		System.out.println(MD5.md5("abc123"));
	}
	
	@Test
	public void baseTest() {
		System.out.println(Math.random());
	}
}
