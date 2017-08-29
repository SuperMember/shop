package com.goods.rest.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.goods.manager.mapper.TbAppMapper;
import com.goods.manager.pojo.TbApp;
import com.goods.rest.service.AppService;
import com.goods.tools.common.util.TaotaoResult;

@Service
public class AppServiceImpl implements AppService {

	@Autowired
	private TbAppMapper tbAppMapper;

	// 查询app版本信息
	public TaotaoResult checkApp(String appName, String version) {
		try {
			TbApp app = tbAppMapper.selectByPrimaryKey(appName);
			if (app != null && !app.getAppLVersion().equals(version)) {
				return TaotaoResult.ok(app);// 返回app最新更新版本
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return TaotaoResult.build(400, "没有版本更新");
	}

}
