package com.goods.manager.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.goods.manager.pojo.TbItem;
import com.goods.manager.service.GoodsItemService;
import com.goods.tools.commom.pojo.GoodsListItem;
import com.goods.tools.common.util.TaotaoResult;

@Controller
public class GoodsController {

	@Autowired
	private GoodsItemService goodsItemService;

	// 获取商品列表
	@RequestMapping("/goods/list")
	@ResponseBody
	public GoodsListItem getGoodsItemList(@RequestParam(value = "page", defaultValue = "1") long page,
			@RequestParam(value = "rows", defaultValue = "15") long rows, HttpServletRequest request,
			@RequestParam(value = "status", required = false) int status) {
		GoodsListItem listItem = goodsItemService.getGoodsItemList(request, page, rows, status);
		return listItem;
	}

	// 增加商品
	@RequestMapping(value = "/goods/add", method = RequestMethod.POST)
	@ResponseBody
	public TaotaoResult addGoods(HttpServletRequest request, TbItem tbItem, String desc) {
		TaotaoResult taotaoResult = goodsItemService.addGoods(request, tbItem, desc);
		return taotaoResult;
	}

	@ModelAttribute
	public void getItem(@RequestParam(value = "itemId", required = false) Long itemId,
			@RequestParam(value = "status", required = false) Integer status,
			@RequestParam(value = "value", required = false) Long value,
			@RequestParam(value = "type", required = false) String type, Model model) {
		if (itemId != null) {
			// 从数据库中获取
			TbItem tbItem = goodsItemService.getTbItem(itemId);
			if (tbItem != null) {
				if (status != null) {
					// 修改状态
					tbItem.setStatus((byte) status.intValue());
				} else if (value != null && type != null) {

					if (type.equals("num")) {
						// 修改库存
						tbItem.setNum((Integer) value.intValue());
					} else {
						// 修改价格
						tbItem.setPrice(value);
					}
				}
				model.addAttribute("tbItem", tbItem);
			}
		}

	}

	// 编辑
	@RequestMapping(value = "/goods/edit", method = RequestMethod.GET)
	@ResponseBody
	public TaotaoResult editGoods(TbItem tbItem) {
		TaotaoResult taotaoResult = goodsItemService.editGoods(tbItem);
		return taotaoResult;
	}

	// 删除
	@RequestMapping("/goods/delete")
	@ResponseBody
	public TaotaoResult deleteGoods(@RequestParam(value = "itemIds") String[] itemIds) {
		TaotaoResult taotaoResult = goodsItemService.deleteGoods(itemIds);
		return taotaoResult;
	}

	
}
