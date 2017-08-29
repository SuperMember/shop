package com.goods.search.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.goods.search.service.ItemService;
import com.goods.tools.common.util.TaotaoResult;

@Controller
public class ItemController {

	@Autowired
	private ItemService itemService;

	@RequestMapping("/add/item")
	@ResponseBody
	public TaotaoResult addItem() {
		TaotaoResult taotaoResult = itemService.importAllItems();
		return taotaoResult;
	}
	
	@RequestMapping("/up/item")
	@ResponseBody
	public TaotaoResult upItem(long itemId) {
		TaotaoResult taotaoResult = itemService.addItem(itemId);
		return taotaoResult;
	}
	
}
