package com.novel.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.novel.core.interceptor.AuthPassport;
import com.novel.core.validate.UserLogin;
import com.novel.core.validate.UserReg;
import com.novel.model.User;
import com.novel.service.UserService;

@Controller
@RequestMapping(value="/admin/member")
public class UserAdminController {

	@Resource(name="userService")
	private UserService userService;

	@RequestMapping(value="")
	public String member(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return "admin/login";
	}
	
	@RequestMapping(value="/login", method=RequestMethod.POST)
	public String login(@Validated(value={UserLogin.class}) User user, BindingResult bindingResult, 
			HttpServletRequest request, HttpServletResponse response, HttpSession session, RedirectAttributes redirectAttributes) throws Exception {
		// 如果已经登录
		if(session.getAttribute("user") != null) {
			return "redirect:/admin/main";
		}
		
		// 获取校验的错误信息
		if(bindingResult.hasErrors()) {
			redirectAttributes.addFlashAttribute("errorTip", "用户名或密码错误！");
			return "redirect:/admin/member";
		}
		
		User u = userService.queryUser(user);
		if(u != null) {
			session.setAttribute("user", u);
			return "redirect:/admin/main";
		}
		redirectAttributes.addFlashAttribute("errorTip", "用户名或密码错误！");
		return "redirect:/admin/member";
	}
	
	@AuthPassport
	@RequestMapping(value="/reg/easy", produces="application/json")
	@ResponseBody
	public Map<String, Object> reg(@Validated(value={UserReg.class}) User user, BindingResult bindingResult, 
			HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		
		User admin = (User) session.getAttribute("user");
		
		if(admin.getRule() != 1){  // TODO 查找权限
			// 没有权限
			modelMap.put("success", false);
			modelMap.put("msg", "没有权限");
		} else if(bindingResult.hasErrors()) {
			StringBuilder sb = new StringBuilder();
		
			// 获取校验的错误信息
			List<ObjectError> allErrors = bindingResult.getAllErrors();
			
			for (ObjectError objectError : allErrors) {
				sb.append("<p>");
				sb.append(objectError.getDefaultMessage());
				sb.append("</p>");
			}
			
			modelMap.put("success", false);
			modelMap.put("msg", sb.toString());
		} else {
			User u = userService.queryUser(user);
			
			if(u != null) {
				modelMap.put("success", false);
				modelMap.put("msg", "用户名已存在");
			}
			
			user.setRegtime(new Date());
			Integer line = userService.addUser(user);
			
			if(line > 0) {
				modelMap.put("success", true);
			} else {
				modelMap.put("success", false);
				modelMap.put("msg", "注册不成功！");
			}
		}
		
		return modelMap;
	}
	
	@RequestMapping(value="/logout", method=RequestMethod.GET)
	public String logout(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception {
		session.removeAttribute("user");
		return "redirect:/admin/member";
	}
}
