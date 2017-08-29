package com.goods.manager.mapper;

import com.goods.manager.pojo.TbComments;
import com.goods.manager.pojo.TbCommentsExample;
import com.goods.manager.pojo.TbCommentsResult;

import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TbCommentsMapper {
    int countByExample(TbCommentsExample example);

    int deleteByExample(TbCommentsExample example);

    int deleteByPrimaryKey(Long id);

    int insert(TbComments record);

    int insertSelective(TbComments record);

    List<TbCommentsResult> selectByExample(TbCommentsExample example);

    TbComments selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") TbComments record, @Param("example") TbCommentsExample example);

    int updateByExample(@Param("record") TbComments record, @Param("example") TbCommentsExample example);

    int updateByPrimaryKeySelective(TbComments record);

    int updateByPrimaryKey(TbComments record);
}