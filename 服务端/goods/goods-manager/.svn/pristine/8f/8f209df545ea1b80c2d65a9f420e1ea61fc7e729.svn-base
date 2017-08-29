package com.goods.manager.service;

import javax.servlet.http.HttpServletRequest;

import com.goods.manager.pojo.TbLogistics;
import com.goods.tools.commom.pojo.GoodsListItem;
import com.goods.tools.commom.pojo.LogisticsJsonDetail;
import com.goods.tools.common.util.TaotaoResult;

public interface LogisticsService {
	TaotaoResult sendgoods(TbLogistics tbLogistics, HttpServletRequest request);

	GoodsListItem showLogistics(long page, long rows, HttpServletRequest request);

	LogisticsJsonDetail showDetail(String logisticsId);
}
