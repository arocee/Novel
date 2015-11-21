package com.novel.controller.admin;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.novel.core.interceptor.AuthPassport;
import com.novel.model.admin.User;
import com.novel.service.admin.UserService;

@Controller
@RequestMapping(value="/admin/myinfo")
public class MyInfoAdminController {
	
	@Resource(name="userService")
	private UserService userService;

	@AuthPassport
	@RequestMapping(value="")
	public ModelAndView basicData(Integer page, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView("admin/main");

		mav.addObject("page", page);
		mav.addObject("pageName", "个人信息");
		return mav;
	}
	
	@AuthPassport
	@RequestMapping(value="/uploadphoto/easy", method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> uploadphoto(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		
		User user = (User)request.getSession().getAttribute("user");
		
		String left = request.getParameter("left");
		String top = request.getParameter("top");
		String areaWidth = request.getParameter("areaWidth");
		String areaHeight = request.getParameter("areaHeight");
		String width = request.getParameter("width");
		String height = request.getParameter("height");

		// 单文件可以省略 @RequestParam 多文件则不可省略
		// 转型为MultipartHttpRequest：     
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		// 获得文件：     
		MultipartFile file = multipartRequest.getFile("tou");
		
		String fileName = file.getOriginalFilename();
		String suffix = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
		
		if(!suffix.toLowerCase().matches("^(png|jpg|jpeg|bmp|gif|png)$")) {
			modelMap.put("success", false);
			modelMap.put("msg", "图片格式错误！");
		} else {
			User u = userService.uploadPhoto(file.getInputStream(), Float.parseFloat(left), Float.parseFloat(top), 
					Float.parseFloat(areaWidth), Float.parseFloat(areaHeight), Float.parseFloat(width),Float.parseFloat(height), suffix.toLowerCase(), user);
			
			if(u != null) {
				request.getSession().setAttribute("user", u);
				modelMap.put("success", true);
				modelMap.put("src", user.getImgurl());
			} else {
				modelMap.put("success", false);
				modelMap.put("msg", "头像上传失败！");
			}
		}
		
		return modelMap;
	}
}
