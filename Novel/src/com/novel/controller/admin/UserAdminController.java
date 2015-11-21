package com.novel.controller.admin;

import java.net.URLDecoder;
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
import org.springframework.web.servlet.ModelAndView;

import com.novel.core.interceptor.AuthPassport;
import com.novel.core.validate.UserDel;
import com.novel.core.validate.UserPass;
import com.novel.core.validate.UserReg;
import com.novel.core.validate.UserRule;
import com.novel.model.admin.User;
import com.novel.service.admin.UserService;
import com.novel.util.Constants;
import com.novel.util.DateHandle;
import com.novel.util.MD5;
import com.novel.vo.UserRuleVo;

@Controller
@RequestMapping(value="/admin/user")
public class UserAdminController {

	@Resource(name="userService")
	private UserService userService;
	
	@AuthPassport
	@RequestMapping(value="")
	public ModelAndView basicData(Integer page, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView("admin/main");
		
		List<UserRuleVo> userRuleVoes = userService.queryUserByRule();
		
		int total = 0;  // 总用户数
		
		for (UserRuleVo userRuleVo : userRuleVoes) {
			total += userRuleVo.getUsers().size();
		}

		mav.addObject("page", page);
		mav.addObject("pageName", "用户管理");
		mav.addObject("userRuleVoes", userRuleVoes);
		mav.addObject("total", total);
		return mav;
	}
	
	@AuthPassport
	@RequestMapping(value="/reg/easy", produces="application/json", method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> reg(@Validated(value={UserReg.class}) User user, BindingResult bindingResult, 
			HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception {
		User admin = (User) session.getAttribute("user");
		
		Map<String, Object> modelMap = this.validate(bindingResult, admin);
		
		if(modelMap.size() == 0){
			user.setRegtime(new Date());
			user.setImgurl(Constants.defaultTou);
			user.setPassword(MD5.md5(Constants.defaultPass));
			Integer line = userService.addUser(user);
			
			if(line > 0) {
				modelMap.put("id", user.getId());
				modelMap.put("imgurl", user.getImgurl());
				modelMap.put("regtime", DateHandle.getFormatedDate(user.getRegtime(), null));
				modelMap.put("success", true);
			} else {
				modelMap.put("success", false);
				modelMap.put("msg", "添加用户失败");
			}
		}
		
		return modelMap;
	}
	
	@AuthPassport
	@RequestMapping(value="/reset/easy", produces="application/json", method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> reset(@Validated(value={UserPass.class}) User user, BindingResult bindingResult,
			HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception {
		User admin = (User) session.getAttribute("user");
		
		Map<String, Object> modelMap = this.validate(bindingResult, admin);
		
		if(modelMap.size() == 0){
			user.setPassword(MD5.md5(Constants.defaultPass));
			Integer line = userService.updateUser(user, user.getId());
			
			if(line > 0) {
				modelMap.put("success", true);
			} else {
				modelMap.put("success", false);
				modelMap.put("msg", "重置密码失败");
			}
		}
		
		return modelMap;
	}
	
	@AuthPassport
	@RequestMapping(value="/move/easy", produces="application/json", method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> move(@Validated(value={UserRule.class}) User user, BindingResult bindingResult,
			HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception {
		User admin = (User) session.getAttribute("user");
		
		Map<String, Object> modelMap = this.validate(bindingResult, admin);
		
		if(modelMap.size() == 0){
			Integer line = userService.updateUser(user, user.getId());
			
			if(line > 0) {
				modelMap.put("success", true);
			} else {
				modelMap.put("success", false);
				modelMap.put("msg", "移动分组失败");
			}
		}
		
		return modelMap;
	}
	
	@AuthPassport
	@RequestMapping(value="/del/easy", produces="application/json", method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> del(@Validated(value={UserDel.class}) User user, BindingResult bindingResult,
			HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception {
		User admin = (User) session.getAttribute("user");
		
		Map<String, Object> modelMap = this.validate(bindingResult, admin);
		
		if(modelMap.size() == 0){
			Integer line = userService.deleteUserByPrimaryKey(user.getId());
			
			if(line > 0) {
				modelMap.put("success", true);
			} else {
				modelMap.put("success", false);
				modelMap.put("msg", "删除用户失败");
			}
		}
		
		return modelMap;
	}
	
	/**
	 * 权限验证和表单验证
	 * @param bindingResult
	 * @param admin
	 * @return
	 * @throws Exception
	 */
	private Map<String, Object> validate(BindingResult bindingResult, User admin) throws Exception {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		
		if(admin.getRule() != Constants.adminRule && admin.getRule() != Constants.adduserRule){
			modelMap.put("success", false);
			modelMap.put("msg", "没有权限");
		} else if(bindingResult.hasErrors()) {
			StringBuilder sb = new StringBuilder();
		
			// 获取错误验证信息
			List<ObjectError> allErrors = bindingResult.getAllErrors();
			
			for (ObjectError objectError : allErrors) {
				sb.append(URLDecoder.decode(objectError.getDefaultMessage(), "utf-8"));
			}
			
			modelMap.put("success", false);
			modelMap.put("msg", sb.toString());
		}
		
		return modelMap;
	}
}
