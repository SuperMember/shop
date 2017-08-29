package com.goods.manager.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.goods.manager.mapper.TbOrderFinishMapper;
import com.goods.manager.pojo.TbOrderFinish;
import com.goods.manager.pojo.TbUser;
import com.goods.manager.service.OrderService;
import com.goods.tools.commom.pojo.GoodsListItem;
import com.goods.tools.common.util.HttpClientUtil;
import com.goods.tools.common.util.JsonUtils;
import com.goods.tools.common.util.TaotaoResult;

@Service
public class OrderServiceImpl implements OrderService {

	@Value("${ORDER_BASE_URL}")
	private String ORDER_BASE_URL;
	@Value("${ORDER_URL}")
	private String ORDER_URL;
	@Value("${ORDER_EDIT_URL}")
	private String ORDER_EDIT_URL;

	@Value("${ORDER_SHOW_SOLD_URL}")
	private String ORDER_SHOW_SOLD_URL;
	@Autowired
	private TbOrderFinishMapper tbOrderFinishMapper;

	public GoodsListItem showorder(long page, long rows, HttpServletRequest request, Integer type) {
		try {
			// 获取cookies中token的用户信息
			// 根据type执行相关的查找条件
			TbUser user = (TbUser) request.getAttribute("user");
			long itemId = user.getId();
			Map<String, String> param = new HashMap<String, String>();
			param.put("page", page + "");
			param.put("rows", rows + "");
			if (type != null)
				param.put("type", type + "");
			String result = HttpClientUtil.doGet(ORDER_BASE_URL + ORDER_URL + itemId, param);
			TaotaoResult taotaoResult = TaotaoResult.formatToPojo(result, GoodsListItem.class);
			if (taotaoResult.getStatus() == 200) {
				GoodsListItem data = (GoodsListItem) taotaoResult.getData();
				return data;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	// 编辑订单
	public TaotaoResult editorder(long itemId, int status) {
		try {
			Map<String, String> param = new HashMap<String, String>();
			param.put("itemId", itemId + "");
			param.put("status", status + "");
			String get = HttpClientUtil.doGet(ORDER_BASE_URL + ORDER_EDIT_URL, param);
			TaotaoResult taotaoResult = TaotaoResult.format(get);
			if (taotaoResult.getStatus() == 200) {
				return taotaoResult;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return TaotaoResult.build(400, "修改失败，请稍后重试");
	}

	// 展示已经完成交易的订单
	public TaotaoResult showgoodssold(HttpServletRequest request) {
		try {
			// 获取用户id
			TbUser tbUser = (TbUser) request.getAttribute("user");
			Long itemId = tbUser.getId();
			// 调用订单服务
			Map<String, String> param = new HashMap<String, String>();
			param.put("itemId", itemId + "");
			String get = HttpClientUtil.doGet(ORDER_BASE_URL + ORDER_SHOW_SOLD_URL, param);
			TaotaoResult taotaoResult = TaotaoResult.format(get);
			if (taotaoResult.getStatus() == 200) {
				String json = (String) taotaoResult.getData();
				List<TbOrderFinish> list = JsonUtils.jsonToList(json, TbOrderFinish.class);
				return TaotaoResult.ok(list);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return TaotaoResult.build(400, "暂无完成订单");
	}

}
