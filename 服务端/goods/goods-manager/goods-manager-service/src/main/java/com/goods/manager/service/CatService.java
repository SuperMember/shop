package com.goods.manager.service;

import java.util.List;

import com.goods.tools.commom.pojo.EUTreeNode;

//商品类目
public interface CatService {
	List<EUTreeNode> getCatList(long parentId);
}
