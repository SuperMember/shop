package com.goods.rest.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

import com.goods.manager.pojo.TbUser;
import com.goods.tools.common.util.TaotaoResult;

public interface FileService {
	Map uploadFile(MultipartFile uploadFile);
	Map uploadFile(MultipartFile file,String appName);
}
