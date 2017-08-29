package com.goods.manager.mapper;

import com.goods.manager.pojo.TbGoodsRank;
import com.goods.manager.pojo.TbGoodsRankExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TbGoodsRankMapper {
    int countByExample(TbGoodsRankExample example);

    int deleteByExample(TbGoodsRankExample example);

    int deleteByPrimaryKey(Long id);

    int insert(TbGoodsRank record);

    int insertSelective(TbGoodsRank record);

    List<TbGoodsRank> selectByExample(TbGoodsRankExample example);

    TbGoodsRank selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") TbGoodsRank record, @Param("example") TbGoodsRankExample example);

    int updateByExample(@Param("record") TbGoodsRank record, @Param("example") TbGoodsRankExample example);

    int updateByPrimaryKeySelective(TbGoodsRank record);

    int updateByPrimaryKey(TbGoodsRank record);
    
    TbGoodsRank getTbGoodsRank(@Param("name")String name,@Param("userId")long userId,@Param("orderId")long orderId);
}