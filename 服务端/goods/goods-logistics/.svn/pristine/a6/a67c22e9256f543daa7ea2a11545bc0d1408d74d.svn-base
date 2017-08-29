package com.goods.logistics.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.goods.logistics.pojo.LogisticsJsonDetail;
import com.goods.logistics.service.LogisticsService;
import com.goods.manager.pojo.TbLogistics;
import com.goods.tools.commom.pojo.GoodsListItem;
import com.goods.tools.common.util.TaotaoResult;

@Controller
public class LogisticsController {
	@Autowired
	private LogisticsService logisticsService;

	// 城市联动
	@RequestMapping("/location")
	@ResponseBody
	public Object getLocation(@RequestParam(value = "callback", required = false) String callback,
			@RequestParam("type") String type, @RequestParam(value = "id", required = false) Integer id) {
		String catResult = logisticsService.getLocatioin(type, id);
		if (callback != null) {

			MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(TaotaoResult.ok(catResult));
			mappingJacksonValue.setJsonpFunction(callback);
			return mappingJacksonValue;
		}
		return TaotaoResult.ok(catResult);
	}

	// 发货
	@RequestMapping(value = "/send/goods", method = RequestMethod.POST)
	@ResponseBody
	public TaotaoResult sendgoods(@RequestBody TbLogistics tbLogistics) {
		TaotaoResult taotaoResult = logisticsService.sendgoods(tbLogistics);
		return taotaoResult;
	}

	// 显示与该卖家有关的物流情况
	@RequestMapping(value = "/show/logistics", method = RequestMethod.GET)
	@ResponseBody
	public GoodsListItem showlogistics(@RequestParam(value = "page", defaultValue = "1") Long page,
			@RequestParam(value = "rows", defaultValue = "15") Long rows, long userId) {
		GoodsListItem goodsListItem = logisticsService.showLogistics(page, rows, userId);
		if (goodsListItem != null) {
			return goodsListItem;
		}
		return new GoodsListItem();
	}

	// 查找物流
	// 返回json格式
	@RequestMapping(value = "/show/logistics/detail", method = RequestMethod.GET)
	@ResponseBody
	public LogisticsJsonDetail showDetail(String logisticsId) {
		LogisticsJsonDetail logisticsJsonDetail = logisticsService.showDetail(logisticsId);
		return logisticsJsonDetail;
	}
}
