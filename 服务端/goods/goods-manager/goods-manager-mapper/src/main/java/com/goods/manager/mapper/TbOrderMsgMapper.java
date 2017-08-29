package com.goods.manager.mapper;

import com.goods.manager.pojo.TbOrderMsg;
import com.goods.manager.pojo.TbOrderMsgExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TbOrderMsgMapper {
    int countByExample(TbOrderMsgExample example);

    int deleteByExample(TbOrderMsgExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TbOrderMsg record);

    int insertSelective(TbOrderMsg record);

    List<TbOrderMsg> selectByExample(TbOrderMsgExample example);

    TbOrderMsg selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TbOrderMsg record, @Param("example") TbOrderMsgExample example);

    int updateByExample(@Param("record") TbOrderMsg record, @Param("example") TbOrderMsgExample example);

    int updateByPrimaryKeySelective(TbOrderMsg record);

    int updateByPrimaryKey(TbOrderMsg record);
}