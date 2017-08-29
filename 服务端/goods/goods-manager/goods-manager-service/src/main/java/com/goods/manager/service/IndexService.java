package com.goods.manager.service;

import javax.servlet.http.HttpServletRequest;

import com.goods.manager.pojo.IndexDataEntity;
import com.goods.tools.commom.pojo.OrderEntity;
import com.goods.tools.common.util.TaotaoResult;

public interface IndexService {
	IndexDataEntity showDatas(HttpServletRequest request);

	OrderEntity showOrderDatas(HttpServletRequest request, String current);
}
