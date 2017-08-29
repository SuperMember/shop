package com.goods.manager.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.goods.manager.pojo.TbLogistics;
import com.goods.manager.pojo.TbUser;
import com.goods.manager.service.LogisticsService;
import com.goods.tools.commom.pojo.GoodsListItem;
import com.goods.tools.commom.pojo.LogisticsJsonDetail;
import com.goods.tools.common.util.HttpClientUtil;
import com.goods.tools.common.util.JsonUtils;
import com.goods.tools.common.util.TaotaoResult;

@Service
public class LogisticsServiceimpl implements LogisticsService {
	@Value("${LOGISTICS_BASE_URL}")
	private String LOGISTICS_BASE_URL;
	@Value("${LOGISTICS_SEND_URL}")
	private String LOGISTICS_SEND_URL;
	@Value("${LOGISTICS_SHOW_URL}")
	private String LOGISTICS_SHOW_URL;
	@Value("${LOGISTICS_SHOW_DETAIL_URL}")
	private String LOGISTICS_SHOW_DETAIL_URL;

	// 物流
	public TaotaoResult sendgoods(TbLogistics tbLogistics, HttpServletRequest request) {
		try {
			// 获取卖家id
			TbUser tbUser = (TbUser) request.getAttribute("user");
			Long userId = tbUser.getId();
			tbLogistics.setMuserid(userId);// 设置卖家id
			// 通过物流服务存储物流信息
			String result = HttpClientUtil.doPostJson(LOGISTICS_BASE_URL + LOGISTICS_SEND_URL,
					JsonUtils.objectToJson(tbLogistics));
			if (result != null) {
				TaotaoResult taotaoResult = TaotaoResult.format(result);
				if (taotaoResult.getStatus() == 200) {
					return taotaoResult;
				}
			}
			return null;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return TaotaoResult.build(400, "出错，请稍后重试");
	}

	// 展示与该卖家有关的物流信息
	public GoodsListItem showLogistics(long page, long rows, HttpServletRequest request) {
		try {
			// 获取卖家id
			TbUser tbUser = (TbUser) request.getAttribute("user");
			Long userId = tbUser.getId();
			// 调用物流服务获取物流信息
			Map<String, String> param = new HashMap<String, String>();
			param.put("page", page + "");
			param.put("rows", rows + "");
			param.put("userId", userId + "");
			String result = HttpClientUtil.doGet(LOGISTICS_BASE_URL + LOGISTICS_SHOW_URL, param);
			if (result != null) {
				GoodsListItem goodsListItem = JsonUtils.jsonToPojo(result, GoodsListItem.class);
				if (goodsListItem.getTotal() != 0) {
					return goodsListItem;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	// 显示物流的详细情况
	public LogisticsJsonDetail showDetail(String logisticsId) {
		try {
			// 调用物流服务
			Map<String, String> param = new HashMap<String, String>();
			param.put("logisticsId", logisticsId);
			String result = HttpClientUtil.doGet(LOGISTICS_BASE_URL + LOGISTICS_SHOW_DETAIL_URL, param);
			if (result != null) {
				LogisticsJsonDetail logisticsJsonDetail = JsonUtils.jsonToPojo(result, LogisticsJsonDetail.class);
				return logisticsJsonDetail;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
