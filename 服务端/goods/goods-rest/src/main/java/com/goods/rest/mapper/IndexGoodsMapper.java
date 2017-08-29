package com.goods.rest.mapper;

import java.util.List;

import com.goods.manager.pojo.TbItem;
import com.goods.rest.pojo.AdItem;

public interface IndexGoodsMapper {
	List<AdItem> getAds();
	
	List<TbItem> getGoods(Long cid);
	
	List<TbItem> getOther();
	
	List<TbItem> getHomeTool();
}
