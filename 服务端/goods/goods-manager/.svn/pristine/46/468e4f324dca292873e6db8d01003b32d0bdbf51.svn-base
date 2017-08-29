package com.goods.manager.mapper;

import com.goods.manager.pojo.TbDatas;
import com.goods.manager.pojo.TbDatasExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TbDatasMapper {
    int countByExample(TbDatasExample example);

    int deleteByExample(TbDatasExample example);

    int deleteByPrimaryKey(Long id);

    int insert(TbDatas record);

    int insertSelective(TbDatas record);

    List<TbDatas> selectByExample(TbDatasExample example);

    TbDatas selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") TbDatas record, @Param("example") TbDatasExample example);

    int updateByExample(@Param("record") TbDatas record, @Param("example") TbDatasExample example);

    int updateByPrimaryKeySelective(TbDatas record);

    int updateByPrimaryKey(TbDatas record);
}