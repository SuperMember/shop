package com.goods.manager.mapper;

import com.goods.manager.pojo.TbCommentsMsg;
import com.goods.manager.pojo.TbCommentsMsgExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TbCommentsMsgMapper {
    int countByExample(TbCommentsMsgExample example);

    int deleteByExample(TbCommentsMsgExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TbCommentsMsg record);

    int insertSelective(TbCommentsMsg record);

    List<TbCommentsMsg> selectByExample(TbCommentsMsgExample example);

    TbCommentsMsg selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TbCommentsMsg record, @Param("example") TbCommentsMsgExample example);

    int updateByExample(@Param("record") TbCommentsMsg record, @Param("example") TbCommentsMsgExample example);

    int updateByPrimaryKeySelective(TbCommentsMsg record);

    int updateByPrimaryKey(TbCommentsMsg record);
}