package com.goods.manager.mapper;

import com.goods.manager.pojo.TbCommentsReply;
import com.goods.manager.pojo.TbCommentsReplyExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TbCommentsReplyMapper {
    int countByExample(TbCommentsReplyExample example);

    int deleteByExample(TbCommentsReplyExample example);

    int deleteByPrimaryKey(Long id);

    int insert(TbCommentsReply record);

    int insertSelective(TbCommentsReply record);

    List<TbCommentsReply> selectByExample(TbCommentsReplyExample example);

    TbCommentsReply selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") TbCommentsReply record, @Param("example") TbCommentsReplyExample example);

    int updateByExample(@Param("record") TbCommentsReply record, @Param("example") TbCommentsReplyExample example);

    int updateByPrimaryKeySelective(TbCommentsReply record);

    int updateByPrimaryKey(TbCommentsReply record);
}