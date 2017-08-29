package com.goods.search.mapper;

import java.util.List;


import com.goods.search.pojo.Item;



public interface ItemMapper {

	List<Item> getItemList();
	Item selectByPrimaryKey(Long id);
}
