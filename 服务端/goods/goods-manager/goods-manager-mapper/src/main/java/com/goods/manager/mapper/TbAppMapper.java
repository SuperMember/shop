package com.goods.manager.mapper;

import com.goods.manager.pojo.TbApp;
import com.goods.manager.pojo.TbAppExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TbAppMapper {
    int countByExample(TbAppExample example);

    int deleteByExample(TbAppExample example);

    int deleteByPrimaryKey(String appname);

    int insert(TbApp record);

    int insertSelective(TbApp record);

    List<TbApp> selectByExample(TbAppExample example);

    TbApp selectByPrimaryKey(String appname);

    int updateByExampleSelective(@Param("record") TbApp record, @Param("example") TbAppExample example);

    int updateByExample(@Param("record") TbApp record, @Param("example") TbAppExample example);

    int updateByPrimaryKeySelective(TbApp record);

    int updateByPrimaryKey(TbApp record);
}