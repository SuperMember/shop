package com.goods.manager.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.activemq.filter.function.makeListFunction;
import org.apache.taglibs.standard.lang.jstl.test.beans.PublicInterface2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.goods.manager.pojo.TbOrderFinish;
import com.goods.manager.service.OrderService;
import com.goods.tools.commom.pojo.GoodsListItem;
import com.goods.tools.common.util.TaotaoResult;

//订单详情

@Controller
public class OrderController {
	@Autowired
	private OrderService orderService;

	@RequestMapping("/page/order")
	public String showorder(@RequestParam(value = "page", defaultValue = "1") long page,
			@RequestParam(value = "rows", defaultValue = "8") long rows,
			@RequestParam(value = "type", required = false) Integer type, HttpServletRequest request, Model model) {
		GoodsListItem listItem = orderService.showorder(page, rows, request, type);
		if (listItem != null) {
			model.addAttribute("itemList", listItem.getRows());
		}
		model.addAttribute("type", type);
		return "order";
	}

	// 修改订单
	@RequestMapping("/page/eidt/order")
	@ResponseBody
	public TaotaoResult editorder(long itemId, int status) {
		TaotaoResult taotaoResult = orderService.editorder(itemId, status);
		return taotaoResult;
	}

	// 展示已经完成的订单
	@RequestMapping("/page/show/finish")
	public String showfinish(HttpServletRequest request, Model model) {
		TaotaoResult taotaoResult = orderService.showgoodssold(request);

		List<TbOrderFinish> list = (List<TbOrderFinish>) taotaoResult.getData();
		if (list != null && list.size() != 0) {
			model.addAttribute("list", list);
		} else {
			model.addAttribute("list", new ArrayList<TbOrderFinish>());
		}

		return "soldgoods";
	}
}
