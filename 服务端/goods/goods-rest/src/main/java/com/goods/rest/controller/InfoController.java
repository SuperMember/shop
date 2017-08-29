package com.goods.rest.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.goods.rest.service.InFoService;
import com.goods.tools.common.util.TaotaoResult;

@Controller
public class InfoController {
	@Autowired
	private InFoService inFoService;

	// 修改头像
	@RequestMapping("/edit/info")
	@ResponseBody
	public Map edit(String description, MultipartFile multipartFile) {
		Map editPor = inFoService.editPor(description, multipartFile);
		return editPor;
	}
}
