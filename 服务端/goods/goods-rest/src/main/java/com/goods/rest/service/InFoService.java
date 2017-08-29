package com.goods.rest.service;

import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.goods.tools.common.util.TaotaoResult;

public interface InFoService {
	Map editPor(String userId,MultipartFile multipartFile);
}
