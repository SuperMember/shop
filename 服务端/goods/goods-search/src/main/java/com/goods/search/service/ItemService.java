package com.goods.search.service;

import com.goods.search.pojo.Item;
import com.goods.tools.common.util.TaotaoResult;

public interface ItemService {

	TaotaoResult importAllItems();
	TaotaoResult addItem(long itemId);//上架
	TaotaoResult removeItem(long itemId);//下架
}
