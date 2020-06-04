package com.tgr.springbootmybatis.mapper;

import com.tgr.springbootmybatis.entity.Dict_C1;

public interface Dict_C1Mapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Dict_C1 record);

    int insertSelective(Dict_C1 record);

    Dict_C1 selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Dict_C1 record);

    int updateByPrimaryKey(Dict_C1 record);
}