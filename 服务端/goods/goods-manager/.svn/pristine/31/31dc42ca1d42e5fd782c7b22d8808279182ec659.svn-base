package com.goods.manager.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.goods.manager.pojo.TbUser;
import com.goods.manager.service.UserService;
import com.goods.manager.service.impl.UserServiceImpl;
import com.goods.tools.common.util.CookieUtils;

public class LoginInterceptor implements HandlerInterceptor {


	@Autowired
	private UserService userService;

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
	
		String token = CookieUtils.getCookieValue(request, "TT_TOKEN");
		// 从token中获取用户信息
		TbUser user = userService.getUserByToken(token);
		
		if (null == user) {
			//没有进行登录或过期
			response.sendRedirect("http://localhost:8080/login.html");
			
			return false;
		}
		// 传递用户信息
		request.setAttribute("user", user);
	
		return true;
	}

	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {


	}

	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
	
	}

}
