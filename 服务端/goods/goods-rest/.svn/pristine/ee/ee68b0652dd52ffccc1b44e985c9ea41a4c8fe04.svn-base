package com.goods.rest.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.goods.manager.mapper.TbCommentsMsgMapper;
import com.goods.manager.mapper.TbOrderMsgMapper;
import com.goods.manager.pojo.TbCommentsMsg;
import com.goods.manager.pojo.TbCommentsMsgExample;
import com.goods.manager.pojo.TbOrderMsg;
import com.goods.manager.pojo.TbOrderMsgExample;
import com.goods.manager.pojo.TbOrderMsgExample.Criteria;
import com.goods.rest.service.MessageService;
import com.goods.tools.commom.pojo.GoodsListItem;
import com.goods.tools.common.util.JsonUtils;
import com.goods.tools.common.util.TaotaoResult;

@Service
public class MessageServiceImpl implements MessageService {
	@Autowired
	TbOrderMsgMapper tbOrderMsgMapper;
	@Autowired
	TbCommentsMsgMapper tbCommentsMsgMapper;

	// 获取评论的消息或订单的消息
	public TaotaoResult getMessage(long userId, Integer page, int type) {
		try {
			switch (type) {
			case 0:
				// 订单
				GoodsListItem goodsListItem = new GoodsListItem();
				TbOrderMsgExample tbOrderMsgExample = new TbOrderMsgExample();
				tbOrderMsgExample.setOrderByClause("time desc");
				Criteria criteria = tbOrderMsgExample.createCriteria();
				PageHelper.startPage(page, 10);
				criteria.andUseridEqualTo(userId);
				List<TbOrderMsg> list = tbOrderMsgMapper.selectByExample(tbOrderMsgExample);
				if (list != null && list.size() != 0) {
					goodsListItem.setRows(list);
					String json = JsonUtils.objectToJson(goodsListItem);
					return TaotaoResult.ok(json);
				}
				break;
			case 1:
				// 评论
				GoodsListItem commentListItem = new GoodsListItem();
				TbCommentsMsgExample tbCommentsMsgExample = new TbCommentsMsgExample();
				com.goods.manager.pojo.TbCommentsMsgExample.Criteria commentcriteria = tbCommentsMsgExample
						.createCriteria();
				PageHelper.startPage(page, 10);
				commentcriteria.andUseridEqualTo(userId);
				List<TbCommentsMsg> comments = tbCommentsMsgMapper.selectByExample(tbCommentsMsgExample);
				if (comments != null && comments.size() != 0) {
					commentListItem.setRows(comments);
					String json = JsonUtils.objectToJson(commentListItem);
					return TaotaoResult.ok(json);
				}
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return TaotaoResult.build(400, "获取失败，请重试");
	}

}
