package com.novel.controller;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.novel.core.interceptor.AuthPassport;
import com.novel.model.Article;
import com.novel.model.Novel;
import com.novel.model.Search;
import com.novel.model.Section;
import com.novel.model.Type;
import com.novel.service.NovelService;
import com.novel.util.PropertiesUtil;
import com.novel.vo.PagerVo;

@Controller
@RequestMapping("/static")
public class NovelController {
	
	@Resource(name="novelService")
	private NovelService novelService;
	
	@AuthPassport
	@RequestMapping("/index")
	public ModelAndView index() throws Exception {
		ModelAndView mav = new ModelAndView("index");
		List<Type> nav = novelService.getTypes();
		mav.addObject("nav", nav);
		return mav;
	}
	
	@AuthPassport
	@RequestMapping("/detail/{tid}/{aid}/{sid}")
	public ModelAndView detail(HttpServletRequest request, HttpServletResponse response, @PathVariable Integer tid, @PathVariable Integer aid, @PathVariable Integer sid) throws Exception {
		ModelAndView mav = new ModelAndView("detail");
		List<Type> nav = novelService.getTypes();
		mav.addObject("nav", nav); // 一级菜单
		
		for (Type type : nav) {
			if(type.getId().equals(tid)) {
				mav.addObject("subNav", type); // 二级菜单
			}
		}
		
		Article thirdNav = novelService.getAricleAndSection(aid);
		mav.addObject("thirdNav", thirdNav); // 三级菜单
		
		if(sid.equals(0)) sid = thirdNav.getSections().get(0).getId();
		
		Section contents = novelService.getSectionAndParagraph(sid);
		mav.addObject("contents", contents); // 三级菜单
		
		mav.addObject("tid", tid);
		mav.addObject("aid", aid);
		mav.addObject("sid", sid);
		
		return mav;
	}
	
	@RequestMapping("/searchIndex")
	public ModelAndView searchIndex(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView("searchIndex");
		List<Type> nav = novelService.getTypes();
		mav.addObject("nav", nav);
		return mav;
	}
	
	@RequestMapping(value="/hotKeywords", method=RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> hotKeywords(String key, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		
		List<Search> keywords = novelService.getHotestKeyWords(key);
		modelMap.put("keywords", keywords);
		modelMap.put("success", true);
	
		return modelMap;
	}
	
	@RequestMapping("/search")
	public ModelAndView search(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView("search");
		
		String searchType = request.getParameter("searchType");
		String tid_str = request.getParameter("tid");
		String aid_str = request.getParameter("aid");
		String sid_str = request.getParameter("sid");
		String keyWords = request.getParameter("keyWords");
		String pageNow = request.getParameter("pageNow");
		
		if(keyWords == null || "".equals(keyWords)) {
			mav = new ModelAndView("redirect:/static/searchIndex");
			return mav;
		}
		
		int id = 0;
		
		if("1".equals(searchType)) {
			id = Integer.parseInt(tid_str);
		} else if("2".equals(searchType)) {
			id = Integer.parseInt(aid_str);
		} else if("3".equals(searchType)) {
			id = Integer.parseInt(sid_str);
		}
		
		if(pageNow == null || pageNow.equals("")) {
			pageNow = "0";
		}
		
		int count = 0;
		List<Novel> list = null;
		
		if(keyWords.matches("^\\p{Punct}$|\\p{Blank}|\\p{Space}$")) {
			list = new ArrayList<>();
		} else {
			String fixedKeyWords = keyWords.replaceAll("\\p{Punct}|\\p{Blank}|\\p{Space}|\\\\|\\/", "");
			count = novelService.getNoverCount(fixedKeyWords, id, Integer.parseInt(searchType));
			list = novelService.getNover(fixedKeyWords, id, Integer.parseInt(searchType), Integer.parseInt(pageNow));
		}
		
		// 获取分页数
		int pageCount = (int) Math.ceil((float)count / Integer.parseInt(PropertiesUtil.getPropertyValue("lucene.pagesize")));
		
		// 获取URL
		StringBuffer sb = new StringBuffer("/Novel/static/search.html?keyWords=");
		sb.append(URLEncoder.encode(keyWords,"utf-8"));
		
		if(tid_str != null && !tid_str.equals("")) {
			sb.append("&tid=" + tid_str);
		}
		if(aid_str != null && !aid_str.equals("")) {
			sb.append("&aid=" + aid_str);
		}
		if(sid_str != null && !sid_str.equals("")) {
			sb.append("&sid=" + sid_str);
		}
		
		sb.append("&searchType=");
		sb.append(searchType);
		
		sb.append("&pageNow=");
		
		PagerVo pv = new PagerVo(pageCount, Integer.parseInt(pageNow), sb.toString());
		
		List<Type> nav = novelService.getTypes();
		mav.addObject("nav", nav);
		
		mav.addObject("keyWords", keyWords);
		mav.addObject("count", count);
		mav.addObject("novels", list);
		mav.addObject("pv", pv);
		mav.addObject("pageNow", pageNow);
		mav.addObject("CREATE_HTML", false);
		return mav;
	}
	
}
