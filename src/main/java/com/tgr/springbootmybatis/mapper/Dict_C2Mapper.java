package com.tgr.springbootmybatis.mapper;

import com.tgr.springbootmybatis.entity.Dict_C2;

public interface Dict_C2Mapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Dict_C2 record);

    int insertSelective(Dict_C2 record);

    Dict_C2 selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Dict_C2 record);

    int updateByPrimaryKey(Dict_C2 record);
}