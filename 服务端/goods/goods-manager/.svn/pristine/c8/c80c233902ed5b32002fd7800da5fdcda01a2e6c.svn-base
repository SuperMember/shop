package com.goods.manager.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.goods.manager.pojo.CommentsResult;
import com.goods.manager.pojo.TbCommentsReply;
import com.goods.manager.service.CommentsService;
import com.goods.tools.commom.pojo.GoodsListItem;
import com.goods.tools.common.util.JsonUtils;
import com.goods.tools.common.util.TaotaoResult;

//评论
@Controller
@RequestMapping("/page")
public class CommentController {
	@Autowired
	private CommentsService commentsService;

	// 评论
	@RequestMapping("/show/comments")
	public String showcomments(@RequestParam(value = "page", defaultValue = "1") Long page,
			@RequestParam(value = "rows", defaultValue = "10") Long rows, HttpServletRequest request, Model model) {
		try {
			TaotaoResult taotaoResult = commentsService.getCommentsEntity(page, rows, request);
			String json = (String) taotaoResult.getData();
			List<CommentsResult> list = JsonUtils.jsonToList(json, CommentsResult.class);
			model.addAttribute("list", list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "showcomments";
	}

	// 创建评论
	@RequestMapping("/create/comments")
	public String createComments(TbCommentsReply tbCommentsReply, HttpServletRequest request) {
		TaotaoResult taotaoResult = commentsService.createComments(tbCommentsReply, request);
		if(taotaoResult.getStatus()==200){
			return "redirect:/page/show/comments";
		}
		return "error";
	}

	// 删除评论
	@RequestMapping("/delete/comments")
	public String deleteComments(long id) {
		TaotaoResult taotaoResult = commentsService.deleteComments(id);
		if(taotaoResult.getStatus()==200){
			return "redirect:/page/show/comments";			
		}
		return "error";
	}
}
