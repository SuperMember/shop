package com.goods.manager.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.impl.conn.LoggingSessionOutputBuffer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.goods.manager.pojo.TbUser;
import com.goods.manager.service.UserService;
import com.goods.tools.common.util.TaotaoResult;

@Controller

public class UserController {

	@Autowired
	private UserService userService;

	// 显示登录页面
	@RequestMapping("/login.html")
	public String index() {
		return "login";
	}

	// 登出
	@RequestMapping("/logout.html")
	public String logout(HttpServletRequest request, HttpServletResponse response, Model model) {
		TaotaoResult taotaoResult = userService.logout(request, response);
		if (taotaoResult.getStatus() == 200) {
			return "redirect:/login.html";
		}
		model.addAttribute("message", taotaoResult.getMsg());
		return "error";
	}

	// 显示主页
	@RequestMapping(value = "/index.html", method = RequestMethod.POST)
	public String login(String username, String password, Integer type, HttpServletRequest request,
			HttpServletResponse response, Model model) {
		TaotaoResult result = userService.login(username, password, type, request, response);
		if (result.getStatus() == 200) {
			return "index";
		}
		model.addAttribute("msg", result.getMsg());
		return "login";
	}

	// 从cookies中的token换取用户信息
	@RequestMapping(value = "/token/{token}", method = RequestMethod.GET)
	@ResponseBody
	public TbUser getUserKey(HttpServletRequest request, @PathVariable("token") String token) {
		TbUser result = userService.getUserByToken(token);
		return result;
	}
}
