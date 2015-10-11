package com.novel.controller.admin;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.novel.core.validate.UserLogin;
import com.novel.model.admin.User;
import com.novel.service.admin.UserService;

@Controller
@RequestMapping(value="/admin/member")
public class MemberAdminController {

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
	
	@RequestMapping(value="/logout", method=RequestMethod.GET)
	public String logout(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception {
		session.removeAttribute("user");
		return "redirect:/admin/member";
	}
}
