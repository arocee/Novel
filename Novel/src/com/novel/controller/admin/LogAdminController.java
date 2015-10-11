package com.novel.controller.admin;

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
import com.novel.model.admin.Log;
import com.novel.service.admin.LogService;

@Controller
@RequestMapping(value="/admin/log")
public class LogAdminController {
	
	@Resource(name="logService")
	private LogService logService;

	@AuthPassport
	@RequestMapping(value="")
	public ModelAndView log(Integer page, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView("admin/main");
		
		List<Log> logs = logService.getLogs();
		
		String content = "<p class='emptyTip'>没有日志</p>";
		
		if(logs.size() > 0){
			content = logService.getLogContent(logs.get(0).getFileNames().get(0));
		}
		
		mav.addObject("logs", logs);
		mav.addObject("page", page);
		mav.addObject("pageName", "日志查看");
		mav.addObject("content", content);
		return mav;
	}
	
	@AuthPassport
	@RequestMapping(value="/getLogContent/easy", method=RequestMethod.GET, produces="application/json")
	@ResponseBody
	public Map<String, Object> getLog(HttpServletRequest request,HttpServletResponse response, String fileName) throws Exception {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		
		String content = logService.getLogContent(fileName);
		
		modelMap.put("success", true);
		modelMap.put("content", content);
		
		return modelMap; 
	}
}
