package com.goods.manager.service;

import javax.servlet.http.HttpServletRequest;

import com.goods.tools.commom.pojo.GoodsListItem;
import com.goods.tools.common.util.TaotaoResult;

public interface OrderService {
	GoodsListItem showorder(long page, long rows,HttpServletRequest request,Integer type);
	TaotaoResult editorder(long itemId,int status);//编辑
	TaotaoResult showgoodssold(HttpServletRequest request);//展示已经完成的订单(即已经出售的商品)
}
