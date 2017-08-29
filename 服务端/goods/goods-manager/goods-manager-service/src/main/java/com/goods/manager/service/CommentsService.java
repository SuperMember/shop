package com.goods.manager.service;

import javax.servlet.http.HttpServletRequest;

import com.goods.manager.pojo.TbCommentsReply;
import com.goods.tools.common.util.TaotaoResult;

public interface CommentsService {
	TaotaoResult getCommentsEntity(long page, long rows, HttpServletRequest request);

	TaotaoResult createComments(TbCommentsReply tbCommentsReply, HttpServletRequest request);
	TaotaoResult deleteComments(long id);
}
