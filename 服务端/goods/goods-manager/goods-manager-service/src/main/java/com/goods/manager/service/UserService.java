package com.goods.manager.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.goods.manager.pojo.TbUser;
import com.goods.tools.common.util.TaotaoResult;

//用户管理
public interface UserService {
	TaotaoResult login(String username, String password, Integer type, HttpServletRequest request,
			HttpServletResponse response);

	TbUser getUserByToken(String token);// 通过cookies中的token值去redis换取卖家信息

	TaotaoResult logout(HttpServletRequest request, HttpServletResponse response);// 登出
}
