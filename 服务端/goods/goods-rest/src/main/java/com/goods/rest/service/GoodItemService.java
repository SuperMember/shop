package com.goods.rest.service;

import com.goods.rest.pojo.ItemDetail;
import com.goods.tools.common.util.TaotaoResult;

public interface GoodItemService {
	public ItemDetail getDetail(long itemId);// 获取商品详情

	public TaotaoResult getClassify(long parentId);// 获取分类信息

	public TaotaoResult getGoods(long parentId, long page, long rows, Long cid);// 根据类别获取商品

	public TaotaoResult getIndex();// 首页

	public TaotaoResult getIndexOther(int page);// 首页中加载更多
}
