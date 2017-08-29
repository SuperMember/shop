package com.goods.manager.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.goods.manager.pojo.IndexDataEntity;
import com.goods.manager.service.IndexService;
import com.goods.tools.commom.pojo.OrderEntity;
import com.goods.tools.common.util.TaotaoResult;

@Controller
public class IndexController {

	@Autowired
	private IndexService indexService;

	@RequestMapping("/index/showdatas")
	public String showdates(HttpServletRequest request, Model model) {
		IndexDataEntity indexDataEntity = indexService.showDatas(request);
		if (indexDataEntity != null) {
			model.addAttribute("data", indexDataEntity);
			model.addAttribute("commentList", indexDataEntity.getList());// 评论表
			model.addAttribute("goodrank", indexDataEntity.getRanks());// 畅销榜
			return "index_v1";
		}
		model.addAttribute("message", "暂无数据，请稍后重试");
		return "error";
	}

	// 订单情况
	@RequestMapping("/index/order")
	@ResponseBody
	public OrderEntity getOrderData(String current, HttpServletRequest request) {
		OrderEntity entity = indexService.showOrderDatas(request, current);
		return entity;
	}

}
