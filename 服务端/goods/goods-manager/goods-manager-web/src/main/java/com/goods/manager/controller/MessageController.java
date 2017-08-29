package com.goods.manager.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MessageController {

	@RequestMapping(value = "/admin/push/{path}", produces = "text/event-stream")
	@ResponseBody
	public String message(@PathVariable("path") String path) {
		
		return "dd";
	}
}
