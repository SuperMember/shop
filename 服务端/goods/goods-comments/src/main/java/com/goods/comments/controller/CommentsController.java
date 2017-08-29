package com.goods.comments.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.goods.comments.service.CommentService;
import com.goods.manager.pojo.TbComments;
import com.goods.manager.pojo.TbCommentsReply;
import com.goods.tools.common.util.TaotaoResult;

@Controller
public class CommentsController {
	@Autowired
	private CommentService commentService;

	// 创建第一条评论
	@RequestMapping(value = "/create/{orderId}", method = RequestMethod.POST)
	@ResponseBody
	public TaotaoResult createComments(@PathVariable("orderId") long orderId, @RequestBody TbComments tbComments) {
		TaotaoResult taotaoResult = commentService.createComments(tbComments, orderId);
		return taotaoResult;
	}

	// 回复评论
	@RequestMapping(value = "/create/reply/{type}", method = RequestMethod.POST)
	@ResponseBody
	public TaotaoResult createReply(@RequestBody TbCommentsReply tbCommentsReply, @PathVariable("type") Integer type) {
		TaotaoResult taotaoResult = commentService.createReply(tbCommentsReply, type);
		return taotaoResult;
	}

	// 展示评论
	@RequestMapping(value = "/show", method = RequestMethod.GET)
	@ResponseBody
	public TaotaoResult showComments(@RequestParam("userId") long userId) {
		TaotaoResult taotaoResult = commentService.getGoodsComments(userId);
		return taotaoResult;
	}

	// 删除评论
	@RequestMapping(value = "/delete/reply", method = RequestMethod.GET)
	@ResponseBody
	public TaotaoResult deleteComments(long id) {
		TaotaoResult taotaoResult = commentService.deleteReply(id);
		return taotaoResult;
	}

	// 根据商品id获取评论
	@RequestMapping(value = "/show/goods/comments", method = RequestMethod.GET)
	@ResponseBody
	public TaotaoResult show(@RequestParam("itemId") long itemId,
			@RequestParam(value = "page", defaultValue = "1") long page,
			@RequestParam(value = "rows", defaultValue = "10") long rows) {
		TaotaoResult taotaoResult = commentService.getComments(itemId, page, rows);
		return taotaoResult;
	}

}
