package com.goods.manager.mapper;

import com.goods.manager.pojo.TbLogistics;
import com.goods.manager.pojo.TbLogisticsExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TbLogisticsMapper {
    int countByExample(TbLogisticsExample example);

    int deleteByExample(TbLogisticsExample example);

    int deleteByPrimaryKey(Long id);

    int insert(TbLogistics record);

    int insertSelective(TbLogistics record);

    List<TbLogistics> selectByExample(TbLogisticsExample example);

    TbLogistics selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") TbLogistics record, @Param("example") TbLogisticsExample example);

    int updateByExample(@Param("record") TbLogistics record, @Param("example") TbLogisticsExample example);

    int updateByPrimaryKeySelective(TbLogistics record);

    int updateByPrimaryKey(TbLogistics record);
}