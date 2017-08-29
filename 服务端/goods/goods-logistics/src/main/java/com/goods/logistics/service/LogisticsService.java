package com.goods.logistics.service;

import com.goods.logistics.pojo.LogisticsJsonDetail;
import com.goods.manager.pojo.TbLogistics;
import com.goods.tools.commom.pojo.GoodsListItem;
import com.goods.tools.common.util.TaotaoResult;

public interface LogisticsService {
	String getLocatioin(String type, Integer id);

	TaotaoResult sendgoods(TbLogistics tbLogistics);

	GoodsListItem showLogistics(long page, long rows, long userId);// 显示卖家的物流

	LogisticsJsonDetail showDetail(String logisticsId);
}
