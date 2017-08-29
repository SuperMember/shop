package com.goods.order.service;

import java.util.List;

import com.goods.manager.pojo.TbOrder;
import com.goods.manager.pojo.TbOrderFinish;
import com.goods.manager.pojo.TbOrderItem;
import com.goods.manager.pojo.TbOrderShipping;
import com.goods.order.pojo.OrderItem;
import com.goods.tools.commom.pojo.GoodsListItem;
import com.goods.tools.common.util.TaotaoResult;

public interface OrderService {
	TaotaoResult showorder(long itemId,long page,long rows, Integer type);//显示未处理或交易完成
	TaotaoResult createorder(TbOrder tbOrder, TbOrderItem tbOrderItem, TbOrderShipping tbOrderShipping);
	TaotaoResult editorder(TbOrder tbOrder);
	TaotaoResult deleteorder(long itemId);
	TbOrder getOrder(long itemId);
	TaotaoResult showgoodssold(long itemId);
	TaotaoResult showUserOrder(long itemId, long page, long rows, Integer type);//显示买家的订单情况
}
