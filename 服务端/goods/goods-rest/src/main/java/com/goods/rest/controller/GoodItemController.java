package com.goods.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.goods.rest.pojo.ItemDetail;
import com.goods.rest.service.GoodItemService;
import com.goods.tools.common.util.TaotaoResult;

@Controller
public class GoodItemController {
	@Autowired
	private GoodItemService goodItemService;

	// 商品详情页
	@RequestMapping("/item/detail")
	@ResponseBody
	public ItemDetail getGood(long itemId) {
		ItemDetail detail = goodItemService.getDetail(itemId);
		return detail;
	}

	// 获取分类信息
	@RequestMapping("/item/classify")
	@ResponseBody
	public TaotaoResult getClassify(long parentId) {
		TaotaoResult taotaoResult = goodItemService.getClassify(parentId);
		return taotaoResult;
	}

	// 根据商品的类别获取商品
	@RequestMapping(value = "/item/classify/goods")
	@ResponseBody
	public TaotaoResult getGoods(long parentId, @RequestParam(value = "page", defaultValue = "1") long page,
			@RequestParam(value = "rows", defaultValue = "15") long rows,
			@RequestParam(value = "cid", required = false) Long cid) {
		TaotaoResult taotaoResult = goodItemService.getGoods(parentId, page, rows, cid);
		return taotaoResult;
	}

	// 首页
	@RequestMapping(value = "/item/index")
	@ResponseBody
	public TaotaoResult getIndex() {
		TaotaoResult taotaoResult = goodItemService.getIndex();
		return taotaoResult;
	}

	// 首页加载更多
	@RequestMapping(value = "/item/index/more")
	@ResponseBody
	public TaotaoResult getIndexMore(Integer page) {
		TaotaoResult taotaoResult = goodItemService.getIndexOther(page);
		return taotaoResult;
	}
}
