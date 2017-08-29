package com.goods.manager.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.goods.manager.mapper.TbUserMapper;
import com.goods.manager.pojo.TbUser;
import com.goods.manager.service.FileService;
import com.goods.tools.common.util.TaotaoResult;

@Controller
public class PictureController {

	@Autowired
	private FileService fileService;
	@Autowired
	private TbUserMapper tbUserMapper;

	// 上传文件
	@RequestMapping(value = "/file/upload", method = RequestMethod.POST)
	@ResponseBody
	public Map uploadFile(MultipartFile uploadFile) {

		Map upload = fileService.uploadFile(uploadFile);
		return upload;

	}

	

}
