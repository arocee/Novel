package com.novel.controller.admin;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.novel.core.interceptor.AuthPassport;
import com.novel.model.admin.Search;
import com.novel.service.admin.DataService;
import com.novel.util.DateHandle;
import com.novel.vo.NovelCountVo;
import com.novel.vo.PvCountVo;
import com.novel.vo.PvDataVo;

@Controller
@RequestMapping(value="/admin/data")
public class DataAdminController {
	
	@Resource(name="dataService")
	private DataService dataService;
	
	@AuthPassport
	@RequestMapping(value="")
	public ModelAndView basicData(Integer page, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView("admin/main");
		
		NovelCountVo novelCountVo = dataService.getNovelCount();  // type, article, section, paragraph, search
		PvCountVo pvCountVo = dataService.getPvCount();  // ftPv, bcPv, user
		
		// 查询最近7天
		Date startDate = DateHandle.getTheDate(7);
		Date endDate = DateHandle.getTodayZoreTime();
		
		List<Date> dates = DateHandle.getBetweenDay(startDate, endDate);
		List<String> days = DateHandle.getFomatedDays(dates);

		mav.addObject("page", page);
		mav.addObject("pageName", "基本信息");
		mav.addObject("novelCountVo", novelCountVo);
		mav.addObject("pvCountVo", pvCountVo);
		mav.addObject("days", days);
		
		return mav;
	}
	
	@AuthPassport
	@RequestMapping(value="/searchdiagram/easy", produces="application/json", method=RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> searchdata(HttpServletRequest request, HttpServletResponse response, String date) throws Exception {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		
		List<Search> searches = null;
		
		if("全部".equals(date))
			searches = dataService.getSearchAllDay(); // search
		else {
			List<Date> dateRange = DateHandle.getDateRange(date);
			searches = dataService.getSearchByLastSevenDay(dateRange.get(0), dateRange.get(1)); // search
		}
		
		modelMap.put("success", true);
		modelMap.put("date", date);
		modelMap.put("searches", searches);
		
		return modelMap;
	}
	
	@AuthPassport
	@RequestMapping(value="/diagram", produces="application/json", method=RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> pvdata(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		
		// 查询最近7天
		Date startDate = DateHandle.getTheDate(7);
		Date endDate = DateHandle.getTodayZoreTime();
		
		List<Date> dates = DateHandle.getBetweenDay(startDate, endDate);
		List<String> days = DateHandle.getFomatedDays(dates);
		
		String date = days.get(days.size() - 1);
		
		List<PvDataVo> pvDataVoes = dataService.getPvByLastSevenDay(startDate, endDate);  // PV 
		
		List<Date> dateRange = DateHandle.getDateRange(date);
		List<Search> searches = dataService.getSearchByLastSevenDay(dateRange.get(0), dateRange.get(1)); // search
		
		modelMap.put("success", true);
		modelMap.put("days", days);
		modelMap.put("date", date);
		modelMap.put("pvDataVoes", pvDataVoes);
		modelMap.put("searches", searches);
		
		return modelMap;
	}
}
