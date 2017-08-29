package com.goods.manager.controller;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.goods.manager.service.CatService;
import com.goods.tools.commom.pojo.EUTreeNode;

@Controller
public class CatController {

	@Autowired
	private CatService catService;

	//获取商品的类目
	@RequestMapping("/item/cat/list")
	@ResponseBody
	private List<EUTreeNode> getCatList(@RequestParam(value = "id", defaultValue = "0") Long parentId) {
		List<EUTreeNode> list = catService.getCatList(parentId);
		return list;
	}
}
