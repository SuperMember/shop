package com.goods.manager.mapper;

import com.goods.manager.pojo.Muser;
import com.goods.manager.pojo.MuserExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface MuserMapper {
    int countByExample(MuserExample example);

    int deleteByExample(MuserExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Muser record);

    int insertSelective(Muser record);

    List<Muser> selectByExample(MuserExample example);

    Muser selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Muser record, @Param("example") MuserExample example);

    int updateByExample(@Param("record") Muser record, @Param("example") MuserExample example);

    int updateByPrimaryKeySelective(Muser record);

    int updateByPrimaryKey(Muser record);
}