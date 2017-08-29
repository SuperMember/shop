package com.goods.manager.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;


import com.goods.manager.pojo.TbItem;
import com.goods.tools.commom.pojo.GoodsListItem;
import com.goods.tools.common.util.TaotaoResult;

//商品
public interface GoodsItemService {
	GoodsListItem getGoodsItemList(HttpServletRequest request,long page,long rows,Integer status);
	TaotaoResult addGoods(HttpServletRequest request,TbItem tbItem,String desc);
	TaotaoResult editGoods(TbItem tbItem);
	TbItem getTbItem(long itemId);
	TaotaoResult deleteGoods(String[] itemIds);


}
