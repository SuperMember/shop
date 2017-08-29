package com.goods.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.goods.rest.service.MessageService;
import com.goods.tools.common.util.TaotaoResult;

@Controller
public class MessageController {
	@Autowired
	MessageService messageService;

	@RequestMapping("/message")
	@ResponseBody
	public TaotaoResult getMessage(long userId, @RequestParam(value = "page", defaultValue = "1") Integer page,
			int type) {
		TaotaoResult taotaoResult = messageService.getMessage(userId, page, type);
		return taotaoResult;
	}
}
