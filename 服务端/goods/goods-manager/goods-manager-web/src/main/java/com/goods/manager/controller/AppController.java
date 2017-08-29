package com.goods.manager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.goods.manager.pojo.TbApp;
import com.goods.manager.service.AppService;
import com.goods.tools.common.util.TaotaoResult;

@Controller
public class AppController {
	@Autowired
	private AppService appService;

	@RequestMapping("/update/app")
	@ResponseBody
	public TaotaoResult updateApp(TbApp tbApp, MultipartFile file) {
		TaotaoResult taotaoResult = appService.updateApp(tbApp,file);
		return taotaoResult;
	}

	@RequestMapping("/check/app")
	@ResponseBody
	public TaotaoResult checkApp() {
		TaotaoResult taotaoResult = appService.checkApp();
		return taotaoResult;
	}
}
