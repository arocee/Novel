package com.test;

import static org.junit.Assert.assertEquals;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import com.novel.dao.NovelMapper;
import com.novel.dao.PvMapper;
import com.novel.dao.SearchMapper;
import com.novel.model.Article;
import com.novel.model.Search;
import com.novel.util.DateHandle;
import com.novel.util.JedisCache;
import com.novel.util.MD5;
import com.novel.vo.PvDataVo;
import com.novel.vo.PvVo;

@RunWith(SpringJUnit4ClassRunner.class)  
@WebAppConfiguration  
@ContextConfiguration(locations = { "classpath:applicationContext.xml", "file:C:/Program Files/apache-tomcat-8.0.14/webapps/Novel/WEB-INF/spring-servlet.xml"})  
@TransactionConfiguration(defaultRollback = true)  
@Transactional  
public class NovelTests {
	private RequestMappingHandlerAdapter handlerAdapter = new RequestMappingHandlerAdapter();
	
	@Autowired
	private NovelController controller;
	
	@Resource
	private ArticleMapper articleMapper;
	
	@Resource
	private SearchMapper searchMapper;
	
	@Resource
	private NovelMapper novelMapper;
	
	@Resource
	private PvMapper pvMapper;
	
	private final MockHttpServletRequest request = new MockHttpServletRequest();      
	private final MockHttpServletResponse response = new MockHttpServletResponse();
	
	@Test
	public void testGetPatchList_1() throws Exception {          
		//Dao鐨勬煇涓柟娉�  
		List<Article> articles = articleMapper.selectList();
		assertEquals(1, articles.size());
	}
	
	@Test
	public void testSearchMapper() throws Exception {      
		List<Search> searches = searchMapper.selectTimesAll();
		for (Search searchVo : searches) {
			System.out.println(searchVo.getKeyword());
			System.out.println(searchVo.getResultcount());
		}
	}
	
	@Test
	public void testSearchMapper2() throws Exception {      
		int count = searchMapper.selectAllTimes();
		System.out.println(count);
	}
	
	@Test
	public void testSearchMapper3() throws Exception {      
		List<Search> searches = searchMapper.selectTimesByDay(DateHandle.getTheDate(1), DateHandle.getTheDate(-1));
		for (Search searchVo : searches) {
			System.out.println(searchVo.getKeyword());
			System.out.println(searchVo.getResultcount());
		}
	}
	
	@Test
	public void testNovelMapper() throws Exception {
		List<Search> keywords = novelMapper.selectKeyword(null);
		System.out.println(keywords.size());
		for (Search search : keywords) {
			System.out.println(search.getKeyword());
		}
	}
	
	@Test
	public void testPvMapper() throws Exception {   
		//List<Date> list = DateHandle.getBetweenDay(DateHandle.getTheDate(7), DateHandle.getTodayZoreTime());
		List<PvDataVo> pvDataVoes = pvMapper.selectTimesByDay(DateHandle.getTheDate(7), DateHandle.getTheDate(-1));
		for (PvDataVo pvDataVo : pvDataVoes) {
			System.out.println(pvDataVo.getDate());
			for (PvVo pvVo : pvDataVo.getPvVo()) {
				System.out.println(pvVo.getType());
				System.out.println(pvVo.getResultcount());
			}
		}
		
	}
	
	@Test
	public void testPvMapper2() throws Exception {   
		int count1 = pvMapper.selectAllTimes(0);
		int count2 = pvMapper.selectAllTimes(1);
		System.out.println(count1 + "==" + count2);
	}
	
	@Test
	public void testMain4User() throws Exception {
		request.setRequestURI("/index");          
		request.setMethod(HttpMethod.POST.name());          
		//HttpSession session = request.getSession();          
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
	public void baseTest() throws Exception {
		Date date = new Date(1441436858837l);
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		System.out.println(df.format(date));
	}
	
	@Test
	public void saveSearch() throws Exception {
		// 每100条便存一次
		List<String> list = JedisCache.getList("sc");
		JedisCache.del("sc");
		
		List<Search> searchList = new ArrayList<>();
		
		for (String key : list) {
			List<String> values = JedisCache.getList(key, "keyword", "count", "time");
			JedisCache.del(key);
			
			if(values.get(0) == null || values.get(1) == null || 
					values.get(2) == null) {
				continue;
			}
			
			Search search = new Search();
			search.setKeyword(values.get(0));
			search.setResultcount(Integer.parseInt(values.get(1)));
			search.setTime(new Date(Long.parseLong(values.get(2))));
			
			searchList.add(search);
		}
		
		if(searchList.size() > 0)
			searchMapper.batchInsert(searchList);
	}
}
