package com.goods.manager.mapper;

import com.goods.manager.pojo.TbAddr;
import com.goods.manager.pojo.TbAddrExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TbAddrMapper {
    int countByExample(TbAddrExample example);

    int deleteByExample(TbAddrExample example);

    int deleteByPrimaryKey(Long addrid);

    int insert(TbAddr record);

    int insertSelective(TbAddr record);

    List<TbAddr> selectByExample(TbAddrExample example);

    TbAddr selectByPrimaryKey(Long addrid);

    int updateByExampleSelective(@Param("record") TbAddr record, @Param("example") TbAddrExample example);

    int updateByExample(@Param("record") TbAddr record, @Param("example") TbAddrExample example);

    int updateByPrimaryKeySelective(TbAddr record);

    int updateByPrimaryKey(TbAddr record);
}