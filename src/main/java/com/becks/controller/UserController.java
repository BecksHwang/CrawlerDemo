package com.becks.controller;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.becks.entity.User;
import com.becks.service.UserService;

/**
 * 创建时间：
 * 
 * @author 
 * @version 2.2 描述： 用户Controller
 */
@Controller
@RequestMapping("/user")
public class UserController {

	private static final Logger LOGGER = Logger.getLogger(UserController.class);

	@Autowired
	private UserService userService;

	@RequestMapping("/showInfo/{userId}")
	public String showUserInfo(ModelMap modelMap, @PathVariable String userId) {
		LOGGER.info("查询用户：" + userId);
		User userInfo = userService.load(userId);
		modelMap.addAttribute("userInfo", userInfo);
		return "/user/showInfo";
	}

	@RequestMapping("/showInfos")
	public @ResponseBody List<User> showUserInfos() {
		LOGGER.info("查询用户全部用户");
		List<User> userInfos = userService.findAll();
		return userInfos;
	}
}
