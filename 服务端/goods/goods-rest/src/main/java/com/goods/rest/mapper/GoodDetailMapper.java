package com.goods.rest.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.goods.manager.pojo.TbItemResult;
import com.goods.rest.pojo.ItemDetail;

public interface GoodDetailMapper {
	public ItemDetail getDetail(long id);
	public List<TbItemResult> getGoods(@Param("parentId")long parentId,@Param("page")long page,@Param("rows")long rows);
	public List<TbItemResult> getGoodsById(@Param("parentId")long parentId,@Param("page")long page,@Param("rows")long rows,@Param("cid")Long cid);
}
