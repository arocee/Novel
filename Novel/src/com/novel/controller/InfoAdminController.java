package com.novel.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;

import com.novel.service.DataService;

@Controller
public class InfoAdminController {
	
	@Resource(name="dataService")
	private DataService dataService;
	
}
