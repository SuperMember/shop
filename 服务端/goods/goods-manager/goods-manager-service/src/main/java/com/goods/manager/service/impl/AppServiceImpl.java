package com.goods.manager.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.goods.manager.mapper.TbAppMapper;
import com.goods.manager.pojo.TbApp;
import com.goods.manager.pojo.TbAppExample;
import com.goods.manager.service.AppService;
import com.goods.manager.service.FileService;
import com.goods.tools.common.util.TaotaoResult;

@Service
public class AppServiceImpl implements AppService {

	@Autowired
	private TbAppMapper tbAppMapper;

	@Autowired
	private FileService fileService;// 上传

	public TaotaoResult updateApp(TbApp tbApp, MultipartFile file) {
		try {
			Map map = fileService.uploadFile(file, tbApp.getAppname());
			if ((Integer) map.get("error") == 0) {
				String url = (String) map.get("url");// 文件地址
				tbApp.setFile(url);
				tbApp.setUpdatetime(new Date());
				TbApp lApp = tbAppMapper.selectByPrimaryKey(tbApp.getAppname());
				if (lApp == null) {
					// 首次为插入数据
					tbAppMapper.insert(tbApp);
				} else {
					tbAppMapper.updateByPrimaryKey(tbApp);
				}
			}
			return TaotaoResult.ok();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return TaotaoResult.build(400, "暂无版本更新");
	}

	// 检查版本
	public TaotaoResult checkApp() {
		try {
			TbAppExample tbAppExample = new TbAppExample();
			List<TbApp> list = tbAppMapper.selectByExample(tbAppExample);
			if (list != null && list.size() != 0) {
				return TaotaoResult.ok(list.get(0));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return TaotaoResult.build(400, "暂无版本信息");
	}

}
