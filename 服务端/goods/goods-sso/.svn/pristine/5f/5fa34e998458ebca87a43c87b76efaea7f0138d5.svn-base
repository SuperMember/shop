package com.goods.sso.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.goods.manager.pojo.TbAddr;
import com.goods.manager.pojo.TbUser;
import com.goods.sso.service.UserService;
import com.goods.tools.common.util.TaotaoResult;

@Controller
public class UserController {
	@Autowired
	private UserService userService;

	// ��¼
	@RequestMapping(value = "/login/", method = RequestMethod.POST)
	@ResponseBody
	public TaotaoResult login(String username, String password, HttpServletRequest request,
			HttpServletResponse response, @RequestParam(value = "user", required = false) Integer user) {
		TaotaoResult taotaoResult = userService.login(username, password, request, response, user);
		return taotaoResult;
	}

	// ע��ʱ�������ֶ��Ƿ����
	@RequestMapping("/check/{type}")
	@ResponseBody
	public TaotaoResult checkdatas(@PathVariable("type") int type, String datas) {
		TaotaoResult taotaoResult = userService.checkDatas(datas, type);
		return taotaoResult;
	}

	// ע��
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	@ResponseBody
	public TaotaoResult register(TbUser tbUser) {
		TaotaoResult taotaoResult = userService.register(tbUser);
		return taotaoResult;
	}

	// 修改邮编地址
	@RequestMapping(value = "/edit/addr", method = RequestMethod.POST)
	@ResponseBody
	public TaotaoResult editAddr(TbAddr tbAddr) {
		TaotaoResult taotaoResult = userService.editAddr(tbAddr);
		return taotaoResult;
	}

	// 删除
	@RequestMapping(value = "/delete/addr", method = RequestMethod.GET)
	@ResponseBody
	public TaotaoResult deleteAddr(Long id) {
		TaotaoResult taotaoResult = userService.deleteAddr(id);
		return taotaoResult;
	}

	// 增加
	@RequestMapping(value = "/add/addr", method = RequestMethod.POST)
	@ResponseBody
	public TaotaoResult addAddr(TbAddr tbAddr) {
		TaotaoResult taotaoResult = userService.addAddr(tbAddr);
		return taotaoResult;
	}
}
