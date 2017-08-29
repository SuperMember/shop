package com.goods.rest.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.goods.manager.mapper.TbUserMapper;
import com.goods.manager.pojo.TbUser;
import com.goods.rest.service.FileService;
import com.goods.tools.common.util.FtpUtil;
import com.goods.tools.common.util.IDUtils;
import com.goods.tools.common.util.TaotaoResult;

@Service
public class FileServiceImpl implements FileService {

	// ftp地址
	@Value("${FTP_ADDRESS}")
	private String FTP_ADDRESS;
	// ftp端口
	@Value("${FTP_PORT}")
	private Integer FTP_PORT;
	// ftp用户名
	@Value("${FTP_USERNAME}")
	private String FTP_USERNAME;
	// ftp密码
	@Value("${FTP_PASSWORD}")
	private String FTP_PASSWORD;
	// frp主地址
	@Value("${FTP_BASE_PATH}")
	private String FTP_BASE_PATH;
	// 图片地址
	@Value("${IMAGE_BASE_URL}")
	private String IMAGE_BASE_URL;
	// 文件地址
	@Value("${FILE_BASE_URL}")
	private String FILE_BASE_URL;

	// 上传图片
	public Map uploadFile(MultipartFile uploadFile) {
		Map resultMap = new HashMap();

		try {
			// 原始名称
			String oldName = uploadFile.getOriginalFilename();
			// UUID.randomUUID();
			String newName = IDUtils.genImageName();
			newName = newName + oldName.substring(oldName.lastIndexOf("."));
			// 图片存放地址
			String imagePath = new DateTime().toString("/yyyy/MM/dd");
			boolean result = FtpUtil.uploadFile(FTP_ADDRESS, FTP_PORT, FTP_USERNAME, FTP_PASSWORD, FTP_BASE_PATH,
					imagePath, newName, uploadFile.getInputStream());
			// 返回结果
			if (!result) {
				resultMap.put("error", 1);
				resultMap.put("message", "文件上传失败");
				return resultMap;
			}
			resultMap.put("error", 0);
			resultMap.put("url", IMAGE_BASE_URL + imagePath + "/" + newName);
			return resultMap;

		} catch (Exception e) {
			e.printStackTrace();
			resultMap.put("error", 1);
			resultMap.put("message", "文件上传出错，请稍后重试");
			return resultMap;
		}
	}

	// 上传app
	public Map uploadFile(MultipartFile uploadFile, String appName) {
		Map resultMap = new HashMap();
		try {
			// 原始名称
			String oldName = uploadFile.getOriginalFilename();
			boolean result = FtpUtil.uploadFile(FTP_ADDRESS, FTP_PORT, FTP_USERNAME, FTP_PASSWORD, FTP_BASE_PATH,
					"/apk", oldName, uploadFile.getInputStream());
			// 返回信息
			if (!result) {
				resultMap.put("error", 1);
				resultMap.put("message", "文件上传失败");
				return resultMap;
			}
			resultMap.put("error", 0);
			resultMap.put("url", FILE_BASE_URL + "/apk" + oldName);
			return resultMap;

		} catch (Exception e) {
			e.printStackTrace();
			resultMap.put("error", 1);
			resultMap.put("message", "文件上传出错，请稍后重试");
			return resultMap;
		}
	}

}
