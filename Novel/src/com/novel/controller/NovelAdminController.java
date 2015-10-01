package com.novel.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.novel.core.interceptor.AuthPassport;

@Controller
public class NovelAdminController {

	@AuthPassport
	@RequestMapping(value="/admin/main")
	public String admin(Integer page, HttpServletRequest request, HttpServletResponse response) throws Exception {
		if(page == null) {
			page = 0;
		}
		
		switch(page) {
		case 0:
			return "redirect:/admin/data?page=0";  // 数据统计页面
		case 1:
			return "redirect:/admin/log?page=1";  // 查询日志页面
		case 2:
			return "redirect:/admin/edit?page=2";  // 内容编辑页面
		case 3:
			return "redirect:/admin/user?page=3";  // 用户管理
		case 4:
			return "redirect:/admin/myinfo?page=4";  // 个人资料
		default:
			return "redirect:/admin/data?page=0";
		}
	}
}
