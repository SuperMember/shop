package com.goods.rest.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.goods.manager.mapper.TbUserMapper;
import com.goods.manager.pojo.TbUser;
import com.goods.rest.service.FileService;
import com.goods.rest.service.InFoService;
import com.goods.tools.common.util.HttpClientUtil;
import com.goods.tools.common.util.TaotaoResult;

@Service
public class InFoServiceImpl implements InFoService {

	@Autowired
	private FileService fileService;
	@Autowired
	private TbUserMapper tbUserMapper;

	public Map editPor(String userId, MultipartFile multipartFile) {
		Map map = fileService.uploadFile(multipartFile);
		int result = (Integer) map.get("error");
		if (result == 0) {
			// 修改用户信息
			TbUser tbUser = tbUserMapper.selectByPrimaryKey(Long.parseLong(userId));
			tbUser.setImg((String) map.get("url"));
			tbUserMapper.updateByPrimaryKey(tbUser);
		}
		return map;
	}

}
