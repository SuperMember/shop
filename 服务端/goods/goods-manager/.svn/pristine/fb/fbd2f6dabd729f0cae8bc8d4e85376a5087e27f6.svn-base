package com.goods.manager.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.goods.manager.mapper.TbItemCatMapper;
import com.goods.manager.pojo.TbItemCat;
import com.goods.manager.pojo.TbItemCatExample;
import com.goods.manager.pojo.TbItemCatExample.Criteria;
import com.goods.manager.service.CatService;
import com.goods.tools.commom.pojo.EUTreeNode;

@Service
public class CatServiceImpl implements CatService {

	@Autowired
	private TbItemCatMapper itemCatMapper;

	
	public List<EUTreeNode> getCatList(long parentId) {

		// 创建查询条件
		TbItemCatExample example = new TbItemCatExample();
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(parentId);
		// 根据条件查询
		List<TbItemCat> list = itemCatMapper.selectByExample(example);
		List<EUTreeNode> resultList = new ArrayList();
		// 把列表转换成treeNodelist
		for (TbItemCat tbItemCat : list) {
			EUTreeNode node = new EUTreeNode();
			node.setId(tbItemCat.getId());
			node.setText(tbItemCat.getName());
			node.setState(tbItemCat.getIsParent() ? "closed" : "open");
			resultList.add(node);
		}
		// 返回结果
		return resultList;
	}

}
