package com.goods.manager.controller;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.goods.manager.pojo.TbLogistics;
import com.goods.manager.service.LogisticsService;
import com.goods.tools.commom.pojo.Data;
import com.goods.tools.commom.pojo.GoodsListItem;
import com.goods.tools.commom.pojo.LogisticsJsonDetail;
import com.goods.tools.common.util.TaotaoResult;

@Controller
public class LogisticsController {
	@Autowired
	private LogisticsService logisticsService;

	// 物流
	@RequestMapping(value = "/send/goods", method = RequestMethod.POST)
	@ResponseBody
	public TaotaoResult sendgoods(TbLogistics tbLogistics, HttpServletRequest request) {
		TaotaoResult taotaoResult = logisticsService.sendgoods(tbLogistics, request);
		return taotaoResult;
	}

	// 跳转发货页面发货
	@RequestMapping("/send")
	public String send(String orderId, String address, String userId, Model model) {
		// 解决get参数乱码
		try {
			address = new String(address.getBytes("iso-8859-1"), "utf-8");
			model.addAttribute("orderId", orderId);
			model.addAttribute("address", address);
			return "sendgoods";
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return "error";
	}

	// 查看相关的物流信息
	@RequestMapping("/send/logistics")
	public String showLogistics(@RequestParam(value = "page", defaultValue = "1") Long page,
			@RequestParam(value = "rows", defaultValue = "15") Long rows, HttpServletRequest request, Model model) {
		GoodsListItem goodsListItem = logisticsService.showLogistics(page, rows, request);
		if (goodsListItem != null) {
			model.addAttribute("list", goodsListItem.getRows());
		}
		return "showLogistics";
	}

	// 查看详细物流
	@RequestMapping("/show/detail/logistics")
	public String showDetail(String logisticsId, Model model) {
		LogisticsJsonDetail logisticsJsonDetail = logisticsService.showDetail(logisticsId);
		if (logisticsJsonDetail != null && logisticsJsonDetail.getMessage().equals("ok")) {
			model.addAttribute("list", logisticsJsonDetail.getData());
		} else {
			model.addAttribute("list", new ArrayList<Data>());
		}
		return "showlogisticsdetail";
	}
}
